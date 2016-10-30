package org.adl;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.lang.String;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ADL
 */

public class Main {
    static boolean flag; /*findFile метод*/

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
// Меняем вывод даты при вводе

        try {
            /*чтение файла конфигурации*/
            InputStream myFile = new BufferedInputStream(new FileInputStream("config.txt"));
            Scanner myScan = new Scanner(myFile, "windows-1251");
            String line = myScan.nextLine();

            /*чтение даты в консоль*/
            System.out.println("Введите дату");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String read = input.readLine();
            String den = read.substring(0, 2);//22.09.2016
            String mon = read.substring(3, 5);
            String year = read.substring(6, 10);
            String data = (year + "-" + mon + "-" + den + "-com.txt");

            /*сортировка ПО_ все*/
            String ffile = findFile(line, data);//"2016-09-22-com.txt"
            String allWindow = "(?i).*ПО_.*";

            /*выбор типа сортировки*/
            System.out.println("Тип вывода");
            BufferedReader inputtype = new BufferedReader(new InputStreamReader(System.in));
            String type = inputtype.readLine();

            switch (type) {
                case "okno":
                    System.out.println("Введите номер окна");
                    BufferedReader inputNomerOkna = new BufferedReader(new InputStreamReader(System.in));
                    String readNumberWindow = inputNomerOkna.readLine();
                    String numberWindow = ("(?i).*Okno-" + readNumberWindow + ".*");
                    findString(ffile, allWindow, numberWindow); //"#OFFLINE", "#ONLINE", "#KEY_OFF_PRESSED", "#KEY_ON_PRESSED"
                    break;
                case "line":
                    String onOffLine = "(?i).*LINE";
                    findString(ffile, allWindow, onOffLine); //"#OFFLINE", "#ONLINE", "#KEY_OFF_PRESSED", "#KEY_ON_PRESSED"
                    break;
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Неправельно введена дата. Введите дату в формате DD.MM.EEEE (пример: 01.01.2016)");
        }
    }

    /**
     * @param put
     * @param numberWindow
     * @param endString
     * @throws IOException
     */
    /*Вывод найденные строки*/
    static void findString(String put, String numberWindow, String endString) throws IOException {//

        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(put), Charset.forName("windows-1251"))) {
            list = stream
                    .filter(line -> line.matches(numberWindow))
                    .filter(line -> line.matches(endString))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return list;
        list.forEach(System.out::println);
//        System.out.println("Время: " + time1 + " " + "\033[31m" + okno + "\033[m" + " Соединение установлено");
    }

    /**
     * {@link}
     *
     * @param path
     * @param find
     * @return path finds file
     */
    /*поиск файла в директории*/
    static String findFile(String path, String find) {
        File f = new File(path);
        String[] list = f.list();     /*список файлов в текущей папке*/
        assert list != null;
        for (String file : list) {      /*проверка на совпадение*/
            if (find.equals(file)) {
                flag = true;
            }
            if (!path.endsWith("/")) {
                path += "/";
            }
            File tempfile = new File(path + file);
            if (!file.equals(".") && !file.equals("..")) {
                if (tempfile.isDirectory()) {                  /*иначе проверяем, если это папка*/
                    findFile(path + file, find);               /*то рекурсивный вызов этой функции*/
                    if (flag) return file;
                }
            }
        }
        return path + "/" + find;
    }
}



