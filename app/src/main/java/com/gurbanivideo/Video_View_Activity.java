package com.gurbanivideo;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.io.File;
import java.util.Objects;

public class Video_View_Activity extends AppCompatActivity {
    VideoView videoView;
    Intent intent;
    ProgressDialog pd;
    ImageView backImageButton;
    Button saveVideoBtn, shareVideoBtn;
    String videoPath = null;
    String imageUri = null;
    String[] storagePermissions;
    final int STORAGE_REQUEST_CODE = 2;
    final int STORAGE_REQUEST_CODE_ABOVE_THIRTEEN = 300;
    boolean shareFlag = false;
    private ProgressDialog progressDialog;
    String ImageUrl, videoUrl;

    TextView titleTextView;
    DownloadDialog exitDialog;

    private InterstitialAd mInterstitialAd;

    AdRequest adRequest;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        backImageButton = findViewById(R.id.backImageBtn);
        saveVideoBtn = findViewById(R.id.saveVideoBtn);
        shareVideoBtn = findViewById(R.id.shareVideoBtn);
        titleTextView = findViewById(R.id.txtTitleVideo);


        //deleteButton = findViewById(R.id.delete);
        titleTextView.setText(getIntent().getStringExtra("Title"));

        storagePermissions = new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO};
        /**/
        backImageButton.setOnClickListener(view -> {
            Intent intent = new Intent(Video_View_Activity.this, MainActivity.class);
            intent.putExtra("from", "Video");
            intent.putExtra("Section", getIntent().getStringExtra("Section"));
            startActivity(intent);
            finish();
        });

        progressDialog = new ProgressDialog(this);
        pd = new ProgressDialog(this);
        pd.setTitle("Video loading");
        pd.setMessage("wait while video is loading....");
        pd.show();
        videoView = findViewById(R.id.VideoView);
        intent = getIntent();
        videoUrl = intent.getStringExtra("URL");
        ImageUrl = intent.getStringExtra("ImageUrl");
        Uri uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);

        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(videoView);

        // sets the media player to the videoView
        mediaController.setMediaPlayer(videoView);

        // sets the media controller to the videoView
        videoView.setMediaController(mediaController);

        // starts the video
        videoView.start();

        videoView.setOnPreparedListener(mediaPlayer -> pd.dismiss());

        imageUri = videoUrl;

        shareVideoBtn.setOnClickListener(view -> {
            shareFlag = true;
            videoView.pause();
            InterstitialAd.load(Video_View_Activity.this, "ca-app-pub-2223313192114405/3771225674", adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            mInterstitialAd = interstitialAd;
                            Log.i(TAG, "onAdLoaded");
                            showFullScreenAds();
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            Log.d(TAG, loadAdError.toString());
                            mInterstitialAd = null;
                            startDownload();
                        }
                    });
            //startDownload();
        });

        saveVideoBtn.setOnClickListener(view -> {
            shareFlag = false;
            videoView.pause();

            // startDownload();
            InterstitialAd.load(Video_View_Activity.this, "ca-app-pub-2223313192114405/3771225674", adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            mInterstitialAd = interstitialAd;
                            Log.i(TAG, "onAdLoaded");
                            showFullScreenAds();
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            Log.d(TAG, loadAdError.toString());
                            mInterstitialAd = null;
                            startDownload();
                        }
                    });
        });


    }

