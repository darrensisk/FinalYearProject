package ie.darren_sisk.fantasyfootballdraft;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.MalformedJsonException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darren_Sisk on 23/03/2017.
 */
public class NewsfeedAdapter extends ArrayAdapter {

    private Context context;
    private String url = "http://192.168.8.101";
    String json_string, json_data;

    List list = new ArrayList();




    public NewsfeedAdapter(Context context, int resource){
        super(context, resource);

    }


    public void add(Players object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View row;
        row = convertView;
        final PlayerHolder playerHolder;
        if (row==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.newsfeed_layout,parent,false);
            playerHolder = new PlayerHolder();
            playerHolder.news_tv = (TextView) row.findViewById(R.id.news_tv);
            playerHolder.news_id = (TextView) row.findViewById(R.id.news_id);




            row.setTag(playerHolder);

        }
        else{
            playerHolder = (PlayerHolder) row.getTag();


        }
        Players players = (Players) this.getItem(position);
        playerHolder.news_tv.setText(players.getFirstname());
        playerHolder.news_id.setText(players.getLastname());

        return row;
    }


    class PlayerHolder{

        TextView news_tv, news_id;


    }



}
