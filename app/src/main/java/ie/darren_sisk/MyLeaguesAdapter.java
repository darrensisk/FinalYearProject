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
public class MyLeaguesAdapter extends ArrayAdapter {

    private Context context;
    private String url = "http://192.168.8.101";
    String json_string, json_data;

    List list = new ArrayList();




    public MyLeaguesAdapter(Context context, int resource){
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
            row = layoutInflater.inflate(R.layout.my_leagues_layout,parent,false);
            playerHolder = new PlayerHolder();
            playerHolder.leaguename_tv = (TextView) row.findViewById(R.id.news_tv);
            playerHolder.pin_tv = (TextView) row.findViewById(R.id.pin_tv);
            Button openLeague = (Button)row.findViewById(R.id.open_bn);

            playerHolder.userid = (TextView)row.findViewById(R.id.user_id_tv);
            playerHolder.leagueid = (TextView)row.findViewById(R.id.league_id_tv);
            openLeague.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    // Intent i =new Intent(parent.getContext(), LoginActivity.class);
                    //String p = playerHolder.pin_tv.getText().toString();
                    //System.out.println(p);
                    //parent.getContext().startActivity(i);


                    class sendLeagueTable extends AsyncTask<Void,Void,String>
                    {

                        final String uid = playerHolder.userid.getText().toString();
                        final String lid = playerHolder.leagueid.getText().toString();

                        String json_url;
                        String JSON_STRING;

                        @Override
                        protected void onPreExecute() {

                            json_url = url+"/leagueTable.php?lid=";
                        }

                        @Override
                        protected String doInBackground(Void... voids){

                            try{

                                URL url = new URL(json_url+lid);
                                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                                InputStream inputStream = httpURLConnection.getInputStream();
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                                StringBuilder stringBuilder = new StringBuilder();


                                while((JSON_STRING = bufferedReader.readLine())!=null)
                                {

                                    stringBuilder.append(JSON_STRING+"\n");
                                }

                                bufferedReader.close();
                                inputStream.close();
                                httpURLConnection.disconnect();
                                return stringBuilder.toString().trim();

                            } catch (MalformedJsonException e){
                                e.printStackTrace();
                            } catch (IOException e){
                                e.printStackTrace();
                            }



                            return null;
                        }

                        @Override
                        protected void onProgressUpdate(Void... values) {
                            super.onProgressUpdate(values);
                        }


                        @Override
                        protected void onPostExecute(String result) {

                            json_string = result;

                            if(json_string==null){
                                System.out.println("Error sending league table");
                                // Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

                            }
                            else{
                                Intent i =new Intent(parent.getContext(), MyLeagueTable.class);
                                //Intent intent = new Intent(MyLeagues.this, MyLeagueTable.class);
                                Bundle b = new Bundle();
                                b.putString("json_data", json_string);
                                b.putString("userid", uid);
                                i.putExtras(b);
                                parent.getContext().startActivity(i);

                            }

                        }
                    }

                    new sendLeagueTable().execute();

                }
            });

            row.setTag(playerHolder);

        }
        else{
            playerHolder = (PlayerHolder) row.getTag();


        }
        Players players = (Players) this.getItem(position);
        playerHolder.leaguename_tv.setText(players.getFirstname());
        playerHolder.pin_tv.setText(players.getLastname());
        playerHolder.userid.setText(players.getId());
        playerHolder.leagueid.setText(players.getRank());
        return row;
    }


    class PlayerHolder{

        TextView leaguename_tv, pin_tv,  userid, leagueid;
        Button bn_join;

    }



}
