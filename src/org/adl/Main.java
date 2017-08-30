package org.adl;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.lang.String;
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
            System.out.println("Введите дату\n Пример: 22.09.2017");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String read = input.readLine();
            String den = read.substring(0, 2);//22.09.2016
            String mon = read.substring(3, 5);
            String year = read.substring(6, 10);
            String data = (year + "-" + mon + "-" + den + "-com.txt");

            /*сортировка ПО_ все*/
            String ffile = findFile(line, data);//"2016-09-22-com.txt"
            String allWindow = "(?i).*ПО_(?i).*";

            /*выбор типа сортировки*/
            System.out.println("Тип вывода");
            BufferedReader inputtype = new BufferedReader(new InputStreamReader(System.in));
            String type = inputtype.readLine();

            switch (type) {
                case "okno": //for Lyantor
                    System.out.println("Введите номер окна\n Пример: okno");
                    BufferedReader inputNomerOkna = new BufferedReader(new InputStreamReader(System.in));
                    String readNumberWindow = inputNomerOkna.readLine();
                    String numberWindow = ("(?i).*Okno-" + readNumberWindow + "(?i).*");
                    List<String> lines = findString(ffile, allWindow, numberWindow); //"#OFFLINE", "#ONLINE", "#KEY_OFF_PRESSED", "#KEY_ON_PRESSED"
                    Path file = Paths.get("okna.txt");
                    Files.write(file, lines, Charset.forName("UTF-8"));

                    break;
                case "oper": //for Sortim
                    System.out.println("Введите номер окна\n Пример: oper");
                    BufferedReader inputNomerOkna1 = new BufferedReader(new InputStreamReader(System.in));
                    String readNumberWindow1 = inputNomerOkna1.readLine();
                    String numberWindow1 = ("(?i).*OPERATOR-" + readNumberWindow1 + "(?i).*");//03.11.2016
                    List<String> lines1 = findString(ffile, allWindow, numberWindow1); //"#OFFLINE", "#ONLINE", "#KEY_OFF_PRESSED", "#KEY_ON_PRESSED"
                    Path file1 = Paths.get("oper.txt");
                    Files.write(file1, lines1, Charset.forName("UTF-8"));

                    break;
                case "line": //ALL
                    System.out.println("Выбрать все\n Пример: line");
                    String onOffLine = "(?i).*LINE";
                    List<String> lines2 = findString(ffile, allWindow, onOffLine); //"#OFFLINE", "#ONLINE", "#KEY_OFF_PRESSED", "#KEY_ON_PRESSED"
                    Path file2 = Paths.get("line.txt");
                    Files.write(file2, lines2, Charset.forName("UTF-8"));
                    break;
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Неправельно введена дата. Введите дату в формате DD.MM.EEEE (пример: 01.01.2016)");
        } catch (FileNotFoundException e) {
            System.out.println("Нет файла конфигурации config.txt");
        } catch (NoSuchFileException e) {
            System.out.println("Такого файла не существует или неправельно введена дата");
        }
    }

    /**
     *
     * @param put
     * @param numberWindow
     * @param endString
     * @return "List<String>"
     * @throws IOException
     */
    /*Перебор строк в фаайле и передача в List<String>*/
    static List<String> findString(String put, String numberWindow, String endString) throws IOException {//

        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(put), Charset.forName("windows-1251"))) {
            stream
                    .filter(line -> line.matches(numberWindow))
                    .filter(line -> line.matches(endString))
                    .forEach(list::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
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



