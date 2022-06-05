package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.decorator.TimestampMessageDecorator;
import com.tcs.edu.domain.Message;
import com.tcs.edu.repository.HashMapMessageRepository;
import com.tcs.edu.service.MessageService;
import com.tcs.edu.service.OrderedDistinctedMessageService;
import com.tcs.edu.validator.LogException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.stream.Collectors;
import static com.tcs.edu.decorator.Doubling.DISTINCT;
import static com.tcs.edu.decorator.Doubling.DOUBLES;
import static com.tcs.edu.decorator.MessageOrder.ASC;
import static com.tcs.edu.decorator.MessageOrder.DESC;
import static com.tcs.edu.decorator.Severity.MAJOR;
import static com.tcs.edu.decorator.Severity.MINOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class OrderedDistinctedMessageServiceTests {
    private MessageService messageService;
    private Message messageMin;
    private Message messageMaj;
    private Message messageErr;
    private Message messageEmpty;

    @BeforeEach
    void precondition() {
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
        assertThat(throwMessage.getMessage()).isEqualTo("notValidArgMessage");
    }

    @Test
    @Tag("negative")
    @DisplayName("Проверка валидации, когда doubling = null")
    public void checkValidationDoublingNull() {
        Throwable throwMessage = assertThrows(LogException.class, () -> messageService.log(ASC, (Doubling) null, messageErr));
        assertThat(throwMessage.getMessage()).isEqualTo("notValidArgMessage");
    }

    @Test
    @Tag("negative")
    @DisplayName("Проверка валидации, когда doubling = null при незаданном messageOrder")
    public void checkValidationDoublingNullWhenOrderEmpty() {
        Throwable throwMessage = assertThrows(LogException.class, () -> messageService.log((Doubling) null, messageErr));
        assertThat(throwMessage.getMessage()).isEqualTo("notValidArgMessage");
    }

    @Test
    @Tag("negative")
    @DisplayName("Проверка валидации, когда messageOrder = null при незаданном doubling")
    public void checkValidationOrderNullWhenDoublingEmpty() {
        Throwable throwMessage = assertThrows(LogException.class, () -> messageService.log((MessageOrder) null, messageErr));
        assertThat(throwMessage.getMessage()).isEqualTo("notValidArgMessage");
    }

    @Test
    @Tag("negative")
    @DisplayName("Проверка валидации, когда в Messages все месседжи = null")
    public void checkValidationMessagesNull() {
        Throwable throwMessage = assertThrows(LogException.class, () -> messageService.log(ASC, DOUBLES, null, null));
        assertThat(throwMessage.getMessage()).isEqualTo("notValidArgMessage");
    }

    @Test
    @Tag("negative")
    @DisplayName("Проверка валидации, когда в Messages не заданы")
    public void checkValidationMessagesEmpty() {
        Throwable throwMessage = assertThrows(LogException.class, () -> messageService.log(DESC, DOUBLES));
        assertThat(throwMessage.getMessage()).isEqualTo("notValidArgMessage");
    }

    @Test
    @Tag("negative")
    @DisplayName("Проверка валидации, когда в Messages имеется message=''")
    public void checkValidationMessagesContainEmptyMessage() {
        Throwable throwMessage = assertThrows(LogException.class, () -> messageService.log(ASC, DOUBLES, messageErr, messageEmpty));
        assertThat(throwMessage.getMessage()).isEqualTo("notValidArgMessage");
    }

    @Test
    @Tag("positive")
    @DisplayName("Проверка, что при повторных вызовах мессаджи записываются в коллекцию корректно")
    public void checkMessagesAfterDoubleLog() {
        messageService.log(ASC, DOUBLES, messageMin);
        messageService.log(ASC, DOUBLES, messageMaj);
        var messages = messageService.findAll();
        assertThat(messages).isNotNull().hasSize(2);
        var messMin = messages.stream()
                .filter(m -> m.getId().equals(messageMin.getId()))
                .collect(Collectors.toList());
        var messMaj = messages.stream()
                .filter(m -> m.getId().equals(messageMaj.getId()))
                .collect(Collectors.toList());
        assertThat(messMin).hasSize(1);
        assertThat(messMin.get(0).getSeverity()).isEqualTo(messageMin.getSeverity());
        assertThat(messMin.get(0).getBody()).isEqualTo(messageMin.getBody());
        assertThat(messMaj).hasSize(1);
        assertThat(messMaj.get(0).getSeverity()).isEqualTo(messageMaj.getSeverity());
        assertThat(messMaj.get(0).getBody()).isEqualTo(messageMaj.getBody());
    }

    @Test
    @Tag("positive")
    @DisplayName("Проверка, что при doubling = DOUBLES, одинаковые сообщения не дедуплицируются")
    public void checkDoubleMessages() {
        messageService.log(ASC, DOUBLES, messageMin, messageMin);
        var messages = messageService.findAll();
        assertThat(messages).isNotNull().hasSize(2);
        for (var message : messages) {
            assertThat(message.getSeverity()).isEqualTo(messageMin.getSeverity());
            assertThat(message.getBody()).isEqualTo(messageMin.getBody());
        }
    }

    @Test
    @Tag("positive")
    @DisplayName("Проверка, что при doubling = DISTINCT, одинаковые сообщения дедуплицируются")
    public void checkDistinctMessages() {
        messageService.log(ASC, DISTINCT, messageMin, messageMin);
        var messages = messageService.findAll();
        assertThat(messages).isNotNull().hasSize(1);
        for (var message : messages) {
            assertThat(message.getSeverity()).isEqualTo(messageMin.getSeverity());
            assertThat(message.getBody()).isEqualTo(messageMin.getBody());
        }
    }

    @Test
    @Tag("positive")
    @DisplayName("Проверка, что при незаданном doubling, сообщения записываются без дедупликации")
    public void checkDefaultDoubling() {
        messageService.log(ASC, messageMin, messageMin);
        var messages = messageService.findAll();
        assertThat(messages).isNotNull().hasSize(2);
        for (var message : messages) {
            assertThat(message.getSeverity()).isEqualTo(messageMin.getSeverity());
            assertThat(message.getBody()).isEqualTo(messageMin.getBody());
        }
    }
}
