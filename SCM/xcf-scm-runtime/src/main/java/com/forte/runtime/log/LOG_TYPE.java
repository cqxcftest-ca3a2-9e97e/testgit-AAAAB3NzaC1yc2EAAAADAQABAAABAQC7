package com.forte.runtime.log;
public enum LOG_TYPE
{
  FORTE_COMMON("forte.common"),
  FORTE_ERROR("forte.error");

  public String val;

  private LOG_TYPE(String val) {
    this.val = val;
  }
}