package com.forte.runtime.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;

/**
 * 
 * @author liaosifa
 *
 */
public class AbstractDocument implements BriefResult {
    @Id
    protected String id;

    private Date createTime=new Date();

    /**
     * Returns the identifier of the document.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        AbstractDocument that = (AbstractDocument) obj;

        return this.id.equals(that.getId());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static PageRequest pageRequest(int page){
        return new PageRequest(page,50, Sort.Direction.DESC, "createTime");
    }
    
    public static PageRequest getDefaultPageRequest(int page,int size){
        return new PageRequest(page,size, new Sort(Sort.Direction.DESC,"createTime"));
    }
}
