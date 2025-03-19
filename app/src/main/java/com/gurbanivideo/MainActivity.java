package com.gurbanivideo;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public String GET_VideoURL = "https://www.gurbanistatus.in/GurbaniAppApi/gurbaniVideoApp.php";
    public String GET_IMAGE_HUKUM_MORNING = "https://www.gurbanistatus.in/GurbaniAppApi/getVideos.php?section=KathaVichar";
    public String GET_IMAGE_HUKUM_EVENING = "https://www.gurbanistatus.in/GurbaniAppApi/getVideos.php?section=GurbaniKirtan";

    private ProgressBar progressAllStatus;
    ArrayList<VideoListHolder> videoArrayList;
    VideoListAdapter videoAdapter = null;
    ListView listView;
    TextView titleTv;
    GetVideosFromApi getVideoUrl;

    private ConsentInformation consentInformation;

    Toolbar toolbar;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressAllStatus = findViewById(R.id.progressAllStatus);
        listView = findViewById(R.id.listview);
        toolbar = findViewById(R.id.toolbar);
        titleTv = findViewById(R.id.titleTv);
        setSupportActionBar(toolbar);

        videoArrayList = new ArrayList<>();

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            VideoListHolder holder = (VideoListHolder) adapterView.getAdapter().getItem(i);
            Intent intent = new Intent(MainActivity.this, Video_View_Activity.class);
            intent.putExtra("URL", holder.getUrl());
            intent.putExtra("ImageUrl", holder.getImageUrl());
            intent.putExtra("Title", holder.getTitle());
            intent.putExtra("Section", "mParam1");
            startActivity(intent);
        });
        getVideoUrl = new GetVideosFromApi();
        String video = getIntent().getStringExtra("video");
        assert video != null;
        if (video.equals("Kirtan")) {
            GET_VideoURL = GET_IMAGE_HUKUM_EVENING;
          //  titleTv.setText(getString(R.string.gurbani_kirtan));
        } else {
            GET_VideoURL = GET_IMAGE_HUKUM_MORNING;
           // titleTv.setText(getString(R.string.katha_vichar));

        }
        getVideoUrl.execute(GET_VideoURL);
        listView.setVisibility(View.VISIBLE);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.action_menu, menu);
//        return true;
//    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.d("MenuItemClick", "Item ID: " + item.getItemId());
//
//        if (item.getItemId() == R.id.privacy) {
//            // Handle the privacy menu item click
//            startActivity(new Intent(MainActivity.this, PrivacyPolicy.class));
//            return true;
//        } else {
//            return super.onOptionsItemSelected(item);
//        }
//    }



    @SuppressLint("StaticFieldLeak")
    class GetVideosFromApi extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressAllStatus.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            BufferedReader bufferedReader;
            StringBuilder sb = new StringBuilder();
            URL url;
            try {
                url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String json;
                while ((json = bufferedReader.readLine()) != null) {
                    sb.append(json).append("\n");
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