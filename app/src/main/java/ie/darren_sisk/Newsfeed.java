package ie.darren_sisk.fantasyfootballdraft;

import android.app.Activity;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Newsfeed extends AppCompatActivity {

    private TextView user_id;
    String url = "http://192.168.8.101";
    String story_url = url + "/getNewsStory.php";
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    NewsfeedAdapter playerAdapter;
    ListView listview;
    String headline, news_id , uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        listview = (ListView) findViewById(R.id.listview);

        playerAdapter = new NewsfeedAdapter(this, R.layout.newsfeed_layout);
        listview.setAdapter(playerAdapter);

        listview.setClickable(true);

        user_id = (TextView)findViewById(R.id.textView10);


        json_string = getIntent().getExtras().getString("json_data");
        uid = getIntent().getExtras().getString("uid");

        user_id.setText(uid);





        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;



            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);

                headline = jo.getString("headline");
                news_id = jo.getString("news_id");
                //System.out.println(news_id);




                Players players = new Players(headline, news_id);

                playerAdapter.add(players);
                count++;



            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i , long l) {

                String selected =((TextView)view.findViewById(R.id.news_id)).getText().toString();
                showWeeklyPoints s = new showWeeklyPoints();
                s.execute(selected);

            }

        });


    }


    //
    //Run php script for Right Striker
    //
    class showWeeklyPoints extends AsyncTask<String,Void,String>
    {


        String json_url = url+"/getNewsStory.php?id=";
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params){

            try{

                final String id = params[0];

                URL url = new URL(json_url+id);
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
            //final String u = userid.getText().toString();

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(Newsfeed.this, NewsStory.class);
                Bundle b = new Bundle();

                b.putString("json_data", json_string);
                b.putString("userid", uid);
                intent.putExtras(b);

                startActivity(intent);

            }

        }
    }



}
