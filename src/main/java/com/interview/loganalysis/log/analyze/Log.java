package com.interview.loganalysis.log.analyze;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Log {

    private final String uniqueId;
    private long duration;
    private String logType;
    private String host;
    private Boolean alert;

    private long startTimestamp;
    private long endTimestamp;

    void setEndTimestampAndAlertIfApplicable(final long endTimestamp) {
        this.endTimestamp = endTimestamp;
        this.duration = this.endTimestamp - this.startTimestamp;
        if (this.duration > 4) {
            this.alert = true;
        } else {
            this.alert = false;
        }
    }

    void setStartTimestampAndAlertIfApplicable(final long startTimestamp) {
        this.startTimestamp = startTimestamp;
        this.duration = this.endTimestamp - this.startTimestamp;
        if (this.duration > 4) {
            this.alert = true;
        } else {
            this.alert = false;
        }
    }
}
