package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.TimestampMessageDecorator;
import com.tcs.edu.domain.Message;
import com.tcs.edu.printer.ConsolePrinter;
import com.tcs.edu.service.MessageService;
import com.tcs.edu.service.OrderedDistinctedMessageService;
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
                new ConsolePrinter(),
                new TimestampMessageDecorator()
        );
        Message messageMin = new Message(MINOR, "Message with MINOR severity");
        Message messageReg = new Message(REGULAR, "Message with REGULAR severity");
        Message messageMaj = new Message(MAJOR, "Message with MAJOR severity");
        Message messageDef = new Message("Message with NO severity");
        Message messageErr = new Message("Это сообщение не должно выводиться");

        messageService.log(null, DOUBLES, messageErr);
        messageService.log(ASC, (Doubling) null, messageErr);
        messageService.log((Doubling) null, messageErr);
        messageService.log(null, (Doubling) null, messageErr);
        messageService.log(DISTINCT,null, null, null, null);
        messageService.log(ASC, DOUBLES);
        messageService.log(ASC);
        messageService.log(DOUBLES);
        messageService.log();
        messageService.log(null, DOUBLES, messageErr);
        messageService.log(ASC, (Doubling) null, messageErr);
        messageService.log((Doubling) null, messageErr);
        messageService.log(null, (Doubling) null, messageErr);

        messageService.log(ASC, DOUBLES, messageMin, messageReg, messageMaj, messageDef);
        messageService.log(DESC, DOUBLES, messageMin, messageReg, messageMaj, messageDef);
        messageService.log(DESC, DISTINCT, null, messageReg, null, messageDef, null, messageDef, messageDef, null);
        messageService.log(DOUBLES, messageMin, messageReg, messageReg);
        messageService.log(ASC, messageMin, messageReg, messageReg);
        messageService.log(messageMin, messageReg, messageMin, messageDef);

        messageService.log(DISTINCT,null, null, null, null);

        //task9.2: тесты
        Message minorWarning1 = new Message(MINOR, "This is a warning");
        Message minorWarning2 = new Message(MINOR, "This is a warning");
        Message majorWarning = new Message(MAJOR, "This is a warning");
        Message majorError = new Message(MAJOR, "This is an error");
        String stringMessage = "";
        System.out.println("minorWarning1: " + minorWarning1);
        System.out.println("minorWarning2: " + minorWarning2);
        System.out.println("majorWarning: " + majorWarning);
        System.out.println("majorError: " + majorError);
        System.out.println("majorWarning, majorWarning: " + majorWarning.equals(majorWarning));
        System.out.println("minorWarning1, minorWarning1: " + minorWarning1.equals(minorWarning2));
        System.out.println("minorWarning2, minorWarning1: " + minorWarning2.equals(minorWarning1));
        System.out.println("minorWarning1, majorWarning: " + minorWarning1.equals(majorWarning));
        System.out.println("majorWarning, majorError: " + majorWarning.equals(majorError));
        System.out.println("majorError, null: " + majorError.equals(null));
        System.out.println("majorError, stringMessage: " + majorError.equals(stringMessage));

        System.out.println("minorWarning1.hashCode: " + minorWarning1.hashCode());
        System.out.println("minorWarning.hashCode2: " + minorWarning2.hashCode());
        System.out.println("majorWarning.hashCode: " + majorWarning.hashCode());
        System.out.println("majorError.hashCode: " + majorError.hashCode());
    }
}