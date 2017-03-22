package ru.yandex.money.gradle.plugins.library.helpers;

import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Представляет интерфейс командной строки для запуска команд и получения их вывода
 *
 * @author Konstantin Novokreshchenov (knovokresch@yamoney.ru)
 * @since 21.03.2017
 */
public class Shell {
    private static final File CURRENT_WORKING_DIRECTORY = new File(".");
    private final Logger log = Logging.getLogger(Shell.class);
    private final File workingDirectory;
    private final String[] args;

    /**
     * Запускает команду с указанными аргументами в текущей директории и возвращает вывод команды
     *
     * @param args аргументы командные строки
     * @return вывод команды
     */
    public static String execute(String... args) {
        return execute(CURRENT_WORKING_DIRECTORY, args);
    }

    /**
     * Запускает команду с указанными аргументами в указанной директории и возвращает вывод команды
     *
     * @param workingDirectory рабочая директория
     * @param args аргументы командные строки
     * @return вывод команды
     */
    public static String execute(File workingDirectory, String... args) {
        return new Shell(workingDirectory, args).execute();
    }

    private Shell(File workingDirectory, String... args) {
        this.workingDirectory = workingDirectory;
        this.args = args;
    }

    private String execute() {
        String output = "";
        Process process;
        try {
            process = new ProcessBuilder().directory(workingDirectory).command(args).start();
            output = readOutput(process);
            waitFor(process);
        } catch (IOException e) {
            log.warn("Failed to run command [{}]: {}", Arrays.toString(args), e.getMessage());
        }
        return output;
    }

    private String readOutput(Process process) {
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            log.warn("Failed to read process output: {}", e.getMessage());
        }
        return buffer.toString().replaceAll("\n", "");
    }

    private void waitFor(Process process) {
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            log.warn("Error occurred while waiting for process termination: {}", e.getMessage());
        }
    }
}