package ru.yandex.money.gradle.plugins.library;

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import ru.yandex.money.gradle.plugins.library.changelog.CheckChangelogPlugin;
import ru.yandex.money.gradle.plugins.library.dependencies.CheckDependenciesPlugin;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.GitExpiredBranchPlugin;

import java.util.Arrays;
import java.util.Collection;

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
            CheckChangelogPlugin.class,
            CheckDependenciesPlugin.class,
            GitExpiredBranchPlugin.class
    );

    @Override
    public void apply(Project project) {
        PLUGINS_TO_APPLY.forEach(pluginClass -> project.getPluginManager().apply(pluginClass));
        ExtensionConfigurator.configure(project);
    }

}
