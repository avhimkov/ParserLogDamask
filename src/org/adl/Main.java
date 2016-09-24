package org.adl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
//            PrintWriter result = new PrintWriter("result.txt");
            PrintWriter currentLog = null;

            Scanner s = new Scanner(new File("C:\\Users\\AvhimkovDL\\Desktop\\Отчеты Дамаск\\12 часов 22.09.2016\\2016-09-22-com.txt"));
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.startsWith("0"))
                    System.out.println(currentLog);// = result;

//                else if (currentLog != null)
//                    currentLog.println(line);
            }

//            result.close();
//            s.close();
        } catch (IOException ioex) {
            // handle exception...
        }
    }
}
