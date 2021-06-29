package ru.yoomoney.gradle.plugins.library;

import org.apache.commons.lang3.StringUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.wrapper.Wrapper;
import ru.yoomoney.gradle.plugins.backend.build.JavaExtension;
import ru.yoomoney.gradle.plugins.backend.build.git.GitManager;
import ru.yoomoney.gradle.plugins.library.configurer.CheckDependenciesConfigurer;
import ru.yoomoney.gradle.plugins.release.ReleaseExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

/**
 * Конфигуратор настроек плагинов.
 *
 * @author Dmitry Komarov
 * @since 05.12.2018
 */
public class ExtensionConfigurator {

    private static final String GIT_EMAIL = "SvcReleaserBackend@yoomoney.ru";
    private static final String GIT_USER = "SvcReleaserBackend";
    /**
     * Url, по которому будет скачиваться gradle в wrapper таске.
     */
    private static final String GRADLE_DISTRIBUTION_URL = "https://services.gradle.org/distributions/gradle-6.4.1-all.zip";

    /**
     * Конфигурирует плагины.
     *
     * @param project целевой проект
     */
    static void configure(Project project) {
        configureReleasePlugin(project);
        configureWrapper(project);
        configureJavaPlugin(project);
        CheckDependenciesConfigurer.configureCheckDependencies(project);
    }

    private static void configureWrapper(Project project) {
        project.getTasks().maybeCreate("wrapper", Wrapper.class)
                .setDistributionUrl(GRADLE_DISTRIBUTION_URL);
    }

    private static void configureReleasePlugin(Project project) {
        ReleaseExtension releaseExtension = project.getExtensions().getByType(ReleaseExtension.class);
        releaseExtension.getReleaseTasks().clear();

        //задачи, которые будут запускаться при релизе.
        //publish - опубликовать артефакт
        //closeAndReleaseMavenStagingRepository - закрыть staging репозиторий и выпустить артефакт в релизный репозиторий (MavenCentral)
        releaseExtension.getReleaseTasks().addAll(Arrays.asList("build", "publish", "closeAndReleaseMavenStagingRepository"));
        releaseExtension.setPathToGitPrivateSshKey(System.getenv("GIT_PRIVATE_SSH_KEY_PATH"));
        releaseExtension.setPassphraseToGitPrivateSshKey(System.getenv("GIT_KEY_PASSPHRASE"));

        releaseExtension.setGitUsername(GIT_USER);
        releaseExtension.setGitEmail(GIT_EMAIL);

        releaseExtension.setChangelogRequired(true);
        releaseExtension.setAddPullRequestLinkToChangelog(true);

        releaseExtension.setPullRequestInfoProvider("GitHub");
        releaseExtension.setGithubAccessToken(System.getenv("GITHUB_TOKEN"));

        try (ru.yoomoney.gradle.plugins.backend.build.git.GitManager git = new GitManager(project)) {
            if (git.isDevelopmentBranch()) {
                project.getTasks().getByName("build")
                        .dependsOn(project.getTasks().getByName("checkChangelog"));
            }
        }
    }

    private static void configureJavaPlugin(Project project) {
        List<String> repositories = Collections.singletonList(project.getRepositories().mavenCentral().getUrl().toString());

        List<String> snapshotsRepositories = Arrays.asList(
                project.getRepositories().mavenLocal().getUrl().toString(),
                "https://oss.sonatype.org/content/repositories/snapshots/");

        JavaExtension javaExtension = project.getExtensions().getByType(JavaExtension.class);
        javaExtension.setRepositories(repositories);
        javaExtension.setSnapshotsRepositories(snapshotsRepositories);
    }

    /**
     * Возвращает artifactId
     */
    public static String getArtifactId(Project project) {
        String value = (String) project.getExtensions().getExtraProperties().get("artifactId");
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("property artifactId is empty");
        }
        return value;
    }

    /**
     * Возвращает описание
     */
    public static String getDescription(String artifactId) {
        return format("Library by YooMoney. See README: " +
                "https://github.com/yoomoney-tech/%s", artifactId);
    }
}
