package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.decorator.Severity;
import com.tcs.edu.service.MessageService;

import static com.tcs.edu.decorator.Doubling.*;
import static com.tcs.edu.decorator.MessageOrder.*;
import static com.tcs.edu.decorator.Severity.*;
import static com.tcs.edu.service.MessageService.log;

/**
 * Test case
 */
class Application {
    public static void main(String[] args) {
        log(MAJOR, ASC, DOUBLES,"Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(MINOR, DESC, DOUBLES,"Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(REGULAR, DESC, DISTINCT,"Zero0", "Hi! 1", "Hi! 1", "Zero4");
        log(REGULAR, DESC, DISTINCT,"Zero0", null, null, "Zero4");

        log(REGULAR, DESC, DISTINCT);
        log(MAJOR, ASC, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(MAJOR, DISTINCT,"Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(ASC, DOUBLES,"Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(MAJOR, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(DESC, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(DISTINCT, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log("Zero", "Hi! 1", "Hi! 1", "What's up! 3");

        log(null, ASC, DOUBLES,"Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(MAJOR, null, DOUBLES,"Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(MAJOR, ASC, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log((Severity) null, DOUBLES, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(null, ASC, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log((MessageOrder) null, DOUBLES, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(MAJOR, (MessageOrder) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(ASC, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(MAJOR, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(null, null, DOUBLES, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(null, (MessageOrder) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(null, ASC, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log((MessageOrder) null, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(MAJOR, null, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log((MessageOrder) null, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
        log(null, null, (Doubling) null, "Zero", "Hi! 1", "Hi! 1", "What's up! 3");
    }
}