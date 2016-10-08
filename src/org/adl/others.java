package org.adl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class others {
    public static void main(String[] args) throws IOException {
        readStreamOfLinesUsingFilesWithTryBlock();
    }
    private static void readStreamOfLinesUsingFilesWithTryBlock() throws IOException
    {
        Charset inputCharset = Charset.forName("windows-1251");
        Path path = Paths.get("D:\\1", "2016-09-22-com.txt");
        //When filteredLines is closed, it closes underlying stream as well as underlying file.
        try(Stream<String> filteredLines = Files.lines(path)
                //test if file is closed or not
                .filter(s -> s.contains("ПО_Okno-5"))){
            Optional<String> hasPassword = filteredLines.findFirst();
            if(hasPassword.isPresent()){
                System.out.println(hasPassword.get());
            }
        }
    }
}
