package org.adl;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.lang.String;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ADL
 */

public class Main {
    static boolean flag;

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
// Меняем вывод даты при вводе

        try {
//          ввод даты в консоль
            System.out.println("Введите дату");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String read = input.readLine();
            String den = read.substring(0, 2);//22.09.2016
            String mon = read.substring(3, 5);
            String year = read.substring(6, 10);
            String data = (year + "-" + mon + "-" + den + "-com.txt");
//          получениеи номера окна
            System.out.println("Введите номер окна");
            BufferedReader inputNomerOkna = new BufferedReader(new InputStreamReader(System.in));
            String readNumberWindow = inputNomerOkna.readLine();
            String numberWindow = ("ПО_Okno-" + readNumberWindow);

            System.out.println("Введите строку поиска");//KEY_SUCCESS_PRESSED
            BufferedReader inputFindSring = new BufferedReader(new InputStreamReader(System.in));
            String readFindString = inputFindSring.readLine();

//          чтение файла конфигурации
            InputStream myFile = new BufferedInputStream(new FileInputStream("config.txt"));
            Scanner myScan = new Scanner(myFile, "windows-1251");
            myScan.hasNext();
            String line = myScan.nextLine();

//          вызов функци поиска файла
            String ffile = findFile(line, data);//"2016-09-22-com.txt"

//          вызов функции для поиска строк
            findString(ffile, numberWindow, readFindString); //"#OFFLINE", "#ONLINE", "#KEY_OFF_PRESSED", "#KEY_ON_PRESSED"

        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Неправельно введена дата. Введите дату в формате DD.MM.EEEE (пример: 01.01.2016)");
        }
    }

    /**
     * @param put
     * @param numberWindow
     * @throws IOException
     */
    //Вывод найденные строки
    static void findString(String put, String numberWindow, String findeSring) throws IOException {

        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(put), Charset.forName("windows-1251"))) {
            list = stream
                    .filter(line -> line.contains(numberWindow))
                    .filter(line -> line.contains(findeSring))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.forEach(System.out::println);

//                String[] substr = line.split(" ");
//                вывод строк в другом порядеке
//                if (online.matcher(line).find()) {
//                    String okno = substr[1];
//                    String timest = substr[0];
//                    String time1 = timest.substring(0, 8);
//                    System.out.println("Время: " + time1 + " " + "\033[31m" + okno + "\033[m" + " Соединение установлено");
//                }
//                else if (offline.matcher(line).find()) {
//                    String okno = substr[1];
//                    String timest = substr[0];
//                    String time1 = timest.substring(0, 8);
//                    System.out.println("Время: " + time1 + " " + "\033[31m" + okno + "\033[m" + " Нет связи");
//                } else if (keyoff.matcher(line).find()) {
//                    String okno = substr[1];
//                    String timest = substr[0];
//                    String time1 = timest.substring(0, 8);
//                    System.out.println("Время: " +time1 + " " + "\033[31m" +  okno + "\033[m" + " Нажата кнопка отключить");
//                } else if (keyon.matcher(line).find()) {
////                    int index;
//                    String okno = substr[1];
//                    String timest = substr[0];
//                    String time1 = timest.substring(0, 8);
//                    String name = substr[substr.length-1];
//                    System.out.println("Время: " + time1 + " " + "\033[31m" + okno + "\033[m" + " Имя " + "\033[32m" + name + "\033[m" +  " Нажата кнопка включить");
//                }
//    }
    }

    /**
     * {@link}
     * @param path
     * @param find
     * @return path finds file
     */
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



