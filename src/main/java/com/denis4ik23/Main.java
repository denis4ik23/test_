package com.denis4ik23;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
   private static String prefix = "";
   private static String filePath = "";
   private static boolean append = false;
   private static boolean shortS = false;
   private static boolean fullS = false;
   private static int counterInt = 0;
   private static int counterFloat = 0;
   private static int counterString = 0;
   private static long sumInt = 0;
   private static double sumFloat = 0.0;
   private static long minInt = Long.MAX_VALUE;
   private static long maxInt = Long.MIN_VALUE;
   public static double minFloat = Double.MAX_VALUE;
   private static double maxFloat = Double.MIN_VALUE;
   public static int minStr = Integer.MAX_VALUE;
   private static int maxStr = Integer.MIN_VALUE;

   public static void main(String[] args) {
        List<String> fileIns = new ArrayList<>();
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-o")) {
                    try {
                        if (!args[i+1].startsWith("/")) {
                            filePath = "";
                            System.out.println("Путь не был установлен! Вводите путь через пробел после аргумента -o");
                            continue;
                        }
                        filePath = args[i + 1];
                        ++i;
                        continue;
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Путь не был установлен! Вводите путь через пробел после аргумента -o");
                    }
                }
                if (args[i].equals("-p")) {
                    try {
                        if (args[i + 1].equals("-o") || args[i + 1].startsWith("/") ||
                                args[i + 1].equals("-a") || args[i + 1].equals("-s") ||
                                args[i +1 ].equals("-f") || args[i + 1].endsWith(".txt")) {
                            prefix = "";
                            System.out.println("Префикс не был установлен! Вводите префикс через пробел после аргумента -p");
                            continue;
                        }
                        prefix = args[i + 1];
                        ++i;
                        continue;
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Префикс не был установлен! Вводите префикс через пробел после аргумента -p");
                    }
                }
                if (args[i].equals("-a")) {
                    append = true;
                    continue;
                }
                if (args[i].equals("-s")) {
                    shortS = true;
                    continue;
                }
                if (args[i].equals("-f")) {
                    fullS = true;
                }
                if (args[i].endsWith(".txt")) {
                    fileIns.add(args[i]);
                }
            }
        } else {
            System.out.println("Аргументы не были введены");
        }

        File file1 = new File(filePath + prefix + "strings.txt");
        File file2 = new File(filePath + prefix + "integers.txt");
        File file3 = new File(filePath + prefix + "floats.txt");
        for (String fileIn : fileIns) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileIn))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (isInteger(line)) {
                        try(BufferedWriter writerInt = new BufferedWriter(new FileWriter(file2, append))) {
                            writerInt.write(line);
                            writerInt.write('\n');
                            counterInt++;
                            sumInt += Long.parseLong(line);
                            minInt = Math.min(minInt, Long.parseLong(line));
                            maxInt = Math.max(maxInt, Long.parseLong(line));
                            continue;
                        }
                    }
                    if (isDouble(line)) {
                        try(BufferedWriter writerFloat = new BufferedWriter(new FileWriter(file3, append))) {
                            writerFloat.write(line);
                            writerFloat.write('\n');
                            counterFloat++;
                            sumFloat += Double.parseDouble(line);
                            minFloat = Math.min(minFloat, Double.parseDouble(line));
                            maxFloat = Math.max(maxFloat, Double.parseDouble(line));
                            continue;
                        }
                    }
                    if (!isInteger(line) && !isDouble(line)) {
                        try(BufferedWriter writerString = new BufferedWriter(new FileWriter(file1, append))) {
                            writerString.write(line);
                            writerString.write('\n');
                            counterString++;
                            minStr = Math.min(minStr, line.length());
                            maxStr = Math.max(maxStr, line.length());
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
            } catch (IOException e) {
                System.out.println("Недопустимые данные");
            }
        }
        statistics();
    }
    public static boolean isInteger(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return Double.parseDouble(s) != (long)Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void statistics() {
        if (shortS || fullS) {
            System.out.println(
                    " Количество записанных элементов integers: " + counterInt +'\n' +
                    " Количество записанных элементов floats: " + counterFloat + '\n' +
                    " Количество записанных элементов Strings: " + counterString + '\n'
            );
        }
        if (fullS) {
            if (counterInt != 0) {
                System.out.println(
                        "integers | " + "min значение: " + minInt + " | max значение: " + maxInt + " | сумма: " + sumInt + " | среднее: " + sumInt / counterInt);
            }
            if (counterFloat != 0) {
                System.out.println(
                        "floats   | " + "min значение: " + minFloat + " | max значение: " + maxFloat + " | сумма " + sumFloat + " | среднее " + sumFloat / counterFloat);
            }
            if (counterString != 0) {
                System.out.println(
                        "Strings  | " + "Размер самой короткой строки: " + minStr + " | Размер самой длинной строки: " + maxStr);
            }
        }
   }
}