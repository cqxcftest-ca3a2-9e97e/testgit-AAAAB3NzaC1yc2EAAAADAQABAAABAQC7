package com.xcf.scm.dal.model;

import java.math.BigDecimal;
import java.util.Date;

public class AcctAccountDO {
    private Integer id;

    private String acctId;

    private String acctName;

    private String acctType;

    private String acctStatus;

    private String ccy;

    private String payStop;

    private String receviepayStop;

    private Date createTime;

    private Date updateTime;

    private BigDecimal maxTransAmt;

    private Date lastTransTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId == null ? null : acctId.trim();
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName == null ? null : acctName.trim();
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType == null ? null : acctType.trim();
    }

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus == null ? null : acctStatus.trim();
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }

    public String getPayStop() {
        return payStop;
    }

    public void setPayStop(String payStop) {
        this.payStop = payStop == null ? null : payStop.trim();
    }

    public String getReceviepayStop() {
        return receviepayStop;
    }

    public void setReceviepayStop(String receviepayStop) {
        this.receviepayStop = receviepayStop == null ? null : receviepayStop.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getMaxTransAmt() {
        return maxTransAmt;
    }

    public void setMaxTransAmt(BigDecimal maxTransAmt) {
        this.maxTransAmt = maxTransAmt;
    }

    public Date getLastTransTime() {
        return lastTransTime;
    }

    public void setLastTransTime(Date lastTransTime) {
        this.lastTransTime = lastTransTime;
    }
}