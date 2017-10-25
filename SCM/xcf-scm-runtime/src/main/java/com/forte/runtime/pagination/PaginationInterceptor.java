package com.forte.runtime.pagination;

import java.util.Properties;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.RowBounds;

@Intercepts({@org.apache.ibatis.plugin.Signature(type=org.apache.ibatis.executor.Executor.class, method="query", args={MappedStatement.class, Object.class, RowBounds.class, org.apache.ibatis.session.ResultHandler.class})})
public class PaginationInterceptor
  implements Interceptor
{
  static int INDEX_MAPPED_STATEMENT = 0;
  static int INDEX_PARAMETER = 1;
  static int INDEX_ROW_BOUNDS = 2;
  static int INDEX_RESULT_HANDLER = 3;
  private Dialect dialect;

  public Object intercept(Invocation invocation)
    throws Throwable
  {
    MappedStatement mappedStatement = getMappedStatement(invocation);
    Object parameter = getParameter(invocation);
    RowBounds rowBounds = getRowBounds(invocation);

    int offset = rowBounds.getOffset();
    int limit = rowBounds.getLimit();

    if ((this.dialect.supportsLimit()) && ((offset != 0) || (limit != 2147483647)))
    {
      BoundSql boundSql = mappedStatement.getBoundSql(parameter);
      String sql = boundSql.getSql().trim();

      if (this.dialect.supportOffsetLimit())
        sql = this.dialect.getLimitString(sql, offset, limit);
      else {
        sql = this.dialect.getLimitString(sql, 0, limit);
      }

      setMappedStatement(invocation, buildMappedStatement(mappedStatement, boundSql, sql));

      setRowBounds(invocation, RowBounds.DEFAULT);
    }

    return invocation.proceed();
  }

  private MappedStatement buildMappedStatement(MappedStatement ms, BoundSql boundSql, String sql) {
    MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), new BoundSqlSqlSource(ms, boundSql, sql), ms.getSqlCommandType());

    builder.resource(ms.getResource());
    builder.parameterMap(ms.getParameterMap());
    builder.resultMaps(ms.getResultMaps());
    builder.fetchSize(ms.getFetchSize());
    builder.timeout(ms.getTimeout());
    builder.statementType(ms.getStatementType());
    builder.resultSetType(ms.getResultSetType());
    builder.cache(ms.getCache());
    builder.flushCacheRequired(ms.isFlushCacheRequired());
    builder.useCache(ms.isUseCache());
    builder.keyGenerator(ms.getKeyGenerator());
    builder.keyProperty(delimitedArraytoString(ms.getKeyProperties()));
    builder.keyColumn(delimitedArraytoString(ms.getKeyColumns()));
    builder.databaseId(ms.getDatabaseId());

    return builder.build();
  }

  private MappedStatement getMappedStatement(Invocation invocation) {
    return (MappedStatement)invocation.getArgs()[INDEX_MAPPED_STATEMENT];
  }

  private void setMappedStatement(Invocation invocation, MappedStatement mappedStatement) {
    invocation.getArgs()[INDEX_MAPPED_STATEMENT] = mappedStatement;
  }

  private Object getParameter(Invocation invocation) {
    return invocation.getArgs()[INDEX_PARAMETER];
  }

  private RowBounds getRowBounds(Invocation invocation) {
    return (RowBounds)invocation.getArgs()[INDEX_ROW_BOUNDS];
  }

  private void setRowBounds(Invocation invocation, RowBounds rowBounds) {
    invocation.getArgs()[INDEX_ROW_BOUNDS] = rowBounds;
  }

  public Object plugin(Object target)
  {
    return Plugin.wrap(target, this);
  }

  public void setProperties(Properties properties)
  {
  }

  private static String delimitedArraytoString(String[] in)
  {
    if ((in == null) || (in.length == 0)) {
      return null;
    }
    StringBuilder answer = new StringBuilder();
    for (String str : in) {
      answer.append(str).append(",");
    }
    return answer.toString();
  }

  public void setDialect(Dialect dialect)
  {
    this.dialect = dialect;
  }

  public static class BoundSqlSqlSource
    implements SqlSource
  {
    private final BoundSql boundSql;

    public BoundSqlSqlSource(MappedStatement ms, BoundSql boundSql, String sql)
    {
      this.boundSql = buildBoundSql(ms, boundSql, sql);
    }

    public BoundSql getBoundSql(Object parameterObject) {
      return this.boundSql;
    }

    private BoundSql buildBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
      BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());

      for (ParameterMapping mapping : boundSql.getParameterMappings()) {
        String prop = mapping.getProperty();
        if (boundSql.hasAdditionalParameter(prop)) {
          newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
        }
      }
      return newBoundSql;
    }
  }
}