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
		this.myName = myName;
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
		StringTokenizer token = new StringTokenizer(securityAsAString);
		myName = token.nextToken();
		myTicker = token.nextToken();
		myCurrentPrice = Double.parseDouble(token.nextToken());
		myHigh = Double.parseDouble(token.nextToken());
		myLow = Double.parseDouble(token.nextToken());
		myOpen = Double.parseDouble(token.nextToken());
		myPrevDayClose = Double.parseDouble(token.nextToken());
		myCeiling = Double.parseDouble(token.nextToken());
		myFloor = Double.parseDouble(token.nextToken());
		socialIndex = Integer.parseInt(token.nextToken());
	}
	
	@Override
	public String toString() {
		return myName + " " + myTicker + " " + myCurrentPrice + " " + myHigh + " " + myLow + " " +
				myOpen + " " + myPrevDayClose + " " + myCeiling + " " + myFloor + " " +  socialIndex;
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
