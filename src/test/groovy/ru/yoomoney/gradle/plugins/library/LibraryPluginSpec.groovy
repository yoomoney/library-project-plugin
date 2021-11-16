package ru.yoomoney.gradle.plugins.library

import ru.yoomoney.gradle.plugins.library.dependencies.CheckDependenciesPlugin

import java.nio.file.Paths

/**
 * @author Oleg Kandaurov
 * @since 15.11.2018
 */
class LibraryPluginSpec extends AbstractPluginSpec {

    def "should contains tasks"() {
        def expectedTasks = [CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME,
                             "preRelease", "release", "checkChangelog"]

        when:
        def result = runTasksSuccessfully("tasks")

        then:
        expectedTasks.forEach({
            assert result.standardOutput.contains(it)
        })
    }

    def "should complete simple java build"() {
        when:
        writeHelloWorld("ru.yoomoney.common")
        then:
        runTasksSuccessfully("clean", "build")
    }

    def "should run java test"() {

        given: "Java тест в resources/src/test/java"
        directory("src/test/java")
        copyResources("classes", "src/test/java")

        when:
        def result = runTasks("clean", "build")

        then: "Запускаются тесты на Java"

        assert result.success
        assert result.standardOutput.contains("run java test...")

    }

    def "should run kotlin test"() {

        given: "Kotlin тест в resources/src/test/java"
        directory("src/test/kotlin")
        copyResources("classes", "src/test/kotlin")

        when:
        def result = runTasks("clean", "build")

        then: "Запускаются тесты на Kotlin"
        assert result.success
        assert result.standardOutput.contains("run kotlin test...")
    }

    def "should publish to maven local"() {
        given: "Hello world app"
        writeHelloWorld("ru.yoomoney.common")
        when: "Run publish task"
        def result = runTasksSuccessfully("clean", "jar", "publishToMavenLocal")
        then: "Artifacts published"
        assert result.success
        def appName = "should-publish-to-maven-local-1.0.0-SNAPSHOT"
        assert Paths.get(projectDir.absolutePath, "target", "libs", "${appName}.jar").toFile().exists()
        assert Paths.get(projectDir.absolutePath, "target", "libs", "${appName}-javadoc.jar").toFile().exists()
        assert Paths.get(projectDir.absolutePath, "target", "libs", "${appName}-sources.jar").toFile().exists()
        def pomFile = Paths.get(projectDir.absolutePath, "target", "publications", "mainArtifact", "pom-default.xml").toFile()
        assert pomFile.exists()
    }

    def "release task should depend on predefined release tasks"() {
        given:
        buildFile << """
            def printTaskDependencies(task, level) {
                task.taskDependencies.getDependencies(task).forEach {
                    println(":\${it.name}".padLeft(level * 4))
                    printTaskDependencies(it, level + 1)
                }
            }
            
            task printReleaseTaskDependencies {
                doLast { printTaskDependencies(project.tasks.getByName("release"), 0) }
            }
        """

        when:
        def result = runTasksSuccessfully("printReleaseTaskDependencies")

        then:
        assert result.standardOutput.contains("closeAndReleaseMavenStagingRepository")
        assert result.standardOutput.contains("publish")
        assert result.standardOutput.contains("build")
    }
}
