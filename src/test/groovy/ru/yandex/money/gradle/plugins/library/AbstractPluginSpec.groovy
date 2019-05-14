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
        System.setProperty("platformDependenciesVersion", "3+")
        repositories {
                maven { url 'https://nexus.yamoney.ru/repository/gradle-plugins/' }
                maven { url 'https://nexus.yamoney.ru/repository/thirdparty/' }
                maven { url 'https://nexus.yamoney.ru/repository/central/' }
                maven { url 'https://nexus.yamoney.ru/repository/releases/' }
                maven { url 'https://nexus.yamoney.ru/repository/jcenter.bintray.com/' }
        }
    }
    apply from: 'tmp/gradle-scripts/_root.gradle'
    ext.checkstyleEnabled = false
    dependencies {
       compile 'com.google.guava:guava:27.1-jre'
       testCompile 'junit:junit:4.12'
    }
    tasks.withType(JavaCompile).configureEach {
        options.compilerArgs << '-Xep:InsecureCipherMode:WARN'
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
