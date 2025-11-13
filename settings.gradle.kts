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

rootProject.name = "Filmy-Android-MVI"
include(":app")
include(":core-tracking")
include(":tracking-processor")
include(":tracking-annotation")
include(":tracking")
