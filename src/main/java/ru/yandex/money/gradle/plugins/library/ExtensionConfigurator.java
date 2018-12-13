package ru.yandex.money.gradle.plugins.library;

import org.gradle.api.Project;
import ru.yandex.money.gradle.plugins.library.dependencies.checkversion.MajorVersionCheckerExtension;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.settings.EmailConnectionExtension;

import java.util.HashSet;
import java.util.Set;

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
}
