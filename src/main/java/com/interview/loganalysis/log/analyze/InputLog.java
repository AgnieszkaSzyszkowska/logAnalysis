package com.interview.loganalysis.log.analyze;

import lombok.Getter;

@Getter
public class InputLog {

    private final String uniqueId;
    private final InputLogState state;
    private final long timestamp;
    private String logType;
    private String host;

    public InputLog(final String uniqueId, final InputLogState state, final long timestamp) {
        this.uniqueId = uniqueId;
        this.state = state;
        this.timestamp = timestamp;
    }

    public InputLog(final String uniqueId, final InputLogState state, final long timestamp, final String logType, final String host) {
        this.uniqueId = uniqueId;
        this.state = state;
        this.timestamp = timestamp;
        this.logType = logType;
        this.host = host;
    }
}
