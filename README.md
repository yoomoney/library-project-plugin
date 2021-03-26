# library-project-plugin
Плагин создан для упрощения сборки существующих библиотек и разработки новых.
Данный плагин выступает в качестве агрегатора функционала - в нём применяются настройки и другие плагины, 
необходимые для работы и сборки библиотек.

## Подключение
Для подключения в проект этого плагина, нужно добавить файл ```project.gradle```:
```groovy
System.setProperty("platformLibraryProjectVersion", "4.+")
System.setProperty("platformDependenciesVersion", "3.+")

repositories {
    maven { url 'https://nexus.yamoney.ru/repository/gradle-plugins/' }
    maven { url 'https://nexus.yamoney.ru/repository/thirdparty/' }
    maven { url 'https://nexus.yamoney.ru/repository/central/' }
    maven { url 'https://nexus.yamoney.ru/repository/releases/' }
    maven { url 'https://nexus.yamoney.ru/repository/jcenter.bintray.com/' }

    dependencies {
        classpath 'ru.yandex.money.gradle.plugins:yamoney-library-project-plugin:' + 
                System.getProperty("platformLibraryProjectVersion")
        classpath group: 'ru.yandex.money.platform', name: 'yamoney-libraries-dependencies', 
                version: System.getProperty("platformDependenciesVersion"), ext: 'zip'
    }
}
```
А в `build.gradle` добавить соответствующую секцию, чтобы конфигурационный файл выглядел подобным образом:
```groovy
buildscript {
    apply from: 'project.gradle', to: buildscript
}
apply plugin: 'yamoney-library-project-plugin'
/////////////////////////////////////////////

groupIdSuffix = "common"
artifactID = "yamoney-json-utils"

dependencies {
    compile 'com.fasterxml.jackson.core:jackson-annotations:2.9.0'
}
```

Если проект полностью написан на kotlin то надо применить плагин `yamoney-kotlin-module-plugin` после `yamoney-library-project-plugin`
```groovy
buildscript {
    apply from: 'project.gradle', to: buildscript
}
apply plugin: 'yamoney-library-project-plugin'
apply plugin: 'yamoney-kotlin-module-plugin'
/////////////////////////////////////////////

groupIdSuffix = "common"
artifactID = "yamoney-json-utils"

dependencies {
    compile 'com.fasterxml.jackson.core:jackson-annotations:2.9.0'
}
```

## Публикация артефакта
Публикация артефакта производится по вызову gradle задачи `publish`

Плагин поддерживает два вида публикации артефактов:
- `docker` - публикация предварительно собранного docker образа в реестр образов yoomoney. 
  Локальный образ должен быть предварительно собран согласно логике, определенной в библиотеке.
  Имя образа должно соответствовать формату: `yoomoney/{groupIdSuffix}/{artifactID}:{version}`.
- `maven` - публикация в maven репозиторий

Тит публикуемого артефакта задается настройкой `yamoney-library-project-plugin.artifact-type`.
По умолчанию, если значение явно не задано, будет публиковаться maven артефакт.

## Подключенные плагины
* [artifact-release-plugin](https://github.com/yoomoney-gradle-plugins/artifact-release-plugin/blob/master/README.md)
* [yoomoney-java-module-plugin](https://bitbucket.yamoney.ru/projects/BACKEND-GRADLE-PLUGINS/repos/module-project-plugin/browse/README.md)
* [yamoney-git-expired-branch-plugin](https://bitbucket.yamoney.ru/projects/BACKEND-GRADLE-PLUGINS/repos/git-expired-branch-plugin/browse/README.md)
* [yamoney-build-monitoring-plugin](https://bitbucket.yamoney.ru/projects/BACKEND-GRADLE-PLUGINS/repos/build-monitoring-plugin/browse/README.md)
