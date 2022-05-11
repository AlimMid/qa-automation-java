package com.tcs.edu.printer;

/**
 * @author Midov Alim
 * @apiNote Класс com.tcs.edu.printer.ConsolePrinter предназначен для вывода информации на консоль
 * @version 1.0.0
 */
public class ConsolePrinter implements Printer{
    /**
     * @apiNote Метод предназначен для вывода сообщения передаваемого в качестве аргумента
     * @param message тип String
     */
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
