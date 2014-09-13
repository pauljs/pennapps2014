package com.example.finance;

import android.util.Log;

public class Security {
	private String myName;
	private double myCurrentPrice;
	private double myHigh;
	private double myLow;
	private double myOpen;
	private double myPrevDayClose;
	private double myCeiling;
	private double myFloor;
	
	
	public String getMyName() {
		return myName;
	}
	public void setMyName(String myName) {
		this.myName = myName;
	}
	public double getMyCurrentPrice() {
		return myCurrentPrice;
	}
	public void setMyCurrentPrice(double myCurrentPrice) {
		this.myCurrentPrice = myCurrentPrice;
	}
	public double getMyHigh() {
		return myHigh;
	}
	public void setMyHigh(double myHigh) {
		this.myHigh = myHigh;
	}
	public double getMyLow() {
		return myLow;
	}
	public void setMyLow(double myLow) {
		this.myLow = myLow;
	}
	public double getMyOpen() {
		return myOpen;
	}
	public void setMyOpen(double myOpen) {
		this.myOpen = myOpen;
	}
	public double getMyPrevDayClose() {
		return myPrevDayClose;
	}
	public void setMyPrevDayClose(double myPrevDayClose) {
		this.myPrevDayClose = myPrevDayClose;
	}
	public void print() {
		System.out.println("**Name: " + myName);
		System.out.println("**Current Price: " + myCurrentPrice);
		System.out.println("**High: " + myHigh);
		System.out.println("**Low: " + myLow);
		System.out.println("**Open: " + myOpen);
		System.out.println("**Previous Day's Close: " + myPrevDayClose);	
	}
	
	public void log(){
		Log.i("Name", myName);
		Log.i("Current Price", "" + myCurrentPrice);
		Log.i("High", "" + myHigh);
		Log.i("Low", "" + myLow);
		Log.i("Open", "" + myOpen);
		//Log.i("Previous Day's Close", "" + myPrevDayClose);
	}
	public double getMyCeiling() {
		return myCeiling;
	}
	public void setMyCeiling(double myCeiling) {
		this.myCeiling = myCeiling;
	}
	public double getMyFloor() {
		return myFloor;
	}
	public void setMyFloor(double myFloor) {
		this.myFloor = myFloor;
	}
	
	
}
