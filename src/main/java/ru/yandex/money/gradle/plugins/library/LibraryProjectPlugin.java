package ru.yandex.money.gradle.plugins.library;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.util.VersionNumber;
import ru.yandex.money.gradle.plugins.javapublishing.JavaArtifactPublishPlugin;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.GitExpiredBranchPlugin;
import ru.yandex.money.gradle.plugins.task.monitoring.BuildMonitoringPlugin;
import ru.yoomoney.gradle.plugins.moduleproject.ModuleProjectPlugin;
import ru.yoomoney.gradle.plugins.release.ReleasePlugin;

import java.util.Arrays;
import java.util.Collection;

/**
 * Входная точка library-plugin'а, подключает все необходимые плагины-зависимости.
 *
 * @author Kirill Bulatov (mail4score@gmail.com)
 * @since 22.12.2016
 */
public class LibraryProjectPlugin implements Plugin<Project> {

    private static final Collection<Class<?>> PLUGINS_TO_APPLY = Arrays.asList(
            ModuleProjectPlugin.class,
            JavaArtifactPublishPlugin.class,
            ReleasePlugin.class,
            GitExpiredBranchPlugin.class,
            BuildMonitoringPlugin.class
    );

    @Override
    public void apply(Project project) {
        if (VersionNumber.parse(project.getGradle().getGradleVersion()).compareTo(VersionNumber.parse("6.0.1'")) < 0) {
            throw new IllegalStateException("Gradle >= 6.0.1 is required");
        }
        ExtensionConfigurator.configurePublishPlugin(project);
        PLUGINS_TO_APPLY.forEach(pluginClass -> project.getPluginManager().apply(pluginClass));
        ExtensionConfigurator.configure(project);
    }

}
