package se.janochmicael.firstapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FirstAppActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		TextView calendarTV = (TextView) findViewById(R.id.calendar);

		String calendarText = "";
		String response = getCalendarString();
		try {
			JSONObject jsonObject = new JSONObject(response);
			String date = jsonObject.getString("datum");
			calendarText = date;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		calendarTV.setText(calendarText);

	}

	private String getCalendarString() {
		URL oracle;
		BufferedReader in;
		String inputLine = "";
		String response = "";
		try {
			oracle = new URL("http://api.dryg.net/dagar/v1/");
			in = new BufferedReader(new InputStreamReader(oracle.openStream()));
			while ((inputLine = in.readLine()) != null) {
				response += inputLine;
			}

			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}