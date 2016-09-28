package org.adl;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.lang.String;

public class Main {
    static boolean flag;

    public static void main(String[] args) throws IOException {
// Меняем вывод даты при вводе
        try {
//          ввод даты в консоль
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String read = input.readLine();
            String den = read.substring(0, 2);//22.09.2016
            String mon = read.substring(3, 5);
            String god = read.substring(6, 10);
            String data = (god + "-" + mon + "-" + den + "-com.txt");

//          чтение файла конфигурации
            InputStream myFile = new BufferedInputStream(new FileInputStream("config.txt"));
            Scanner myScan = new Scanner(myFile, "windows-1251");
            myScan.hasNext();
            String line = myScan.nextLine();
            String ffile = findFile(line, data);//"2016-09-22-com.txt"

//          вызов функции для поиска строк
            findString(ffile, "#OFFLINE", "#ONLINE", "#KEY_OFF_PRESSED", "#KEY_ON_PRESSED");

        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Неправельно введена дата. Введите дату в формате DD.MM.GGGG (пример: 01.01.2016)");
        }
    }

    //Вывод найденные строки
    static void findString(String put, String offrex, String onrex, String keyoffrex, String keyonrex) {

        try {
            InputStream myFile = new BufferedInputStream(new FileInputStream(put));
            Pattern offline = Pattern.compile(offrex);
            Pattern online = Pattern.compile(onrex);
            Pattern keyoff = Pattern.compile(keyoffrex);
            Pattern keyon = Pattern.compile(keyonrex);
            Scanner myScan = new Scanner(myFile, "windows-1251");
//            проход по строка и поиск згачений
            while (myScan.hasNext()) {
                String line = myScan.nextLine();
                String[] substr = line.split(" ");
//                вывод строк в другом порчдеке
                if (online.matcher(line).find()) {
                    String okno = substr[1];
                    String timest = substr[0];
                    String time1 = timest.substring(0, 8);
                    System.out.println("Время: " + time1 + " " + "\033[31m" + okno + "\033[m" + " Соединение установлено");
                } else if (offline.matcher(line).find()) {
                    String okno = substr[1];
                    String timest = substr[0];
                    String time1 = timest.substring(0, 8);
                    System.out.println("Время: " + time1 + " " + "\033[31m" + okno + "\033[m" + " Нет связи");
                } else if (keyoff.matcher(line).find()) {
                    String okno = substr[1];
                    String timest = substr[0];
                    String time1 = timest.substring(0, 8);
                    System.out.println("Время: " +time1 + " " + "\033[31m" +  okno + "\033[m" + " Нажата кнопка отключить");
                } else if (keyon.matcher(line).find()) {
//                    int index;
                    String okno = substr[1];
                    String timest = substr[0];
                    String time1 = timest.substring(0, 8);
                    String name = substr[substr.length-1];
                    System.out.println("Время: " + time1 + " " + "\033[31m" + okno + "\033[m" + " Имя " + "\033[32m" + name + "\033[m" +  " Нажата кнопка включить");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Фаил не найден");
//            e.printStackTrace();
        }
    }
    //  поиск файла в директории
    static String findFile(String path, String find) {
        File f = new File(path);
        String[] list = f.list();     //список файлов в текущей папке
        assert list != null;
        for (String file : list) {      //проверка на совпадение
            if (find.equals(file)) {
                flag = true;
            }
            if (!path.endsWith("\\")) {
                path += "\\";
            }
            File tempfile = new File(path + file);
            if (!file.equals(".") && !file.equals("..")) {        //!!!
                if (tempfile.isDirectory()) {      //иначе проверяем, если это папка
                    findFile(path + file, find);               //то рекурсивный вызов этой функции
                    if (flag) return file;
                }
            }
        }
        return path + "\\" + find;
    }
}



