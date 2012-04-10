package se.janochmicael.firstapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FirstAppActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        TextView tv = (TextView) findViewById(R.id.hello);
        
        URL oracle;
        BufferedReader in;
        String inputLine = "";
        String response = "";
		try {
			oracle = new URL("http://api.dryg.net/dagar/v1/");
			in = new BufferedReader(new InputStreamReader(oracle.openStream()));
			while ((inputLine = in.readLine()) != null) {
			    System.out.println(inputLine);
			    response += inputLine;
			}

	        in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        tv.setText(response);
        
    }
}