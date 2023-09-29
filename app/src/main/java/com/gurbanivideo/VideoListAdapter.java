package com.gurbanivideo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoListAdapter extends ArrayAdapter<VideoListHolder> {
    ArrayList<VideoListHolder> arrayList;
    Context context;

    public VideoListAdapter(@NonNull Context context, int resource, ArrayList<VideoListHolder> videoListHolders) {
        super(context, resource,videoListHolders);
        this.context = context;
        this.arrayList= videoListHolders;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_status_layout, parent, false);
        ImageView iv = view.findViewById(R.id.imgStatusAdmin);
        TextView title  = view.findViewById(R.id.txtTitleImage);
        Picasso.get().load(arrayList.get(position).getImageUrl()).placeholder(R.drawable.select_image).into(iv);
        title.setText(arrayList.get(position).getTitle());
        return view;
    }
}
