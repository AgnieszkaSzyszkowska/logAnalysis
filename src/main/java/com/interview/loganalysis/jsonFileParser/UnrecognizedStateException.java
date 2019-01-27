package com.interview.loganalysis.jsonFileParser;

class UnrecognizedStateException extends Exception {

    UnrecognizedStateException(final String state) {
        super(state);
    }
}
