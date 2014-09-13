package com.example.finance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

/**
 * A dummy fragment representing a section of the app, but that simply displays dummy text.
 */
public class CurrentStocksFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
    private View rootView;
    public static String file = "storage";
    public ArrayAdapter<String> listAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
        Bundle args = getArguments();
        ListView stocksListView = (ListView) rootView.findViewById(R.id.stocksListView);
        reloadSavedState(stocksListView);
    	stocksListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NewsfeedActivity.class);
				intent.putExtra("company", listAdapter.getItem(position));
				startActivity(intent);
			}
		});
		stocksListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = getActivity().getSharedPreferences("storage", 0).edit();
		    	String removingCompany = listAdapter.getItem(position);
		    	editor.remove("currentStock" + removingCompany).commit();
				listAdapter.remove(removingCompany);
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
    	ListView stocksListView = (ListView) rootView.findViewById(R.id.stocksListView);
    	SharedPreferences settings = reloadSavedState(stocksListView);
    	
    	String newCompany = stockEditText.getText().toString();
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putString("currentStock" + newCompany, newCompany);
    	listAdapter.add(stockEditText.getText().toString());
		Toast.makeText(getActivity(), "Added", Toast.LENGTH_LONG).show();
		stockEditText.setText("");
		editor.commit();
    }

	private SharedPreferences reloadSavedState(ListView stocksListView) {
		listAdapter= new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, 0);
    	stocksListView.setAdapter(listAdapter);

    	SharedPreferences settings = getActivity().getSharedPreferences("storage", 0);
    	for(String key : settings.getAll().keySet()) {
    		String company = settings.getString(key, null);
    		if(company != null && key.startsWith("current")) {
        		listAdapter.add(company);
    		}
    	}
		return settings;
	}
}
