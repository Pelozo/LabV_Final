package net.pelozo.FinalTPLab5DB2.exception;

public class MeterNotExistsException extends Throwable {

    public static final Integer errorCode = 135;
    public MeterNotExistsException(){super("This meter does not exists!");}
}
