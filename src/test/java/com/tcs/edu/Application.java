package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.TimestampMessageDecorator;
import com.tcs.edu.domain.Message;
import com.tcs.edu.printer.ConsolePrinter;
import com.tcs.edu.repository.HashMapMessageRepository;
import com.tcs.edu.service.MessageService;
import com.tcs.edu.service.OrderedDistinctedMessageService;
import com.tcs.edu.validator.LogException;

import static com.tcs.edu.decorator.Doubling.DISTINCT;
import static com.tcs.edu.decorator.Doubling.DOUBLES;
import static com.tcs.edu.decorator.MessageOrder.ASC;
import static com.tcs.edu.decorator.MessageOrder.DESC;
import static com.tcs.edu.decorator.Severity.*;

/**
 * Test case
 */
class Application {
    public static void main(String[] args) {
        MessageService messageService = new OrderedDistinctedMessageService(
                new HashMapMessageRepository(),
                new TimestampMessageDecorator()
        );
        Message messageMin = new Message(MINOR, "Message with MINOR severity");
        Message messageReg = new Message(REGULAR, "Message with REGULAR severity");
        Message messageMaj = new Message(MAJOR, "Message with MAJOR severity");
        Message messageDef = new Message("Message with NO severity");
        Message messageErr = new Message("Это сообщение не должно выводиться");
        Message messageEmpty = new Message("");

        // блок вызовов для проверки валидации
//        {
//            try {
//                messageService.log(null, DOUBLES, messageErr);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log(ASC, (Doubling) null, messageErr);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log((Doubling) null, messageErr);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log(null, (Doubling) null, messageErr);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log(DISTINCT,null, null, null, null);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log(ASC, DOUBLES);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log(ASC, DOUBLES, messageDef, messageEmpty);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log(ASC);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log(DOUBLES);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log();
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log(null, DOUBLES, messageErr);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log(ASC, (Doubling) null, messageErr);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log((Doubling) null, messageErr);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log(null, (Doubling) null, messageErr);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//            try {
//                messageService.log(DESC, DISTINCT, null, messageReg, null, messageDef, null, messageDef, messageDef, null);
//            } catch (LogException e) {
//                e.printStackTrace();
//            }
//        }

        // блок позитивных кейсов
        messageService.log(ASC, DOUBLES, messageMin, messageReg, messageMaj, messageDef);
        System.out.println(messageService.findAll());
//        System.out.println(messageService.findByPrimaryKey(messageMin.getId()));
//        System.out.println(messageService.findByPrimaryKey(messageReg.getId()));
//        System.out.println(messageService.findByPrimaryKey(messageMaj.getId()));
//        System.out.println(messageService.findByPrimaryKey(messageDef.getId()));

//        messageService.log(DESC, DOUBLES, messageMin, messageReg, messageMaj, messageDef);
//        messageService.log(DOUBLES, messageMin, messageReg, messageReg);
//        messageService.log(ASC, messageMin, messageReg, messageReg);
//        messageService.log(messageMin, messageReg, messageMin, messageDef);

        // task9.2: тесты
//        Message minorWarning1 = new Message(MINOR, "This is a warning");
//        Message minorWarning2 = new Message(MINOR, "This is a warning");
//        Message majorWarning = new Message(MAJOR, "This is a warning");
//        Message majorError = new Message(MAJOR, "This is an error");
//        String stringMessage = "";
//        System.out.println("minorWarning1: " + minorWarning1);
//        System.out.println("minorWarning2: " + minorWarning2);
//        System.out.println("majorWarning: " + majorWarning);
//        System.out.println("majorError: " + majorError);
//        System.out.println("majorWarning, majorWarning: " + majorWarning.equals(majorWarning));
//        System.out.println("minorWarning1, minorWarning1: " + minorWarning1.equals(minorWarning2));
//        System.out.println("minorWarning2, minorWarning1: " + minorWarning2.equals(minorWarning1));
//        System.out.println("minorWarning1, majorWarning: " + minorWarning1.equals(majorWarning));
//        System.out.println("majorWarning, majorError: " + majorWarning.equals(majorError));
//        System.out.println("majorError, null: " + majorError.equals(null));
//        System.out.println("majorError, stringMessage: " + majorError.equals(stringMessage));
//
//        System.out.println("minorWarning1.hashCode: " + minorWarning1.hashCode());
//        System.out.println("minorWarning.hashCode2: " + minorWarning2.hashCode());
//        System.out.println("majorWarning.hashCode: " + majorWarning.hashCode());
//        System.out.println("majorError.hashCode: " + majorError.hashCode());
    }
}