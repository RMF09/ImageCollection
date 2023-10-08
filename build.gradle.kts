buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.hiltAgp)
    }
}

plugins{
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false

}
