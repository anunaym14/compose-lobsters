package dev.msfjarvis.lobsters.utils

import androidx.compose.runtime.Composable

private fun stringEnumMapper(stringEnum: Strings): String {
  return when (stringEnum) {
    Strings.AddToSavedPosts -> "Add to saved posts"
    Strings.AppName -> "Claw"
    Strings.AvatarContentDescription -> "%1s's avatar"
    Strings.HottestPosts -> "Hottest"
    Strings.Loading -> "Loading posts…"
    Strings.NoSavedPost -> "You don't have any saved posts"
    Strings.OpenComments -> "Open comments"
    Strings.RefreshPostsContentDescription -> "Refresh posts"
    Strings.RemoveFromSavedPosts -> "Remove from saved posts"
    Strings.SubmittedBy -> "submitted by %1s"
  }
}

@Composable
actual fun Strings.get(): String {
  return stringEnumMapper(this)
}

@Composable
actual fun Strings.get(fmt: Any): String {
  return stringEnumMapper(this).format(fmt)
}
