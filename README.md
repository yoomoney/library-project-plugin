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

## Подключенные плагины
* [yamoney-artifact-release-plugin](https://bitbucket-public.yamoney.ru/projects/BACKEND-GRADLE-PLUGINS/repos/artifact-release-plugin/browse/README.md)
* [yamoney-java-module-plugin](https://bitbucket-public.yamoney.ru/projects/BACKEND-GRADLE-PLUGINS/repos/java-module-plugin/browse/README.md)
* [yamoney-git-expired-branch-plugin](https://bitbucket-public.yamoney.ru/projects/BACKEND-GRADLE-PLUGINS/repos/git-expired-branch-plugin/browse/README.md)
