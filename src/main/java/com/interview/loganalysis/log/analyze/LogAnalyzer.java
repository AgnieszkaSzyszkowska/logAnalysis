package com.interview.loganalysis.log.analyze;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Component
class LogAnalyzer {

    private InputLogData inputLogData;
    private AlertedLogsStorage alertedLogsStorage;

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalyzer.class);


    void startAnalysis() throws IOException {

        final Map<String, Log> mapOfLogs = new ConcurrentHashMap<>();

        inputLogData.streamInputData()
                .parallel()
                .forEach(inputLog -> {
                    if (InputLogState.STARTED.equals(inputLog.getState())) {
                        final Optional<Log> logToClean = Optional.ofNullable(mapOfLogs.get(inputLog.getUniqueId()));
                        if (logToClean.isPresent()) {
                            LOGGER.debug("Second input for id: {}", logToClean.get().getUniqueId());
                            logToClean.get().setStartTimestampAndAlertIfApplicable(inputLog.getTimestamp());
                            alertedLogsStorage.storeAlertedLogs(logToClean.get());
                            LOGGER.debug("Log with id: {} stored", inputLog.getUniqueId());
                            mapOfLogs.remove(inputLog.getUniqueId());
                            LOGGER.debug("Id: {} removed from map", inputLog.getUniqueId());
                        } else {
                            LOGGER.debug("First input for id: {}", inputLog.getUniqueId());
                            mapOfLogs.put(inputLog.getUniqueId(), createNewLogWithStartDate(inputLog));
                        }
                    } else if (InputLogState.FINISHED.equals(inputLog.getState())) {
                        final Optional<Log> logToClean = Optional.ofNullable(mapOfLogs.get(inputLog.getUniqueId()));
                        if (logToClean.isPresent()) {
                            LOGGER.debug("Second input for id: {}", logToClean.get().getUniqueId());
                            logToClean.get().setEndTimestampAndAlertIfApplicable(inputLog.getTimestamp());
                            alertedLogsStorage.storeAlertedLogs(logToClean.get());
                            LOGGER.debug("Log with id: {} stored", inputLog.getUniqueId());
                            mapOfLogs.remove(inputLog.getUniqueId());
                            LOGGER.debug("Id: {} removed from map", inputLog.getUniqueId());
                        } else {
                            LOGGER.debug("First input for id: {}", inputLog.getUniqueId());
                            mapOfLogs.put(inputLog.getUniqueId(), createNewLogWithEndDate(inputLog));
                        }
                    }
                });
    }


    private Log createNewLogWithStartDate(final InputLog inputLog) {

        final Optional<String> logType = Optional.ofNullable(inputLog.getLogType());

        if (logType.isPresent()) {
            return Log.builder()
                    .uniqueId(inputLog.getUniqueId())
                    .logType(inputLog.getLogType())
                    .host(inputLog.getHost())
                    .startTimestamp(inputLog.getTimestamp())
                    .build();
        } else {
            return Log.builder()
                    .uniqueId(inputLog.getUniqueId())
                    .startTimestamp(inputLog.getTimestamp())
                    .build();
        }
    }

    private Log createNewLogWithEndDate(final InputLog inputLog) {

        final Optional<String> logType = Optional.ofNullable(inputLog.getLogType());

        if (logType.isPresent()) {
            return Log.builder()
                    .uniqueId(inputLog.getUniqueId())
                    .logType(inputLog.getLogType())
                    .host(inputLog.getHost())
                    .endTimestamp(inputLog.getTimestamp())
                    .build();
        } else {
            return Log.builder()
                    .uniqueId(inputLog.getUniqueId())
                    .endTimestamp(inputLog.getTimestamp())
                    .build();
        }
    }

}
