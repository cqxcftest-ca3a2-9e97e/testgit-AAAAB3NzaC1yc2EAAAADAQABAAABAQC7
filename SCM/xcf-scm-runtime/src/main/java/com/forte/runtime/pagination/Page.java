package com.forte.runtime.pagination;

import java.io.Serializable;

public class Page
  implements Serializable
{
  private static final long serialVersionUID = -2332161085849113773L;
  private int pageSize;
  private int totalPage;
  private int currPage = 1;
  private int totalRecords;

  public Page(int pageSize, int currentPage)
  {
    this.pageSize = pageSize;
    this.currPage = currentPage;
  }

  public Page()
  {
  }

  public int getPageSize()
  {
    return this.pageSize;
  }

  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }

  public int getTotalPage()
  {
    return this.totalPage;
  }

  public void setTotalPage(int totalPage)
  {
    this.totalPage = totalPage;
  }

  public int getCurrPage()
  {
    return this.currPage;
  }

  public void setCurrPage(int currPage)
  {
    this.currPage = currPage;
  }

  public int getTotalRecords()
  {
    return this.totalRecords;
  }

  public void setTotalRecords(int totalRecords)
  {
    this.totalRecords = totalRecords;
  }

  public String toString()
  {
    return "Page [pageSize=" + this.pageSize + ", totalPage=" + this.totalPage + ", currPage=" + this.currPage + ", totalRecords=" + this.totalRecords + "]";
  }
}