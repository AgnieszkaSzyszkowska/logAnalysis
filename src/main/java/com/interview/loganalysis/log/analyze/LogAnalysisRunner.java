package com.interview.loganalysis.log.analyze;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LogAnalysisRunner implements CommandLineRunner {

    private LogAnalyzer logAnalyzer;

    @Override
    public void run(String... args) throws Exception {
        logAnalyzer.startAnalysis();

    }
}
