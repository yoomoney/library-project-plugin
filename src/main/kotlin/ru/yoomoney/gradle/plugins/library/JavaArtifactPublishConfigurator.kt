package ru.yoomoney.gradle.plugins.library

import org.gradle.api.Project
import ru.yoomoney.gradle.plugins.javapublishing.JavaArtifactPublishExtension
import ru.yoomoney.gradle.plugins.javapublishing.JavaArtifactPublishPlugin
import ru.yoomoney.gradle.plugins.javapublishing.PublicationAdditionalInfo

/**
 * Конфигурация плагина JavaArtifactPublishPlugin
 *
 * @author ivdoroshenko
 * @since 18.03.2021
 */
class JavaArtifactPublishConfigurator {

    fun configure(project: Project) {
        configurePublishPlugin(project)
    }

    private fun configurePublishPlugin(project: Project) {
        // Создаем extension сами, для того, чтобы выставить очередность afterEvaluate
        project.extensions.create(JavaArtifactPublishPlugin.extensionName,
                JavaArtifactPublishExtension::class.java)
        project.extensions.extraProperties["artifactId"] = ""
        project.extensions.extraProperties["groupIdSuffix"] = ""
        project.extensions.extraProperties["signingKey"] = ""
        project.extensions.extraProperties["signingPassword"] = ""
        val publishExtension = project.extensions.getByType(JavaArtifactPublishExtension::class.java)

        project.afterEvaluate { p: Project? ->
            publishExtension.nexusUser = System.getenv("NEXUS_USER")
            publishExtension.nexusPassword = System.getenv("NEXUS_PASSWORD")
            publishExtension.signing = true
            val artifactId = ExtensionConfigurator.getArtifactId(project)
            val publicationAdditionalInfo = PublicationAdditionalInfo()
            publicationAdditionalInfo.addInfo = true
            publicationAdditionalInfo.description = ExtensionConfigurator.getDescription(artifactId)
            publicationAdditionalInfo.organizationUrl = "https://github.com/yoomoney-tech"
            val license = PublicationAdditionalInfo.License()
            license.name = "MIT License"
            license.url = "http://www.opensource.org/licenses/mit-license.php"
            publicationAdditionalInfo.license = license
            val developer = PublicationAdditionalInfo.Developer()
            developer.name = "Oleg Kandaurov"
            developer.email = "kandaurov@yoomoney.ru"
            developer.organizationUrl = "https://yoomoney.ru"
            developer.organization = "YooMoney"
            val developers = ArrayList<PublicationAdditionalInfo.Developer>()
            developers.add(developer)
            publicationAdditionalInfo.developers = developers
            publishExtension.publicationAdditionalInfo = publicationAdditionalInfo
            publishExtension.groupId = "ru.yoomoney.tech"
            publishExtension.artifactId = artifactId
            publishExtension.snapshotRepository = "https://oss.sonatype.org/content/repositories/snapshots/"
            publishExtension.releaseRepository = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
        }
    }
}