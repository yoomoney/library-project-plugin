package ru.yandex.money.gradle.plugins.library;

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.plugins.JavaPlugin;
import ru.yandex.money.gradle.plugins.library.changelog.CheckChangelogPlugin;
import ru.yandex.money.gradle.plugins.library.dependencies.CheckDependenciesPlugin;
import ru.yandex.money.gradle.plugins.library.git.BranchName;
import ru.yandex.money.gradle.plugins.library.git.GitRepositoryProperties;
import ru.yandex.money.gradle.plugins.library.git.TagName;
import ru.yandex.money.gradle.plugins.library.readme.ReadmePlugin;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Входная точка library-plugin'а, подключает все необходимые плагины-зависимости.
 *
 * @author Kirill Bulatov (mail4score@gmail.com)
 * @since 22.12.2016
 */
public class LibraryProjectPlugin implements Plugin<Project> {
    /**
     * Для подключения новой функциональности, достаточно добавить плагин в этот список.
     * Все остальные настройки должны делаться в самом добавляемом плагине.
     */
    private static final Collection<Class<?>> PLUGINS_TO_APPLY = Arrays.asList(
            JavaPlugin.class,
            DependencyManagementPlugin.class,
            ReadmePlugin.class,
            CheckChangelogPlugin.class,
            CheckDependenciesPlugin.class
    );

    @Override
    public void apply(Project project) {
        PLUGINS_TO_APPLY.forEach(pluginClass -> project.getPluginManager().apply(pluginClass));
        configureRepositories(project);
    }

    /**
     * Добавляем в проект все репозитории, нужные для получения зависимостей.
     */
    private void configureRepositories(Project project) {
        Stream<String> repositoriesToApply = Stream.of(
                "https://nexus.yamoney.ru/content/repositories/central/",
                "https://nexus.yamoney.ru/content/repositories/releases/",
                "https://nexus.yamoney.ru/content/repositories/thirdparty/",
                "https://nexus.yamoney.ru/content/repositories/spp-releases/",
                "https://nexus.yamoney.ru/content/repositories/jcenter.bintray.com/"
        );

        if (canConsumeSnapshots(project.getProjectDir())) {
            System.out.println("Using snapshot repositories");
            repositoriesToApply = Stream.concat(repositoriesToApply, Stream.of(
                    "https://nexus.yamoney.ru/content/repositories/snapshots/",
                    "https://nexus.yamoney.ru/content/repositories/spp-snapshots/"
            ));
        }

        RepositoryHandler repositories = project.getRepositories();
        repositoriesToApply.map(repoUrl -> repositories.maven(mavenRepository -> mavenRepository.setUrl(repoUrl)))
                           .forEach(repositories::add);
    }

    /**
     * Определяет допустимость использования в проекте SNAPSHOT-репозиториев на основании текущей git-ветки
     *
     * @param projectDir корневая папка текущего проекта
     * @return true, если допустимо, false - иначе
     */
    private boolean canConsumeSnapshots(File projectDir) {
        GitRepositoryProperties properties = new GitRepositoryProperties(projectDir.getAbsolutePath());
        BranchName currentBranch = properties.getCurrentBranchName();
        Optional<TagName> currentTag = properties.getTagNameOnHead();

        return !isReleaseRelated(currentBranch) && !isReleaseRelated(currentTag);
    }

    private static boolean isReleaseRelated(BranchName branchName) {
        return branchName.isMaster()
            || branchName.isDev()
            || branchName.isRelease()
            || branchName.isTagRelease();
    }

    private static boolean isReleaseRelated(Optional<TagName> tag) {
        return tag.map(TagName::isRelease).orElse(false);
    }
}
