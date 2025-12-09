package com.epam.autocode;

import com.epam.autocode.models.Message;
import com.epam.autocode.models.Role;
import com.epam.autocode.models.Conversation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ChatApplication core functionality.
 */
public class ChatApplicationTest {

    private Conversation conversation;

    @BeforeEach
    void setUp() {
        conversation = new Conversation();
    }

    @Test
    void testMessageCreation() {
        Message message = new Message(Role.USER, "Hello, world!");

        assertEquals("user", message.getRole());
        assertEquals("Hello, world!", message.getContent());
        assertNotNull(message.role());
        assertNotNull(message.content());
    }

    @Test
    void testConversationAddMessage() {
        Message systemMessage = new Message(Role.SYSTEM, "You are a helpful assistant.");
        Message userMessage = new Message(Role.USER, "Hello!");

        conversation.addMessage(systemMessage);
        conversation.addMessage(userMessage);

        assertEquals(2, conversation.getMessages().size());
        assertEquals(systemMessage, conversation.getMessages().get(0));
        assertEquals(userMessage, conversation.getMessages().get(1));
    }

    @Test
    void testConversationHasId() {
        assertNotNull(conversation.getId());
        assertFalse(conversation.getId().isEmpty());
    }

    @Test
    void testConversationEmpty() {
        assertTrue(conversation.getMessages().isEmpty());

        conversation.addMessage(new Message(Role.USER, "Test message"));
        assertFalse(conversation.getMessages().isEmpty());
    }

    @Test
    void testRoleValues() {
        assertEquals("system", Role.SYSTEM.getValue());
        assertEquals("user", Role.USER.getValue());
        assertEquals("assistant", Role.AI.getValue());
    }

    @Test
    void testMessageRecord() {
        Message message = new Message(Role.AI, "Hello! How can I help you?");

        assertEquals(Role.AI, message.role());
        assertEquals("Hello! How can I help you?", message.content());
        assertEquals("assistant", message.getRole());
        assertEquals("Hello! How can I help you?", message.getContent());
    }

    @Test
    void testConversationImmutableList() {
        Message msg1 = new Message(Role.USER, "First message");
        Message msg2 = new Message(Role.AI, "Second message");

        conversation.addMessage(msg1);
        List<Message> messages = conversation.getMessages();

        // Adding to returned list should not affect original
        messages.add(msg2);
        assertEquals(1, conversation.getMessages().size());
    }
}
