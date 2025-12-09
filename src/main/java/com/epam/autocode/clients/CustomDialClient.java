package com.epam.autocode.clients;

import com.epam.autocode.constants.Constants;
import com.epam.autocode.models.Message;
import com.epam.autocode.models.Role;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CustomDialClient extends BaseClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String endpoint;

    public CustomDialClient(String deploymentName) {
        super(deploymentName);
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
        this.objectMapper = new ObjectMapper();
        this.endpoint = Constants.DIAL_ENDPOINT + "/openai/deployments/" + deploymentName + "/chat/completions";
    }

    @Override
    public Message getCompletion(List<Message> messages) throws Exception {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("messages", messages);

        String jsonBody = objectMapper.writeValueAsString(requestData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("api-key", apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            JsonNode choices = jsonNode.get("choices");

            if (choices != null && !choices.isEmpty()) {
                String content = choices.get(0).get("message").get("content").asText();
                System.out.println(content);
                return new Message(Role.AI, content);
            }
            throw new RuntimeException("No choices in response");
        } else {
            throw new RuntimeException("HTTP " + response.statusCode() + ": " + response.body());
        }
    }

    @Override
    public CompletableFuture<Message> streamCompletion(List<Message> messages) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Map<String, Object> requestData = new HashMap<>();
                requestData.put("stream", true);
                requestData.put("messages", messages);

                String jsonBody = objectMapper.writeValueAsString(requestData);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endpoint))
                        .header("api-key", apiKey)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    return parseStreamResponse(response.body());
                } else {
                    throw new RuntimeException("HTTP " + response.statusCode() + ": " + response.body());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Message parseStreamResponse(String responseBody) throws IOException {
        StringBuilder content = new StringBuilder();
        String[] lines = responseBody.split("\n");

        for (String line : lines) {
            if (line.startsWith("data: ")) {
                String data = line.substring(6).trim();
                if (!"[DONE]".equals(data) && !data.isEmpty()) {
                    String contentSnippet = getContentSnippet(data);
                    System.out.print(contentSnippet);
                    content.append(contentSnippet);
                }
            }
        }

        System.out.println();
        return new Message(Role.AI, content.toString());
    }

    private String getContentSnippet(String data) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(data);
        JsonNode choices = jsonNode.get("choices");

        if (choices != null && !choices.isEmpty()) {
            JsonNode delta = choices.get(0).get("delta");
            if (delta != null && delta.has("content")) {
                return delta.get("content").asText();
            }
        }
        return "";
    }
}
