package ru.yandex.money.gradle.plugins.library;

import org.gradle.api.Project;
import ru.yandex.money.gradle.plugin.architecturetest.ArchitectureTestExtension;
import ru.yandex.money.gradle.plugins.backend.build.JavaModuleExtension;
import ru.yandex.money.gradle.plugins.library.git.GitManager;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.settings.EmailConnectionExtension;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.settings.GitConnectionExtension;
import ru.yandex.money.gradle.plugins.release.ReleaseExtension;

import java.util.Arrays;

/**
 * Конфигуратор настроек плагинов.
 *
 * @author Dmitry Komarov
 * @since 05.12.2018
 */
public class ExtensionConfigurator {
    private static final String GIT_EMAIL = "SvcReleaserBackend@yamoney.ru";
    private static final String GIT_USER = "SvcReleaserBackend";

    /**
     * Конфигурирует плагины.
     *
     * @param project целевой проект
     */
    static void configure(Project project) {
        configureGitExpiredBranchesExtension(project);
        configureReleasePlugin(project);
        configureJavaModulePlugin(project);
        addMissingCheckSnapshotsTask(project);
        configurationArchitectureTestPlugin(project);
    }

    private static void addMissingCheckSnapshotsTask(Project project) {
        project.getTasks().create("checkComponentSnapshotDependencies").dependsOn("checkSnapshotsDependencies");
    }

    private static void configureJavaModulePlugin(Project project) {
        project.afterEvaluate(p -> {
            JavaModuleExtension module = p.getExtensions().getByType(JavaModuleExtension.class);
            if (p.hasProperty("checkstyleEnabled")) {
                module.setCheckstyleEnabled((Boolean) p.property("checkstyleEnabled"));
            }
            if (p.hasProperty("findbugsEnabled")) {
                module.setSpotbugsEnabled((Boolean) p.property("findbugsEnabled"));
            }
        });
    }

    private static void configureReleasePlugin(Project project) {
        ReleaseExtension releaseExtension = project.getExtensions().getByType(ReleaseExtension.class);
        releaseExtension.getReleaseTasks().addAll(Arrays.asList("build", "publish"));
        releaseExtension.setChangelogRequired(true);
        releaseExtension.setPathToGitPrivateSshKey(System.getenv("GIT_PRIVATE_SSH_KEY_PATH"));
        releaseExtension.setGitEmail(GIT_EMAIL);
        releaseExtension.setGitUsername(GIT_USER);

        try (GitManager git = new GitManager(project.getRootDir())) {
            if (!git.isCurrentBranchForRelease()) {
                project.getTasks().getByName("build")
                        .dependsOn(project.getTasks().getByName("checkChangelog"));
            }
        }
    }

    private static void configureGitExpiredBranchesExtension(Project project) {
        EmailConnectionExtension emailConnection = project.getExtensions().findByType(EmailConnectionExtension.class);
        emailConnection.emailHost = "mail.yamoney.ru";
        emailConnection.emailPort = 25;
        emailConnection.emailAuthUser = System.getenv("MAIL_USER");
        emailConnection.emailAuthPassword = System.getenv("MAIL_PASSWORD");

        GitConnectionExtension gitConnectionExtension =
                project.getExtensions().findByType(GitConnectionExtension.class);

        gitConnectionExtension.setPathToGitPrivateSshKey(System.getenv("GIT_PRIVATE_SSH_KEY_PATH"));
        gitConnectionExtension.setEmail(GIT_EMAIL);
        gitConnectionExtension.setUsername(GIT_USER);
    }

    private static void configurationArchitectureTestPlugin(Project project) {
        ArchitectureTestExtension architectureTestExtension = project.getExtensions().getByType(ArchitectureTestExtension.class);
        architectureTestExtension.getInclude().addAll(Arrays.asList(
                "check_unique_enums_codes",
                "check_unique_enums_secondary_codes"));
    }
}
