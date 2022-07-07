package com.smb.entity;

public class DoubleIdObjectEntity {
	private String otherAcc;
	private String thisAcc;
	
	public DoubleIdObjectEntity(String otherAcc,String thisAcc) {
		super();
		this.otherAcc=otherAcc;
		this.thisAcc=thisAcc;
	}
	public DoubleIdObjectEntity() {
		super();
	}
	public String getOtherAcc() {
		return otherAcc;
	}
	public void setOtherAcc(String otherAcc) {
		this.otherAcc=otherAcc;
	}
	public String getThisAcc() {
		return thisAcc;
	}
	public void setThisAcc(String thisAcc) {
		this.thisAcc = thisAcc;
	}
	
}
