/*
 * Copyright © 2021-2022 Harsh Shandilya.
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package dev.msfjarvis.claw.android.ui.lists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.msfjarvis.claw.common.posts.PostActions
import dev.msfjarvis.claw.common.ui.decorations.MonthHeader
import dev.msfjarvis.claw.database.local.SavedPost
import java.time.Month

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DatabasePosts(
  items: Map<Month, List<SavedPost>>,
  listState: LazyListState,
  postActions: PostActions,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    state = listState,
    modifier = modifier,
  ) {
    items.forEach { (month, posts) ->
      stickyHeader { MonthHeader(month = month) }
      items(posts) { item ->
        ListItem(
          item = item,
          isSaved = { true },
          postActions = postActions,
        )

        Divider()
      }
    }
  }
}
