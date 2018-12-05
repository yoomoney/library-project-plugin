package ru.yandex.money.gradle.plugins.library;

import org.gradle.api.Project;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.settings.EmailConnectionExtension;

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
    }

    private static void configureGitExpiredBranchesExtension(Project project) {
        EmailConnectionExtension emailConnection = project.getExtensions().findByType(EmailConnectionExtension.class);
        emailConnection.emailHost = "mail.yamoney.ru";
        emailConnection.emailPort = 25;
        emailConnection.emailAuthUser = System.getenv("MAIL_USER");
        emailConnection.emailAuthPassword = System.getenv("MAIL_PASSWORD");
    }
}
