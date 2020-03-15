package com.example.android.tutorfinder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomListView extends ArrayAdapter<String> {

    private ArrayList<String> userNames;
    private ArrayList<String> userLocation;
    private ArrayList<String> userPrice;
    private ArrayList<Bitmap> userImage;
    private Activity context;

    public CustomListView(Activity context, ArrayList<String> userNames, ArrayList<String> userLocation, ArrayList<String> userPrice, ArrayList<Bitmap> userImage) {
        super(context, R.layout.listview_item,userNames);

        this.context = context;
        this.userNames = userNames;
        this.userLocation = userLocation;
        this.userPrice = userPrice;
        this.userImage = userImage;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r= convertView;
        ViewHolder viewHolder = null;
        if(r == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listview_item,null,true);
            viewHolder= new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) r.getTag();
        }

        if(!userImage.isEmpty()){
            viewHolder.tvw4.setImageBitmap(userImage.get(position));
        }
        if(!userNames.isEmpty()){
            viewHolder.tvw1.setText(userNames.get(position));
        }
        if(!userLocation.isEmpty()){
            viewHolder.tvw2.setText(userLocation.get(position));
        }
        if(!userPrice.isEmpty()) {
            viewHolder.tvw3.setText(userPrice.get(position));
        }
        return r;
    }


    class ViewHolder {
        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        ImageView tvw4;

        ViewHolder(View v){
            tvw1 = v.findViewById(R.id.listViewNameTextView);
            tvw2 = v.findViewById(R.id.userLocationTextView);
            tvw3 = v.findViewById(R.id.userSalaryTextView);
            tvw4 = v.findViewById(R.id.imageView);

        }


    }
}
