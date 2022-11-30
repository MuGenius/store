package com.my.store.entity;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable {

    private String created_user; //创建人
    private Date created_time; //创建时间
    private String modified_user; //修改人
    private Date modified_time; //修改时间

    public String getCreated_user() {
        return created_user;
    }

    public void setCreated_user(String created_user) {
        this.created_user = created_user;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public String getModified_user() {
        return modified_user;
    }

    public void setModified_user(String modified_user) {
        this.modified_user = modified_user;
    }

    public Date getModified_time() {
        return modified_time;
    }

    public void setModified_time(Date modified_time) {
        this.modified_time = modified_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        if (created_user != null ? !created_user.equals(that.created_user) : that.created_user != null) return false;
        if (created_time != null ? !created_time.equals(that.created_time) : that.created_time != null) return false;
        if (modified_user != null ? !modified_user.equals(that.modified_user) : that.modified_user != null)
            return false;
        return modified_time != null ? modified_time.equals(that.modified_time) : that.modified_time == null;
    }

    @Override
    public int hashCode() {
        int result = created_user != null ? created_user.hashCode() : 0;
        result = 31 * result + (created_time != null ? created_time.hashCode() : 0);
        result = 31 * result + (modified_user != null ? modified_user.hashCode() : 0);
        result = 31 * result + (modified_time != null ? modified_time.hashCode() : 0);
        return result;
    }
}
