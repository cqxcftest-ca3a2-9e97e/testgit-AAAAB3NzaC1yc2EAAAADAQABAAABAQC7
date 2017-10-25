package com.forte.runtime.pagination;

public abstract class BaseDialect
  implements Dialect
{
  protected static final String SQL_END_DELIMITER = ";";

  protected String trim(String sql)
  {
    sql = sql.trim();
    if (sql.endsWith(";")) {
      sql = sql.substring(0, sql.length() - 1 - ";".length());
    }
    return sql;
  }

  public boolean supportsLimit()
  {
    return true;
  }

  public boolean supportOffsetLimit()
  {
    return true;
  }
}