//    public void showInterstitialAd(AdRequest adRequest) {
//        //Interstitial ads init
//        mInterstitialAd = new InterstitialAd() {
//            @Nullable
//            @Override
//            public FullScreenContentCallback getFullScreenContentCallback() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public OnPaidEventListener getOnPaidEventListener() {
//                return null;
//            }
//
//            @NonNull
//            @Override
//            public ResponseInfo getResponseInfo() {
//                return null;
//            }
//
//            @NonNull
//            @Override
//            public String getAdUnitId() {
//                return null;
//            }
//
//            @Override
//            public void setFullScreenContentCallback(@Nullable FullScreenContentCallback fullScreenContentCallback) {
//
//            }
//
//            @Override
//            public void setImmersiveMode(boolean b) {
//
//            }
//
//            @Override
//            public void setOnPaidEventListener(@Nullable OnPaidEventListener onPaidEventListener) {
//
//            }
//
//            @Override
//            public void show(@NonNull Activity activity) {
//
//            }
//        };
//        InterstitialAd.load(Video_View_Activity.this, "ca-app-pub-2223313192114405/3771225674", adRequest,
//        InterstitialAd.load(Video_View_Activity.this, "ca-app-pub-2223313192114405/3771225674", adRequest,
//                new InterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        // The mInterstitialAd reference will be null until
//                        // an ad is loaded.
//                        mInterstitialAd = interstitialAd;
//                        Log.i(TAG, "onAdLoaded");
//                        showFullScreenAds();
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        // Handle the error
//                        Log.d(TAG, loadAdError.toString());
//                        mInterstitialAd = null;
//                        startDownload();
//                    }
//                });
//
//
//    }

    public void showFullScreenAds() {
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad dismissed fullscreen content.");
                mInterstitialAd = null;
                startDownload();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.");
                mInterstitialAd = null;
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.");
            }
        });
        if (mInterstitialAd != null) {
            mInterstitialAd.show(Video_View_Activity.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

    private void startDownload() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            // API level is greater than 32
            // Your code here
            if (!checkPermissionAboveThirteen()) {
                requestStoragePermissionAboveThirteen();
            } else {
                ShareVideo SV = new ShareVideo();
                SV.execute("");
            }
        } else {
            // API level is 32 or below
            // Your code here
            if (!checkPermission()) {
                requestStoragePermission();
            } else {
                ShareVideo SV = new ShareVideo();
                SV.execute("");
            }
        }


    }

    private class ShareVideo extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            videoView.stopPlayback();
            // showDialog();
            exitDialog = new DownloadDialog(Video_View_Activity.this);
            exitDialog.show();
            Window window = exitDialog.getWindow();
            Objects.requireNonNull(window).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }

        @Override
        protected String doInBackground(String... strings) {
            if (imageUri != null) {
                startDownloadForSharing(imageUri);
            }
            return null;
        }

        private void startDownloadForSharing(String uri) {

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(uri));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
            request.allowScanningByMediaScanner();
            //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            videoPath = "Gurbani Status/Videos/" + System.currentTimeMillis() + ".mp4";
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, videoPath);
            DownloadManager manager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
            long downloadId = manager.enqueue(request);
            downloadFileProcess(downloadId);
        }

        @SuppressLint("Range")
        private void downloadFileProcess(long downloadId) {
            DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
            boolean downloading = true;
            while (downloading) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                Cursor cursor = downloadManager.query(query);
                cursor.moveToFirst();

                @SuppressLint("Range") int bytes_download = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                @SuppressLint("Range") int total_size = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false;
                }

                int progress = (int) (bytes_download * 100L) / total_size;
                String status = statusMessage(cursor);
                publishProgress(String.valueOf(progress), String.valueOf(bytes_download), status);
                cursor.close();
            }
        }

        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate();
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            exitDialog.dismiss();
            if (shareFlag) {
                Intent sharintent = new Intent(Intent.ACTION_SEND);
                sharintent.setType("video/mp4");
                sharintent.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + videoPath));
                startActivity(Intent.createChooser(sharintent, "share"));
            } else {
                Toast.makeText(getApplicationContext(), "Video successfully downloaded at " + videoPath, Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }

    @SuppressLint("Range")
    private String statusMessage(Cursor c) {
        String msg = switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED -> "Download failed!";
            case DownloadManager.STATUS_PAUSED -> "Download paused!";
            case DownloadManager.STATUS_PENDING -> "Download pending!";
            case DownloadManager.STATUS_RUNNING -> "Download in progress!";
            case DownloadManager.STATUS_SUCCESSFUL -> "Download complete!";
            default -> "Download is nowhere in sight";
        };

        return (msg);
    }

    public boolean checkPermission() {
        return ContextCompat.checkSelfPermission(
                Video_View_Activity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                Video_View_Activity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;

    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public boolean checkPermissionAboveThirteen() {
        return ContextCompat.checkSelfPermission(
                Video_View_Activity.this,
                Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                Video_View_Activity.this,
                Manifest.permission.READ_MEDIA_VIDEO
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        Log.e("Result", "Request permission is called.");
        ActivityCompat.requestPermissions(
                Video_View_Activity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                STORAGE_REQUEST_CODE
        );
    }

    private void requestStoragePermissionAboveThirteen() {
        Log.e("Result", "Request permission is called.");
        ActivityCompat.requestPermissions(
                Video_View_Activity.this,
                storagePermissions,
                STORAGE_REQUEST_CODE_ABOVE_THIRTEEN
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoView.stopPlayback();
       // showInterstitialAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        videoView.stopPlayback();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.start();
    }
}