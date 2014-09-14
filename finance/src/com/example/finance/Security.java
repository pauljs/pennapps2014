package com.example.finance;

import java.util.StringTokenizer;

import android.util.Log;

public class Security {
	private String myName;
	private String myTicker;
	private double myCurrentPrice;
	private double myHigh;
	private double myLow;
	private double myOpen;
	private double myPrevDayClose;
	private double myCeiling;
	private double myFloor;
	private int socialIndex;
	
	
	
	public Security(String myName, String myTicker, double myCurrentPrice, double myHigh, 
					double myLow, double myOpen, double myPrevDayClose, double myCeiling, double myFloor,
					int socialIndex) {
		this.myName = new StringTokenizer(myName).nextToken();
		this.myTicker = myTicker;
		this.myCurrentPrice = myCurrentPrice;
		this.myHigh = myHigh;
		this.myLow = myLow;
		this.myOpen = myOpen;
		this.myPrevDayClose = myPrevDayClose;
		this.myCeiling = myCeiling;
		this.myFloor = myFloor;
		this.socialIndex = socialIndex;
		
	}
	
	public Security(String securityAsAString) {
		String[] array = securityAsAString.split("\t");
		myName = new StringTokenizer(array[0]).nextToken();
		myTicker = array[1];
		myCurrentPrice = Double.parseDouble(array[2]);
		myHigh = Double.parseDouble(array[3]);
		myLow = Double.parseDouble(array[4]);
		myOpen = Double.parseDouble(array[5]);
		myPrevDayClose = Double.parseDouble(array[6]);
		myCeiling = Double.parseDouble(array[7]);
		myFloor = Double.parseDouble(array[8]);
		socialIndex = Integer.parseInt(array[9]);
	}
	
	@Override
	public String toString() {
		return myName + "\t" + myTicker + "\t" + myCurrentPrice + "\t" + myHigh + "\t" + myLow + "\t" +
				myOpen + "\t" + myPrevDayClose + "\t" + myCeiling + "\t" + myFloor + "\t" +  socialIndex;
	}
	
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
	public int getSocialIndex() {
		return socialIndex;
	}
	public void setSocialIndex(int socialIndex) {
		this.socialIndex = socialIndex;
	}

	public String getMyTicker() {
		return myTicker;
	}

	public void setMyTicker(String myTicker) {
		this.myTicker = myTicker;
	}
	
	
}
