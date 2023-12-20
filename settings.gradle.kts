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

rootProject.name = "srbrus"
include(":app")
include(":feature:actions")
include(":feature:addword")
include(":core:repository")
include(":core:dictionary")
include(":feature:repository")
include(":feature:dictionary")
include(":feature:home")
include(":core:design")
include(":core:ui")
include(":feature:search")
