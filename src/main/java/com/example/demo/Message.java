package com.example.demo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String content;

    @DateTimeFormat(pattern = "yyyy-M-d")
    private Date posteddate;

    @NotNull
    private String sentby;

    private String pic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPosteddate() {
        return posteddate;
    }

    public void setPosteddate(Date posteddate) {
        this.posteddate = posteddate;
    }

    public String getSentby() {
        return sentby;
    }

    public void setSentby(String sentby) {
        this.sentby = sentby;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Date getCurrentDate(){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        Date today = Calendar.getInstance().getTime();

        String reportDate = df.format(today);

        return today;
    }

    public String dateToString(){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        String reportDate = df.format(posteddate);

        return reportDate;
    }
}
