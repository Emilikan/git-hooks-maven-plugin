# git-hooks-maven-plugin #

[![MIT Licence][licence-image]][licence-url]

## Краткое описание <a name="desc"></a> ##

Репозиторий с настроенными git хуками и maven плагином, который эти git хуки автоматически установит. 
Подключи в проект и автоматически получи для всех разработчиков едино-настроенные git хуки.

## Доступные скрипты
1) ```ktlint-check``` - скрипт запускает проверку mvn ktlint:check. 
Если плагин ktlint в проекте не установлен - проекрка будет пропущена. Если ktlint завершится с ошибкой 
(найдет несоответствие кода с правилами) - скрипт выдаст ошибку и выполняемая фаза git-а будет прервана
2) ```ktlint-format``` - скрипт запускает форматирование кода mvn ktlint:format.
   Если плагин ktlint в проекте не установлен - проекрка будет пропущена

## Подключить плагин
В проекте для подключения плагина в pom.xml

```xml
<build>
    <plugins>
        <plugin>
            <groupId>ru.qiwi.ao-common</groupId>
            <artifactId>ao-common-git-hooks-maven-plugin</artifactId>
            <version>${git-hooks-maven-plugin.version}</version>
            <executions>
                <execution>
                    <goals>
                        <goal>install-hooks</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <hooks>
                    <hook>
                        <name>pre-commit</name>
                        <scripts>
                            <script>check-platform-version</script>
                        </scripts>
                    </hook>
                </hooks>
            </configuration>
        </plugin>
    </plugins>
</build>
```

В настройках укажите имя хука и скрипты, которые нужно выполнить в этом хуке. Скриптов может быть несколько (см пример)
 
> Для пропуска установки хуков можно использовать maven параметр ```-Dgithookinstaller.install.skip=true```

## Особенности реализации <a name="implementation"></a> ##

> Скрипты накатываются в фазе INITIALIZE после выполнения команды ЖЦ maven. 
Например, mvn install, mvn compile и тд 

По пути resources/hooks лежат git хуки, настроенные на работу с несколькими скриптами

Пу пути resources/scripts лежат все скрипты, которые можно использовать с разными гит хуками

Плагин перенесет хуки из ```resources/hooks``` и скрипты из ```resources/scripts``` в ```<project_dir>/.git/hooks```. 
Также там же будет создан файл <hook_name>-config в котором будет лежать список скриптом для каждого хука. 
Далее git автоматически вызовет хуки, которые на основании конфига вызовут те или иные скрипты 

### Для добавления нового скрипта
1) Добавить скрипт в resources/scripts
2) Занести информацию в enum ScriptName, указать название файла
3) Коротко описать скрипт в README.md

### Для добавления нового хука
1) Добавить скрипт в resources/hooks
2) Наполнение скопировать из существующего хука (например, pre-commit)
3) Изменить имя конфиг файла в скрипте

Для ```pre-commit``` - указать ```pre-commit-config```
```
CONFIG_FILE="$GIT_DIR/hooks/pre-commit-config"
```

Для ```pre-push``` - указать ```pre-push-config```
```
CONFIG_FILE="$GIT_DIR/hooks/pre-push-config"
```
И тд по аналогии
4) Занести информацию в enum HookName, указать название файла

## Инструменты для разработчиков <a name="dev-tools"></a> ##

Установить версию библиотеки локально
```bash
mvn clean install 
```

## Ссылки <a name="links"></a> ##
[Документация maven по плагинам](https://maven.apache.org/guides/plugin/guide-java-plugin-development.html#Plugin_Naming_Convention_and_Apache_Maven_Trademark)

[licence-image]: http://img.shields.io/npm/l/gulp-rtlcss.svg?style=flat
[licence-url]: https://tldrlegal.com/license/mit-license