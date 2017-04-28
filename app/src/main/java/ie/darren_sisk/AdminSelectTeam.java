package ie.darren_sisk.fantasyfootballdraft;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.MalformedJsonException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class AdminSelectTeam extends AppCompatActivity {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    AdminPremTeamAdapter playerAdapter;
    ListView listview;
    String id, teamname;
    String url = "http://192.168.8.101";
    Button fin;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_select_team);


        listview = (ListView) findViewById(R.id.listview);

        playerAdapter = new AdminPremTeamAdapter(this, R.layout.admin_prem_teams_layout);
        listview.setAdapter(playerAdapter);

        listview.setClickable(true);

        //user_id = (TextView) findViewById(R.id.textView10);


        json_string = getIntent().getExtras().getString("json_data");
        //uid = getIntent().getExtras().getString("uid");

        //user_id.setText(uid);


        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;


            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);

                id = jo.getString("team_id");
                teamname = jo.getString("teamname");
                //System.out.println(news_id);


                Players players = new Players(id, teamname);

                playerAdapter.add(players);
                count++;


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



        fin = (Button)findViewById(R.id.finished);

        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminSelectTeam.this, AdminHome.class);
                intent.putExtra("code", 3);
                startActivity(intent);

            }
        });


                listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i , long l) {

                String selected =((TextView)view.findViewById(R.id.tx_id)).getText().toString();
                //getTeamsPlayers s = new getTeamsPlayers();
                //s.execute(selected);

                getTeamsPlayers get = new getTeamsPlayers();
                get.execute(selected);
            }

        });

    }




    class getTeamsPlayers extends AsyncTask<String,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/getPremTeamsPlayers.php";
        }

        @Override
        protected String doInBackground(String... params){

            try{


                String team = params[0];
                System.out.println(team);
                URL url = new URL(json_url+"?club="+team);
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
                Toast.makeText(getApplicationContext(), "First get JSON Data", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(AdminSelectTeam.this, AdminPickPlayer.class);
                Bundle b = new Bundle();
                b.putString("json_data", json_string);
                intent.putExtras(b);
                startActivity(intent);

            }
        }
    }




}
