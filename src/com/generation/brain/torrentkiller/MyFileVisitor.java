package com.generation.brain.torrentkiller;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;

public class MyFileVisitor extends SimpleFileVisitor<Path> {
    private PathMatcher matcher;
    public ArrayList<Path> list = new ArrayList<>();

    public MyFileVisitor(String pattern) {
        try {
            matcher = FileSystems.getDefault().getPathMatcher(pattern);
        } catch (IllegalArgumentException iae) {
            System.err.println("Invalid pattern; did you forget to prefix \"glob:\" or \"regex:\"?");
            System.exit(1);
        }
    }

    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) {
        find(path);
        return FileVisitResult.CONTINUE;
    }

    public void find(Path path) {
        Path name = path.getFileName();

        if(matcher.matches(name)){
            System.out.println("Matching name:" + name.getFileName());
            list.add(path);
        }
    }

    public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
        System.out.printf("Found %d file(s).\n", list.size());

        Iterator iter = list.iterator();
        while(iter.hasNext()) {
            Path path = (Path) iter.next();

            Files.delete(path);
            iter.remove();
            System.out.println("FIle " + path.toAbsolutePath() + " was deleted");
        }
        return FileVisitResult.CONTINUE;
    }
}