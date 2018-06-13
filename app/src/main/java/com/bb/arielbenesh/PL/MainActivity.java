package com.bb.arielbenesh.PL;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bb.arielbenesh.DL.Item;
import com.bb.arielbenesh.DL.ItemAdapter;
import com.bb.arielbenesh.DAL.JsonHandler;
import com.bb.arielbenesh.R;
import com.bb.arielbenesh.DL.SeparatorDecoration;

import org.json.JSONException;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemsList;
    private Toolbar toolbar;

    // json source url
    private String url = "http://nikita.hackeruweb.co.il/hackDroid/items.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
        // Create connection object
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        // Execute connection
        okHttpHandler.execute(url);
        // Find the recyclerView inside the activity layout
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    // Invoke notifyDataSetChanged to refresh list after changes in settings layout
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        try {
            itemAdapter.notifyDataSetChanged();
        }catch (NullPointerException e){
            e.printStackTrace();
        }


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI
                Intent intent = new Intent(this,PrefActivitiy.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    // Inner class of HttpHandler
    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        // update itemlist with JsonArray the JsonHandler returns
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                itemsList = JsonHandler.getCustomListFromJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // define and set item adapter and layoutmanager etc. for the recyclerView
            // invoke notifyDataSetChanged
            itemAdapter = new ItemAdapter(itemsList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(itemAdapter);
            recyclerView.addItemDecoration(new SeparatorDecoration(getApplicationContext(), Color.BLACK, 1));
            itemAdapter.notifyDataSetChanged();
        }
    }

}
