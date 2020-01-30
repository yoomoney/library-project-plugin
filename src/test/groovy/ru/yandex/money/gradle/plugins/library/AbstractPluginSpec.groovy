package ru.yandex.money.gradle.plugins.library

import nebula.test.IntegrationSpec
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.transport.URIish

/**
 *
 * @author Kirill Bulatov (mail4score@gmail.com)
 * @since 22.12.2016
 */
abstract class AbstractPluginSpec extends IntegrationSpec {

    private static final String BUILD_FILE_CONTENTS = """
    buildscript {    
        repositories {
                maven { url 'https://nexus.yamoney.ru/repository/gradle-plugins/' }
                maven { url 'https://nexus.yamoney.ru/repository/thirdparty/' }
                maven { url 'https://nexus.yamoney.ru/repository/central/' }
                maven { url 'https://nexus.yamoney.ru/repository/releases/' }
                maven { url 'https://nexus.yamoney.ru/repository/jcenter.bintray.com/' }
        }
    }
    
    System.setProperty("ignoreDeprecations", "true")
    apply plugin: 'yamoney-library-project-plugin'
    artifactID = 'test-artifact'
    groupIdSuffix = 'test-group'
    ext.checkstyleEnabled = false
    dependencies {
       compile 'com.google.guava:guava:27.1-jre'
       testImplementation 'junit:junit:4.12'
    }
    """.stripIndent()

    protected Git git

    def setup() {
        git = Git.init().setDirectory(projectDir).setBare(false).call()
        buildFile << BUILD_FILE_CONTENTS
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
