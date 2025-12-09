package com.epam.autocode.clients;

import com.epam.autocode.constants.Constants;
import com.epam.autocode.models.Message;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class BaseClient {
    protected final String apiKey;
    protected final String deploymentName;

    public BaseClient(String deploymentName) {
        String key = Constants.API_KEY;
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("API key cannot be null or empty");
        }
        this.apiKey = key;
        this.deploymentName = deploymentName;
    }

    public abstract Message getCompletion(List<Message> messages) throws Exception;

    public abstract CompletableFuture<Message> streamCompletion(List<Message> messages);
}
