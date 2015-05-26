package com.example.simplenfc;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ListViewCustomAdapter extends BaseAdapter {

    public ArrayList<String> album_names;

    public ArrayList<String> photos;

    public Activity context;

    public LayoutInflater inflater;

    public ListViewCustomAdapter(Activity context,
                                 ArrayList<String> album_names, ArrayList<String> photos) {
        // TODO Auto-generated constructor stub
        //super();
        this.context = context;
        this.album_names = album_names;
        this.photos = photos;

        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return album_names.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public class ViewHolder {
        ImageView thumbnail;
        TextView txtViewAlbum;
        TextView txtViewPhotos;

    }

    public View getView(int position, View convertview, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;
        if (convertview == null) {
            holder = new ViewHolder();
            convertview = inflater.inflate(R.layout.listview_row, null);


            holder.thumbnail = (ImageView) convertview
                    .findViewById(R.id.imgViewLogo);
            holder.txtViewAlbum = (TextView) convertview
                    .findViewById(R.id.txtViewTitle);
            holder.txtViewPhotos = (TextView) convertview
                    .findViewById(R.id.txtViewDescription);

            convertview.setTag(holder);
        }

        else

            holder = (ViewHolder) convertview.getTag();

        holder.thumbnail.setImageResource(R.drawable.gachon);
        holder.txtViewAlbum.setText(album_names.get(position));
        holder.txtViewPhotos.setText(photos.get(position));

        return convertview;
    }

}
