package com.hsinpingweng.calendar.model;

import java.util.List;

import com.hsinpingweng.calendar.model.data.SharedUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedUserRepository extends JpaRepository<SharedUser, Integer> {
    
    List<SharedUser> findByhostUserId(String hostUserId);

    List<SharedUser> findByshareToUserId(String userId);
    
}
