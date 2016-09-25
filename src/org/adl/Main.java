package org.adl;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.lang.String;

public class Main {
    static boolean flag;

    public static void main(String[] args) throws IOException {
// Меняем вывод даты при вводе
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String read = input.readLine();
            String den = read.substring(0, 2);//22.09.2016
            String mon = read.substring(3, 5);
            String god = read.substring(6, 10);
            String data = (god + "-" + mon + "-" + den + "-com.txt");
            String ffile = findFile("D:\\1", data);//"2016-09-22-com.txt"
            findString(ffile, "#OFFLINE", "#ONLINE", "#KEY_OFF_PRESSED", "#KEY_ON_PRESSED");

        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Введите дату в формате DD.MM.GGGG (пример: 01.01.2016)");
        }

    }

    //Вывод найденные слова
    static void findString(String put, String offrex, String onrex, String keyoffrex, String keyonrex) {

        try {
            File myFile = new File(put);

            Pattern offline = Pattern.compile(offrex);
            Pattern online = Pattern.compile(onrex);
            Pattern keyoff = Pattern.compile(keyoffrex);
            Pattern keyon = Pattern.compile(keyonrex);
            Scanner myScan = new Scanner(myFile, "windows-1251");

            while (myScan.hasNext()) {

                String line = myScan.nextLine();
                if (offline.matcher(line).find()) {
                    System.out.println(line);
                } else if (online.matcher(line).find()) {
                    System.out.println(line);
                } else if (keyoff.matcher(line).find()) {
                    System.out.println(line);
                } else if (keyon.matcher(line).find()) {
                    System.out.println(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static String findFile(String path, String find) {
        File f = new File(path);
        String[] list = f.list();     //список файлов в текущей папке
        assert list != null;
        for (String file : list) {      //проверка на совпадение
            if (find.equals(file)) {
                flag = true;
//                System.out.println(path + "\\" + file + " !!!!!!!!!!!!!!!!!!");  //если найден, то выход
////                return path;
            }
            if (!path.endsWith("\\")) {
                path += "\\";
            }
            File tempfile = new File(path + file);
//            System.out.println(path + file);
            if (!file.equals(".") && !file.equals("..")) {        //!!!
                if (tempfile.isDirectory()) {      //иначе проверяем, если это папка
                    //path += file;
                    findFile(path + file, find);               //то рекурсивный вызов этой функции
                    if (flag) return file;
                }
            }
        }
        String put = path + "\\" + find;
        return put;
    }
}



