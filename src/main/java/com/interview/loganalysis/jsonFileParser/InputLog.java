package com.interview.loganalysis.jsonFileParser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InputLog {

    private final String uniqueId;
    private final String state;
    private final long timestamp;
    private String logType;
    private String host;

    @JsonCreator
    public InputLog(
            @JsonProperty(value = "id", required = true) final String id,
            @JsonProperty(value = "state", required = true) final String state,
            @JsonProperty(value = "timestamp", required = true) final long timestamp,
            @JsonProperty(value = "type") final String logType,
            @JsonProperty(value = "host") final String host) {
        this.uniqueId = id;
        this.state = state;
        this.timestamp = timestamp;
        this.logType = logType;
        this.host = host;
    }
}
