package com.example.android.tutorfinder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListView extends ArrayAdapter<String> {

    private String[] userNames;
    private String[] userLocation;
    private String[] userPrice;
    private String[] userImage;
    private Activity context;

    public CustomListView(Activity context, String[] userNames, String[] userLocation, String[] userPrice,String[] userImage) {
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
        viewHolder.tvw4.setImageResource(Integer.parseInt(userImage[position]));
        viewHolder.tvw1.setText(userNames[position]);
        viewHolder.tvw2.setText(userLocation[position]);
        viewHolder.tvw3.setText(userPrice[position]);


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
