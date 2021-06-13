package net.pelozo.FinalTPLab5DB2.exception;

public class ClientNotExistsException extends Throwable{

    public static final Integer errorCode = 125;

    public ClientNotExistsException(){super("This client does not exists!");}
}
