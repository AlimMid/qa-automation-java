package com.tcs.edu.printer;

import com.tcs.edu.validator.ValidatingService;

/**
 * @author Midov Alim
 * @version 1.0.0
 * @apiNote Класс com.tcs.edu.printer.ConsolePrinter предназначен для вывода информации на консоль
 */
public class ConsolePrinter extends ValidatingService implements Printer {
    /**
     * @param message тип String
     * @apiNote Метод предназначен для вывода сообщения передаваемого в качестве аргумента
     */
    @Override
    public void print(String message) {
        isArgValid(message);
        System.out.println(message);
    }

}
