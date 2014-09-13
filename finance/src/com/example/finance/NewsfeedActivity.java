package com.example.finance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NewsfeedActivity extends Activity {

	String key_items = "item";
	String key_title = "title";
	String key_description = "description";
	String key_link = "link";
	String key_date = "pubDate";
	ListView lstPost;
	List<HashMap<String, Object>> post_lists = new ArrayList<HashMap<String, Object>>();
	List<String> lists = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	RSSReader rssfeed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsfeed);
		lstPost = (ListView) findViewById(R.id.newsfeedListView);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_2, android.R.id.text1, lists) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView txt1 = (TextView) view
						.findViewById(android.R.id.text1);
				TextView txt2 = (TextView) view
						.findViewById(android.R.id.text2);
				HashMap<String, Object> data = post_lists.get(position);
				txt1.setText(data.get(key_title).toString());
				txt2.setText(data.get(key_description).toString());
				return view;
			}

		};
		String company = getIntent().getExtras().getString("company");
		MyAsyncTask task = new MyAsyncTask("http://d.yimg.com/autoc.finance.yahoo.com/autoc?query=" + company + "&callback=YAHOO.Finance.SymbolSuggest.ssCallback");
		task.execute();
		lstPost.setAdapter(adapter);
		lstPost.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(post_lists.get(position).get(key_link).toString()));
				startActivity(intent);
			}
		});
	}
	
	public void fillNewsFeed(String symbolForCompany) {
		rssfeed = new RSSReader();
		Document xmlFeed = rssfeed
				.getRSSFromServer("http://finance.yahoo.com/rss/headline?s=" + symbolForCompany);
		NodeList nodes = xmlFeed.getElementsByTagName("item");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element item = (Element) nodes.item(i);
			HashMap<String, Object> feed = new HashMap<String, Object>();
			feed.put(key_title, rssfeed.getValue(item, key_title));
			feed.put(key_description, rssfeed.getValue(item, key_description));
			feed.put(key_link, rssfeed.getValue(item, key_link));
			feed.put(key_date, rssfeed.getValue(item, key_date));
			post_lists.add(feed);
			lists.add(feed.get(key_title).toString());
		}
	}
	
	public class MyAsyncTask extends AsyncTask<Void, String, Void> {

	    private ProgressDialog progressDialog = new ProgressDialog(NewsfeedActivity.this);
	    InputStream inputStream = null;
	    String result = ""; 
	    String url;
	    String symbolForCompany = "";
	    
	    public MyAsyncTask(String url) {
	    	this.url = url;
	    }

	    protected void onPreExecute() {
	        progressDialog.setMessage("Downloading your data...");
	        progressDialog.show();
	        progressDialog.setOnCancelListener(new OnCancelListener() {
	            public void onCancel(DialogInterface arg0) {
	                MyAsyncTask.this.cancel(true);
	            }
	        });
	    }

	    @Override
	    protected Void doInBackground(Void... params) {

	        String url_select = url;

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

	        try {
	            // Set up HTTP post

	            // HttpClient is more then less deprecated. Need to change to URLConnection
	            HttpClient httpClient = new DefaultHttpClient();

	            HttpPost httpPost = new HttpPost(url_select);
	            httpPost.setEntity(new UrlEncodedFormEntity(param));
	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            HttpEntity httpEntity = httpResponse.getEntity();

	            // Read content & Log
	            inputStream = httpEntity.getContent();
	        } catch (UnsupportedEncodingException e1) {
	            Log.e("UnsupportedEncodingException", e1.toString());
	            e1.printStackTrace();
	        } catch (ClientProtocolException e2) {
	            Log.e("ClientProtocolException", e2.toString());
	            e2.printStackTrace();
	        } catch (IllegalStateException e3) {
	            Log.e("IllegalStateException", e3.toString());
	            e3.printStackTrace();
	        } catch (IOException e4) {
	            Log.e("IOException", e4.toString());
	            e4.printStackTrace();
	        }
	        // Convert response to string using String Builder
	        try {
	            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
	            StringBuilder sBuilder = new StringBuilder();

	            String line = null;
	            while ((line = bReader.readLine()) != null) {
	                sBuilder.append(line + "\n");
	            }

	            inputStream.close();
	            result = sBuilder.toString();

	        } catch (Exception e) {
	            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
	        }
			return null;
	    } // protected Void doInBackground(String... params)
	    
	    protected void onPostExecute(Void v) {
	        //parse JSON data
	    	progressDialog.dismiss();
//	        try {
	        	Log.i("here", result);
	        	int wordSymbolIndex = result.indexOf("symbol");
	        	int startingIndex = wordSymbolIndex + 9;
	        	int endingIndex = startingIndex;
	        	while(!result.substring(endingIndex, endingIndex + 1).equals("\"")) {
	        		endingIndex++;
	        	}
	        	symbolForCompany = result.substring(startingIndex, endingIndex);
	        	Log.i("THE SYMBOL IS ", symbolForCompany);
//	            JSONArray jArray = new JSONArray(result);    
//	            Log.i("now", "now");
//	            for(int i=0; i < jArray.length(); i++) {
//
//	                JSONObject jObject = jArray.getJSONObject(i);
//
//	                String name = jObject.getString("symbol");
//	                String tab1_text = jObject.getString("tab1_text");
//	                int active = jObject.getInt("active");
//
//	            } // End Loop
	            this.progressDialog.dismiss();
	            fillNewsFeed(symbolForCompany);
//	        } catch (JSONException e) {
//	            Log.e("JSONException", "Error: " + e.toString());
//	        } // catch (JSONException e)
	    } // protected void onPostExecute(Void v)
	    
	    public String getSymbol() {
	    	return symbolForCompany;
	    }
	} //class MyAsyncTask extends AsyncTask<String, String, Void>

}