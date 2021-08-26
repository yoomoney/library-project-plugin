[![Build Status](https://travis-ci.com/yoomoney/library-project-plugin.svg?branch=master)](https://travis-ci.com/yoomoney/library-project-plugin)
[![codecov](https://codecov.io/gh/yoomoney/library-project-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/yoomoney/library-project-plugin)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# library-project-plugin
Плагин создан для упрощения сборки существующих библиотек и разработки новых.
Данный плагин выступает в качестве агрегатора функционала - в нём применяются настройки и другие плагины, 
необходимые для работы и сборки библиотек.

## Подключение
Для подключения в проект этого плагина, нужно добавить файл ```project.gradle```:
```groovy
repositories {
  mavenCentral()
  maven { url 'https://plugins.gradle.org/m2/' }

  dependencies {
    classpath 'ru.yoomoney.gradle.plugins:library-project-plugin:7.0.0'
  }
}
```
А в `build.gradle` добавить соответствующую секцию, чтобы конфигурационный файл выглядел подобным образом:
```groovy
buildscript {
    apply from: 'project.gradle', to: buildscript
}
apply plugin: "ru.yoomoney.gradle.plugins.library-project-plugin"
/////////////////////////////////////////////

artifactId = "yoomoney-json-utils"

dependencies {
    compile 'com.fasterxml.jackson.core:jackson-annotations:2.9.0'
}
```

Если проект полностью написан на kotlin то надо применить плагин `ru.yoomoney.gradle.plugins.kotlin-plugin` после `ru.yoomoney.gradle.plugins.library-project-plugin`
```groovy
buildscript {
    apply from: 'project.gradle', to: buildscript
}
apply plugin: "ru.yoomoney.gradle.plugins.library-project-plugin"
apply plugin: 'ru.yoomoney.gradle.plugins.kotlin-plugin'
/////////////////////////////////////////////

artifactId = "yoomoney-json-utils"

dependencies {
    compile 'com.fasterxml.jackson.core:jackson-annotations:2.9.0'
}
```

## Публикация артефакта
Публикация артефакта производится по вызову gradle задачи `publish`

Плагин производит публикацию в maven репозиторий.

## Подключенные плагины
* [artifact-release-plugin](https://github.com/yoomoney/artifact-release-plugin/blob/master/README.md)
* [java-artifact-publish-plugin](https://github.com/yoomoney/java-artifact-publish-plugin)
* [java-plugin](https://github.com/yoomoney/java-plugin)
* [check-dependencies-plugin](https://github.com/yoomoney/check-dependencies-plugin)
