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
//          чтение файла конфигурации
            InputStream myFile = new BufferedInputStream(new FileInputStream("config.txt"));
            Scanner myScan = new Scanner(myFile, "windows-1251");
            String line = myScan.nextLine();

//          ввод даты в консоль
            System.out.println("Введите дату");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String read = input.readLine();
            String den = read.substring(0, 2);//22.09.2016
            String mon = read.substring(3, 5);
            String year = read.substring(6, 10);
            String data = (year + "-" + mon + "-" + den + "-com.txt");

            String numberWindow = "(?i).*ПО_.*";
            String okno = "(?i).*ПО_.*";
            String endString = "(?i).*LINE";

//            получениеи номера окна
//            System.out.println("Введите номер окна");
//            BufferedReader inputNomerOkna = new BufferedReader(new InputStreamReader(System.in));
//            String readNumberWindow = inputNomerOkna.readLine();
//            String numberWindow = ("ПО_" + readNumberWindow);

//            String endString =  "LINE";

//            System.out.println("Введите строку поиска");//KEY_SUCCESS_PRESSED
//            BufferedReader inputFindSring = new BufferedReader(new InputStreamReader(System.in));
//            String readFindString = inputFindSring.readLine();

//          нужно описаь для Linux
//          вызов функци поиска файла
            String ffile = findFile(line, data);//"2016-09-22-com.txt"
//          вызов функции для поиска строк
//            findString(ffile, numberWindow, endString).forEach(System.out::println); //"#OFFLINE", "#ONLINE", "#KEY_OFF_PRESSED", "#KEY_ON_PRESSED"
//            System.out.println("\n" + "\n");
            findString(ffile, numberWindow, okno).forEach(System.out::println); //"#OFFLINE", "#ONLINE", "#KEY_OFF_PRESSED", "#KEY_ON_PRESSED"
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
    static List<String> findString(String put, String numberWindow, String endString) throws IOException {//

        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(put), Charset.forName("windows-1251"))) {
            list = stream
                    .filter(line -> line.matches(numberWindow))
                    .filter(line -> line.matches(endString))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        list.forEach(System.out::println);
//        System.out.println("Время: " + time1 + " " + "\033[31m" + okno + "\033[m" + " Соединение установлено");
        return list;
    }

    /**
     * {@link}
     *
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
            if (!path.endsWith("/")) {
                path += "/";
            }
            File tempfile = new File(path + file);
            if (!file.equals(".") && !file.equals("..")) {        //!!!
                if (tempfile.isDirectory()) {      //иначе проверяем, если это папка
                    findFile(path + file, find);               //то рекурсивный вызов этой функции
                    if (flag) return file;
                }
            }
        }
        return path + "/" + find;
    }
}



