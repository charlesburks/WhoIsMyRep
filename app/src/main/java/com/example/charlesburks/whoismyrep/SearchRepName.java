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


public class SearchRepName extends ActionBarActivity {

    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rep_name);
        Button zipButton = (Button) findViewById(R.id.button2);
        zipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEdit = (EditText) findViewById(R.id.nameEdit);
                String nameEnter = (nameEdit.getText().toString());
                //String nameEnter1 = nameEnter.substring(0,1).toUpperCase() + nameEnter.substring(1).toLowerCase();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Loading...", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 50);
                toast.show();
                name = "http://whoismyrepresentative.com/getall_reps_byname.php?name=" + nameEnter + "&output=json";
                switch (v.getId()) {
                    case R.id.button2:
                        new XMLParser().execute(name);
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
            TextView repText = (TextView) findViewById(R.id.repName);
            try {
                JSONObject jObject = new JSONObject(result);
                // Pulling items from the array
                JSONArray jArray = jObject.getJSONArray("results");
                JSONObject oneObject = jArray.getJSONObject(0);
                // Pulling items from the array
                String oneObjectsItem = oneObject.getString("name");
                //String oneObjectsItem2 = oneObject.getString("anotherSTRINGNAMEINtheARRAY");
                repText.setText(oneObjectsItem);
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
