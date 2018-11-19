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
    apply from: 'tmp/gradle-scripts/_root.gradle'
    ext.checkstyleEnabled = false
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
