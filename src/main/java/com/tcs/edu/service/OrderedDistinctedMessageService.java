package com.tcs.edu.service;

import com.tcs.edu.decorator.*;
import com.tcs.edu.domain.Message;
import com.tcs.edu.repository.HashMapMessageRepository;
import com.tcs.edu.repository.MessageRepository;
import com.tcs.edu.validator.LogException;
import com.tcs.edu.validator.ValidatingService;

import java.util.UUID;

public class OrderedDistinctedMessageService extends ValidatingService implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageDecorator decorator;
    private final SeverityLevelMapper levelMapper = new SeverityLevelMapper();

    public OrderedDistinctedMessageService(MessageRepository messageRepository, MessageDecorator decorator) {
        this.messageRepository = messageRepository;
        this.decorator = decorator;
    }

    public OrderedDistinctedMessageService() {
        this(new HashMapMessageRepository(), new TimestampMessageDecorator());
    }

    public Message findByPrimaryKey(UUID key) {
        return messageRepository.findByPrimaryKey(key);
    }

    /**
     * @param messageOrder порядок вывода сообщений
     * @param doubling     признак дедупликации сообщений
     * @param messages     список объектов Message
     * @apiNote Сервис преобразования сообщений и вывода
     */
    @Override
    public void log(MessageOrder messageOrder, Doubling doubling, Message... messages)
            throws LogException {
        try {
            super.isArgValid(doubling);
            if (doubling.equals(Doubling.DOUBLES)) {
                log(messageOrder, messages);
            } else if (doubling.equals(Doubling.DISTINCT)) {
                log(messageOrder, deduplicate(messages));
            }
        } catch (IllegalArgumentException e) {
            throw new LogException("notValidArgMessage", e);
        }
    }

    @Override
    public void log(MessageOrder messageOrder, Message... messages) {
        try {
            super.isArgValid(messageOrder);
            if (messageOrder.equals(MessageOrder.ASC)) {
                log(messages);
            } else if (messageOrder.equals(MessageOrder.DESC)) {
                log(reverse(messages));
            }
        } catch (IllegalArgumentException e) {
            throw new LogException("notValidArgMessage", e);
        }
    }

    @Override
    public void log(Message... messages) {
        try {
            super.isArgValid(messages);
            for (Message currentMessage : messages) {
                super.isArgValid(currentMessage);
                currentMessage.setBody(String.format("%s %s", currentMessage.getBody(),
                        levelMapper.mapToString(currentMessage.getSeverity())));
                messageRepository.create(decorator.decorate(currentMessage));
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new LogException("notValidArgMessage", e);
        }
    }

    @Override
    public void log(Doubling doubling, Message... messages) {
        log(MessageOrder.ASC, doubling, messages);
    }

    /**
     * @param messages массив сообщений
     * @return массив сообщений без дубликатов
     * @implNote Дедупликатор сообщений
     */
    private Message[] deduplicate(Message... messages) {
        Message[] messagesOutput = new Message[messages.length];
        if (messages.length != 0) {
            messagesOutput[0] = messages[0];
            int k = 1;
            for (int i = 1; i < messages.length; i++) {
                if (!checkContains(messages[i], messagesOutput)) {
                    messagesOutput[k] = messages[i];
                    k++;
                }
            }
        }
        return messagesOutput;
    }

    /**
     * @param messages массив сообщений
     * @return массив сообщений в обратном порядке
     * @implNote реверс массива сообщений
     */
    private Message[] reverse(Message... messages) {
        int k = messages.length;
        Message[] messagesReverse = new Message[k];
        for (int i = 0; i < k; i++) {
            messagesReverse[i] = messages[k - i - 1];
        }
        return messagesReverse;
    }

    /**
     * @param message  Message
     * @param messages Message[]
     * @return boolean, если message содержится в массиве messages, то true, иначе false
     * @implNote Метод проверяет cовпадение поля body объекта message с полем body одного из объектов массива messages.
     * Если message = null или не найдено ни одного совпадения, то возвращает false.
     */
    private boolean checkContains(Message message, Message[] messages) {
        if (message != null) {
            for (Message s : messages) {
                if (s != null && s.getBody().equals(message.getBody())) {
                    return true;
                }
            }
        }
        return false;
    }
}
