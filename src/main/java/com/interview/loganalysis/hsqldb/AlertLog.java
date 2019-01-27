package com.interview.loganalysis.hsqldb;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
class AlertLog {

    @Id
    private String id;
    @Column(nullable = false)
    private long duration;
    @Column
    private String type;
    @Column
    private String host;
    @Column(nullable = false)
    private Boolean alert;

    AlertLog(final String id, final long duration, final Boolean alert) {
        this.id = id;
        this.duration = duration;
        this.alert = alert;
    }

    AlertLog(final String id, final long duration, final String type, final String host, final Boolean alert) {
        this.id = id;
        this.duration = duration;
        this.type = type;
        this.host = host;
        this.alert = alert;
    }
}
