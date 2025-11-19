package be.skenteridis.contacts.exception;

public class DuplicateContactInformationException extends RuntimeException {
    public DuplicateContactInformationException(String message) {
        super(message);
    }
}
