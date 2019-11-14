package com.generation.brain.torrentkiller;

import java.io.IOException;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {

        Path sourcePath = Paths.get("C:\\Torrents");
        String pattern = "regex:.+\\.torrent$";

        try {
            Files.walkFileTree(sourcePath, new MyFileVisitor(pattern));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}