package ru.yoomoney.gradle.plugins.library.configurer;

import org.gradle.api.Project;
import ru.yoomoney.gradle.plugins.library.dependencies.CheckDependenciesPluginExtension;
import ru.yoomoney.gradle.plugins.library.dependencies.checkversion.MajorVersionCheckerExtension;

import java.util.HashSet;

import static java.util.Collections.singletonList;

/**
 * Конфигурация check-dependencies-plugin
 *
 * @author horyukova
 * @since 12.03.2021
 */
public final class CheckDependenciesConfigurer {
    private CheckDependenciesConfigurer(){}

    public static void configureCheckDependencies(Project project) {
        configureCheckDependenciesExtension(project);
        configureMajorVersionCheckerExtension(project);
    }

    private static void configureMajorVersionCheckerExtension(Project project) {
        HashSet<String> includeGroupId = new HashSet<>();
        includeGroupId.add("ru.yoomoney");

        MajorVersionCheckerExtension majorVersionCheckerExtension =
                project.getExtensions().findByType(MajorVersionCheckerExtension.class);
        majorVersionCheckerExtension.includeGroupIdPrefixes = includeGroupId;
    }

    private static void configureCheckDependenciesExtension(Project project) {
        CheckDependenciesPluginExtension checkDependenciesPluginExtension = project.getExtensions()
                .findByType(CheckDependenciesPluginExtension.class);

        checkDependenciesPluginExtension.exclusionsRulesSources = singletonList("libraries-versions-exclusions.properties");
    }
}
