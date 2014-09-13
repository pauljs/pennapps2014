package com.example.finance;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.NotificationCompat;


public class FloorAlertFragment extends Fragment {
	
    public static final String ARG_SECTION_NUMBER = "section_number";
    private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.floor_alerts, container, false);
        Bundle args = getArguments();
        
        Button notifyBtn = (Button) rootView.findViewById(R.id.notifyBtn);
		Intent resultIntent = new Intent(getActivity(), MainActivity.class);
		PendingIntent resultPendingIntent = PendingIntent.getActivity(getActivity(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity())
	    .setSmallIcon(R.drawable.ic_launcher)
	    .setContentTitle("My notification")
	    .setContentText("Hello World!");
		mBuilder.setContentIntent(resultPendingIntent);
		
        notifyBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int mNotificationId = 001;
				NotificationManager mNotifyMgr = (NotificationManager) getActivity().getSystemService(android.content.Context.NOTIFICATION_SERVICE );
				mNotifyMgr.notify(mNotificationId, mBuilder.build());
			}
		});
        return rootView;
	}
	
	public void testNotificationBtnOnClick(View v) {
		
	}
}
