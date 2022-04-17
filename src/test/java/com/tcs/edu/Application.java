package com.tcs.edu;

import com.tcs.edu.decorator.Severity;
import com.tcs.edu.printer.ConsolePrinter;
import com.tcs.edu.service.MessageService;

import static com.tcs.edu.decorator.Severity.*;
import static com.tcs.edu.decorator.TimestampMessageDecorator.decorate;

/**
 * Test case
 */
class Application {
    public static void main(String[] args) {
        MessageService.process(MAJOR, "Hello world!", "Hi!", "Hey!", "What's up!");
        MessageService.process(REGULAR, "Hello world!", "Hi!", "Hey!", "What's up!");
        MessageService.process(MINOR, "Hello world!", "Hi!", "Hey!", "What's up!");
        MessageService.process("Hello world!", "Hi!", "Hey!", "What's up!");

//        MessageService.process(MAJOR, "Hello world!");
//        MessageService.process(MINOR, "Hello world!");
//        MessageService.process(MAJOR, "Hello world!");
//        MessageService.process(MAJOR, "Hello world!");
//        MessageService.process(REGULAR, "Hello world!");
//        MessageService.process(MINOR, "Hello world!");
//        MessageService.process(REGULAR, "Hello world!");
//        MessageService.process(REGULAR, "Hello world!");
//        MessageService.process(REGULAR, "Hello world!");
//        MessageService.process(null, "Hello world!");
//        MessageService.process("Hello world!");

//        ConsolePrinter.print(decorate("Hello world!"));
//        ConsolePrinter.print(decorate("Hello world!"));
//        ConsolePrinter.print(decorate("Hello world!"));
//        ConsolePrinter.print(decorate("Hello world!"));
//        ConsolePrinter.print(decorate("Hello world!"));
//        ConsolePrinter.print(decorate("Hello world!"));
//        ConsolePrinter.print(decorate("Hello world!"));
//        ConsolePrinter.print(decorate("Hello world!"));
//        ConsolePrinter.print(decorate("Hello world!"));
//        ConsolePrinter.print(decorate("Hello world!"));
//        ConsolePrinter.print(decorate("Hello world!"));
    }
}