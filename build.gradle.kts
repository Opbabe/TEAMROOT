// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2") // Make sure this is correct

    }
}

plugins {
    alias(libs.plugins.android.application) apply false
}
