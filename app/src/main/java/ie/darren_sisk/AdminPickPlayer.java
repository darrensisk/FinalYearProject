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

public class AdminPickPlayer extends AppCompatActivity {


    String url = "http://192.168.8.101";
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    PlayerAdapter playerAdapter;
    ListView listview;
    String firstname, surname, id;
    TextView uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pick_player);



        listview = (ListView) findViewById(R.id.listview);
        listview.setClickable(true);
        playerAdapter = new PlayerAdapter(this, R.layout.row_layout);




        listview.setAdapter(playerAdapter);
        listview.setClickable(true);



        Bundle b = getIntent().getExtras();


        json_string = getIntent().getExtras().getString("json_data");





        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;


            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);

                firstname = jo.getString("firstname");
                surname = jo.getString("lastname");
                id = jo.getString("player_id");


                Players players = new Players(firstname, surname, id);

                playerAdapter.add(players);
                count++;


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i , long l) {

                final String selected =((TextView)view.findViewById(R.id.tx_player_id)).getText().toString();



                getPlayerDetails getP = new getPlayerDetails();
                getP.execute(selected);
            }

        });

    }

    class getPlayerDetails extends AsyncTask<String,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/adminGetPlayerDetails.php";
        }

        @Override
        protected String doInBackground(String... params){

            try{


                final String pid = params[0];

                URL url = new URL(json_url+"?id="+pid);
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
                Intent intent = new Intent(AdminPickPlayer.this, AdminUpdatePlayerPoints.class);
                Bundle b = new Bundle();
                b.putString("json_data", json_string);

                intent.putExtras(b);
                startActivity(intent);

            }
        }
    }



}
