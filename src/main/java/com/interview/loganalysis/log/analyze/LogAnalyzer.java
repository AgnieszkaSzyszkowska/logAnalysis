package com.interview.loganalysis.log.analyze;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Component
public class LogAnalyzer {

    private InputLogData inputLogData;
    private AlertedLogsStorage alertedLogsStorage;

    public void startAnalysis() throws IOException {

        final Map<String, Log> mapOfLogs = new ConcurrentHashMap<>();

        inputLogData.streamInputData()
                .peek(inputLog -> {
                    if (InputLogState.STARTED.equals(inputLog.getState())) {
                        mapOfLogs.put(inputLog.getUniqueId(), createNewLog(inputLog));
                    }
                })
                .filter(inputLog -> InputLogState.FINISHED.equals(inputLog.getState()))
                .map(inputLog -> {
                    final Log logToClean = mapOfLogs.get(inputLog.getUniqueId());
                    logToClean.setEndTimestampAndAlertIfApplicable(inputLog.getTimestamp());
                    mapOfLogs.remove(inputLog.getUniqueId());

                    return logToClean;
                })
                .forEach(log -> alertedLogsStorage.storeAlertedLogs(log));

    }

    private Log createNewLog(final InputLog inputLog) {

        final Optional<String> logType = Optional.ofNullable(inputLog.getLogType());

        if (logType.isPresent()) {
            return new Log(inputLog.getUniqueId(), inputLog.getLogType(), inputLog.getHost(), inputLog.getTimestamp());
        } else {
            return new Log(inputLog.getUniqueId(), inputLog.getTimestamp());
        }
    }
}
