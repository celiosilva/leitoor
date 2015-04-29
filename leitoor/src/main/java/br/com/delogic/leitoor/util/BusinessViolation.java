package br.com.delogic.leitoor.util;

public abstract class BusinessViolation implements Violation {

    private final String name;
    private final String message;

    public BusinessViolation(String name, String message) {
        this.name = name;
        this.message = message;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
