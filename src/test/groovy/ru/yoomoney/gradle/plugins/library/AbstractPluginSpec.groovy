package ru.yoomoney.gradle.plugins.library

import nebula.test.IntegrationSpec
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.transport.URIish

import java.nio.file.Paths

/**
 *
 * @author Kirill Bulatov (mail4score@gmail.com)
 * @since 22.12.2016
 */
abstract class AbstractPluginSpec extends IntegrationSpec {

    private static final String BUILD_FILE_CONTENTS = """
    
    System.setProperty("ignoreDeprecations", "true")
    apply plugin: 'ru.yoomoney.gradle.plugins.library-project-plugin'

    artifactId = 'test-artifact'
    groupIdSuffix = 'test-group'
    ext.checkstyleEnabled = false
    dependencies {
       implementation 'com.google.guava:guava:27.1-jre'
       testImplementation 'junit:junit:4.12'
    }
    """.stripIndent()

    protected Git git

    def setup() {
        git = Git.init().setDirectory(projectDir).setBare(false).call()
        buildFile << BUILD_FILE_CONTENTS
        def gradleProperties = Paths.get(projectDir.absolutePath, 'gradle.properties').toFile()
        gradleProperties.createNewFile()

        gradleProperties << "version=1.0.0-SNAPSHOT\n" +
                "signingPassword=123456\n" +
                "signingKey="

        git.add().addFilepattern('build.gradle').call()
        git.commit().setMessage('build.gradle commit').setAll(true).call()
        git.remoteSetUrl()
                .setRemoteUri(new URIish("file://${projectDir}/"))
                .setRemoteName("origin")
                .call()
    }

    def cleanup() {
        git.close()
    }
}
