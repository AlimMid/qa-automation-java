package com.tcs.edu.printer;

/**
 * @author Midov Alim
 * @apiNote Класс com.tcs.edu.printer.ConsolePrinter предназначен для аггрегации полей и
 * методов для обработки сообщения и последующего вывода информации на консоль
 * @version 1.0.0
 */
public class ConsolePrinter {
    /**
     * @apiNote Метод предназначен для кастомного вывода сообщения передаваемого в качестве аргумента, не возвращает никаких данных
     * @param message тип String, аргумент, через который в метод передается строка, которую необходимо обработать и вывести
     * @implNote побочный эффект - кастомизируемый метод выводда информации на консоль
     */
    public static void print(String message) {
        System.out.println(message);
    }
}
