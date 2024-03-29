### NEXT_VERSION_TYPE=MAJOR|MINOR|PATCH
### NEXT_VERSION_DESCRIPTION_BEGIN
### NEXT_VERSION_DESCRIPTION_END
## [7.3.1](https://github.com/yoomoney/library-project-plugin/pull/9) (16-11-2021)

Обновлена версия java-plugin 4.1.0 -> 4.3.0

## [7.3.0](https://github.com/yoomoney/library-project-plugin/pull/8) (26-08-2021)

* Переезд организации yoomoney-gradle-plugins -> yoomoney

## [7.2.1](https://github.com/yoomoney/library-project-plugin/pull/7) (03-08-2021)

* Обновлена версия java-artifact-publish-plugin с 3.2.0 на 3.2.1

## [7.2.0](https://github.com/yoomoney/library-project-plugin/pull/6) (30-06-2021)

* Конфигурация публикации артефакта в staging репозиторий перенесена в `java-artifact-publish-plugin`;
* Обновлена версия `java-artifact-publish-plugin` 3.0.2 -> 3.2.0.

## [7.1.2](https://github.com/yoomoney/library-project-plugin/pull/5) (19-05-2021)

* Добавлена информация о сборке, покрытии, лицензии в README.md.
* Добавлен файл coverage.properties для контроля тестового покрытия.

## [7.1.1](https://github.com/yoomoney/library-project-plugin/pull/4) (19-05-2021)

* Работа с groupId вынесена из afterEvaluate.

## [7.1.0](https://github.com/yoomoney/library-project-plugin/pull/3) (26-04-2021)

* Работа с publishExtension вынесена из afterEvaluate.

## [7.0.1](https://github.com/yoomoney/library-project-plugin/pull/2) (22-04-2021)

* Внесены правки в readme.md

## [7.0.0](https://github.com/yoomoney/library-project-plugin/pull/1) (22-04-2021)

* **breaking changes** Внесены изменения в связи с переходом в GitHub:
* Переименованы пакеты с yandex/money -> yoomoney
* Сборка переведена на travis (ранее использовался jenkins)


## [6.9.0]() (30-03-2021)

* Поднята версия ru.yandex.money.gradle.plugins:yoomoney-module-project-plugin: 2.0.1 -> 2.1.0

## [6.8.0]() (30-03-2021)

* Обновлена версия artifact-release-plugin с 3.8.1 на 3.11.1

## [6.7.0]() (26-03-2021)

* Добавлена возможность публикации docker образов в dev и stable. Подробнее см. README "Публикация артефакта"

## [6.6.0]() (12-03-2021)

* Переключение на плагин java-artifact-publish-plugin из github: ru.yandex.money.gradle.plugins -> ru.yoomoney.gradle.plugins.

## [6.5.0]() (18-02-2021)

* Подключение java-module-plugin и artifact-dependencies-plugin заменено на подключение
[module-project-plugin]()

## [6.4.0]() (10-02-2021)

* Поднята версия yamoney-architecture-test-plugin 2.4.0 -> 2.5.0

## [6.3.0]() (04-02-2021)

* Поднята версия yamoney-artifact-release-plugin 3.6.0 -> 3.8.1

## [6.2.0]() (25-01-2021)

* Переключение на плагин artifact-release-plugin из github: ru.yamoney.gradle.plugins -> ru.yoomoney.gradle.plugins

## [6.1.0]() (20-01-2021)

* Поднята версия yamoney-artifact-release-plugin 3.2.0 -> 3.3.2
* Поднята версия yamoney-java-artifact-publish-plugin 2.2.0 -> 2.3.2
* Поднята версия yamoney-java-module-plugin 3.3.0 -> 3.2.1
* Поднята версия yamoney-build-monitoring-plugin:3.3.1

## [6.0.0]() (18-01-2021)

* Поднята версия yamoney-architecture-test: 4.6.1 -> 6.0.1

## [5.7.0]() (14-01-2021)

* Поднята версия yamoney-architecture-test-plugin: 2.3.1 -> 2.4.0

## [5.6.2]() (10-12-2020)

* Поднята версия yamoney-git-expired-branch-plugin: 5.2.0 -> 5.2.2

## [5.6.1]() (23-11-2020)

* Замена доменов email @yamoney.ru -> @yoomoney.ru

## [5.6.0]() (28-10-2020)

* Обновлена версия yamoney-architecture-test-plugin: 2.3.0 -> 2.3.1

## [5.5.0]() (13-10-2020)

* Явно задана версия арх. тестов -> 4.6.1
* Включены правила `check_api_allowable_values_contract_in_request_response_properties` и
`check_override_tostring_in_request_response_model_classes`

## [5.4.2]() (07-07-2020)

