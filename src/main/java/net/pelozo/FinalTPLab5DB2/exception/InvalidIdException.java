package net.pelozo.FinalTPLab5DB2.exception;

public class InvalidIdException extends Throwable {

    public static final Integer errorCode = 1440;

    public InvalidIdException(String resource) {
        super(String.format("%s doesn't exists", resource));
    }
}
