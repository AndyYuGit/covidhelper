package com.fyp.covidhelper.Appender;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


import ch.qos.logback.classic.db.DBAppender;
import ch.qos.logback.classic.db.DBHelper;
import ch.qos.logback.classic.db.names.DBNameResolver;
import ch.qos.logback.classic.spi.*;


public class CustomDBAppender extends DBAppender {

    protected String insertPropertiesSQL;
    protected String insertExceptionSQL;
    protected String insertSQL;
    protected static final Method GET_GENERATED_KEYS_METHOD;

    private DBNameResolver dbNameResolver;

    static final int DATETIME_INDEX = 1;
    static final int FORMATTED_MESSAGE_INDEX = 2;
    static final int LOGGER_NAME_INDEX = 3;
    static final int LEVEL_STRING_INDEX = 4;
    static final int THREAD_NAME_INDEX = 5;
    static final int REFERENCE_FLAG_INDEX = 6;
    static final int ARG0_INDEX = 7;
    static final int ARG1_INDEX = 8;
    static final int ARG2_INDEX = 9;
    static final int ARG3_INDEX = 10;
    static final int CALLER_FILENAME_INDEX = 11;
    static final int CALLER_CLASS_INDEX = 12;
    static final int CALLER_METHOD_INDEX = 13;
    static final int CALLER_LINE_INDEX = 14;
    static final int EVENT_ID_INDEX = 15;

    static final StackTraceElement EMPTY_CALLER_DATA = CallerData.naInstance();

    static {
        Method getGeneratedKeysMethod;
        try {
            // the
            getGeneratedKeysMethod = PreparedStatement.class.getMethod("getGeneratedKeys", (Class[]) null);
        } catch (Exception ex) {
            getGeneratedKeysMethod = null;
        }
        GET_GENERATED_KEYS_METHOD = getGeneratedKeysMethod;
    }


    public void setDbNameResolver(DBNameResolver dbNameResolver) {
        //this.dbNameResolver = dbNameResolver;
    }

    @Override
    public void start() {
        if (dbNameResolver == null)
            dbNameResolver = new CustomDBNameResolver();
        insertExceptionSQL = SQLBuilder.buildInsertExceptionSQL(dbNameResolver);
        insertPropertiesSQL = SQLBuilder.buildInsertPropertiesSQL(dbNameResolver);
        insertSQL = SQLBuilder.buildInsertSQL(dbNameResolver);
        super.start();
    }

    @Override
    protected void subAppend(ILoggingEvent event, Connection connection, PreparedStatement insertStatement) throws Throwable {

        bindLoggingEventWithInsertStatement(insertStatement, event);
        bindLoggingEventArgumentsWithPreparedStatement(insertStatement, event.getArgumentArray());
        bindCallerDataWithPreparedStatement(insertStatement, event.getCallerData());

        int updateCount = insertStatement.executeUpdate();
        if (updateCount != 1) {
            addWarn("Failed to insert loggingEvent");
        }
    }


    protected void secondarySubAppend(ILoggingEvent event, Connection connection, long eventId) throws Throwable {
        super.secondarySubAppend(event,connection,eventId);
    }


    void bindLoggingEventWithInsertStatement(PreparedStatement stmt, ILoggingEvent event) throws SQLException {
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(event.getTimeStamp()), ZoneId.systemDefault());
        stmt.setObject(DATETIME_INDEX, date);
        stmt.setString(FORMATTED_MESSAGE_INDEX, event.getFormattedMessage());
        stmt.setString(LOGGER_NAME_INDEX, event.getLoggerName());
        stmt.setString(LEVEL_STRING_INDEX, event.getLevel().toString());
        stmt.setString(THREAD_NAME_INDEX, event.getThreadName());
        stmt.setShort(REFERENCE_FLAG_INDEX, DBHelper.computeReferenceMask(event));
    }

    void bindLoggingEventArgumentsWithPreparedStatement(PreparedStatement stmt, Object[] argArray) throws SQLException {
        int arrayLen = argArray != null ? argArray.length : 0;

        for (int i = 0; i < arrayLen && i < 4; i++) {
            stmt.setString(ARG0_INDEX + i, asStringTruncatedTo254(argArray[i]));
        }
        if (arrayLen < 4) {
            for (int i = arrayLen; i < 4; i++) {
                stmt.setString(ARG0_INDEX + i, null);
            }
        }
    }

    String asStringTruncatedTo254(Object o) {
        String s = null;
        if (o != null) {
            s = o.toString();
        }

        if (s == null) {
            return null;
        }
        if (s.length() <= 254) {
            return s;
        } else {
            return s.substring(0, 254);
        }
    }

    void bindCallerDataWithPreparedStatement(PreparedStatement stmt, StackTraceElement[] callerDataArray) throws SQLException {

        StackTraceElement caller = extractFirstCaller(callerDataArray);

        stmt.setString(CALLER_FILENAME_INDEX, caller.getFileName());
        stmt.setString(CALLER_CLASS_INDEX, caller.getClassName());
        stmt.setString(CALLER_METHOD_INDEX, caller.getMethodName());
        stmt.setString(CALLER_LINE_INDEX, Integer.toString(caller.getLineNumber()));
    }

    private StackTraceElement extractFirstCaller(StackTraceElement[] callerDataArray) {
        StackTraceElement caller = EMPTY_CALLER_DATA;
        if (hasAtLeastOneNonNullElement(callerDataArray))
            caller = callerDataArray[0];
        return caller;
    }

    private boolean hasAtLeastOneNonNullElement(StackTraceElement[] callerDataArray) {
        return callerDataArray != null && callerDataArray.length > 0 && callerDataArray[0] != null;
    }


    @Override
    protected Method getGeneratedKeysMethod() {
        return GET_GENERATED_KEYS_METHOD;
    }

    @Override
    protected String getInsertSQL() {
        return insertSQL;
    }
}