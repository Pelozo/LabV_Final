package net.pelozo.FinalTPLab5DB2.exception;


public class NonExistentResourceException extends Throwable{

    public static final Integer errorCode = 100;

    public NonExistentResourceException() {
        super("Resource doesn't exists");
    }
}
