package com.epam.autocode.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Conversation {
    private final String id;
    private final List<Message> messages;

    public Conversation() {
        this.id = UUID.randomUUID().toString();
        this.messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public String getId() {
        return id;
    }
}
