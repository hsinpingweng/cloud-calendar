package com.hsinpingweng.calendar.model.data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

// import lombok.Data;

@Entity
@Table(name="events")
// @Data
public class Event {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Boolean getIsBooking() {
        return this.isBooking;
    }

    public void setIsBooking(Boolean isBooking) {
        this.isBooking = isBooking;
    }

    @Column(name="host_user_id")
    private String hostUserId;

    @Size(min=2, message = "Title must be at least 2 characters long")
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    // @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime start;

    // @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end;

    @Column(name="is_booking")
    private Boolean isBooking;
}