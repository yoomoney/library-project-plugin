## [3.4.0]() (26-04-2019)

* Скрипты check-dependencies и build-publishing перенесены в код плагина

## [3.3.2]() (26-04-2019)

* Добавлена недостающая таска checkComponentSnapshotDependencies

## [3.3.1]() (26-04-2019)

* Добавлен `JavaModulePlugin`

## [3.3.0]() (26-04-2019)

* Часть скриптов сборки перенесена в java-module-plugin

## [3.2.0]() (26-04-2019)

* Подняла версию `yamoney-git-expired-branch-plugin` c '2.0.4' до `3.0.0`.

## [3.1.3]() (24-04-2019)

* Одна версия coverage.gradle для java8 и java11

## [3.1.2]() (20-03-2019)

* Исправлена авторизация в гите по ssh

## [3.1.1]() (11-03-2019)

* Поднял версию `yamoney-check-dependencies-plugin` до `4.4.3`

## [3.1.0]() (07-03-2019)

* Подняла версию `yamoney-check-dependencies-plugin` до `4.4.2`

## [3.0.0]() (05-03-2019)

* Переход на yamoney-artifact-release-plugin
* Поменялся релизный цикл, теперь вместо release, в мастере надо вызвать preRelease и release
* Поменялся формат CHANGELOG.md подробности в https://bitbucket.yamoney.ru/projects/BACKEND-GRADLE-PLUGINS/repos/artifact-release-plugin/browse/README.md

## [2.1.0]() (26-02-2019)

* Переход на platformGradleProjectVersion 4 версии

## [2.0.15]() (21-02-2019)

* Поднял версию `yamoney-check-dependencies-plugin` до `4.1.0`

## [2.0.14]() (12-02-2019)

Совместимость со сборкой под Java 11

## [2.0.13]() (16-01-2019)

Изменена версия check-dependencies-plugin на 4.0.3

## [2.0.12]() (17-12-2018)

Поправила kotlin-test.gradle 

## [2.0.11]() (13-12-2018)

Изменила kotlinVersion 1.2.71 -> 1.2.61

## [2.0.10]() (13-12-2018)

Подняла версию check-dependencies-plugin

## [2.0.9]() (06-12-2018)

Починила скрипт build-check-version.gradle

## [2.0.8]() (06-12-2018)

Добавил kotlin-test

## [2.0.7]() (05-12-2018)

* Добавил `GitExpiredBranchPlugin`

## [2.0.6]() (30-11-2018)

* Плагин требует gradle >= 4.10.2

## [2.0.5]() (27-11-2018)

* Поправил условие выпуска релиза

## [2.0.3]() (19-11-2018)

Обновил check-dependencies-plugin

## [2.0.2]() (14-11-2018)

Сборка при помощи gradle-project-plugin=2.x

## [2.0.1]() (14-11-2018)

Сборка при помощи gradle-project-plugin

## [2.0.0]() (03-11-2018)

Скрипты build-scripts/backend-platform/build-common-without-plugins.gradle и все включаемые скрипты перенесены 
в папку gradle-scripts и публикуются в zip артефакт в целях версионирования.

Минимальная конфигурация выглядит так:

build.gradle:
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

groupIdSuffix = "common"
artifactID = "yamoney-json-utils"

dependencies {
    compile 'com.fasterxml.jackson.core:jackson-annotations:2.9.0'
}
```

project.gradle:
```groovy
System.setProperty("platformLibraryProjectVersion", "2.+")
System.setProperty("platformDependenciesVersion", "3.+")

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
    }
}
```

## [1.2.0]() (03-08-2017)

Изменена версия check-dependencies-plugin на 2.3.0

## [1.1.4]() (16-05-2017)

Изменена версия gradle-release-plugin на 1.2.0

## [1.1.3]() (19-04-2017)

Обновлена версия плагина check-dependencies-plugin на 2.2.2

## [1.1.2]() (14-04-2017)

Обновлена версия плагина check-dependencies-plugin на 2.2.1

## [1.1.1]() (10-04-2017)

Добавлено распознавание релизных бранчей с именем в формате tags/several-words-delimited-by-dash-N.N.N(.N)

## [1.1.0]() (30-03-2017)

Обновлена версия плагина check-dependencies-plugin на 2.2.0

## [1.0.1]() (17-03-2017)

Обновлена версия плагина check-dependencies-plugin на 2.0.0
Реализовано подключение SNAPSHOT-репозиториев только в том случае, если ветка не релизная

## [1.0.0]() (16-03-2017)

Изменена структура проекта: разработка каждого плагина теперь осуществляется в отдельном репозитории.
Зависимости к плагинам прописываются в билд-скрипте.

## [0.4.5]() (22-03-2017)

Подключаем в проект SNAPSHOT-репозитории, только если текущая ветка не релизная
Ветка считается релизной, если:
 - имя ветки равно 'dev', 'master'
 - имя ветки начинается с 'release/'
 - имя ветки имеет формат 'tags/N.N.N' или 'tags/N.N.N.N'
 - HEAD ветки ссылается на tag-ветку

## [0.4.4]() (21-03-2017)

убрал snapshot из Repository для резолва

## [0.4.3]() (21-02-2017)

Изменено умолчательное имя файла с правилами изменений версий для слуая, когда правла находятся в зип артефакте

## [0.4.2]() (14-02-2017)

Изменено умолчательное имя файла с правилами изменений версий

## [0.4.1]() (14-02-2017)

Исправлена ошибка проверки CheckChangelog, в результате которой неправильно определелялась версия проекта

## [0.4.0]() (10-02-2017)

Улучшена поддержка указания правил исключений проверки изменений версий библиотек в плагине CheckDependenciesPlugin. 
  * Добавлена поддержка чтения файла с правилами из Maven артефакта.
  * Добавлена настройка "excludedConfigurations" позволяющая исключить из проверки указанные конфигурации
  * Теперь указывается список источников, содержащих файлы с правилами. В списке можно указывать: либо путь к файлу локальной 
    системы, либо название мавен артефакта, содержащего файл "libraries_versions_exclusions.properties". При этом допускается 
    указание нескольких файлов с правилами и артефактами. В этом случае правила будут объединяться.

## [0.3.1]() (04-02-2017)

Добавлена поддержка правил исключений проверки изменений версий библиотек в плагине CheckDependenciesPlugin. Теперь опционально 
можно указать для каких библиотек разрешается изменение версий. При этом жестко прописывается: с какой версии на какую разрешено 
изменение.

## [0.3.0]() (01-02-2017)

Добавлена функциональность проверки согласованности версий используемых библиотек с версиями библиотек, зафиксированными в 
стороннем плагине "IO Spring Dependency Management Plugin"

## [0.2.0]() (18-01-2017)

Добавлена функциональность проверки CHANGELOG.md файла на наличие записи о текущей версии библиотеки.

## [0.1.0]() (22-12-2016)

Создана черновая версия общего плагина для библиотек.
Добавлена первая функциональность: публикация readme файла.