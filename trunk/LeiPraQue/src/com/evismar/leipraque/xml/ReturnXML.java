package com.evismar.leipraque.xml;

import java.io.InputStream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.util.Log;

import com.evismar.leipraque.activities.ProposicaoDetails;



public class ReturnXML extends Thread {
	private String URL = "";
	private String result = "";
	Activity parent = null;

	public ReturnXML(Activity caller) {
		parent = caller;
	}

	private HttpClient Client = new DefaultHttpClient();

	// After call for background.start this run method call
	public void run() {
		try {
			synchronized (parent) {

				String SetServerString = "";
				HttpGet httpget = new HttpGet(URL);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				SetServerString = Client.execute(httpget, responseHandler);
				setResult(SetServerString);
				parent.notify();
			}
			
		} catch (Throwable t) {
			// just end the background thread
			Log.i("Animation", "Thread  exception " + t);

		}

	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
