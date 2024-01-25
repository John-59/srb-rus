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
include(":feature:addword")
include(":core:repository")
include(":core:dictionary")
include(":feature:repository")
include(":feature:dictionary")
include(":feature:home")
include(":core:design")
include(":core:ui")
include(":feature:search")
include(":feature:learn")
include(":feature:mocks")
include(":feature:editword")
include(":core:utils")
