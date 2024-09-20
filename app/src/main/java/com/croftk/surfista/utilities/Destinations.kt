package com.croftk.surfista.utilities

interface Destinations {
	val route: String
}

object DashboardScreen : Destinations {
	override val route: String
		get() = "Dashboard"
}
object SettingsScreen : Destinations {
	override val route: String
		get() = "Settings"
}
object QuiverScreen : Destinations {
	override val route: String
		get() = "Quiver"
}
object SearchScreen : Destinations {
	override val route: String
		get() = "Search"
}
object SplashScreen : Destinations {
	override val route: String
		get() = "Splash"
}
object LoginScreen : Destinations {
	override val route: String
		get() = "Login"
}