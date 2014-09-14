package com.example.finance;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bloomberglp.blpapi.Event;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;

/**
 * A dummy fragment representing a section of the app, but that simply displays dummy text.
 */
public class CurrentStocksFragment extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";
	private View rootView;
	public static String file = "storage";
	private ArrayList<Security> securityList = new ArrayList<Security>();
	public ArrayAdapter<String> listAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
		Bundle args = getArguments();
		ListView stocksListView = (ListView) rootView.findViewById(R.id.stocksListView);
		listAdapter= new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, 0);
		reloadSavedState(stocksListView, null);
		stocksListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NewsfeedActivity.class);
				String companyNameStr = securityList.get(position).getMyName();
				SharedPreferences preferences = getActivity().getSharedPreferences("storage", 0);
				//This will get the most updated version of the company. If for some reason it fails, itll use the previous one.
				intent.putExtra("company", preferences.getString("currentStock"+companyNameStr, securityList.get(position).toString()));

				startActivity(intent);
			}
		});
		stocksListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = getActivity().getSharedPreferences("storage", 0).edit();
				editor.clear().commit();
				securityList.remove(position);
				for(Security security : securityList) {
					editor.putString("currentStock"+security.getMyName(), security.toString());
				}
				editor.commit();
				listAdapter.remove(listAdapter.getItem(position));
				return false;
			}
		});



		return rootView;
	}

	//    @Override
	//    public void onSaveInstanceState(Bundle outState) {
	//    	Log.i("saving state", "saving state");
	//        super.onSaveInstanceState(outState);
	//        String[] data = convertArrayAdapterToStringArray(listAdapter);
	//        outState.putCharSequenceArray("data", data);
	//    }

	//    @Override
	//    public void onActivityCreated(Bundle savedInstanceState) {
	//    	// TODO Auto-generated method stub
	//    	super.onActivityCreated(savedInstanceState);
	//    	if (savedInstanceState != null) {
	//            // Restore last state for checked position.
	//    		Log.i("using savedstate", "using savedstate");
	//            listAdapter = convertStringArrayToArrayAdapter(savedInstanceState.getCharSequenceArray("data"));
	//        }
	//    	
	//    }

	//    private String[] convertArrayAdapterToStringArray(ArrayAdapter<String> listAdapter) {
	//		// TODO Auto-generated method stub
	//		String[] data = new String[listAdapter.getCount()];
	//		for(int position = 0; position < listAdapter.getCount(); position++) {
	//			data[position] = listAdapter.getItem(position);
	//		}
	//		return data;
	//	}
	//    
	//    private ArrayAdapter<String> convertStringArrayToArrayAdapter(CharSequence[] charSequences) {
	//		// TODO Auto-generated method stub
	//    	ArrayAdapter<String> remadeAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, 0);
	//		for(int position = 0; position < listAdapter.getCount(); position++) {
	//			remadeAdapter.add(charSequences[position].toString());
	//		}
	//		return remadeAdapter;
	//	}

	public void addStockBtnClicked(View v) {
		EditText stockEditText = (EditText) rootView.findViewById(R.id.stockEditText);
		if(stockEditText.getText() != null) {
			ListView stocksListView = (ListView) rootView.findViewById(R.id.stocksListView);
			//SharedPreferences settings = getActivity().getSharedPreferences("storage", 0);
			listAdapter= new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, 0);
			stocksListView.setAdapter(listAdapter);
	
			String newCompanyStr = stockEditText.getText().toString() + " US Equity";
			stockEditText.setText("");
			Toast.makeText(getActivity(), "Added", Toast.LENGTH_LONG).show();
			//INSERT SEANS FUNCTION HERE
			//loadStocks(newCompanyStr);
			SharedPreferences settings = reloadSavedState(stocksListView, newCompanyStr);
	
			//    	SharedPreferences.Editor editor = settings.edit();
			//    	editor.putString(newCompany, newCompany);
			//listAdapter.add(stockEditText.getText().toString());
			//		editor.commit();
		} else {
			Toast.makeText(rootView.getContext(), "Please enter valid ticker symbol", Toast.LENGTH_LONG).show();
		}
	}

	private void loadStocks(String newCompanyStr, boolean newCompany) {
		// TODO Auto-generated method stub
		ReturnWrapper rw = new ReturnWrapper();
		rw.s = new String[securityList.size() + 1];
		for(int i = 0; i < securityList.size(); i++) {
			rw.s[i] = securityList.get(i).getMyTicker();
		}
		rw.s[rw.s.length - 1] = newCompanyStr;
		listAdapter.clear();
		rw.al = listAdapter;
		Log.i("BEFORE REQUESTTASKS", "GO");
		new RequestTasks(newCompany).execute(rw);
	}

	private void calculateSocialIndex(ArrayList<Security> securities) {
		securityList = securities;
		Security newCompany = securityList.get(securityList.size() - 1);
		SimpleStreamTask sstask = new SimpleStreamTask(newCompany.getMyTicker(), newCompany);
		sstask.execute();
	}

	private void saveNewCompany(Security newCompany, int socialIndex) {
		SharedPreferences.Editor editor = getActivity().getSharedPreferences("storage", 0).edit();
		newCompany.setSocialIndex(socialIndex);
		editor.putString("currentStocks" + newCompany.getMyName(), newCompany.toString()).commit();
	}

	private SharedPreferences reloadSavedState(ListView stocksListView, String newCompanyStr) {
		securityList.clear();
		//		listAdapter= new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, 0);
		stocksListView.setAdapter(listAdapter);

		SharedPreferences settings = getActivity().getSharedPreferences("storage", 0);
		for(String key : settings.getAll().keySet()) {
			String companyStr = settings.getString(key, null);
			if(companyStr != null && key.startsWith("current")) {
				Security company = new Security(companyStr);
				securityList.add(company);
				listAdapter.add(company.getMyName() + ": " + company.getMyCurrentPrice());
			}
		}
		if(newCompanyStr!=null){
			//securityList.add(newCompanyStr);
			loadStocks(newCompanyStr, true);
		}else{
			loadStocks(false);
		}
		return settings;
	}

	private void loadStocks(boolean newCompany) {
		ReturnWrapper rw = new ReturnWrapper();
		rw.s = new String[securityList.size()];
		for(int i = 0; i < securityList.size(); i++) {
			rw.s[i] = securityList.get(i).getMyTicker();
		}
		listAdapter.clear();
		rw.al = listAdapter;
		new RequestTasks(newCompany).execute(rw);		
	}

	public class RequestTasks extends AsyncTask <ReturnWrapper, Integer, ArrayList<Security> >{

		private ArrayList<Security> mySecurity = new ArrayList<Security>();
		private ArrayAdapter<String> myList;
		private boolean newCompany;

		public RequestTasks(boolean newCompany) {
			this.newCompany = newCompany;
		}

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
				String curStr = arg0[0].s[i];
				rm.addSecurity(curStr);
			}
			Log.i(" ", "after for loop");
			try {
				rm.makeRequest();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.i("makeRequest",e.toString());
			}
			Log.i("makeRequestSuccess", "yayyyy");
			mySecurity = rm.getMySL();


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
			for(int i = 0; i < mySecurity.size(); ++i){
				myList.add(mySecurity.get(i).getMyName() + ": " + mySecurity.get(i).getMyCurrentPrice());
			}
			listAdapter = myList;
			if(newCompany) {
				Log.i("CALCULATING SOCIAL INDEX", "GO");
				calculateSocialIndex(mySecurity);
			}
		}
	}

	public class SimpleStreamTask extends AsyncTask <Void, SimpleStreamTask, Void>{

		private String ticker;
		private String companyName;
		private int socialIndex;

		private Security company;

		public SimpleStreamTask(String ticker, Security company) {
			this.ticker = ticker;
			this.company = company;
			this.companyName = company.getMyName();
			
		}

		@Override
		protected Void doInBackground(Void... params) {
			SimpleStream ss = new SimpleStream(ticker, companyName);
			socialIndex = ss.getSocialIndex();
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub\
			Log.i("now saving new company and social index", "Go");
			Log.i("social index",""+socialIndex);
			saveNewCompany(company, socialIndex);

		}

	}


}
