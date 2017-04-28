package ie.darren_sisk.fantasyfootballdraft;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.util.MalformedJsonException;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class playerDetails extends AppCompatActivity {

    String url = "http://192.168.8.101";
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    TextView firstname_tx, surname_tx, position_tx, club_tx, country_tx, games_tx, cleanSheets_tx;

    PlayerAdapter playerAdapter;
    ListView listview;
    Button button, winlossbutton;
    private String TAG = "playerDetails";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);


        winlossbutton = (Button)findViewById(R.id.pieChart);

        json_string = getIntent().getExtras().getString("json_data");

        firstname_tx = (TextView)findViewById(R.id.firstname2);
        surname_tx = (TextView)findViewById(R.id.surname2);
        position_tx = (TextView)findViewById(R.id.position2);
        club_tx = (TextView)findViewById(R.id.club2);
        country_tx = (TextView)findViewById(R.id.country2);
        cleanSheets_tx = (TextView)findViewById(R.id.cleanSheets2);
        games_tx = (TextView)findViewById(R.id.games2);

        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("php_response-player_details");
            int count = 0;

            String firstname, surname, position, club, country, games, cleansheets;

            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);

                firstname = jo.getString("firstname");
                surname = jo.getString("lastname");
                position = jo.getString("position");
                club = jo.getString("club");
                country = jo.getString("country");
                games = jo.getString("games");
                cleansheets = jo.getString("cleansheets");

                firstname_tx.setText(firstname);
                surname_tx.setText(surname);
                position_tx.setText(position);
                club_tx.setText(club);
                country_tx.setText(country);
                games_tx.setText(games);
                cleanSheets_tx.setText(cleansheets);



                count++;


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }





    }



    public void showPlayerGrowth(View view){
        Intent intent = new Intent(playerDetails.this, PlayerGrowth.class);

        startActivity(intent);
    }

    public void showPlayerAttributes(View view){
        Intent intent = new Intent(playerDetails.this, PlayerAttributes.class);

        startActivity(intent);
    }




    //
    //show win loss ratio of player
    //
    public void openPieChart(View view)  {

        new LoadPlayerDetails().execute();
    }

    //
    //Run php script for player details
    //
    class LoadPlayerDetails extends AsyncTask<Void,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/zlatanDetails.php";
        }

        @Override
        protected String doInBackground(Void... voids){

            try{

                URL url = new URL(json_url);
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
                Intent intent = new Intent(playerDetails.this, playerStats.class);
                intent.putExtra("json_data", json_string);
                startActivity(intent);

            }


        }
    }

}


