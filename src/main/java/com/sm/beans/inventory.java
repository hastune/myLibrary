package com.sm.beans;

import java.util.Date;

public class inventory {
    private int product_id; 
    private String amount; 
    private Date updateTime; 

    public void setProduct_id(int product_id){
        this.product_id=product_id;
    }

    public int getProduct_id(){
        return product_id;
    }

    public void setAmount(String amount){
        this.amount=amount;
    }

    public String getAmount(){
        return amount;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime=updateTime;
    }

    public Date getUpdateTime(){
        return updateTime;
    }
}