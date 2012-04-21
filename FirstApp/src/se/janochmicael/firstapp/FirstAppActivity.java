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
	TextView redDayTV;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		submitButton = (Button) findViewById(R.id.submitButton);
		datePicker = (DatePicker) findViewById(R.id.datePicker1);

		submitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Date d = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
				updateText(getCalendarString(d));
			}

		});
		// submitButton.on

		calendarTV = (TextView) findViewById(R.id.calendar);
		redDayTV = (TextView) findViewById(R.id.redDay);

		String response = getCalendarString(Calendar.getInstance().getTime());
		updateText(response);

	}

	private void updateText(String response) {
		try {
			JSONObject jsonObject = new JSONObject(response);
			StringBuffer calendarText = parseAndFormatCalendar(jsonObject);
			calendarTV.setText(calendarText);
			StringBuffer redDayText = parseAndFormatRedDay(jsonObject);
			redDayTV.setText(redDayText);
		} catch (JSONException e) {
			// nada
		}
	}

	private StringBuffer parseAndFormatRedDay(JSONObject jsonObject) {
		StringBuffer calendarText = new StringBuffer();
		try {
			String helgdag = jsonObject.getString("helgdag");
			if (helgdag != null && !"".equals(helgdag)) {
				calendarText.append(helgdag);
			}
		} catch (Exception e) {
			// nada
		}

		return calendarText;
	}

	private StringBuffer parseAndFormatCalendar(JSONObject jsonObject) throws JSONException {
		StringBuffer calendarText = new StringBuffer();
		String datum = jsonObject.getString("datum");
		String dag = jsonObject.getString("dag");
		String vecka = jsonObject.getString("vecka");
		calendarText.append(datum);
		calendarText.append(" ");
		calendarText.append(dag);
		calendarText.append(" ");
		calendarText.append(" V:");
		calendarText.append(vecka);
		return calendarText;
	}

	private String getCalendarString(Date datum) {
		URL oracle;
		BufferedReader in;
		String inputLine = "";
		String response = "";
		try {
			String datumtext = new SimpleDateFormat("yyyyMMdd").format(datum);
			String urlString = "http://api.dryg.net/dagar/v1/?datum=" + datumtext;
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