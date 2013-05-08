package fi.muni.pv168.utils;

import fi.muni.pv168.exceptions.ServiceFailureException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * some database tools
 *
 * @author mulan
 */
public class DBUtils {

    private static final Logger logger = Logger.getLogger(
            DBUtils.class.getName());

    /**
     * Closes connection and logs possible error.
     *
     * @param conn connection to close
     * @param statements statements to close
     */
    public static void closeQuietly(Connection connection, Statement... statements) {
        for (Statement st : statements) {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, "Error while trying to close statement", ex);
                }
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

    /**
     * Reads SQL statements from file. SQL commands in file must be separated by
     * a semicolon.
     *
     * @param url url of the file
     * @return array of command strings
     */
    private static String[] readSqlStatements(URL url) {
        try {
            char buffer[] = new char[256];
            StringBuilder result = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(url.openStream(), "UTF-8");
            while (true) {
                int count = reader.read(buffer);
                if (count < 0) {
                    break;
                }
                result.append(buffer, 0, count);
            }
            return result.toString().split(";");
        } catch (IOException ex) {
            throw new RuntimeException("Cannot read " + url, ex);
        }
    }

    /**
     * Try to execute script for creating tables. If tables already exist,
     * appropriate exception is catched and ignored.
     *
     * @param ds dataSource
     * @param scriptUrl url of script for creating tables
     * @throws SQLException when operation fails
     */
    public static void tryCreateTables(DataSource ds, URL scriptUrl) throws SQLException {
        try {
            executeSqlScript(ds, scriptUrl);
            logger.warning("Tables created");
        } catch (SQLException ex) {
            if ("X0Y32".equals(ex.getSQLState())) {
                // This code represents "Table/View/... already exists"
                // This code is Derby specific!
                return;
            } else {
                throw ex;
            }
        }
    }

    /**
     * Executes SQL script.
     *
     * @param ds datasource
     * @param scriptUrl url of sql script to be executed
     * @throws SQLException when operation fails
     */
    public static void executeSqlScript(DataSource ds, URL scriptUrl) throws SQLException {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            for (String sqlStatement : readSqlStatements(scriptUrl)) {
                if (!sqlStatement.trim().isEmpty()) {
                    conn.prepareStatement(sqlStatement).executeUpdate();
                }
            }
        } finally {
            closeQuietly(conn);
        }
    }
}
