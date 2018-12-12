package ru.yandex.money.gradle.plugins.library

import nebula.test.IntegrationSpec
import org.ajoberstar.grgit.Grgit
import org.apache.commons.io.FileUtils

import java.nio.file.Paths

/**
 *
 * @author Kirill Bulatov (mail4score@gmail.com)
 * @since 22.12.2016
 */
abstract class AbstractPluginSpec extends IntegrationSpec {

    private static final String BUILD_FILE_CONTENTS = """
buildscript {
System.setProperty("kotlinVersion", "1.2.71")

repositories {
    maven { url 'https://nexus.yamoney.ru/content/repositories/thirdparty/' }
    maven { url 'https://nexus.yamoney.ru/content/repositories/central/' }
    maven { url 'https://nexus.yamoney.ru/content/repositories/releases/' }
    maven { url 'https://nexus.yamoney.ru/content/repositories/jcenter.bintray.com/' }

    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:\${System.getProperty('kotlinVersion')}"
    }
}
}
    apply from: 'tmp/gradle-scripts/_root.gradle'
    ext.checkstyleEnabled = false
    dependencies {
       testCompile 'junit:junit:4.12'
    }
    """.stripIndent()

    protected Grgit grgit

    def setup() {

        grgit = Grgit.init(dir: projectDir.absolutePath)
        buildFile << BUILD_FILE_CONTENTS
        FileUtils.copyDirectory(Paths.get(System.getProperty("user.dir"), "gradle-scripts").toFile(),
               Paths.get(projectDir.absolutePath, "tmp", "gradle-scripts").toFile())
        grgit.add(patterns: ['build.gradle'])
        grgit.commit(message: 'build.gradle commit', all: true)
    }

    def cleanup() {
        grgit.close()
    }
}
