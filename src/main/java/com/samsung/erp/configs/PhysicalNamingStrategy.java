package com.samsung.erp.configs;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Locale;

public class PhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private static final String TABLE_PREFIX = "ss";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String newName = camelToUnderscore(name.getText().toLowerCase(Locale.ROOT));
        return new Identifier(TABLE_PREFIX + "_" + newName, name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        String newName = camelToUnderscore(name.getText().toLowerCase(Locale.ROOT));
        return new Identifier(newName, name.isQuoted());
    }

    protected static String camelToUnderscore(String name) {
        return name.replaceAll("([^_A-Z])([A-Z])", "$1_$2");
    }
}
