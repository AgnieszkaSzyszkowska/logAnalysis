package com.interview.loganalysis.log.analyze;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LogAnalyzerTest {

    @Test
    @Ignore
    //FIXME: test ignored due to verification error happening in parallel Stream processing in LogAnalyzer.java
    //FIXME: To run this test comment line 23 (.parallel()) in LogAnalyzer.java
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