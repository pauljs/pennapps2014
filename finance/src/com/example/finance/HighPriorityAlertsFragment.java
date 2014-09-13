package com.example.finance;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A dummy fragment representing a section of the app, but that simply displays dummy text.
 */
public class HighPriorityAlertsFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
    private View rootView;
    private ArrayAdapter<String> listAdapter;
    private ArrayAdapter<String> spinnerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.high_priority_alerts, container, false);
        Bundle args = getArguments();
        //Spinner
    	Spinner stocksSpinner = (Spinner) rootView.findViewById(R.id.highPriorityAlertsSpinner);
    	reloadSavedState();
    	spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	stocksSpinner.setAdapter(spinnerAdapter);
    	stocksSpinner.setPrompt("Select Stock to Add");
    	stocksSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				Toast.makeText(getActivity(), spinnerAdapter.getItem(position), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		//ListView
    	listAdapter= new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, 0);
    	ListView stocksListView = (ListView) rootView.findViewById(R.id.highPriorityAlertsListView);
    	stocksListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), listAdapter.getItem(position), Toast.LENGTH_LONG).show();
			}
		});
		stocksListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = getActivity().getSharedPreferences("storage", 0).edit();
		    	String removingCompany = listAdapter.getItem(position);
		    	Log.i("REMOVING", "ceiling" + removingCompany);
		    	editor.remove("ceiling" + removingCompany).commit();
				listAdapter.remove(removingCompany);
				return false;
			}
		});
        return rootView;
    }
    
    public SharedPreferences reloadSavedState() {
    	Spinner stocksSpinner = (Spinner) rootView.findViewById(R.id.highPriorityAlertsSpinner);
    	stocksSpinner = (Spinner) rootView.findViewById(R.id.highPriorityAlertsSpinner);
		spinnerAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, 0);
    	stocksSpinner.setAdapter(spinnerAdapter);

    	ListView stocksListView = (ListView) rootView.findViewById(R.id.highPriorityAlertsListView);
    	listAdapter= new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, 0);
    	stocksListView.setAdapter(listAdapter);
    	Log.i("RELOADING HIGH", "RELOADING HIGH");
    	SharedPreferences settings = getActivity().getSharedPreferences("storage", 0);
    	for(String key : settings.getAll().keySet()) {
    		String company = settings.getString(key, null);
    		if(company != null) {
    			if(key.startsWith("currentStock")) {
    				spinnerAdapter.add(company);
    			} else if(key.startsWith("ceiling")) {
    				listAdapter.add(company);
    			}
    		}
    	}
		return settings;
	}
    
    
    
    public void addHighPriorityAlertBtnClicked(View v) {
	    	Spinner stocksSpinner = (Spinner) rootView.findViewById(R.id.highPriorityAlertsSpinner);
//	    	stocksSpinner.setAdapter(spinnerAdapter);
	    	stocksSpinner.setPrompt("Select Stock to Add");
	    	if(stocksSpinner.getSelectedItem() != null) {
		    	EditText stockAlertValue = (EditText) rootView.findViewById(R.id.highPriorityAlertStockValueEditText);
		    	ListView highPriorityAlertsListView = (ListView) rootView.findViewById(R.id.highPriorityAlertsListView);
		    	highPriorityAlertsListView.setAdapter(listAdapter);
		    	String company = stocksSpinner.getSelectedItem().toString();
		    	listAdapter.add(company + ": " + stockAlertValue.getText().toString());
				Log.i("ADDING", "ceiling" + company + ": " + stockAlertValue.getText().toString());
		    	SharedPreferences.Editor editor = getActivity().getSharedPreferences("storage", 0).edit();
		    	editor.putString("ceiling" + company + ": " + stockAlertValue.getText().toString(), company + ": " + stockAlertValue.getText().toString());
		    	editor.commit();
		    	Toast.makeText(getActivity(), "Added", Toast.LENGTH_LONG).show();
	    	}
    }
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	Log.i("ONRESUME", "ONRESUME");
    	reloadSavedState();
    }
}
