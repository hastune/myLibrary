package com.sm.beans;
 
public class discount {
    private String clas; 
    private String discount; 
    private int p_id;
    private String describe;
    private String suitable;
    
	public String getClas() {
		return clas;
	}
	public void setClas(String clas) {
		this.clas = clas;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getSuitable() {
		return suitable;
	}
	public void setSuitable(String suitable) {
		this.suitable = suitable;
	}
	@Override
	public String toString() {
		return "discount [clas=" + clas + ", Discount=" + discount + ", p_id=" + p_id + ", describe=" + describe
				+ ", suitable=" + suitable + "]";
	}
    
    
}