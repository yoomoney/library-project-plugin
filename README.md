# library-project-plugin
Плагин создан для упрощения сборки существующих библиотек и разработки новых.
Данный плагин выступает в качестве агрегатора функционала - в нём применяются настройки и другие плагины, 
необходимые для работы и сборки библиотек.

## Подключение
Для подключения в проект этого плагина, нужно добавить файл ```project.gradle```:
```groovy
System.setProperty("platformLibraryProjectVersion", "2.+")
System.setProperty("platformDependenciesVersion", "3.+")
System.setProperty("kotlinVersion", "1.2.61")

repositories {
    maven { url 'https://nexus.yamoney.ru/content/repositories/thirdparty/' }
    maven { url 'https://nexus.yamoney.ru/content/repositories/central/' }
    maven { url 'https://nexus.yamoney.ru/content/repositories/releases/' }
    maven { url 'https://nexus.yamoney.ru/content/repositories/jcenter.bintray.com/' }

    dependencies {
        classpath 'ru.yandex.money.gradle.plugins:yamoney-library-project-plugin:' + 
                System.getProperty("platformLibraryProjectVersion")
        classpath group: 'ru.yandex.money.platform', name: 'yamoney-libraries-dependencies', 
                version: System.getProperty("platformDependenciesVersion"), ext: 'zip'
        classpath 'org.jetbrains.kotlin:kotlin-gradle-plugin:' + 
                        System.getProperty('kotlinVersion')
    }
}
```
А в `build.gradle` добавить соответствующую секцию, чтобы конфигурационный файл выглядел подобным образом:
```groovy
buildscript {
    apply from: 'project.gradle', to: buildscript
    copy {
        from zipTree(buildscript.configurations.classpath.files.find{ it.name.contains('library-project-plugin')})
        into 'tmp'
        include 'gradle-scripts/**'
    }
}
apply from: 'tmp/gradle-scripts/_root.gradle'
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
