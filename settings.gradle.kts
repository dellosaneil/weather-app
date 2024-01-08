pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Weather"
include(":app")
include(":data")
include(":domain")
include(":feature")
include(":feature:forecast")
include(":feature:archive")
include(":feature:current")
include(":common")
include(":feature:maps")
