package com.imagegallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnBtnDetailClickListener {
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likes";

    private RecyclerView recyclerView;
    private ExampleAdapter exampleAdapter;
    private ArrayList<ExampleItem> exampleItems;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exampleItems = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constant.API_URL, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("hits");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject hit = jsonArray.getJSONObject(i);

                    String imageUrl = hit.getString("webformatURL");
                    String creatorName = hit.getString("user");
                    int likes = hit.getInt("likes");

                    exampleItems.add(new ExampleItem(imageUrl, creatorName, likes));
                }

                exampleAdapter = new ExampleAdapter(MainActivity.this, exampleItems);
                recyclerView.setAdapter(exampleAdapter);
                exampleAdapter.setOnItemClickListener(MainActivity.this);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(request);
    }

    @Override
    public void onBtnClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        ExampleItem clickedItem = exampleItems.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
        detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikes());

        startActivity(detailIntent);
    }
}