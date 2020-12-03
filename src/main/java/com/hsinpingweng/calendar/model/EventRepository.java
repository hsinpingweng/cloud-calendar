package com.hsinpingweng.calendar.model;

import java.util.List;

import com.hsinpingweng.calendar.model.data.Event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
    
    List<Event> findByhostUserId(String hostUserId);
    
}
