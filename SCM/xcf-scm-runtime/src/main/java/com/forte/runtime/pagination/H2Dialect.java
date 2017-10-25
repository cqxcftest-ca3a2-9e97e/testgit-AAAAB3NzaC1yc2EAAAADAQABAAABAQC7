package com.forte.runtime.pagination;

public class H2Dialect extends BaseDialect
{
  public String getLimitString(String sql, int offset, int maxRow)
  {
    sql = trim(sql);
    StringBuilder pagingSelect = new StringBuilder(sql.length() + 40).append(sql);
    if (offset > 0) {
      return pagingSelect.append(" limit ").append(maxRow).append(" offset ").append(offset).toString();
    }
    return pagingSelect.append(" limit ").append(maxRow).toString();
  }
}