package ru.yandex.money.gradle.plugins.library

import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.testfixtures.ProjectBuilder
import ru.yandex.money.gradle.plugins.library.changelog.CheckChangelogPlugin
import ru.yandex.money.gradle.plugins.library.dependencies.CheckDependenciesPlugin
import ru.yandex.money.gradle.plugins.library.utils.Shell
import ru.yandex.money.gradle.plugins.library.readme.PublishReadmeTask

/**
 *
 * @author Kirill Bulatov (mail4score@gmail.com)
 * @since 22.12.2016
 */
class LibraryPluginSpec extends AbstractPluginSpec {

    def "check that all custom tasks exist"() {
        def expectedTasks = [PublishReadmeTask.TASK_NAME, CheckChangelogPlugin.CHECK_CHANGELOG_TASK_NAME,
                             CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME]

        when:
        def result = runTasksSuccessfully("tasks")

        then:
        expectedTasks.forEach({
            assert result.standardOutput.contains(it)
        })
    }

    def 'check that when current branch is master then snapshot repositories are not defined'() {
        given:
        def project = createProjectWithDependencyManagement()

        when:
        project.apply plugin: 'yamoney-library-project-plugin'

        then:
        getMavenRepositoryUrls(project).every({ !it.contains("snapshot") })
    }

    def 'check that when current branch is dev then snapshot repositories are not defined'() {
        given:
        checkoutNewBranch('dev')
        def project = createProjectWithDependencyManagement()

        when:
        project.apply plugin: 'yamoney-library-project-plugin'

        then:
        getMavenRepositoryUrls(project).every({ !it.contains("snapshot") })
    }

    def 'check that when current branch is release then snapshot repositories are not defined'() {
        given:
        checkoutNewBranch('release/foo-1.1.1')
        def project = createProjectWithDependencyManagement()

        when:
        project.apply plugin: 'yamoney-library-project-plugin'

        then:
        getMavenRepositoryUrls(project).every({ !it.contains("snapshot") })
    }

    def 'check that when current branch is tags-named-branch than snapshot repositories are not defined'() {
        given:
        checkoutNewBranch('tags/1.1.1')
        def project = createProjectWithDependencyManagement()

        when:
        project.apply plugin: 'yamoney-library-project-plugin'

        then:
        getMavenRepositoryUrls(project).every({ !it.contains("snapshot") })
    }

    def 'check that when there is release tag on current branch than snapshot repositories are not defined'() {
        given:
        createTag('1.1.1')
        def project = createProjectWithDependencyManagement()

        when:
        project.apply plugin: 'yamoney-library-project-plugin'

        then:
        getMavenRepositoryUrls(project).every({ !it.contains("snapshot") })
    }

    def 'check that when HEAD refers to release-branch than snapshot repositories are not defined'() {
        given:
        checkoutNewBranch('release/some')
        checkoutNewBranch('other')
        setRefToBranch('release/some')

        def project = createProjectWithDependencyManagement()

        when:
        project.apply plugin: 'yamoney-library-project-plugin'

        then:
        getMavenRepositoryUrls(project).every({ !it.contains("snapshot") })
    }

    def 'check that when current branch is non-release-related than snapshot repositories are defined'() {
        given:
        checkoutNewBranch('feature/SOME-0000')
        def project = createProjectWithDependencyManagement()

        when:
        project.apply plugin: 'yamoney-library-project-plugin'

        then:
        getMavenRepositoryUrls(project).any({ it.contains("snapshot") })
    }

    def 'check that when HEAD refers to non-release-branch than snapshot repositories are defined'() {
        given:
        checkoutNewBranch('feature/SOME-0000')
        checkoutNewBranch('other')
        setRefToBranch('feature/SOME-0000')

        def project = createProjectWithDependencyManagement()

        when:
        project.apply plugin: 'yamoney-library-project-plugin'

        then:
        getMavenRepositoryUrls(project).any({ it.contains("snapshot") })
    }

    def checkoutNewBranch(String branchName) {
        grgit.checkout(branch: branchName, createBranch: true)
    }

    def setRefToBranch(String branchName) {
        Shell.execute(projectDir, "git symbolic-ref HEAD refs/heads/${branchName}".split(" "))
    }

    def createTag(String tagName) {
        grgit.tag.add(name: tagName)
    }

    def createProjectWithDependencyManagement() {
        def project = ProjectBuilder.builder().withProjectDir(projectDir).build()

        project.buildscript {
            repositories {
                maven { url 'http://nexus.yamoney.ru/content/repositories/releases/' }
                maven { url 'http://nexus.yamoney.ru/content/repositories/jcenter.bintray.com/' }
                maven { url 'http://nexus.yamoney.ru/content/repositories/central/' }
            }
            dependencies {
                classpath 'org.ajoberstar:gradle-git:1.5.0'
                classpath 'ru.yandex.money.common:yamoney-doc-publishing:1.0.1'
                classpath 'io.spring.gradle:dependency-management-plugin:0.6.1.RELEASE'
            }
        }

        project.apply plugin: 'java'
        project.apply plugin: 'io.spring.dependency-management'

        project
    }

    def getMavenRepositoryUrls(Project project) {
        project.repositories.findAll { it instanceof MavenArtifactRepository}
                            .collect { ((MavenArtifactRepository)it).url.toString() }
    }
}
