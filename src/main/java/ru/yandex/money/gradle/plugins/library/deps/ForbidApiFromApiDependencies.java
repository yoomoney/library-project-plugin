package ru.yandex.money.gradle.plugins.library.deps;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.DependencyResolveDetails;
import org.gradle.api.artifacts.ModuleVersionSelector;

/**
 * Проверяет, что артефакты api не зависят от других api
 *
 * @author Valerii Zhirnov (vazhirnov@yamoney.ru)
 * @since 22.04.2019
 */
public class ForbidApiFromApiDependencies {

    public void init(Project project) {
        checkDeps(project);
    }

    private void checkDeps(Project project) {
        ConfigurationContainer configurations = project.getConfigurations();
        configurations.all(conf ->
                conf.resolutionStrategy(
                        rs -> {
                            rs.eachDependency(dep -> applyRules(project, dep));
                        }
                )
        );
    }

    private void applyRules(Project project, DependencyResolveDetails details) {
        if (project.hasProperty("groupIdSuffix") && "api".equals(project.property("groupIdSuffix"))) {
            ModuleVersionSelector requested = details.getRequested();
            String group = requested.getGroup();
            if (group.contains("ru.yandex.money.api")) {
                throw new IllegalStateException(
                        String.format("Api must not depend on other api: %s:%s", group, requested.getName())
                );
            }
        }
    }
}
