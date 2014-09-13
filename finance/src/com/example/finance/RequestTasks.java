package com.example.finance;

import java.net.URL;
import java.util.ArrayList;

import com.bloomberglp.blpapi.Event;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;


public class RequestTasks extends AsyncTask <ReturnWrapper, Integer, ArrayList<Security> >{

	private ArrayList<Security> mySecurity = new ArrayList<Security>();
	private ArrayAdapter<String> myList;
	@Override
	protected ArrayList<Security> doInBackground(ReturnWrapper... arg0) {
		SessionOptions sessionOptions = new SessionOptions();
		sessionOptions = new SessionOptions();
		sessionOptions.setServerHost("10.8.8.1");
		sessionOptions.setServerPort(8194);
		Session session = new Session(sessionOptions);
		myList = arg0[0].al;
		Log.i("session", "hello");
			 
		 //Log.i("shasd","Something is happening");
		 try{
			 if(!session.start()) {
				 Log.i("status",Event.EventType.SESSION_STATUS.toString());
				//Log.i("start", "Could not start session.");
				//System.exit(1);
			 }
			 Log.i("success","success");

			session.openService("//blp/refdata");
			Log.i("service", Event.EventType.SERVICE_STATUS.toString() );
			 
				//System.exit(1);
			 
		 }catch(Exception e){
			 Log.e("exception", e.toString());
		 }	
		 //Service refDataSvc = session.getService("//blp/refdata");
		 //Request request = refDataSvc.createRequest("ReferenceDataRequest");
		 //request.getElement("securities").appendValue("IBM US Equity");

		 
		 RequestMultiple rm = new RequestMultiple(session);
		 Log.i(" ", " after request multiple");
		 int count = arg0[0].s.length;
		 Log.i(" ",  "vefore for loop");
		 for(int i = 0; i < count; ++i){
			 rm.addSecurity(arg0[0].s[i]);
		 }
		 Log.i(" ", "after for loop");
		 try {
			rm.makeRequest();
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("makeRequest",e.toString());
		 }
		 Log.i("makeRequestSuccess", "yayyyy");
		 mySecurity= rm.getMySL();
		 Log.i("hi jurstin", "penis");
		 
		 //Log.i("INFO", sl.get(0).getMyName());
		 //Log.i("list price", "" +sl.get(0).getMyCurrentPrice());
		 /*for(int i = 0; i < sl.size(); ++i){
			 sl.get(i).log();
		 }*/
		//Service refDataSvc = session.getService("//blp/refdata");
		//Request request = refDataSvc.createRequest("ReferenceDataRequest");
		//request.getElement("securities").appendValue("AAPL US Equity");
		 return null;
		 
		 
	}
	
	protected void onPostExecute(ArrayList<Security> result) {
		Log.i("give it to me harder", "i wanna love ya");
		for(int i = 0; i < mySecurity.size(); ++i){
			 myList.add(mySecurity.get(i).getMyName() + ": " + mySecurity.get(i).getMyCurrentPrice());
		 }
		Log.i("give it to me harder", "i wanna fuck ya");

    }

	

}
