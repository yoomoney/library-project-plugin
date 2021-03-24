package ru.yandex.money.gradle.plugins.library

import org.gradle.api.Project

/**
 * Получить значение extraProperty с именем [propertyName].
 * @throws IllegalArgumentException если настройка отсутствует
 */
fun Project.getRequiredExtraProperty(propertyName: String): String {
    val value = project.extensions.extraProperties[propertyName] as String?
    require(!value.isNullOrBlank()) {
        "property $propertyName is empty"
    }

    return value
}