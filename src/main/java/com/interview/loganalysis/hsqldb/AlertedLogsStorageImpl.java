package com.interview.loganalysis.hsqldb;

import com.interview.loganalysis.log.analyze.AlertedLogsStorage;
import com.interview.loganalysis.log.analyze.Log;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AlertedLogsStorageImpl implements AlertedLogsStorage {

    private AlertLogRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertedLogsStorageImpl.class);

    @Override
    public void storeAlertedLogs(final Log alertedLog) {
        repository.save(createAlertLog(alertedLog));
    }

    private AlertLog createAlertLog(final Log alertedLog) {
        LOGGER.info("Log is being parsed to entity object: {}", alertedLog.toString());
        final Optional<String> logType = Optional.ofNullable(alertedLog.getLogType());

        if (logType.isPresent()) {
            return new AlertLog(alertedLog.getUniqueId(), alertedLog.getDuration(), alertedLog.getLogType(), alertedLog.getHost(), alertedLog.getAlert());
        } else {
            return new AlertLog(alertedLog.getUniqueId(), alertedLog.getDuration(), alertedLog.getAlert());
        }
    }
}
