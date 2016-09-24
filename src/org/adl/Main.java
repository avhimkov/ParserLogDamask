package org.adl;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    String put = null;
    //"(#OFFLINE)(#ONLINE)"
    String regexp = null;

    public static void main(String[] args) {
        FindString("D:\\2016-09-22-com.txt", "#OFFLINE");
        FindString("D:\\2016-09-22-com.txt", "#ONLINE");
    }

    static void FindString(String put, String regexp) {
        try {
            File myFile = new File(put);

            Pattern myPat = Pattern.compile(regexp);
            Scanner myScan = new Scanner(myFile, "windows-1251");

            while (myScan.hasNext()) {

                String line = myScan.nextLine();
                if (myPat.matcher(line).find()) {
                    System.out.println(line);
                }

            }

        } catch (Exception e) {

        }
    }
}



