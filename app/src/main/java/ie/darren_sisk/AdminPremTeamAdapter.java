package ie.darren_sisk.fantasyfootballdraft;


import android.content.Context;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.MalformedJsonException;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.TextView;

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
public class AdminPremTeamAdapter extends ArrayAdapter {

    private Context context;
    private String url = "http://192.168.8.101";
    String json_string, json_data;

    List list = new ArrayList();




    public AdminPremTeamAdapter(Context context, int resource){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        PlayerHolder playerHolder;
        if (row==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.admin_prem_teams_layout,parent,false);
            playerHolder = new PlayerHolder();
            playerHolder.id = (TextView) row.findViewById(R.id.tx_id);
            playerHolder.teamname = (TextView) row.findViewById(R.id.tx_teamname);

            row.setTag(playerHolder);

        }
        else{
            playerHolder = (PlayerHolder) row.getTag();


        }
        Players players = (Players) this.getItem(position);
        playerHolder.id.setText(players.getFirstname());
        playerHolder.teamname.setText(players.getLastname());

        return row;
    }



    class PlayerHolder{

        TextView id, teamname;

    }



}
