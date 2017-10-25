package com.forte.runtime.pagination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaginationBean<T>
  implements Serializable
{
  private static final long serialVersionUID = -4481741916857715152L;
  private boolean hasPrePage;
  private boolean hasNextPage;
  private String id;
  private int totalRecords;
  private List<T> pageList;
  private int pageSize;
  private int totalPage;
  private int currentPage;
  private int beginIndex;

  @Override
  public String toString() {
    return "PaginationBean{" +
            "hasPrePage=" + hasPrePage +
            ", hasNextPage=" + hasNextPage +
            ", id='" + id + '\'' +
            ", totalRecords=" + totalRecords +
            ", pageList=" + pageList +
            ", pageSize=" + pageSize +
            ", totalPage=" + totalPage +
            ", currentPage=" + currentPage +
            ", beginIndex=" + beginIndex +
            '}';
  }

  public int getPageSize()
  {
    return this.pageSize;
  }

  public void setPageSize(int i) {
    this.pageSize = i;
  }

  public PaginationBean(Page page, int i) {
    this.pageSize = 0;
    this.totalPage = 0;
    this.currentPage = 0;
    init(page, i);
  }

  public PaginationBean() {
    this.pageSize = 0;
    this.totalPage = 0;
    this.currentPage = 0;
  }

  public void init() {
    this.pageList = new ArrayList();
    createPage(this.currentPage, this.totalRecords, this.pageSize);
  }

  public static int getIntParameter(Object object, int i) {
    if (object == null)
      return i;
    try
    {
      String s1 = ((String[])(String[])object)[0];
      return Integer.parseInt(s1);
    } catch (Exception e) {
      try {
        return Integer.parseInt((String)object); } catch (Exception e1) {  }
    }
    return ((Integer)object).intValue();
  }

  private void init(Page page, int i)
  {
    this.currentPage = page.getCurrPage();
    this.pageSize = page.getPageSize();

    this.totalRecords = i;
    this.pageList = new ArrayList();
    createPage(this.currentPage, this.totalRecords, this.pageSize);
  }

  public void createPage(int currentPage, int totalRecords, int pageSize)
  {
    this.beginIndex = getBeginIndex(pageSize, currentPage);

    this.totalPage = getTotalPage(pageSize, totalRecords);

    this.hasNextPage = hasNextPage(currentPage, this.totalPage);

    this.hasPrePage = hasPrePage(currentPage);

    this.currentPage = getCurrentPage(currentPage, this.totalPage);
  }

  private static int getCurrentPage(int currentPage, int totalPage)
  {
    return currentPage == 0 ? 1 : currentPage;
  }

  private static int getBeginIndex(int everyPage, int currentPage) {
    return (currentPage - 1) * everyPage;
  }

  private static int getTotalPage(int everyPage, int totalRecords) {
    int totalPage = 0;

    if (totalRecords % everyPage == 0)
      totalPage = totalRecords / everyPage;
    else {
      totalPage = totalRecords / everyPage + 1;
    }
    return totalPage;
  }

  private static boolean hasPrePage(int currentPage) {
    return currentPage != 1;
  }

  private static boolean hasNextPage(int currentPage, int totalPage) {
    return (currentPage != totalPage) && (totalPage != 0);
  }

  public int getTotalRecords() {
    return this.totalRecords;
  }

  public void setTotalRecords(int i) {
    this.totalRecords = i;
  }

  public List<T> getPageList() {
    return this.pageList;
  }

  public void setPageList(List<T> list) {
    this.pageList = list;
  }

  public int getCurrentPage() {
    return this.currentPage;
  }

  public void setCurrentPage(int i) {
    this.currentPage = i;
  }

  public int getTotalPage() {
    return this.totalPage;
  }

  public void setTotalPage(int i) {
    this.totalPage = i;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String s) {
    this.id = s;
  }

  public int getBeginIndex()
  {
    return this.beginIndex;
  }

  public void setBeginIndex(int beginIndex)
  {
    this.beginIndex = beginIndex;
  }

  public boolean isHasNextPage()
  {
    return this.hasNextPage;
  }

  public void setHasNextPage(boolean hasNextPage)
  {
    this.hasNextPage = hasNextPage;
  }

  public boolean isHasPrePage()
  {
    return this.hasPrePage;
  }

  public void setHasPrePage(boolean hasPrePage)
  {
    this.hasPrePage = hasPrePage;
  }
}