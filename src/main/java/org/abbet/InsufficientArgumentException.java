package org.abbet;

public class InsufficientArgumentException extends RuntimeException {
    private String option;

    public InsufficientArgumentException(String value) {
        this.option = value;
    }

    public String getOption() {
        return option;
    }

}