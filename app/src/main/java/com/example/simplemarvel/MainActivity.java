package com.example.simplemarvel;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;


import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.simplemarvel.models.CharactersInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = "MainActivity";
    private static final String URL = "https://gateway.marvel.com:443/v1/public/characters?apikey=%s&hash=%s&ts=1&limit=100&offset=%d";
    public static final String API = "66194e6f9cd09542f4446aea94f2c95f";
    public static final String HASH = "fde52363428b7c44b0778de3b07b84a6";
    public int count = 1;
    List<CharactersInfo> characters;
    Toolbar toolbar;
    EndlessRecyclerViewScrollListener recyclerViewScrollListener;
    CharactersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        LinearLayoutManager linear = new LinearLayoutManager(this);
        recyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linear)
        {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view)
            {
                loadChars(100 * count++);
            }
        };

        RecyclerView rvCharInfo = findViewById(R.id.rvCharInfo);

        characters = new ArrayList<>();
        adapter = new CharactersAdapter(characters, this);
        rvCharInfo.setAdapter(adapter);
        rvCharInfo.setLayoutManager(linear);
        rvCharInfo.addOnScrollListener(recyclerViewScrollListener);
        loadChars(0);
    }
    public void loadChars(int offset)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(Locale.US,URL,API,HASH,offset), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json)
            {
                JSONObject object = json.jsonObject;
                try {
                    JSONObject data = object.getJSONObject("data");
                    JSONArray results = data.getJSONArray("results");
                    characters.addAll(CharactersInfo.charactersInfoList(results));
                    Log.d(TAG, CharactersInfo.listToString(characters));
                    Log.d(TAG, characters.size() + " ");
                    Log.d(TAG, data.toString());
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG, "Failed to get info!!!", throwable);
                Log.d(TAG, headers.toString(), throwable);
                throwable.printStackTrace();
            }
        });
    }
}