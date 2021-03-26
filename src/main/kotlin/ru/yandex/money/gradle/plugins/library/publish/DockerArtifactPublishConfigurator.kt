package ru.yandex.money.gradle.plugins.library.publish

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.gradle.api.Project
import ru.yandex.money.gradle.plugins.library.getRequiredExtraProperty
import ru.yoomoney.gradle.plugins.docker.publish.DockerArtifactPublishExtension
import ru.yoomoney.gradle.plugins.docker.publish.DockerArtifactPublishPlugin

/**
 * Конфигурация плагина DockerArtifactPublishPlugin
 *
 * В качестве конвенции по наименованию docker образов для публикации зафиксировано:
 * имя локально собранного docker образа должно соответствовать формату "yoomoney/$groupIdSuffix/$artifactID:$version"
 *
 * @author ivdoroshenko
 * @since 18.03.2021
 */
internal object DockerArtifactPublishConfigurator {
    private val PLUGIN = DockerArtifactPublishPlugin::class.java

    fun configure(project: Project) {
        configurePublishPlugin(project)
        project.pluginManager.apply(PLUGIN)
    }

    @SuppressFBWarnings("HARD_CODE_PASSWORD")
    private fun configurePublishPlugin(project: Project) {
        // Создаем extension сами, для того, чтобы выставить очередность afterEvaluate
        project.extensions.create(DockerArtifactPublishPlugin.extensionName, DockerArtifactPublishExtension::class.java)
        project.extensions.extraProperties["groupIdSuffix"] = ""
        project.extensions.extraProperties["artifactID"] = ""

        val publishExtension = project.extensions.getByType(DockerArtifactPublishExtension::class.java)
        publishExtension.username = System.getenv("NEXUS_USER")
        publishExtension.password = System.getenv("NEXUS_PASSWORD")
        project.afterEvaluate {
            publishExtension.groupId = "yoomoney/" + project.getRequiredExtraProperty("groupIdSuffix")
            publishExtension.artifactId = project.getRequiredExtraProperty("artifactID")
            publishExtension.snapshotRegistry = "docker-ym-dev.nexus.yamoney.ru"
            publishExtension.releaseRegistry = "docker-ym.nexus.yamoney.ru"
        }
    }
}