package dev.msfjarvis.claw.android.ui.navigation

sealed class Destinations(internal val route: String) {
  object Hottest : Destinations("hottest") {
    fun getRoute() = route
  }

  object Newest : Destinations("newest") {
    fun getRoute() = route
  }

  object Saved : Destinations("saved") {
    fun getRoute() = route
  }

  object Comments : Destinations("comments/%s") {
    fun getRoute(postId: String) = route.format(postId)
  }

  companion object {
    val startDestination
      get() = Hottest
  }
}
