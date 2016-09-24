package org.adl;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static boolean flag;
    public static void main(String[] args) {
        findString("D:\\2016-09-22-com.txt", "#OFFLINE", "#ONLINE");
    }

    static void findString(String put, String regexp, String regexp1) {
        try {
            File myFile = new File(put);

            Pattern myPat = Pattern.compile(regexp);
            Pattern myPat1 = Pattern.compile(regexp1);
            Scanner myScan = new Scanner(myFile, "windows-1251");

            while (myScan.hasNext()) {

                String line = myScan.nextLine();
                if (myPat.matcher(line).find()) {
                    System.out.println(line);
                } else {

                    if (myPat1.matcher(line).find()) {
                        System.out.println(line);
                    }
                }
            }

        } catch (Exception e) {

        }
    }

    static void findFile(String path, String find) {
        File f = new File(path);
        String[] list = f.list();     //список файлов в текущей папке
        assert list != null;
        for (String file : list) {      //проверка на совпадение
            if (find.equals(file)) {
                flag=true;
                System.out.println(path + "\\" + file + " !!!!!!!!!!!!!!!!!!");  //если найден, то выход
                return;
            }
            if (!path.endsWith("\\")) {
                path += "\\";
            }
            File tempfile = new File(path + file);
            System.out.println(path + file);
            if (!file.equals(".") && !file.equals("..")) {        //!!!
                if (tempfile.isDirectory()) {      //иначе проверяем, если это папка
                    //path += file;
                    findFile(path + file, find);               //то рекурсивный вызов этой функции
                    if(flag) return;
                }
            }
        }

}



