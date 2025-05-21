package jm.task.core.jdbc.exception;

import java.sql.SQLException;

public class DaoException extends RuntimeException {
    public DaoException(Exception e) {
        super(e);
    }
}
