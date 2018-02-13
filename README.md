# SpeechRecognizerSphinx

[Building an application with sphinx4
](https://cmusphinx.github.io/wiki/tutorialsphinx4/)

## About 

This project uses Sphinx4, a speech recognition library, which is written entirely in the JavaTM programming language by Carnegie Mellon University(CMU). Sphinx4 provides a quick and easy API to convert the speech recordings into text with the help of CMUSphinx acoustic models.

[https://github.com/cmusphinx/sphinx4
](https://github.com/cmusphinx/sphinx4)

## Setting up 

In gradle you need the following lines in build.gradle:

```
repositories {
    mavenLocal()
    maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
}

dependencies {
    compile group: 'edu.cmu.sphinx', name: 'sphinx4-core', version:'5prealpha-SNAPSHOT'
    compile group: 'edu.cmu.sphinx', name: 'sphinx4-data', version:'5prealpha-SNAPSHOT'
}

```
### Note

Sphinx4 may not be the ideal candidate for the Android platform, as it uses desktop specific java sound api. An alternative would be to create a JavaFX application.


