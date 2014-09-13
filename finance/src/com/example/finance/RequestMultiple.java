package com.example.finance;

import java.util.ArrayList;

import com.bloomberglp.blpapi.*;

public class RequestMultiple {
	private Session mySession;
	private ArrayList<String> myNames;
	private ArrayList<Security> mySL;
	
	public RequestMultiple(Session s){
		mySession = s;
		myNames = new ArrayList<String>();
		mySL = new ArrayList<Security>();
	}
	public void addSecurity(String s){
		myNames.add(s);
	}
	
	
	public void makeRequest() throws Exception{
		Service refDataSvc = mySession.getService("//blp/refdata");
		Request request = refDataSvc.createRequest("ReferenceDataRequest");
		for(int i = 0; i < myNames.size(); ++i){
			request.getElement("securities").appendValue(myNames.get(i));
		}
		//request.getElement("securities").appendValue("AAPL US Equity");
		//request.getElement("securities").appendValue("IBM US Equity");
		//request.getElement("securities").appendValue("BLAHBLAHBLAH US Equity");
		request.getElement("fields").appendValue("PX_LAST"); // Last Price
		request.getElement("fields").appendValue("DS002"); // Description
		request.getElement("fields").appendValue("VWAP_VOLUME");
		request.getElement("fields").appendValue("OPEN");
		request.getElement("fields").appendValue("HIGH");
		request.getElement("fields").appendValue("LOW");
		request.getElement("fields").appendValue("PREVIOUS CLOSE");
		mySession.sendRequest(request, new CorrelationID(1));
		boolean continueToLoop = true;
		while (continueToLoop) {
			Event event = mySession.nextEvent();
			switch (event.eventType().intValue()) {
				case Event.EventType.Constants.RESPONSE: // final response
					continueToLoop = false; // fall through
				case Event.EventType.Constants.PARTIAL_RESPONSE:
					handleResponseEvent(event);
					break;
				default:
					handleOtherEvent(event);
					break;
			}
		 }
	}
	
	private void handleResponseEvent(Event event) throws Exception {
		 MessageIterator iter = event.messageIterator();
		 while (iter.hasNext()) {
			 Message message = iter.next();
			 Element ReferenceDataResponse = message.asElement();
			 if (ReferenceDataResponse.hasElement("responseError")) {
				 System.exit(1);
			 }
			 Element securityDataArray = ReferenceDataResponse.getElement("securityData");
			 int numItems = securityDataArray.numValues();
			 for (int i = 0; i < numItems; ++i) {
				 Element securityData = securityDataArray.getValueAsElement(i);
				 String security = securityData.getElementAsString("security");
				 //int sequenceNumber = securityData.getElementAsInt32("sequenceNumber");
				 if (securityData.hasElement("securityError")) {
					 Element securityError = securityData.getElement("securityError");
					 System.out.println("* security =" + security);
					 securityError.print(System.out);
					 return;
				 } else {
					 Element fieldData = securityData.getElement("fieldData");
				 	//double px_last = fieldData.getElementAsFloat64("PX_LAST");
				 	//String ds002 = fieldData.getElementAsString("DS002");
				 	//double vwap_volume = fieldData.getElementAsFloat64("VWAP_VOLUME");
				 	//fieldData.print(System.out);
				 	// Individually output each value
				 	Security tempSec = new Security();
				 	tempSec.setMyName(fieldData.getElementAsString("DS002"));
				 	tempSec.setMyCurrentPrice(fieldData.getElementAsFloat64("PX_LAST"));
				 	tempSec.setMyHigh(fieldData.getElementAsFloat64("HIGH"));
				 	tempSec.setMyLow(fieldData.getElementAsFloat64("LOW"));
				 	tempSec.setMyOpen(fieldData.getElementAsFloat64("OPEN"));
				 	//tempSec.setMyPrevDayClose(fieldData.getElementAsFloat64("PREVIOUS CLOSE"));
				 	update(tempSec);
				 	//System.out.println("* vwap_volume =" + vwap_volume);
				 	//System.out.println("");
				 }
			 }
		 }
	}
	
	private void update(Security tempSec) {
		for(int i = 0; i < mySL.size(); ++i){
			if(mySL.get(i).getMyName().equals(tempSec.getMyName())){
				compare(tempSec, i);
				return;
			}
		}
		mySL.add(tempSec);
		
	}

	private void compare(Security tempSec, int i) {
		 if(mySL.get(i).getMyCeiling() != 0 && tempSec.getMyCurrentPrice() >= mySL.get(i).getMyCeiling()){
			 //TODO ALERT!!!!
			 System.out.println("celieng alert");
		 }else if(mySL.get(i).getMyFloor() != 0 && tempSec.getMyCurrentPrice() <= mySL.get(i).getMyFloor()){
			 //TODO ALERT!!!!
			 System.out.println("floor alert");
		 }
		 double c = mySL.get(i).getMyCeiling();
		 double f = mySL.get(i).getMyFloor();
		 mySL.set(i,tempSec);
		 mySL.get(i).setMyCeiling(c);
		 mySL.get(i).setMyFloor(f);
	}

	private static void handleOtherEvent(Event event) throws Exception{
		 System.out.println("EventType=" + event.eventType());
		 MessageIterator iter = event.messageIterator();
		 while (iter.hasNext()) {
			 Message message = iter.next();
			 System.out.println("correlationID=" + message.correlationID());
			 System.out.println("messageType=" + message.messageType());
			 message.print(System.out);
			 if (Event.EventType.Constants.SESSION_STATUS == event.eventType().intValue() && "SessionTerminated" == message.messageType().toString()){
				 System.out.println("Terminating: " + message.messageType());
				 System.exit(1);
			 }
		 }
	}
	
	public ArrayList<Security> getMySL(){
		return mySL;
	}
	public void changeCeiling(String s, double d){
		for(int i = 0; i < mySL.size(); ++i){
			if(mySL.get(i).getMyName().equals(s)){
				mySL.get(i).setMyCeiling(d);
				return;
			}
		}
	}
	public void changeFloor(String s, double d){
		for(int i = 0; i < mySL.size(); ++i){
			if(mySL.get(i).getMyName().equals(s)){
				mySL.get(i).setMyFloor(d);
				return;
			}
		}
	}
}
