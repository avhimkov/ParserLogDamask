package org.adl;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        FindString("D:\\2016-09-22-com.txt", "#OFFLINE", "#ONLINE");
    }

    static void FindString(String put, String regexp, String regexp1) {
        try {
            File myFile = new File(put);

            Pattern myPat = Pattern.compile(regexp);
            Pattern myPat1 = Pattern.compile(regexp1);
            Scanner myScan = new Scanner(myFile, "windows-1251");

            while (myScan.hasNext()) {

                String line = myScan.nextLine();
                if (myPat.matcher(line).find()) {
                    System.out.println(line);
                }
                else  {

                    if (myPat1.matcher(line).find()) {
                        System.out.println(line);
                    }
                }


            }

        } catch (Exception e) {

        }
    }
}



