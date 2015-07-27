package com.example.charlesburks.whoismyrep;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SearchSenName extends ActionBarActivity {

    String Zip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sen_name);
        Button zipButton = (Button) findViewById(R.id.button);
        zipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText zipEdit = (EditText) findViewById(R.id.editText);
                String zipEnter = (zipEdit.getText().toString());
                //XMLParser xml = new XMLParser();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Loading...", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 50);
                toast.show();
                Zip = "http://whoismyrepresentative.com/getall_mems.php?zip=" + zipEnter + "&output=json";
                switch (v.getId()) {
                    case R.id.button:
                        new XMLParser().execute(Zip);
                        break;
                }
            }


        });
    }


    public class XMLParser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            HttpClient httpclient = new DefaultHttpClient();

            // Prepare a request object
            HttpGet httpget = new HttpGet(urls[0]);

            // Execute the request
            HttpResponse response;
            String xml = null;
            try {
                response = httpclient.execute(httpget);
                // Examine the response status
                Log.i("Praeda", response.getStatusLine().toString());


                // Get hold of the response entity
                HttpEntity entity = response.getEntity();
                xml = EntityUtils.toString(entity);


            } catch (Exception e) {
                Log.i("ERROR", e.toString());

            }
            return xml;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView zipText = (TextView) findViewById(R.id.textView);
            try {
                JSONObject jObject = new JSONObject(result);
                // Pulling items from the array
                JSONArray jArray = jObject.getJSONArray("results");
                JSONObject oneObject = jArray.getJSONObject(1);
                // Pulling items from the array
                String oneObjectsItem = oneObject.getString("name");
                //String oneObjectsItem2 = oneObject.getString("anotherSTRINGNAMEINtheARRAY");
                zipText.setText(oneObjectsItem);
            } catch (JSONException e) {
                Log.i("Error",e.toString());
                // Oops
            }

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
