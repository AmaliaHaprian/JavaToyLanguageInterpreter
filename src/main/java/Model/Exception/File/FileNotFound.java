package Model.Exception.File;

import Model.Exception.MyException;

public class FileNotFound extends MyException {
    public FileNotFound(String message) {
        super(message);
    }
}
