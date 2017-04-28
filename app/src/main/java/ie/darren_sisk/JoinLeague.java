package ie.darren_sisk.fantasyfootballdraft;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.MalformedJsonException;
import android.view.View;
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

public class JoinLeague extends AppCompatActivity {

    private Button myLeague_bn, createLeague_bn;
    private Button open_bn, joinLeague_bn;
    private TextView user;
    String url = "http://192.168.8.101";
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    JoinLeagueAdapter playerAdapter;
    ListView listview;
    String leaguename, pin, leagueid, uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_league);

        listview = (ListView) findViewById(R.id.listview);

        playerAdapter = new JoinLeagueAdapter(this, R.layout.join_leagues_layout);
        listview.setAdapter(playerAdapter);

        listview.setClickable(true);
        user = (TextView)findViewById(R.id.textView10);

        json_string = getIntent().getExtras().getString("json_data");
        uid = getIntent().getExtras().getString("uid");
        user.setText(uid);

        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String rank = "1";


            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);

                leaguename = jo.getString("leaguename");
                pin = jo.getString("pin");
                leagueid = jo.getString("leagueid");
                String u= uid;



                Players players = new Players(leaguename, pin, uid, leagueid);

                playerAdapter.add(players);
                count++;
                int foo = Integer.parseInt(rank);
                foo++;
                rank=Integer.toString(foo);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }




        myLeague_bn = (Button)findViewById(R.id.bn_Football);
        createLeague_bn = (Button)findViewById(R.id.bn_createLeagues);


        myLeague_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openMyLeagues myLeagues = new openMyLeagues();
                myLeagues.execute(uid);

            }












            //
            //Run php script for Right Striker
            //
            class openMyLeagues extends AsyncTask<String,Void,String>
            {


                String json_url = url+"/myLeagues.php";
                String JSON_STRING;

                @Override
                protected void onPreExecute() {

                }

                @Override
                protected String doInBackground(String... params){

                    try{

                        String uid = params[0];

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

                    if(json_string==null){
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

                    }
                    else{
                        Intent intent = new Intent(JoinLeague.this, MyLeagues.class);
                        Bundle b = new Bundle();

                        b.putString("json_data", json_string);
                        b.putString("uid", uid);
                        intent.putExtras(b);

                        startActivity(intent);

                    }

                }
            }


        });

        createLeague_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(JoinLeague.this, CreateLeague.class);
                Bundle b = new Bundle();

                b.putString("uid", uid);
                intent.putExtras(b);

                startActivity(intent);


            }
        });
    }
}
