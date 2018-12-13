package ru.yandex.money.gradle.plugins.library;

import org.gradle.api.Project;
import ru.yandex.money.gradle.plugins.library.dependencies.CheckDependenciesPluginExtension;
import ru.yandex.money.gradle.plugins.library.dependencies.checkversion.MajorVersionCheckerExtension;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.settings.EmailConnectionExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.singletonList;

/**
 * Конфигуратор настроек плагинов.
 *
 * @author Dmitry Komarov
 * @since 05.12.2018
 */
public class ExtensionConfigurator {

    /**
     * Конфигурирует плагины.
     *
     * @param project целевой проект
     */
    static void configure(Project project) {
        configureGitExpiredBranchesExtension(project);
        configureMajorVersionCheckerExtension(project);
        configureCheckDependenciesExtension(project);
    }

    private static void configureGitExpiredBranchesExtension(Project project) {
        EmailConnectionExtension emailConnection = project.getExtensions().findByType(EmailConnectionExtension.class);
        emailConnection.emailHost = "mail.yamoney.ru";
        emailConnection.emailPort = 25;
        emailConnection.emailAuthUser = System.getenv("MAIL_USER");
        emailConnection.emailAuthPassword = System.getenv("MAIL_PASSWORD");
    }

    private static void configureMajorVersionCheckerExtension(Project project) {
        Set<String> includeGroupIdPrefixes = new HashSet<>();
        includeGroupIdPrefixes.add("ru.yamoney");
        includeGroupIdPrefixes.add("ru.yandex.money");

        project.getExtensions().findByType(MajorVersionCheckerExtension.class)
                .includeGroupIdPrefixes = includeGroupIdPrefixes;
    }

    private static void configureCheckDependenciesExtension(Project project) {
        CheckDependenciesPluginExtension checkDependenciesPluginExtension =
                project.getExtensions().findByType(CheckDependenciesPluginExtension.class);

        checkDependenciesPluginExtension.excludedConfigurations = Arrays.asList(
                "checkstyle", "errorprone", "optional", "findbugs",
                "architecture", "architectureTestCompile", "architectureTestCompileClasspath",
                "architectureTestRuntime", "architectureTestRuntimeClasspath");

        checkDependenciesPluginExtension.exclusionsRulesSources =
                singletonList("ru.yandex.money.platform:yamoney-libraries-dependencies");
    }
}
