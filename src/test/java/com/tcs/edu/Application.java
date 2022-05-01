package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.domain.Message;
import static com.tcs.edu.decorator.Doubling.DISTINCT;
import static com.tcs.edu.decorator.Doubling.DOUBLES;
import static com.tcs.edu.decorator.MessageOrder.ASC;
import static com.tcs.edu.decorator.MessageOrder.DESC;
import static com.tcs.edu.decorator.Severity.*;
import static com.tcs.edu.service.MessageService.log;

/**
 * Test case
 */
class Application {
    public static void main(String[] args) {
        Message messageMin = new Message(MINOR, "Message with MINOR severity");
        Message messageReg = new Message(REGULAR, "Message with REGULAR severity");
        Message messageMaj = new Message(MAJOR, "Message with MAJOR severity");
        Message messageDef = new Message("Message with NO severity");
        Message messageErr = new Message("Это сообщение не должно выводиться");

        log(null, DOUBLES, messageErr);
        log(ASC, (Doubling) null, messageErr);
        log((Doubling) null, messageErr);
        log(null, (Doubling) null, messageErr);
        log(DISTINCT,null, null, null, null);
        log(ASC, DOUBLES);
        log(ASC);
        log(DOUBLES);
        log();
        log(null, DOUBLES, messageMin, messageReg, messageMaj, messageDef);
        log(ASC, (Doubling) null, messageMin, messageReg, messageMaj, messageDef);
        log((Doubling) null, messageMin, messageReg, messageMaj, messageDef);
        log(null, (Doubling) null, messageMin, messageReg, messageMaj, messageDef);

        log(ASC, DOUBLES, messageMin, messageReg, messageMaj, messageDef);
        log(DESC, DOUBLES, messageMin, messageReg, messageMaj, messageDef);
        log(DESC, DISTINCT, null, messageReg, null, messageDef, null, messageDef, messageDef, null);
        log(DOUBLES, messageMin, messageReg, messageReg);
        log(ASC, messageMin, messageReg, messageReg);
        log(messageMin, messageReg, messageMin, messageDef);

        log(DISTINCT,null, null, null, null);
    }
}