package ru.yandex.money.gradle.plugins.library

import ru.yandex.money.gradle.plugins.library.changelog.CheckChangelogPlugin
import ru.yandex.money.gradle.plugins.library.dependencies.CheckDependenciesPlugin

/**
 * @author Oleg Kandaurov
 * @since 15.11.2018
 */
class LibraryPluginSpec extends AbstractPluginSpec {

    def "проверяем, что таски плагина определены и запускаются"() {
        def expectedTasks = [CheckChangelogPlugin.CHECK_CHANGELOG_TASK_NAME,
                             CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME]

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
        runTasksSuccessfully("clean", "build", "slowTest")

    }

}
