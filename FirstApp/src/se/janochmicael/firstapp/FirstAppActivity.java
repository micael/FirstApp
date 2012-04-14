package se.janochmicael.firstapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

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

		String response = getCalendarString();
		try {
			JSONObject jsonObject = new JSONObject(response);
			StringBuffer calendarText = parseAndFormat(jsonObject);
			calendarTV.setText(calendarText);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private StringBuffer parseAndFormat(JSONObject jsonObject) throws JSONException {
		StringBuffer calendarText = new StringBuffer();
		String datum = jsonObject.getString("datum");
		String unixdatum = jsonObject.getString("unixdatum");
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(Long.valueOf(unixdatum));
		format.format(date);
		String dag = jsonObject.getString("dag");
		// String veckodag = jsonObject.getString("veckodag");
		String vecka = jsonObject.getString("vecka");
		calendarText.append(datum);
		calendarText.append(" ");
		calendarText.append(format.format(date));
		calendarText.append(" ");
		calendarText.append(dag);
		calendarText.append(" ");
		// calendarText.append(veckodag);
		calendarText.append(" V:");
		calendarText.append(vecka);
		return calendarText;
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