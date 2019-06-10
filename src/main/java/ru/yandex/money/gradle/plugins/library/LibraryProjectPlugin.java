package ru.yandex.money.gradle.plugins.library;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.gradle.util.VersionNumber;
import ru.yandex.money.gradle.plugin.architecturetest.ArchitectureTestPlugin;
import ru.yandex.money.gradle.plugins.backend.build.JavaModulePlugin;
import ru.yandex.money.gradle.plugins.library.deps.ForbidApiFromApiDependencies;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.GitExpiredBranchPlugin;
import ru.yandex.money.gradle.plugins.library.publishing.PublishingConfigurer;
import ru.yandex.money.gradle.plugins.release.ReleasePlugin;

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
            MavenPublishPlugin.class,
            ReleasePlugin.class,
            GitExpiredBranchPlugin.class,
            JavaModulePlugin.class,
            ArchitectureTestPlugin.class
    );

    @Override
    public void apply(Project project) {
        if (VersionNumber.parse(project.getGradle().getGradleVersion()).compareTo(VersionNumber.parse("4.10.2'")) < 0) {
            throw new IllegalStateException("Gradle >= 4.10.2 is required");
        }
        PLUGINS_TO_APPLY.forEach(pluginClass -> project.getPluginManager().apply(pluginClass));
        ExtensionConfigurator.configure(project);
        new ForbidApiFromApiDependencies().init(project);
        new PublishingConfigurer().init(project);
    }

}
