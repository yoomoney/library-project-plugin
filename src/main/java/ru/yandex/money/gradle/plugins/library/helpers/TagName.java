package ru.yandex.money.gradle.plugins.library.helpers;

import java.util.regex.Pattern;

/**
 * Класс для получения имени тега текущей ветки репозитория
 *
 * @author Konstantin Novokreshchenov (knovokresch@yamoney.ru)
 * @since 21.03.2017
 */
public class TagName {
    private static final Pattern RELEASE_TAG_NAME_FORMAT = Pattern.compile("\\d+\\.\\d+\\.\\d+(\\.\\d+)?");
    private final String name;

    TagName(String name) {
        this.name = name;
    }

    public boolean isRelease() {
        return RELEASE_TAG_NAME_FORMAT.matcher(name).matches();
    }
}
