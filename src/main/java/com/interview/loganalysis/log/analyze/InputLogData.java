package com.interview.loganalysis.log.analyze;

import java.io.IOException;
import java.util.stream.Stream;

public interface InputLogData {

    Stream<InputLog> streamInputData() throws IOException;
}
