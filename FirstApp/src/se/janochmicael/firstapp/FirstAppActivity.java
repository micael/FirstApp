package se.janochmicael.firstapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class FirstAppActivity extends Activity {
	Button submitButton;
	DatePicker datePicker;
	TextView calendarTV;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		submitButton = (Button) findViewById(R.id.submitButton);
		datePicker = (DatePicker) findViewById(R.id.datePicker1);

		submitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Date d = new Date(datePicker.getYear() - 1900, datePicker
						.getMonth(), datePicker.getDayOfMonth());
				updateText(getCalendarString(d));
			}

		});
		// submitButton.on

		calendarTV = (TextView) findViewById(R.id.calendar);

		String response = getCalendarString(Calendar.getInstance().getTime());
		updateText(response);

	}

	private void updateText(String response) {
		try {
			JSONObject jsonObject = new JSONObject(response);
			StringBuffer calendarText = parseAndFormat(jsonObject);
			calendarTV.setText(calendarText);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private StringBuffer parseAndFormat(JSONObject jsonObject)
			throws JSONException {
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
		try {
			String helgdag = jsonObject.getString("helgdag");
			if (helgdag != null && !"".equals(helgdag)) {
				calendarText.append(" ").append(helgdag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return calendarText;
	}

	private String getCalendarString(Date datum) {
		URL oracle;
		BufferedReader in;
		String inputLine = "";
		String response = "";
		try {
			String datumtext = new SimpleDateFormat("yyyyMMdd").format(datum);
			String urlString = "http://api.dryg.net/dagar/v1/?datum="
					+ datumtext;
			oracle = new URL(urlString);
			in = new BufferedReader(new InputStreamReader(oracle.openStream()));
			while ((inputLine = in.readLine()) != null) {
				response += inputLine;
				// System.out.println("input " + datum.toLocaleString() +
				// " output " + inputLine);
			}

			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}