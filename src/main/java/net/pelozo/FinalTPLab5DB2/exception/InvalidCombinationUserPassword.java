package net.pelozo.FinalTPLab5DB2.exception;

public class InvalidCombinationUserPassword extends Throwable {
    public static final Integer errorCode = 140;
    public InvalidCombinationUserPassword(){super("Username and Password are incorrect!");}
}
