package com.tcs.edu;

import com.tcs.edu.service.MessageService;
import static com.tcs.edu.decorator.Severity.*;

/**
 * Test case
 */
class Application {
    public static void main(String[] args) {
        MessageService.process(MAJOR, "Hello world!", "Hi!", "Hey!", "What's up!");
        MessageService.process(REGULAR, "Hello world!", "Hi!", "Hey!", "What's up!");
        MessageService.process(MINOR, "Hello world!", "Hi!", "Hey!", "What's up!");
        MessageService.process("Hello world!", "Hi!", "Hey!", "What's up!");
    }
}