package com.epam.autocode.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Message(Role role, String content) {

    @JsonProperty("role")
    public String getRole() {
        return role.getValue();
    }

    @JsonProperty("content")
    public String getContent() {
        return content;
    }
}
