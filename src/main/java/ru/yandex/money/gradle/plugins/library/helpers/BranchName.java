package ru.yandex.money.gradle.plugins.library.helpers;

import java.util.regex.Pattern;

/**
 * Предоставляет информацию об имени ветки, в частности, является ли ветка master-веткой, dev-веткой,
 * релизной веткой или рабочей веткой
 *
 * @author Konstantin Novokreshchenov (knovokresch@yamoney.ru)
 * @since 22.03.2017
 */
public class BranchName {
    private static final String MASTER_BRANCH_NAME = "master";
    private static final String DEV_BRANCH_NAME = "dev";
    private static final String TAGS_PREFIX = "tags/";
    private static final Pattern RELEASE_BRANCH_PATTERN = Pattern.compile("release/.*");

    private final String name;

    /**
     * Конструктор класса
     *
     * @param name имя ветки
     */
    BranchName(String name) {
        this.name = name;
    }

    /**
     * Показывает, является ли ветка master веткой или нет.
     *
     * @return true, если является, false - если нет.
     */
    public boolean isMaster() {
        return name.equalsIgnoreCase(MASTER_BRANCH_NAME);
    }

    /**
     * Показывает, является ли ветка dev веткой или нет.
     *
     * @return true, если является, false - если нет.
     */
    public boolean isDev() {
        return name.equalsIgnoreCase(DEV_BRANCH_NAME);
    }

    /**
     * Показывает, является ли ветка релизной или нет.
     *
     * @return true, если является, false - если нет.
     */
    public boolean isRelease() {
        return RELEASE_BRANCH_PATTERN.matcher(name).find();
    }

    /**
     * Показывает, является ли ветка tag-релизной или нет.
     *
     * @return true, если содержит, false - если нет.
     */
    public boolean isTagRelease() {
        if (name.startsWith(TAGS_PREFIX)) {
            String tagName = name.substring(TAGS_PREFIX.length());
            return new TagName(tagName).isRelease();
        }
        return false;
    }
}
