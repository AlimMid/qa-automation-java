package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.domain.Message;
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
        MessageService messageService = new OrderedDistinctedMessageService();
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
    }
}