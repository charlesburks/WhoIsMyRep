package com.example.charlesburks.whoismyrep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends ActionBarActivity {

    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.my_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.my_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Button go = (Button) findViewById(R.id.goButton);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer answer = spinner.getSelectedItemPosition();
                switch (answer) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, SearchZip.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, SearchRepName.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, SearchRepState.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, SearchSenName.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, SearchSenState.class));
                        break;
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_rep_name, menu);
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
