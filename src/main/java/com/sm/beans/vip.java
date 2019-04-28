package com.sm.beans;

import java.util.Date;

public class vip {
    private int id; 
    private int cla; 
    private String name; 
    private Date initTime; 

    public void setId(int id){
        this.id=id;
    }

    public int getId(){
        return id;
    }

    public void setCla(int cla){
        this.cla=cla;
    }

    public int getCla(){
        return cla;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setInitTime(Date initTime){
        this.initTime=initTime;
    }

    public Date getInitTime(){
        return initTime;
    }
}