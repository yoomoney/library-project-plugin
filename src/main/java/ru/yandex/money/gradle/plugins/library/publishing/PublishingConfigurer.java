package ru.yandex.money.gradle.plugins.library.publishing;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.component.SoftwareComponent;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.javadoc.Javadoc;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Конфигурирует публикацию артефактов
 *
 * @author Valerii Zhirnov (vazhirnov@yamoney.ru)
 * @since 22.04.2019
 */
public class PublishingConfigurer {

    private final Logger logger = Logging.getLogger(PublishingConfigurer.class);

    /**
     * Иницализация
     */
    public void init(Project project) {
        project.getPluginManager().apply(MavenPublishPlugin.class);
        configureJavadoc(project);
        project.afterEvaluate(p -> {
            configurePublishing(project);
            configureStoreVersion(project);
        });
    }

    private void configureJavadoc(Project project) {
        Javadoc javadoc = (Javadoc) project.getTasks().findByName("javadoc");
        javadoc.setClasspath(javadoc.getClasspath().plus(project.getConfigurations().getByName("optional")));
        javadoc.getOptions().setEncoding("UTF-8");
        javadoc.setFailOnError(false);

        Jar sourceJar = project.getTasks().maybeCreate("sourceJar", Jar.class);
        sourceJar.from(project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().getByName("main").getAllJava());

        Jar javadocJar = project.getTasks().maybeCreate("javadocJar", Jar.class);
        javadocJar.dependsOn("javadoc");
        javadocJar.setClassifier("javadoc");
        javadocJar.from(javadoc.getDestinationDir());
    }

    private void configurePublishing(Project project) {
        PublishingExtension publishingExtension = project.getExtensions().getByType(PublishingExtension.class);
        publishingExtension.publications(publicationContainer -> {
            MavenPublication mavenJava = publicationContainer.maybeCreate("mavenJava", MavenPublication.class);
            mavenJava.setGroupId("ru.yandex.money" + groupIdSuffix(project));
            mavenJava.setArtifactId(project.getName());
            mavenJava.from(getPublishingComponent(project));
            mavenJava.artifact(project.getTasks().getByName("sourceJar"), mavenArtifact -> mavenArtifact.setClassifier("sources"));
            mavenJava.artifact(project.getTasks().getByName("javadocJar"), mavenArtifact -> mavenArtifact.setClassifier("javadoc"));
        });
        publishingExtension.repositories(artifactRepositories -> {
            artifactRepositories.maven(mavenArtifactRepository -> {
                if (project.getVersion().toString().endsWith("-SNAPSHOT")) {
                    mavenArtifactRepository.setUrl(URI.create("https://nexus.yamoney.ru/content/repositories/snapshots/"));
                } else {
                    mavenArtifactRepository.setUrl(URI.create("https://nexus.yamoney.ru/content/repositories/releases/"));
                }
                mavenArtifactRepository.credentials(passwordCredentials -> {
                    passwordCredentials.setUsername(System.getenv("NEXUS_USER"));
                    passwordCredentials.setPassword(System.getenv("NEXUS_PASSWORD"));
                });
            });
        });
        project.getTasks().withType(PublishToMavenRepository.class).forEach(task -> task.dependsOn("test"));
    }

    private void configureStoreVersion(Project project) {
        storeVersion(project);
        project.getTasks().getByName("publish").finalizedBy("storeVersion");
    }

    private void storeVersion(Project project) {
        Task storeVersion = project.getTasks().create("storeVersion");
        storeVersion.setDescription("Generates file, which contains information about build version");
        storeVersion.doLast(task -> {
            String debVersion = String.format("%s:%s", project.getName(), project.getVersion());
            storeVersionToFile(project.getBuildDir().getAbsolutePath(), "version.txt", debVersion);
        });
    }

    private void storeVersionToFile(String versionDir, String filename, String content) {
        try {
            Path versionFile = Paths.get(versionDir, filename);
            Files.write(versionFile, Collections.singleton(content), UTF_8);
            logger.lifecycle("File with version generated successfully into " + versionFile);
        } catch (Exception e) {
            logger.lifecycle("Error occurred during storing version", e);
        }
    }

    private String groupIdSuffix(Project project) {
        Object groupIdSuffix = project.findProperty("groupIdSuffix");
        if (groupIdSuffix != null && !groupIdSuffix.toString().isEmpty()) {
            return "." + project.property("groupIdSuffix");
        }
        return "";
    }

    private SoftwareComponent getPublishingComponent(Project project) {
        if (!project.hasProperty("publishingComponent")) {
            return project.getComponents().getByName("java");
        }
        return (SoftwareComponent) project.property("publishingComponent");
    }
}
