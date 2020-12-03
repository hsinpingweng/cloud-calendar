package com.hsinpingweng.calendar.model;

import com.hsinpingweng.calendar.model.data.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByUsername(String username);
    
}
