package com.tcs.edu.domain;

import com.tcs.edu.decorator.Severity;

import java.util.Objects;

/**
 * Класс, описывающий структуру сообщения.
 * body - тело сообщения
 * severity - важность сообщения
 */
public class Message {
    String body;
    Severity severity;

    public Message(Severity severity, String body) {
        this.body = body;
        this.severity = severity;
    }

    public Message(String body) {
        this(Severity.MINOR, body);
    }

    public String getBody() {
        return body;
    }

    public Severity getSeverity() {
        return severity;
    }

    @Override
    public String toString() {
        return "Message{" +
                "body='" + body + '\'' +
                ", severity=" + severity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(body, message.body) && severity == message.severity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, severity);
    }
}
