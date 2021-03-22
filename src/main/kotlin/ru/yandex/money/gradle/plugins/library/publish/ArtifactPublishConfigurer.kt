package ru.yandex.money.gradle.plugins.library.publish

import org.gradle.api.Project

/**
 * Конфигурация публикации артефакта библиотеки
 *
 * @author ivdoroshenko
 * @since 22.03.2021
 */
internal class ArtifactPublishConfigurer(private val project: Project) {

    fun configure() {
        when (getArtifactType(project)) {
            ArtifactType.MAVEN -> JavaArtifactPublishConfigurator.configure(project)
            ArtifactType.DOCKER -> DockerArtifactPublishConfigurator.configure(project)
        }
    }

    private fun getArtifactType(project: Project): ArtifactType {
        return project.findProperty("yamoney-library-project-plugin.artifact-type")
                ?.let { ArtifactType.byCode(it.toString()) }
                ?: ArtifactType.MAVEN
    }

    /**
     * Тип публикуемого артефакта
     */
    private enum class ArtifactType(private val code: String) {
        /**
         * Докер образ
         */
        DOCKER("docker"),

        /**
         * Maven артефакт (jar, zip)
         */
        MAVEN("maven");

        companion object {
            fun byCode(code: String) = values().first { it.code == code }
        }
    }
}