package ru.yandex.money.gradle.plugins.library.helpers;

import org.ajoberstar.grgit.Grgit;

import java.util.regex.Pattern;

/**
 * Класс для получения имени тега текущей ветки репозитория
 *
 * @author Konstantin Novokreshchenov (knovokresch@yamoney.ru)
 * @since 21.03.2017
 */
class GitTag {
    private static final String TAGS_PREFIX = "tags/";
    private static final Pattern RELEASE_TAG_NAME_FORMAT = Pattern.compile("\\d+\\.\\d+\\.\\d+(\\.\\d+)?");
    private final String name;

    /**
     * Получает имя тега на основании имени текущей ветки
     *
     * @param grgit объект для работы с репозиторием
     * @return объект, описывающий имя тега
     */
    static GitTag fromCurrentBranch(Grgit grgit) {
        String branchName = grgit.getBranch().getCurrent().getName();
        String tagName = branchName.startsWith(TAGS_PREFIX) ? branchName.substring(TAGS_PREFIX.length()) : "";

        return new GitTag(tagName);
    }

    /**
     * Получаем имя тега на основании значения HEAD-указателя текущей ветки репозитория
     *
     * @return объект, описывающий имя тега
     */
    static GitTag fromHead() {
        String valueOfHead = Shell.execute("git", "symbolic-ref", "-q", "--short", "HEAD");
        if (valueOfHead != null && valueOfHead.length() > 0) {
            return new GitTag(valueOfHead);
        }

        String tagNameOnHead = Shell.execute("git", "describe", "--tags", "--exact-match");
        return new GitTag(tagNameOnHead);
    }

    private GitTag(String name) {
        this.name = name;
    }

    boolean isRelease() {
        return RELEASE_TAG_NAME_FORMAT.matcher(name).matches();
    }
}
