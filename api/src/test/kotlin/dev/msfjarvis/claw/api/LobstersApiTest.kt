package dev.msfjarvis.claw.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.msfjarvis.claw.util.TestUtils
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.RecordedRequest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.junit.AfterClass
import org.junit.BeforeClass
import retrofit2.Retrofit
import retrofit2.create

@OptIn(ExperimentalSerializationApi::class)
class LobstersApiTest {

  companion object {
    private val contentType = "application/json".toMediaType()
    private val webServer = MockWebServer()
    private val okHttp = OkHttpClient.Builder().build()
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit =
      Retrofit.Builder()
        .client(okHttp)
        .baseUrl("http://localhost:8080/")
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
    private val apiClient = retrofit.create<LobstersApi>()

    @JvmStatic
    @BeforeClass
    fun setUp() {
      webServer.start(8080)
      webServer.dispatcher =
        object : Dispatcher() {
          override fun dispatch(request: RecordedRequest): MockResponse {
            val path = requireNotNull(request.path)
            return when {
              path.startsWith("/hottest") ->
                MockResponse().setBody(TestUtils.getJson("hottest.json")).setResponseCode(200)
              path.startsWith("/s/") ->
                MockResponse()
                  .setBody(TestUtils.getJson("post_details_d9ucpe.json"))
                  .setResponseCode(200)
              else -> fail("'$path' unexpected")
            }
          }
        }
    }

    @JvmStatic
    @AfterClass
    fun tearDown() {
      webServer.shutdown()
    }
  }

  @Test
  fun `api gets correct number of items`() = runBlocking {
    val posts = apiClient.getHottestPosts(1)
    assertEquals(25, posts.size)
  }

  @Test
  fun `posts with no urls`() = runBlocking {
    val posts = apiClient.getHottestPosts(1)
    val commentsOnlyPosts = posts.asSequence().filter { it.url.isEmpty() }.toSet()
    assertEquals(2, commentsOnlyPosts.size)
  }

  @Test
  fun `post details with comments`() = runBlocking {
    val postDetails = apiClient.getPostDetails("d9ucpe")
    assertEquals(7, postDetails.comments.size)
  }
}
