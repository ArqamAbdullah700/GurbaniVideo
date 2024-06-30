package com.gurbanivideo;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

public class DownloadDialog extends Dialog
{
    Activity activity;
    public DownloadDialog(Activity activity)
    {
        super(activity);
        this.activity = activity;
        //this.ad = ad;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.downloading_dialog);
    }
}