* Поднята версия yamoney-java-artifact-publish-plugin 2.0.0 -> 2.2.0
* Подняты версия yamoney-java-artifact-publish-plugin 5.1.1 -> 5.2.0
* Подняты версия yamoney-java-module-plugin 2.0.0 -> 2.8.0
* Подняты версия yamoney-build-monitoring-plugin 3.0.0 -> 3.3.0
* Подняты версия yamoney-architecture-test-plugin 2.2.0 -> 2.3.0

## [5.4.1]() (07-07-2020)

* Принудительное обновление версии yamoney-gradle-project-plugin

## [5.4.0]() (03-07-2020)

* Поднята версия gradle: 6.0.1 -> 6.4.1.

## [5.3.0]() (18-06-2020)

* Поднята версия architecture-test-plugin 2.0.2 -> 2.2.0.

## [5.2.2]() (08-04-2020)

* Откат версии java-module-plugin 2.3.0 -> 2.0.0 из-за конфликта в зависимостях. Versions conflict used libraries with fixed platform libraries.
org.slf4j:slf4j-simple:1.8.0-beta4 -> 1.7.25

## [5.2.1]() (08-04-2020)

* Поднята версия java-module-plugin 2.0.0 -> 2.3.0

## [5.2.0]() (27-02-2020)

* Поднята версия yamoney-artifact-release-plugin: 3.0.0 -> 3.2.0.
Добавление ссылки на bitbucket pull request в changelog при релизе.

## [5.1.1]() (06-02-2020)

* Поднята версия git-expired-branch-plugin 5.0.0 -> 5.1.1

## [5.1.0]() (05-02-2020)

* Сборка на java 11

## [5.0.2]() (05-02-2020)

* Поднята версия architecture-test-plugin 2.0.0 -> 2.0.2

## [5.0.1]() (03-02-2020)

* Требуемая версия gradle изменена `4.10.2` -> `6.0.1`

## [5.0.0]() (30-01-2020)

* Обновлена версия gradle `4.10.2` -> `6.0.1`
* Обновлены версии зависимостей
* Исправлены warnings и checkstyle проблемы

## [4.12.0]() (10-01-2020)

* Обновлён `yamoney-architecture-test-plugin`=`1.5.0`->`1.6.0`

## [4.11.0]() (29-11-2019)

* Обновление версии yamoney-git-expired-branch-plugin 4.1.1 -> 4.2.1

## [4.10.3]() (27-11-2019)

* Обновлена зависимость `yamoney-java-module-plugin` `1.18.0` -> `1.18.1`

## [4.10.2]() (27-11-2019)

* Обновлена зависимость `yamoney-java-module-plugin` `1.17.1` -> `1.18.0`

## [4.10.1]() (26-11-2019)

* Обновлена зависимость ```yamoney-java-module-plugin``` ```4.4.5``` -> ```1.17.1```

## [4.10.0]() (22-11-2019)

* Обновлена версия `java-module-plugin` 1.16.0 -> 1.17.0

## [4.9.0]() (08-11-2019)

* Обновлена версия yamoney-java-module-plugin 1.15.3 -> 1.16.0

## [4.8.3]() (01-11-2019)

* Поднята версия yamoney-java-module-plugin 1.15.2 -> 1.15.3

## [4.8.2]() (28-10-2019)

* Поднятие версии yamoney-java-module-plugin 1.15.1 -> 1.15.2

## [4.8.1]() (23-10-2019)

* Поднятие версии yamoney-java-module-plugin 1.14.3 -> 1.15.1

## [4.8.0]() (22-10-2019)

* Подключен yamoney-java-artifact-publish плагин
* Удалена логика, запрещающая зависимость api от api

## [4.7.6]() (21-10-2019)

* Поднятие версии yamoney-git-expired-branch-plugin 4.0.3 -> 4.1.1

## [4.7.5]() (30-09-2019)

* Исправлено добавление исходников котлин в sources.jar

## [4.7.4]() (27-09-2019)

* Поднятие версии yamoney-git-expired-branch-plugin 4.0.2 -> 4.0.3

## [4.7.3]() (05-09-2019)

* Обновлена версия yamoney-java-module-plugin `1.14.2` -> `1.14.3`, в которой отключены следующие паттерны в spotbug:
1. OCP_OVERLY_CONCRETE_PARAMETER - метод декларирует в аргументах конкретные классы, хотя в его теле используются только методы, определенные в супер-классе или в реализуемом интерфейсе.
2. CE_CLASS_ENVY - метод активнее использует методы другого класса, по сранению с методами своего собственного.
3. CC_CYCLOMATIC_COMPLEXITY - метод имеет высокий показатель цикломатической сложности.

## [4.7.2]() (01-08-2019)

* Поднятие версии yamoney-git-expired-branch-plugin 4.0.1 -> 4.0.2

