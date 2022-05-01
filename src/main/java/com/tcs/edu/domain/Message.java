package com.tcs.edu.domain;

import com.tcs.edu.decorator.Severity;

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
}
