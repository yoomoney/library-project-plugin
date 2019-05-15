package ru.yandex.money.gradle.plugins.library

import nebula.test.IntegrationSpec
import org.ajoberstar.grgit.Grgit

/**
 *
 * @author Kirill Bulatov (mail4score@gmail.com)
 * @since 22.12.2016
 */
abstract class AbstractPluginSpec extends IntegrationSpec {

    private static final String BUILD_FILE_CONTENTS = """
    buildscript {    
        System.setProperty("platformDependenciesVersion", "3+")
        System.setProperty("kotlinVersion", "any")
        repositories {
                maven { url 'https://nexus.yamoney.ru/repository/gradle-plugins/' }
                maven { url 'https://nexus.yamoney.ru/repository/thirdparty/' }
                maven { url 'https://nexus.yamoney.ru/repository/central/' }
                maven { url 'https://nexus.yamoney.ru/repository/releases/' }
                maven { url 'https://nexus.yamoney.ru/repository/jcenter.bintray.com/' }
        }
    }
    apply plugin: 'yamoney-library-project-plugin'
    ext.checkstyleEnabled = false
    dependencies {
       compile 'com.google.guava:guava:27.1-jre'
       testCompile 'junit:junit:4.12'
    }
    """.stripIndent()

    protected Grgit grgit

    def setup() {

        grgit = Grgit.init(dir: projectDir.absolutePath)
        buildFile << BUILD_FILE_CONTENTS
        grgit.add(patterns: ['build.gradle'])
        grgit.commit(message: 'build.gradle commit', all: true)
    }

    def cleanup() {
        grgit.close()
    }
}
