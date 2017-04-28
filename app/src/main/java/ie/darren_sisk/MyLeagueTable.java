package ie.darren_sisk.fantasyfootballdraft;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.MalformedJsonException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyLeagueTable extends AppCompatActivity {

    String url = "http://192.168.8.101";
    String weeklyPointsUrl = url + "getWeeklyPoints?uid=";

    String json_string, user_id;
    JSONObject jsonObject;
    JSONArray jsonArray;
    LeagueAdapter playerAdapter;
    ListView listview;
    TextView ranking, userid;
    String teamname, weeklypoints, totalpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_league_table);

        listview = (ListView) findViewById(R.id.listview);
        listview.setClickable(true);




        ranking = (TextView)findViewById(R.id.tx_id);

        playerAdapter = new LeagueAdapter(this, R.layout.my_leagues_layout);
        listview.setAdapter(playerAdapter);

        Bundle b = getIntent().getExtras();

        json_string = (b.getString("json_data"));
        user_id = (b.getString("userid"));
        userid = (TextView)findViewById(R.id.uid);
        userid.setText(user_id);
        final String user = userid.getText().toString();

        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String rank = "1";


            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);

                teamname = jo.getString("teamname");
                weeklypoints = jo.getString("weeklypoints");
                totalpoints = jo.getString("totalpoints");


                Players players = new Players(teamname, weeklypoints, totalpoints, rank);

                playerAdapter.add(players);
                count++;
                int foo = Integer.parseInt(rank);
                foo++;
                rank=Integer.toString(foo);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }





        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i , long l){





                showWeeklyPoints sendLeagues = new showWeeklyPoints();
                sendLeagues.execute(user);
            }


        });






    }

    //
    //Run php script for Right Striker
    //
    class showWeeklyPoints extends AsyncTask<String,Void,String>
    {


        String json_url = url+"/getWeeklyPoints.php";
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params){

            try{

                final String uid = params[0];

                URL url = new URL(json_url+"?uid="+uid);
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
            final String u = userid.getText().toString();

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(MyLeagueTable.this, UsersWeeklyPoints.class);
                Bundle b = new Bundle();

                b.putString("json_data", json_string);
                b.putString("userid", u);
                intent.putExtras(b);

                startActivity(intent);

            }

        }
    }
}
