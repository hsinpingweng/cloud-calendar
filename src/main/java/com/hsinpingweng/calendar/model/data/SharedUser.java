package com.hsinpingweng.calendar.model.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// import lombok.Data;

// @Data
@Entity
@Table(name="shared_users")
public class SharedUser{
    
    @Id

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHostUserId() {
        return this.hostUserId;
    }

    public void setHostUserId(String hostUserId) {
        this.hostUserId = hostUserId;
    }

    public String getShareToUserId() {
        return this.shareToUserId;
    }

    public void setShareToUserId(String shareToUserId) {
        this.shareToUserId = shareToUserId;
    }
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="host_user_id")
    private String hostUserId;

    @Column(name="share_to_user_id")
    private String shareToUserId;

}

