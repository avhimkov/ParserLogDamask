package org.adl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        File myFile = new File("2016-09-22-com.txt");

        Pattern myPat = Pattern.compile("#ONLINE");

        try{
            Scanner myScan = new Scanner(myFile);

            while(myScan.hasNext()){
                String line = myScan.nextLine();
                if (myPat.matcher(line).find()) {
                    System.out.println(line);
                }
            }

        }catch(Exception ignored){

        }


    }
}
