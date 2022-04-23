package com.tcs.edu.decorator;

import static com.tcs.edu.decorator.Severity.MINOR;

/**
 * @author Midov Alim
 * @version 1.0.0
 * @implNote Класс com.tcs.edu.printer.SeverityLevelDecorator отвечает за строковое представление
 * уровня важности
 */
public class SeverityLevelDecorator {

    /**
     * @param severity Enum (MAJOR, REGULAR, MINOR)
     * @return (! ! !), (!) или () в зависимости от значения severity
     * @apiNote Метод преобразует уровень важности в строковое представление
     */
    public static String mapToString(Severity severity) {
        String severityString = null;
        switch (severity) {
            case MAJOR: {
                severityString = "(!!!)";
                break;
            }
            case REGULAR: {
                severityString = "(!)";
                break;
            }
            case MINOR: {
                severityString = "()";
                break;
            }
        }
        return severityString;
    }
}