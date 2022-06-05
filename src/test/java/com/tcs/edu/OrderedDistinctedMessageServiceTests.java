package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.decorator.TimestampMessageDecorator;
import com.tcs.edu.domain.Message;
import com.tcs.edu.repository.HashMapMessageRepository;
import com.tcs.edu.service.MessageService;
import com.tcs.edu.service.OrderedDistinctedMessageService;
import com.tcs.edu.validator.LogException;
import org.junit.jupiter.api.*;

import java.util.stream.Collectors;

import static com.tcs.edu.decorator.Doubling.DISTINCT;
import static com.tcs.edu.decorator.Doubling.DOUBLES;
import static com.tcs.edu.decorator.MessageOrder.ASC;
import static com.tcs.edu.decorator.MessageOrder.DESC;
import static com.tcs.edu.decorator.Severity.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderedDistinctedMessageServiceTests {
    private MessageService messageService;
    private Message messageMin;
    private Message messageMaj;
    private Message messageErr;
    private Message messageEmpty;

    @BeforeEach
    private void precondition() {
        messageService = new OrderedDistinctedMessageService(
                new HashMapMessageRepository(),
                new TimestampMessageDecorator()
        );
        messageMin = new Message(MINOR, "Message with MINOR severity");
        messageMaj = new Message(MAJOR, "Message with MAJOR severity");
        messageErr = new Message("Это сообщение не должно выводиться");
        messageEmpty = new Message("");
    }

    @Test
    @Tag("negative")
    @DisplayName("Проверка валидации, когда messageOrder = null")
    public void checkValidationMessageOrderNull() {
        Throwable throwMessage = assertThrows(LogException.class, () -> messageService.log(null, DOUBLES, messageErr));
        assertEquals("notValidArgMessage", throwMessage.getMessage());
    }

    @Test
    @Tag("negative")
    @DisplayName("Проверка валидации, когда doubling = null")
    public void checkValidationDoublingNull() {
        Throwable throwMessage = assertThrows(LogException.class, () -> messageService.log(ASC, (Doubling) null, messageErr));
        assertEquals("notValidArgMessage", throwMessage.getMessage());
    }

    @Test
    @Tag("negative")
    @DisplayName("Проверка валидации, когда doubling = null при незаданном messageOrder")
    public void checkValidationDoublingNullWhenOrderEmpty() {
        Throwable throwMessage = assertThrows(LogException.class, () -> messageService.log((Doubling) null, messageErr));
        assertEquals("notValidArgMessage", throwMessage.getMessage());
    }

    @Test
    @Tag("negative")
    @DisplayName("Проверка валидации, когда messageOrder = null при незаданном doubling")
    public void checkValidationOrderNullWhenDoublingEmpty() {
        Throwable throwMessage = assertThrows(LogException.class, () -> messageService.log((MessageOrder) null, messageErr));
        assertEquals("notValidArgMessage", throwMessage.getMessage());
    }

    @Test
    @Tag("negative")
    @DisplayName("Проверка валидации, когда в Messages все месседжи = null")
    public void checkValidationMessagesNull() {
        Throwable throwMessage = assertThrows(LogException.class, () -> messageService.log(ASC, DOUBLES, null, null));
        assertEquals("notValidArgMessage", throwMessage.getMessage());
    }

    @Test
    @Tag("negative")
    @DisplayName("Проверка валидации, когда в Messages не заданы")
    public void checkValidationMessagesEmpty() {
        Throwable throwMessage = assertThrows(LogException.class, () -> messageService.log(DESC, DOUBLES));
        assertEquals("notValidArgMessage", throwMessage.getMessage());
    }

    @Test
    @Tag("negative")
    @DisplayName("Проверка валидации, когда в Messages имеется message=''")
    public void checkValidationMessagesContainEmptyMessage() {
        Throwable throwMessage = assertThrows(LogException.class, () -> messageService.log(ASC, DOUBLES, messageErr, messageEmpty));
        assertEquals("notValidArgMessage", throwMessage.getMessage());
    }

    @Test
    @Tag("positive")
    @DisplayName("Проверка, что при повторных вызовах мессаджи записываются в коллекцию корректно")
    public void checkMessagesAfterDoubleLog() {
        messageService.log(ASC, DOUBLES, messageMin);
        messageService.log(ASC, DOUBLES, messageMaj);
        var messages = messageService.findAll();
        assertNotNull(messages, "коллекция  messages должна быть непустой");
        assertEquals(2, messages.size(), "коллекция  messages должна содержать 2 элемента");
        var messMin = messages.stream()
                .filter(m -> m.getId().equals(messageMin.getId()))
                .collect(Collectors.toList());
        assertEquals(1, messMin.size());
        assertEquals(messageMin.getSeverity(), messMin.get(0).getSeverity());
        assertEquals(messageMin.getBody(), messMin.get(0).getBody());
        var messMaj = messages.stream()
                .filter(m -> m.getId().equals(messageMaj.getId()))
                .collect(Collectors.toList());
        assertEquals(1, messMaj.size());
        assertEquals(messageMaj.getSeverity(), messMaj.get(0).getSeverity());
        assertEquals(messageMaj.getBody(),  messMaj.get(0).getBody());
    }

    @Test
    @Tag("positive")
    @DisplayName("Проверка, что при doubling = DOUBLES, одинаковые сообщения не дедуплицируются")
    public void checkDoubleMessages() {
        messageService.log(ASC, DOUBLES, messageMin, messageMin);
        var messages = messageService.findAll();
        assertNotNull(messages, "коллекция  messages должна быть непустой");
        assertEquals(2, messages.size(), "коллекция  messages должна содержать 2 элемента");
        for (var message : messages) {
            assertEquals(messageMin.getSeverity(), message.getSeverity());
            assertEquals(messageMin.getBody(), message.getBody());
        }
    }

    @Test
    @Tag("positive")
    @DisplayName("Проверка, что при doubling = DISTINCT, одинаковые сообщения дедуплицируются")
    public void checkDistinctMessages() {
        messageService.log(ASC, DISTINCT, messageMin, messageMin);
        var messages = messageService.findAll();
        assertNotNull(messages, "коллекция  messages должна быть непустой");
        assertEquals(1, messages.size(), "коллекция  messages должна содержать 2 элемента");
        for (var message : messages) {
            assertEquals(messageMin.getSeverity(), message.getSeverity());
            assertEquals(messageMin.getBody(), message.getBody());
        }
    }

    @Test
    @Tag("positive")
    @DisplayName("Проверка, что при незаданном doubling, сообщения записываются без дедупликации")
    public void checkDefaultDoubling() {
        messageService.log(ASC, messageMin, messageMin);
        var messages = messageService.findAll();
        assertNotNull(messages, "коллекция  messages должна быть непустой");
        assertEquals(2, messages.size(), "коллекция  messages должна содержать 2 элемента");
        for (var message : messages) {
            assertEquals(messageMin.getSeverity(), message.getSeverity());
            assertEquals(messageMin.getBody(), message.getBody());
        }
    }
}