## [4.7.1]() (30-07-2019)

* Обновлён `yamoney-build-monitoring-plugin`=`2.0.0`->`2.0.1`,
изменены ключи отправляемых метрик

## [4.7.0]() (29-07-2019)

* Обновлён `yamoney-build-monitoring-plugin`=`1.3.0`->`2.0.0`,
с отправкой метрик в продакшен графану
* Обновлён `yamoney-architecture-test-plugin`=`1.4.0`->`1.5.0`,
с обновлением наборов тестов

## [4.6.6]() (16-07-2019)

* Обновлена версия java-module-plugin `1.14.2`, в которой исправлен баг с проставлением timestamp
при локальном автоподнятии coverage

## [4.6.5]() (12-07-2019)

* Повышена версия java-module-plugin `1.14.1`
* Новая версия инициирует автоподнятие coverage при увеличении тестового покрытия при локальной сборке
* В новой версии исправлено отключение findbugs при удалении лимита findbugs static-analysis.properties

## [4.6.4]() (05-07-2019)

* Обновлена версия yamoney-artifact-release-plugin=2.0.0 -> 2.1.0,
для исправления автора коммита при локальной сборке

## [4.6.3]() (04-07-2019)

* Повышена версия java-module-plugin `1.13.1`
* В новой версии исправлена проблема с отсутствием информации об ошибках компиляции в консоле

## [4.6.2]() (03-07-2019)

* Обновлена версия java-module-plugin до `1.12.1`

## [4.6.1]() (03-07-2019)

* Обновлены версии
* git-expired-branch-plugin до `4.0.0`
* artifact-release-plugin до `2.0.0`
Этим плагинам добавлено проставление настроек для git - user и email.

## [4.6.0]() (02-07-2019)

* Поднята версия artifact-release-plugin 1.4.3 -> 1.4.5

## [4.5.0]() (26-06-2019)

* Поднята версия build-monitoring-plugin 1.2.0 -> 1.3.0

## [4.4.1]() (20-06-2019)

* Поднята версия java-module-plugin 1.11.1 -> 1.12.0

## [4.4.0]() (13-06-2019)

* Включен yamoney-build-monitoring-plugin

## [4.3.0]() (11-06-2019)

* Обновлена версия yamoney-architecture-test-plugin
* По умолчанию включены для выполнения следующие тесты:
'check_unique_enums_codes'
'check_unique_enums_secondary_codes'

## [4.2.0]() (03-06-2019)

* Обновлена версия java-module-plugin до 1.11.1

## [4.1.0]() (01-06-2019)

* Поднята версия java-module-plugin, в которой появилась таска componentTest

## [4.0.2]() (27-05-2019)

* Обновлены зависимости, основное - `yamoney-java-module-plugin`, где доработана поддержка котлина для тестов

## [4.0.1]() (20-05-2019)

* Обновлена версия `yamoney-java-module-plugin`, в которой отколючен errorprone

## [4.0.0]() (17-05-2019)

* Полностью удалены скрипты сборки
* Изменился способ подключения плагина, изменённый build.gradle выглядит так:
```groovy
buildscript {
apply from: 'project.gradle', to: buildscript
}
apply plugin: 'yamoney-library-project-plugin'
```

## [3.7.3]() (16-05-2019)

* Обновлён yamoney-java-module-plugin, с округлением coverage до целых чисел

## [3.7.2]() (16-05-2019)

* Патч совместимости плагина с генераторами кода

## [3.7.1]() (15-05-2019)

* Убрал snapshot зависимость

## [3.7.0]() (14-05-2019)

* Обновлён `yamoney-java-module-plugin`, с новым плагином для Kotlin

## [3.6.0]() (14-05-2019)

* Обновлён yamoney-java-module-plugin, с подключенным статическим анализатором `SpotBugs`

## [3.5.2]() (14-05-2019)

* Добавлен репозиторий с Gradle плагинами

## [3.5.1]() (08-05-2019)

* Откат изменений с подключением `SpotBugs`

## [3.5.0]() (08-05-2019)

* Обновлён yamoney-java-module-plugin, с подключенным статическим анализатором `SpotBugs`

## [3.4.5]() (08-05-2019)

* Сборка переведена на yamoney-gradle-project-plugin=5.+
* Обновлён yamoney-java-module-plugin

## [3.4.4]() (08-05-2019)

* Фикс с округлением coverage

## [3.4.3]() (07-05-2019)

* Все изменения вернулись обратно, исправлена сборка и публикация артефактов

## [3.4.2]() (29-04-2019)

* Реверт изменений по переносу скриптов в код

## [3.4.1]() (26-04-2019)

* Поправил сборку артефактов

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
* Поменялся формат CHANGELOG.md подробности в 

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