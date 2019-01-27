package com.interview.loganalysis.log.analyze;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LogAnalysisRunner implements CommandLineRunner {

    private LogAnalyzer logAnalyzer;

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalysisRunner.class);

    @Override
    public void run(String... args) throws Exception {
        LOGGER.debug("Analysis about to start ..");
        logAnalyzer.startAnalysis();
        LOGGER.debug("Analysis completed.");
    }
}
