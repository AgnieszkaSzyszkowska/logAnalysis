package com.interview.loganalysis.jsonFileParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.loganalysis.log.analyze.InputLogData;
import com.interview.loganalysis.log.analyze.InputLogState;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
@Component
public class InputLogDataImpl implements InputLogData {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputLogDataImpl.class);

    @Override
    public Stream<com.interview.loganalysis.log.analyze.InputLog> streamInputData() throws IOException {

        final String fileName = "src/main/resources/InputLogs.txt";
        final ObjectMapper objectMapper = new ObjectMapper();

        final BufferedReader br = Files.newBufferedReader(Paths.get(fileName));

        return br.lines()
                .map(line -> {
                    try {
                        return objectMapper.readValue(line, InputLog.class);
                    } catch (IOException e) {
                        LOGGER.warn("File parsing problem for line: {}", line);
                        LOGGER.debug("Exception: ", e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(inputLog -> {
                    try {
                        return createDomainInputLog(inputLog);
                    } catch (UnrecognizedStateException e) {
                        LOGGER.warn("State field not recognized for id: {}", inputLog.getUniqueId());
                        LOGGER.debug("Exception: ", e);
                    }
                    return null;
                })
                .filter(Objects::nonNull);

    }

    private com.interview.loganalysis.log.analyze.InputLog createDomainInputLog(final InputLog inputLog) throws UnrecognizedStateException {

        final Optional<String> logType = Optional.ofNullable(inputLog.getLogType());

        if (logType.isPresent()) {
            return new com.interview.loganalysis.log.analyze.InputLog(inputLog.getUniqueId(), assignState(inputLog.getState()), inputLog.getTimestamp(), inputLog.getLogType(), inputLog.getHost());
        } else {
            return new com.interview.loganalysis.log.analyze.InputLog(inputLog.getUniqueId(), assignState(inputLog.getState()), inputLog.getTimestamp());
        }
    }

    private InputLogState assignState(final String state) throws UnrecognizedStateException {
        switch (state) {
            case "STARTED":
                return InputLogState.STARTED;
            case "FINISHED":
                return InputLogState.FINISHED;
            default:
                throw new UnrecognizedStateException("state");
        }
    }
}
