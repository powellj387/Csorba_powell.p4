package wol;

import java.io.Serializable;

public class DuplicateKeyException extends RuntimeException implements Serializable {

    public DuplicateKeyException() {
        super();
    }

    public DuplicateKeyException(String message) {
        super(message);
    }
}
