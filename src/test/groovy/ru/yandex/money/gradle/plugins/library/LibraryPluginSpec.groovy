package ru.yandex.money.gradle.plugins.library

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
        writeHelloWorld("ru.yandex.money.common")
        then:
        runTasksSuccessfully("clean", "build", "componentTest")
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
        writeHelloWorld("ru.yandex.money.common")
        when: "Run publish task"
        def result = runTasksSuccessfully("clean", "jar", "publishToMavenLocal")
        then: "Artifacts published"
        assert result.success
        def appName = "should-publish-to-maven-local"
        assert Paths.get(projectDir.absolutePath, "target", "libs", "${appName}.jar").toFile().exists()
        assert Paths.get(projectDir.absolutePath, "target", "libs", "${appName}-javadoc.jar").toFile().exists()
        assert Paths.get(projectDir.absolutePath, "target", "libs", "${appName}-sources.jar").toFile().exists()
        def pomFile = Paths.get(projectDir.absolutePath, "target", "publications", "mainArtifact", "pom-default.xml").toFile()
        assert pomFile.exists()
    }
}
