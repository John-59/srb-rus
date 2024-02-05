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
include(":core:dictionary")
include(":feature:dictionary")
include(":feature:learn")
include(":core:design")
include(":core:ui")
include(":feature:exercise")
include(":feature:editword")
include(":core:utils")
include(":core:translation")
include(":core:repository")
include(":core:mocks")
