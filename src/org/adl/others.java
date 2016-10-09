package org.adl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class others {
    public static void main(String[] args) throws IOException {
        readStreamOfLinesUsingFilesWithTryBlock();
    }

    private static void readStreamOfLinesUsingFilesWithTryBlock() throws IOException {
        String path = "D:/1/2016-09-22-com.txt";
        // When filteredLines is closed, it closes underlying stream as well as underlying file.
        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path), Charset.forName("windows-1251"))) {
            list = stream
//                    .filter(line -> line.contains("s"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.forEach(System.out::println);
    }
}
