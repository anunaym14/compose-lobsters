package dev.msfjarvis.claw.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.slack.eithernet.ApiResult.Failure
import com.slack.eithernet.ApiResult.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.msfjarvis.claw.android.injection.IODispatcher
import dev.msfjarvis.claw.android.paging.LobstersPagingSource
import dev.msfjarvis.claw.android.ui.toLocalDateTime
import dev.msfjarvis.claw.api.LobstersApi
import dev.msfjarvis.claw.database.local.SavedPost
import java.io.IOException
import java.time.Month
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ClawViewModel
@Inject
constructor(
  private val api: LobstersApi,
  private val savedPostsRepository: SavedPostsRepository,
  private val linkMetadataRepository: LinkMetadataRepository,
  private val pagingSourceFactory: LobstersPagingSource.Factory,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
  private var hottestPostsPagingSource: LobstersPagingSource? = null
  private var newestPostsPagingSource: LobstersPagingSource? = null
  private val hottestPostsPager =
    Pager(PagingConfig(20)) {
      pagingSourceFactory.create(api::getHottestPosts).also { hottestPostsPagingSource = it }
    }
  private val newestPostsPager =
    Pager(PagingConfig(20)) {
      pagingSourceFactory.create(api::getHottestPosts).also { newestPostsPagingSource = it }
    }

  val hottestPosts
    get() = hottestPostsPager.flow

  val newestPosts
    get() = newestPostsPager.flow

  private val savedPostsFlow
    get() = savedPostsRepository.savedPosts

  val savedPosts
    get() = savedPostsFlow.map(::mapSavedPosts)

  private fun mapSavedPosts(items: List<SavedPost>): Map<Month, List<SavedPost>> {
    val sorted = items.sortedByDescending { post -> post.createdAt.toLocalDateTime() }
    return sorted.groupBy { post -> post.createdAt.toLocalDateTime().month }
  }

  suspend fun isPostSaved(post: SavedPost): Boolean {
    return savedPostsFlow.first().any { savedPost -> savedPost.shortId == post.shortId }
  }

  fun toggleSave(post: SavedPost) {
    viewModelScope.launch(ioDispatcher) {
      val saved = isPostSaved(post)
      if (saved) {
        savedPostsRepository.removePost(post)
      } else {
        savedPostsRepository.savePost(post)
      }
    }
  }

  suspend fun getPostComments(postId: String) =
    withContext(ioDispatcher) {
      when (val result = api.getPostDetails(postId)) {
        is Success -> result.value
        is Failure.NetworkFailure -> throw result.error
        is Failure.UnknownFailure -> throw result.error
        is Failure.HttpFailure,
        is Failure.ApiFailure -> throw IOException("API returned an invalid response")
      }
    }

  suspend fun getLinkMetadata(url: String) =
    withContext(ioDispatcher) { linkMetadataRepository.getLinkMetadata(url) }

  suspend fun getUserProfile(username: String) =
    withContext(ioDispatcher) {
      when (val result = api.getUser(username)) {
        is Success -> result.value
        is Failure.NetworkFailure -> throw result.error
        is Failure.UnknownFailure -> throw result.error
        is Failure.HttpFailure,
        is Failure.ApiFailure -> throw IOException("API returned an invalid response")
      }
    }

  fun refreshHottestPosts() {
    hottestPostsPagingSource?.invalidate()
  }

  fun refreshNewestPosts() {
    newestPostsPagingSource?.invalidate()
  }
}
