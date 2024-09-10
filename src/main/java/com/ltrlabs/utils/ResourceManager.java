package com.ltrlabs.utils;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Slf4j
public class ResourceManager {

    public static Optional<Path> createDirectory(String directoryName) {
        Path path = Path.of(System.getProperty("user.home") + "/" + directoryName);
        Path directory = null;
        try {
            directory = Files.createDirectories(path);
        } catch (IOException e) {
            log.error("Couldn't create directory on path: {}. Exception message: {}", path, e.getMessage());
        } catch (SecurityException e) {
            log.error("Couldn't create directory on path: {} because of some security issues. Exception message: {}", path, e.getMessage());
        }
        return Optional.ofNullable(directory);
    }

    public static boolean createFile(Path pathToDir, String fileName) {
        if (pathToDir == null || fileName == null || fileName.isEmpty()) return false;
        Path path = pathToDir.resolve(fileName);
        boolean created = false;
        try {
            Files.createFile(path);
            created = true;
        } catch (FileAlreadyExistsException e) {
            created = true;
        } catch (IOException e) {
            log.error("Couldn't create file on path: {}. Exception message: {}", path, e.getMessage());
        } catch (SecurityException e) {
            log.error("Couldn't create file on path: {} because of some security issues. Exception message: {}", path, e.getMessage());
        }
        return created;
    }

    public static boolean isFileEmpty(String path) {
        try {
            return Files.size(Path.of(path)) == 0;
        } catch (IOException e) {
            return true;
        }
    }
}
