package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.decorator.Severity;
import com.tcs.edu.service.MessageService;

import static com.tcs.edu.decorator.Doubling.*;
import static com.tcs.edu.decorator.MessageOrder.*;
import static com.tcs.edu.decorator.Severity.*;

/**
 * Test case
 */
class Application {
    public static void main(String[] args) {
        MessageService.process(MAJOR, ASC, DOUBLES,"Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(MINOR, DESC, DOUBLES,"Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(REGULAR, DESC, DISTINCT,"Zero0", "Hi! 1", "Hi! 1", "Zero4");
        MessageService.process(REGULAR, DESC, DISTINCT,"Zero0", null, null, "Zero4");

        MessageService.process(REGULAR, DESC, DISTINCT);
        MessageService.process(MAJOR, ASC, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(MAJOR, DISTINCT,"Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(ASC, DOUBLES,"Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(MAJOR, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(DESC, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(DISTINCT, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process("Zero", "Hi! 1", "Hi! 1", "What's up! 3");

        MessageService.process(null, ASC, DOUBLES,"Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(MAJOR, null, DOUBLES,"Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(MAJOR, ASC, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process((Severity) null, DOUBLES, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(null, ASC, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process((MessageOrder) null, DOUBLES, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(MAJOR, (MessageOrder) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(ASC, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(MAJOR, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(null, null, DOUBLES, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(null, (MessageOrder) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(null, ASC, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process((MessageOrder) null, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(MAJOR, null, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process((MessageOrder) null, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        MessageService.process(null, null, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
    }
}