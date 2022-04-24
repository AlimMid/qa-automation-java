package com.tcs.edu;

import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.service.MessageService;
import static com.tcs.edu.decorator.MessageOrder.ASC;
import static com.tcs.edu.decorator.MessageOrder.DESC;
import static com.tcs.edu.decorator.Severity.*;

/**
 * Test case
 */
class Application {
    public static void main(String[] args) {
        MessageService.process(MAJOR, ASC, "Zero", "Hi! 1", "Hey! 2", "What's up! 3");
        MessageService.process(REGULAR, DESC, "Zero", "Hi! 1", "Hey! 2", "What's up! 3");

        MessageService.process(MINOR, (MessageOrder) null, "Zero", "Hi! 1", "Hey! 2", "What's up! 3");
        MessageService.process(MINOR, ASC, "Zero", null, "Hey! 2");
        MessageService.process(null, DESC, "Zero", "Hi! 1", "Hey! 2");
        MessageService.process(null, (MessageOrder) null, "Zero", "Hi! 1", "Hey! 2");

        MessageService.process(MAJOR, ASC, "Zero");
        MessageService.process(REGULAR, "Zero", "Hi! 1", "Hey! 2", "What's up! 3");
        MessageService.process(ASC, "Zero", "Hi! 1", "Hey! 2", "What's up! 3");

        MessageService.process(REGULAR, "Zero");
        MessageService.process(DESC, "Zero");
        MessageService.process("Zero", "Hi! 1", "Hey! 2", "What's up! 3");
        MessageService.process("Zero");
        MessageService.process("");
    }
}