package fi.muni.pv168.utils;

import fi.muni.pv168.exceptions.ServiceFailureException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * some database tools
 * @author mulan
 */
public class DBUtils {

    private static final Logger logger = Logger.getLogger(
            DBUtils.class.getName());

    /**
     * Closes connection and logs possible error.
     * 
     * @param conn connection to close
     * @param statements  statements to close
     */
    public static void closeQuietly(Connection connection, Statement querry) {
        if (querry != null) {
            try {
                querry.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Error while trying to close statement", ex);
            }
        }

        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Error while restoring commit mode", ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Error while closing connection", ex);
            }
        }
    }

    /**
     * Rolls back transaction and logs possible error.
     * 
     * @param conn connection
     */
    public static void doRollbackQuietly(Connection connection) {
        if (connection != null) {
            try {
                if (connection.getAutoCommit()) {
                    throw new IllegalMonitorStateException("Connection is in autocommit mode.");
                }
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Error when doing rollback", ex);
            }
        }
    }

    /**
     * Extract key from given ResultSet.
     * 
     * @param key resultSet with key
     * @return key from given result set
     * @throws SQLException when operation fails
     */
    public static Long getId(ResultSet key) throws SQLException {
        if (key.getMetaData().getColumnCount() != 1) {
            throw new IllegalArgumentException("Too many columns in ResultSet");
        }
        if (key.next()) {
            Long result = key.getLong(1);
            if (key.next()) {
                throw new IllegalArgumentException("Too many rows in ResultSet");
            }
            return result;
        } else {
            throw new IllegalArgumentException("No rows in ResultSet");
        }
    }

    /**
     * Check if updates count is one. Otherwise appropriate exception is thrown.
     * 
     * @param count updates count.
     * @param isInsert flag if performed operation was insert
     * @throws ServiceFailureException when updates count is unexpected number
     */
    public static void checkUpdatesCount(int count, boolean isInsert) throws ServiceFailureException {
        if (!isInsert && count == 0) {
            throw new ServiceFailureException("Update failed, no such item.");
        }
        if (count != 1) {
            throw new ServiceFailureException("Unexpected row count in database affected: " + count);
        }
    }
}
