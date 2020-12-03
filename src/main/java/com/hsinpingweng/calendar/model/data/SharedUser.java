package com.hsinpingweng.calendar.model.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="shared_users")
@Data
public class SharedUser{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="host_user_id")
    private String hostUserId;

    @Column(name="share_to_user_id")
    private String shareToUserId;

}

