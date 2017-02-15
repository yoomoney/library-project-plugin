package ru.yandex.money.gradle.plugins.library.dependencies

import ru.yandex.money.gradle.plugins.library.AbstractPluginSpec

/**
 * Функциональные тесты для CheckDependenciesPlugin, проверяющего корректность изменения версий используемых библиотек в проекте.
 *
 * @author Brovin Yaroslav (brovin@yamoney.ru)
 * @since 30.01.2017
 */
class CheckDependenciesPluginSpec extends AbstractPluginSpec {

    def "success check without fixing library versions"() {
        given:
        buildFile << """
                buildscript {
                    repositories {
                        maven { url 'http://nexus.yamoney.ru/content/repositories/central/' }
                        maven { url 'http://nexus.yamoney.ru/content/repositories/releases/' }
                    }
                    dependencies {
                        classpath 'io.spring.gradle:dependency-management-plugin:0.6.1.RELEASE'
                    }
                }
                """.stripIndent()

        when:
        def result = runTasksSuccessfully(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)

        then:
        !result.wasSkipped(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
        result.wasExecuted(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
    }

    def "success check on empty project libraries and fixed versions from Spring IO platform"() {
        given:
        buildFile << """
                dependencyManagement {                    
                    // Фиксируем версии библиотек из pom.xml файла
                    imports {
                        mavenBom 'io.spring.platform:platform-bom:2.0.6.RELEASE'
                    }
                }
                """.stripIndent()

        when:
        def result = runTasksSuccessfully(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)

        then:
        !result.wasSkipped(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
        result.wasExecuted(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
    }

    def "success check on project libraries and empty fixed versions list"() {
        given:
        buildFile << """
                dependencyManagement {                
                    // Запрещаем переопределять версии библиотек в обычной секции Gradle dependency
                    overriddenByDependencies = false
                }
                
                dependencies {
                    compile 'org.springframework:spring-core:4.2.5.RELEASE'
                    compile 'org.hamcrest:hamcrest-core:1.2'
                    
                    testCompile group: 'junit', name: 'junit', version: '4.11'
                }
                """.stripIndent()

        when:
        def result = runTasksSuccessfully(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)

        then:
        !result.wasSkipped(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
        result.wasExecuted(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
    }

    def "fail check on conflicted versions between fixed versions in IO platform and project dependencies section in libraries"() {
        given:
        buildFile << """
                dependencyManagement {                
                    // Запрещаем переопределять версии библиотек в обычной секции Gradle dependency
                    overriddenByDependencies = false
                    
                    // Фиксируем версии библиотек из pom.xml файла
                    imports {
                        mavenBom 'io.spring.platform:platform-bom:2.0.6.RELEASE'
                    }
                }
                
                // Специально определяем версии библиотек, которые конфликтуют с зафиксированными
                dependencies {
                    // Ожидается 4.2.7.RELEASE
                    compile 'org.springframework:spring-core:4.2.5.RELEASE'
                    // Ожидается 1.3
                    compile 'org.hamcrest:hamcrest-core:1.2'
                
                    // Ожидается 4.12
                    // Использует org.hamcrest:hamcrest-core:1.3
                    testCompile group: 'junit', name: 'junit', version: '4.11'
                }
                """.stripIndent()

        when:
        def result = runTasksWithFailure(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)

        then:
        result.failure
    }

    def "fail check on project libraries and fixed versions, which are override in build script"() {
        given:
        buildFile << """
                dependencyManagement {                
                    // Запрещаем переопределять версии библиотек в обычной секции Gradle dependency
                    overriddenByDependencies = false
                    
                    // Фиксируем версии библиотек из pom.xml файла
                    imports {
                        mavenBom 'io.spring.platform:platform-bom:2.0.6.RELEASE'
                    }
                    
                    dependencies {
                        dependency 'org.springframework:spring-core:4.2.5.RELEASE'
                        dependency 'org.hamcrest:hamcrest-core:1.2'
                    }
                    
                    testCompile {
                        dependencies {
                            dependency 'junit:junit:4.11'
                            dependency 'org.hamcrest:hamcrest-core:1.3' // platform fixed 1.2
                        }
                    }                    
                }
                
                dependencies {
                    // Ожидается 4.2.5.RELEASE
                    compile 'org.springframework:spring-core:4.2.5.RELEASE'
                    // Ожидается 1.2
                    compile 'org.hamcrest:hamcrest-core:1.2'
                
                    // Ожидается 4.11
                    // Использует org.hamcrest:hamcrest-core:1.3
                    testCompile group: 'junit', name: 'junit', version: '4.11'
                }            
            """.stripIndent()
        when:
        def result = runTasksWithFailure(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)

        then:
        result.failure
    }

    def "success check on project libraries and fixed versions and rules of changing libraries versions"() {
        given:
        def exclusionFile = new File(projectDir, 'exclusion.properties')
        exclusionFile<<"""
            org.hamcrest.hamcrest-core = 1.2 -> 1.3
        """.stripIndent()

        buildFile << """
                dependencyManagement {                
                    // Запрещаем переопределять версии библиотек в обычной секции Gradle dependency
                    overriddenByDependencies = false
                    
                    // Фиксируем версии библиотек из pom.xml файла
                    imports {
                        mavenBom 'io.spring.platform:platform-bom:2.0.6.RELEASE'
                    }
                    
                    dependencies {
                        dependency 'org.springframework:spring-core:4.2.5.RELEASE'
                        dependency 'org.hamcrest:hamcrest-core:1.2'
                    }
                    
                    testCompile {
                        dependencies {
                            dependency 'junit:junit:4.11'
                            dependency 'org.hamcrest:hamcrest-core:1.3' // platform fixed 1.2
                        }
                    }                    
                }
                
                // Указываем путь к файлу с разрешающими правилами изменения версий библиотек
                checkDependencies {
                    exclusionsRulesSources = ['$exclusionFile.absolutePath',
                                              "ru.yandex.money.platform:platform-dependencies:"]
                }
                
                dependencies {
                    // Ожидается 4.2.5.RELEASE
                    compile 'org.springframework:spring-core:4.2.5.RELEASE'
                    // Ожидается 1.2
                    compile 'org.hamcrest:hamcrest-core:1.2'
                
                    // Ожидается 4.11
                    // Использует org.hamcrest:hamcrest-core:1.3
                    testCompile group: 'junit', name: 'junit', version: '4.11'
                }            
            """.stripIndent()
        when:
        def result = runTasksSuccessfully(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)

        then:
        !result.wasSkipped(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
        result.wasExecuted(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
    }

    def "success check on empty project libraries and wrong exclusions: empty param" () {
        given:
        buildFile << """
                buildscript {
                    repositories {
                        maven { url 'http://nexus.yamoney.ru/content/repositories/central/' }
                        maven { url 'http://nexus.yamoney.ru/content/repositories/releases/' }
                    }
                    dependencies {
                        classpath 'io.spring.gradle:dependency-management-plugin:0.6.1.RELEASE'
                    }
                }
                
                // Не указываем файлы
                checkDependencies {
                    exclusionsRulesSources = []
                }
                """.stripIndent()

        when:
        def result = runTasksSuccessfully(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)

        then:
        !result.wasSkipped(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
        result.wasExecuted(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
    }

    def "success check on empty project libraries and wrong exclusions: null param" () {
        given:
        buildFile << """
                buildscript {
                    repositories {
                        maven { url 'http://nexus.yamoney.ru/content/repositories/central/' }
                        maven { url 'http://nexus.yamoney.ru/content/repositories/releases/' }
                    }
                    dependencies {
                        classpath 'io.spring.gradle:dependency-management-plugin:0.6.1.RELEASE'
                    }
                }
                
                // Указываем null список
                checkDependencies {
                    exclusionsRulesSources = null
                }
                """.stripIndent()

        when:
        def result = runTasksSuccessfully(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)

        then:
        !result.wasSkipped(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
        result.wasExecuted(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
    }

    def "success check on empty project libraries and wrong exclusions: wrong file param" () {
        given:
        buildFile << """
                buildscript {
                    repositories {
                        maven { url 'http://nexus.yamoney.ru/content/repositories/central/' }
                        maven { url 'http://nexus.yamoney.ru/content/repositories/releases/' }
                    }
                    dependencies {
                        classpath 'io.spring.gradle:dependency-management-plugin:0.6.1.RELEASE'
                    }
                }
                
                // Указываем путь к несуществующему файлу
                checkDependencies {
                    exclusionsRulesSources = ["not_existed_file.properties"]
                }
                """.stripIndent()

        when:
        def result = runTasksSuccessfully(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)

        then:
        !result.wasSkipped(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
        result.wasExecuted(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
    }

    def "success check on empty project libraries and wrong exclusions: wrong artifact param" () {
        given:
        buildFile << """
                buildscript {
                    repositories {
                        maven { url 'http://nexus.yamoney.ru/content/repositories/central/' }
                        maven { url 'http://nexus.yamoney.ru/content/repositories/releases/' }
                    }
                    dependencies {
                        classpath 'io.spring.gradle:dependency-management-plugin:0.6.1.RELEASE'
                    }
                }
                
                // Указываем путь к несуществующему файлу
                checkDependencies {
                    exclusionsRulesSources = ["ru.yandex.fakegroup:fakeartifact:"]
                }
                """.stripIndent()

        when:
        def result = runTasksSuccessfully(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)

        then:
        !result.wasSkipped(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
        result.wasExecuted(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
    }

    def "success check on project libraries and excluded compile configuration" () {
        given:
        buildFile << """
                buildscript {
                    repositories {
                        maven { url 'http://nexus.yamoney.ru/content/repositories/central/' }
                        maven { url 'http://nexus.yamoney.ru/content/repositories/releases/' }
                    }
                    dependencies {
                        classpath 'io.spring.gradle:dependency-management-plugin:0.6.1.RELEASE'
                    } 
                }
                
                dependencyManagement {                
                    // Запрещаем переопределять версии библиотек в обычной секции Gradle dependency
                    overriddenByDependencies = false
                    
                    // Фиксируем версии библиотек из pom.xml файла
                    imports {
                        mavenBom 'io.spring.platform:platform-bom:2.0.6.RELEASE'
                    }   
                    
                    dependencies {
                        dependency 'org.springframework:spring-core:4.2.5.RELEASE'
                        dependency 'org.hamcrest:hamcrest-core:1.2'
                    }
                    
                    testCompile {
                        dependencies {
                            dependency 'junit:junit:4.11'
                            dependency 'org.hamcrest:hamcrest-core:1.3' // platform fixed 1.2
                        }
                    }                               
                }                
                
                // Указываем путь к несуществующему файлу
                checkDependencies {
                    excludedConfigurations = ["testCompile", "testRuntime"]
                }
                                
                dependencies {
                    // Ожидается 4.2.5.RELEASE
                    compile 'org.springframework:spring-core:4.2.5.RELEASE'
                    // Ожидается 1.2
                    compile 'org.hamcrest:hamcrest-core:1.2'
                
                    // Ожидается 4.11
                    // Использует org.hamcrest:hamcrest-core:1.3
                    testCompile group: 'junit', name: 'junit', version: '4.11'
                }   
                """.stripIndent()

        when:
        def result = runTasksSuccessfully(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)

        then:
        !result.wasSkipped(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
        result.wasExecuted(CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME)
    }
}