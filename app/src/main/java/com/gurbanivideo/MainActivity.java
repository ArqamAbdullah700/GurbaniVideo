package com.gurbanivideo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String GET_VideoURL = "https://www.gurbanistatus.in/GurbaniAppApi/gurbaniVideoApp.php";
    private ProgressBar progressAllStatus;
    ArrayList<VideoListHolder> videoArrayList;
    VideoListAdapter videoAdapter = null;
    ListView listView;

    GetVideosFromApi getVideoUrl;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressAllStatus = findViewById(R.id.progressAllStatus);
        listView = findViewById(R.id.listview);
        videoArrayList = new ArrayList<>();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VideoListHolder holder = (VideoListHolder) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(MainActivity.this, Video_View_Activity.class);
                intent.putExtra("URL", holder.getUrl());
                intent.putExtra("ImageUrl", holder.getImageUrl());
                intent.putExtra("Title", holder.getTitle());
                intent.putExtra("Section", "mParam1");
                startActivity(intent);
            }
        });
        getVideoUrl = new GetVideosFromApi();
        getVideoUrl.execute(GET_VideoURL);
        listView.setVisibility(View.VISIBLE);
    }

    @SuppressLint("StaticFieldLeak")
    class GetVideosFromApi extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressAllStatus.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            BufferedReader bufferedReader = null;
            StringBuilder sb = new StringBuilder();
            URL url = null;
            try {
                url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String json;
                while ((json = bufferedReader.readLine()) != null) {
                    sb.append(json + "\n");
                }
                JSONArray jsonArray = new JSONArray(sb.toString());
                for (int i = jsonArray.length() - 1; i >= 0; i--) {
                    String st = jsonArray.getJSONObject(i).getString("title");
                    String Url = getString(R.string.baseUrl) + jsonArray.getJSONObject(i).getString("url");
                    String imageUrl = getString(R.string.baseUrl) + jsonArray.getJSONObject(i).getString("thumbnail");
                    videoArrayList.add(new VideoListHolder(st, Url, imageUrl));
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String st) {
            super.onPostExecute(st);
            progressAllStatus.setVisibility(View.GONE);
            videoAdapter = new VideoListAdapter(MainActivity.this, R.layout.image_status_layout, videoArrayList);
            listView.setAdapter(videoAdapter);
        }
    }
}