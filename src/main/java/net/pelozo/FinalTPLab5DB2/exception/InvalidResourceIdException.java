package net.pelozo.FinalTPLab5DB2.exception;

public class InvalidResourceIdException extends Throwable{

        public static final Integer errorCode = 720;

        public InvalidResourceIdException(String resource) {
            super(String.format("%s doesn't exists", resource));
        }
}
