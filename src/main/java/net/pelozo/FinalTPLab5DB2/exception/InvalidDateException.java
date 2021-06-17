package net.pelozo.FinalTPLab5DB2.exception;

public class InvalidDateException extends Throwable{


    public static final Integer errorCode = 1080;

    public InvalidDateException() {
        super("Invalid date");
    }
}
