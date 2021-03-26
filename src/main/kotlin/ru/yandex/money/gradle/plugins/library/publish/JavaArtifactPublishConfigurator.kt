package ru.yandex.money.gradle.plugins.library.publish

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.gradle.api.Project
import ru.yandex.money.gradle.plugins.library.getRequiredExtraProperty
import ru.yoomoney.gradle.plugins.javapublishing.JavaArtifactPublishExtension
import ru.yoomoney.gradle.plugins.javapublishing.JavaArtifactPublishPlugin

/**
 * Конфигурация плагина JavaArtifactPublishPlugin
 *
 * @author ivdoroshenko
 * @since 18.03.2021
 */
internal object JavaArtifactPublishConfigurator {
    private val PLUGIN = JavaArtifactPublishPlugin::class.java

    fun configure(project: Project) {
        configurePublishPlugin(project)
        project.pluginManager.apply(PLUGIN)
    }

    @SuppressFBWarnings("HARD_CODE_PASSWORD")
    private fun configurePublishPlugin(project: Project) {
        // Создаем extension сами, для того, чтобы выставить очередность afterEvaluate
        project.extensions.create(JavaArtifactPublishPlugin.extensionName, JavaArtifactPublishExtension::class.java)
        project.extensions.extraProperties["groupIdSuffix"] = ""
        project.extensions.extraProperties["artifactID"] = ""

        val publishExtension = project.extensions.getByType(JavaArtifactPublishExtension::class.java)
        publishExtension.nexusUser = System.getenv("NEXUS_USER")
        publishExtension.nexusPassword = System.getenv("NEXUS_PASSWORD")
        project.afterEvaluate {
            publishExtension.groupId = "ru.yandex.money." + project.getRequiredExtraProperty("groupIdSuffix")
            publishExtension.artifactId = project.getRequiredExtraProperty("artifactID")
            publishExtension.snapshotRepository = "https://nexus.yamoney.ru/repository/snapshots/"
            publishExtension.releaseRepository = "https://nexus.yamoney.ru/repository/releases/"
        }
    }
}