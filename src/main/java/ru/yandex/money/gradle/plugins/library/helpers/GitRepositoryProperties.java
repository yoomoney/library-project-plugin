package ru.yandex.money.gradle.plugins.library.helpers;

import org.ajoberstar.grgit.Commit;
import org.ajoberstar.grgit.Grgit;
import org.ajoberstar.grgit.Tag;
import org.ajoberstar.grgit.operation.OpenOp;
import org.ajoberstar.grgit.operation.TagListOp;

import java.util.List;
import java.util.Optional;

/**
 * Утилитный класс для получения свойств git-репозитория.
 *
 * @author Kirill Bulatov (mail4score@gmail.com)
 * @author Konstantin Rashev (rashev@yamoney.ru)
 * @since 22.12.2016
 */
public class GitRepositoryProperties {
    private final Grgit grgit;

    /**
     * Конструктор класса. Инициализирует работу с git-репозиторием.
     * Поиск git-репозитория начинается с директории, указанной в baseDir.
     *
     * @param baseDir - директория, начиная с которой идет поиск git-репозитория.
     */
    public GitRepositoryProperties(String baseDir) {
        OpenOp grgitOpenOperation = new OpenOp();
        grgitOpenOperation.setCurrentDir(baseDir);
        grgit = grgitOpenOperation.call();
    }

    /**
     * Возвращает имя текущей ветки.
     *
     * @return имя текущей ветки.
     */
    public BranchName getCurrentBranchName() {
        return new BranchName(grgit.getBranch().getCurrent().getName());
    }

    /**
     * Возвращает имя тега HEAD-указателя
     *
     * @return имя тега
     */
    public TagName getTagNameOnHead() {
        Commit head = grgit.head();
        List<Tag> tags = new TagListOp(grgit.getRepository()).call();
        Optional<Tag> tagOnHead = tags.stream()
                                      .filter(tag -> tag.getCommit().getId().equals(head.getId()))
                                      .findFirst();

        return tagOnHead.isPresent() ? new TagName(tagOnHead.get().getName()) : new TagName("");
    }
}
