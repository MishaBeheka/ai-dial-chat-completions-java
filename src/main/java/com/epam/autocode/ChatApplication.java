package com.epam.autocode;

import com.epam.autocode.clients.CustomDialClient;
import com.epam.autocode.constants.Constants;
import com.epam.autocode.models.Conversation;
import com.epam.autocode.models.Message;
import com.epam.autocode.models.Role;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class ChatApplication {

    public static void main(String[] args) {
        start(true);
    }

    public static void start(boolean stream) {
        CustomDialClient client = new CustomDialClient("gpt-4o");
        Conversation conversation = new Conversation();
        Scanner scanner = new Scanner(System.in);

        // Get system prompt
        System.out.println("🤖 Provide System prompt or press ⏎ 'enter' to continue.");
        System.out.print("🧑‍💻 > ");
        String prompt = scanner.nextLine().trim();

        if (!prompt.isEmpty()) {
            conversation.addMessage(new Message(Role.SYSTEM, prompt));
            System.out.println("✅ System prompt successfully added to conversation.");
        } else {
            conversation.addMessage(new Message(Role.SYSTEM, Constants.DEFAULT_SYSTEM_PROMPT));
            System.out.println("ℹ️ No System prompt provided. Will be used default System prompt: '" +
                    Constants.DEFAULT_SYSTEM_PROMPT + "'");
        }

        System.out.println();
        System.out.println("❓ Type your question or 'exit' to quit.");

        // Main chat loop
        while (true) {
            System.out.print("🧑‍💻 > ");
            String userInput = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(userInput)) {
                System.out.println("👋 Exiting the chat. Goodbye!");
                break;
            }

            conversation.addMessage(new Message(Role.USER, userInput));

            System.out.println("🤖 AI:");
            try {
                Message aiMessage;
                if (stream) {
                    CompletableFuture<Message> future = client.streamCompletion(conversation.getMessages());
                    aiMessage = future.get();
                } else {
                    aiMessage = client.getCompletion(conversation.getMessages());
                }
                conversation.addMessage(aiMessage);
            } catch (Exception e) {
                System.err.println("❌ Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
