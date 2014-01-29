package com.ceazy.lib.STTrial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.FragmentActivity;

public class SuperLinkifyActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		String data = getIntent().getData().toString();
		String phrase = data.substring(data.indexOf("#"));
		parsePhraseAndLaunch(phrase);
	}

	protected void parsePhraseAndLaunch(String phrase) {
		SuperTextAnalyzer analyzer = new SuperTextAnalyzer(this);
		SuperTag launchTag = analyzer.getFirstTag(phrase);
		launchTag.getIntent(this, null).launch(this, new Messenger(new LaunchHandler()));
	}
	
	class LaunchHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			if(msg.getData().getParcelable("error") != null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(SuperLinkifyActivity.this)
				.setTitle("Oops!")
				.setMessage("Something went wrong!")
				.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						finish();
					}
					
				})
				.setNeutralButton("Ok", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
					
				});
				builder.create().show();
			} else {
				finish();
			}
			super.handleMessage(msg);
		}
		
	}
}
