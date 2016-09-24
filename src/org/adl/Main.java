package org.adl;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        try{
            File myFile = new File("D:\\2016-09-22-com.txt");

            Pattern myPat = Pattern.compile("#ONLINE");
            Scanner myScan = new Scanner(myFile, "windows-1251");

            while(myScan.hasNext()){

                String line = myScan.nextLine();
                if (myPat.matcher(line).find()) {
                    System.out.println(line);
                }

            }

        }catch(Exception e){

        }



    }
}
