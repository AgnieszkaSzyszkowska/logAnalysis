package com.interview.loganalysis.log.analyze;

import lombok.Getter;

@Getter
public class Log {

    private final String uniqueId;
    private long duration;
    private String logType;
    private String host;
    private Boolean alert;

    private long startTimestamp;
    private long endTimestamp;

    Log(final String uniqueId, final long startTimestamp) {
        this.uniqueId = uniqueId;
        this.startTimestamp = startTimestamp;
    }

    Log(final String uniqueId, final String logType, final String host, final long startTimestamp) {
        this.uniqueId = uniqueId;
        this.logType = logType;
        this.host = host;
        this.startTimestamp = startTimestamp;
    }

    void setEndTimestampAndAlertIfApplicable(final long endTimestamp) {
        this.endTimestamp = endTimestamp;
        this.duration = this.endTimestamp - this.startTimestamp;
        if (this.duration > 4) {
            this.alert = true;
        }
    }
}
