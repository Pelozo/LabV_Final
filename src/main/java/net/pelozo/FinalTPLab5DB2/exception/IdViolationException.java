package net.pelozo.FinalTPLab5DB2.exception;

public class IdViolationException extends Throwable{
    public static final Integer errorCode = 130;

    public IdViolationException() {
        super("Cannot replace id");
    }
}
