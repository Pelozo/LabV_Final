package net.pelozo.FinalTPLab5DB2.exception;

public class ResidenceNotExistsException extends Throwable {

    public static final Integer errorCode = 135;

    public ResidenceNotExistsException(){super("This residence does not exists!");}
}
