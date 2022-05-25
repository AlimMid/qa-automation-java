package com.tcs.edu.printer;

import com.tcs.edu.validator.ValidatingService;

/**
 * @version 1.0.0
 * @apiNote Класс com.tcs.edu.printer.FilePrinter предназначен для вывода информации в файл
 */
public class FilePrinter extends ValidatingService implements Printer {

    /**
     * @param message тип String
     * @apiNote Метод предназначен для вывода сообщения передаваемого в качестве аргумента
     */
    @Override
    public void print(String message) {
        if (isArgNotValid(message)) {return;}
    }
}
