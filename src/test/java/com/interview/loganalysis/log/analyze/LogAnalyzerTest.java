package com.interview.loganalysis.log.analyze;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LogAnalyzerTest {

    @Test
    void shouldGetDataAndInvokeStorage() throws IOException {

        //given
        final InputLogData inputLogData = mock(InputLogData.class);
        final AlertedLogsStorage alertedLogsStorage = mock(AlertedLogsStorage.class);
        final LogAnalyzer logAnalyzer = new LogAnalyzer(inputLogData, alertedLogsStorage);
        when(inputLogData.streamInputData()).thenReturn(inputs());

        //when
        logAnalyzer.startAnalysis();

        //then
        verify(inputLogData).streamInputData();
        verify(alertedLogsStorage).storeAlertedLogs(any(Log.class));

    }

    private Stream<InputLog> inputs() {
        return Stream.of(
                new InputLog("ABC", InputLogState.STARTED, 10),
                new InputLog("ABC", InputLogState.FINISHED, 20)
        );
    }

}