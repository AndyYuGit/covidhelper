package com.fyp.covidhelper.Appender;

import ch.qos.logback.classic.db.names.DBNameResolver;

public class CustomDBNameResolver implements DBNameResolver {

    public <N extends Enum<?>> String getTableName(N tableName) {
        return tableName.toString().toLowerCase();
    }

    public <N extends Enum<?>> String getColumnName(N columnName) {
        return columnName.toString().toLowerCase();
    }

}