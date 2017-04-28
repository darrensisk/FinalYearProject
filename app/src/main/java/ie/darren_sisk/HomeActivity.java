package ie.darren_sisk.fantasyfootballdraft;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.MalformedJsonException;
import android.view.View;
import android.widget.Button;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    TextView name, playerName, user_id, total_points, date_tv;
    Button myTeam, myLeagues, newsfeed;
    String url = "http://192.168.8.101";
    String json_string;
    String team_url = url + "/checkTeam.php?uid=";
    String d;

    private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Date date = new Date();
        d = (sdf.format(date));



        date_tv = (TextView) findViewById(R.id.d);
        name = (TextView) findViewById(R.id.name);
         user_id = (TextView)findViewById(R.id.userId);
         total_points = (TextView)findViewById(R.id.totalPoints);

         Bundle bundle = getIntent().getExtras();
         name.setText(bundle.getString("username"));
         user_id.setText(bundle.getString("user_id"));
         user_id.setVisibility(View.INVISIBLE);
         total_points.setText(bundle.getString("total_points"));

        date_tv.setText(d);
        final String u = user_id.getText().toString();
        final int value = Integer.valueOf(u);

        myTeam = (Button)findViewById(R.id.myTeamButton);
        myLeagues = (Button)findViewById(R.id.myLeagueButton);
        newsfeed = (Button)findViewById(R.id.newsButton);


        myTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, team_url+value,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{

                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");


                                        if(code.equals("Team_Exists"))
                                        {

                                            String gk_id = jsonObject.getString("gk_id");
                                            String rb_id = jsonObject.getString("rb_id");
                                            String cb1_id = jsonObject.getString("cb1_id");
                                            String cb2_id = jsonObject.getString("cb2_id");
                                            String lb_id = jsonObject.getString("lb_id");
                                            String rm_id = jsonObject.getString("rm_id");
                                            String cm1_id = jsonObject.getString("cm1_id");
                                            String cm2_id = jsonObject.getString("cm2_id");
                                            String lm_id = jsonObject.getString("lm_id");
                                            String ls_id = jsonObject.getString("ls_id");
                                            String rs_id = jsonObject.getString("rs_id");
                                            
                                            String gk_name = jsonObject.getString("gk_name");
                                            String rb_name = jsonObject.getString("rb_name");
                                            String cb1_name = jsonObject.getString("cb1_name");
                                            String cb2_name = jsonObject.getString("cb2_name");
                                            String lb_name = jsonObject.getString("lb_name");
                                            String rm_name = jsonObject.getString("rm_name");
                                            String cm1_name = jsonObject.getString("cm1_name");
                                            String cm2_name = jsonObject.getString("cm2_name");
                                            String lm_name = jsonObject.getString("lm_name");
                                            String ls_name = jsonObject.getString("ls_name");
                                            String rs_name = jsonObject.getString("rs_name");

                                            String team = jsonObject.getString("team_name");
                                            String points = jsonObject.getString("total_points");


                                            Intent intent = new Intent(HomeActivity.this, MyTeamActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("userId", u);
                                            bundle.putString("gk_id", gk_id);
                                            bundle.putString("rb_id", rb_id);
                                            bundle.putString("cb1_id", cb1_id);
                                            bundle.putString("cb2_id", cb2_id);
                                            bundle.putString("lb_id", lb_id);
                                            bundle.putString("rm_id", rm_id);
                                            bundle.putString("cm1_id", cm1_id);
                                            bundle.putString("cm2_id", cm2_id);
                                            bundle.putString("lm_id", lm_id);
                                            bundle.putString("ls_id", ls_id);
                                            bundle.putString("rs_id", rs_id);
                                            
                                            bundle.putString("gk_name", gk_name);
                                            bundle.putString("rb_name", rb_name);
                                            bundle.putString("cb1_name", cb1_name);
                                            bundle.putString("cb2_name", cb2_name);
                                            bundle.putString("lb_name", lb_name);
                                            bundle.putString("rm_name", rm_name);
                                            bundle.putString("cm1_name", cm1_name);
                                            bundle.putString("cm2_name", cm2_name);
                                            bundle.putString("lm_name", lm_name);
                                            bundle.putString("ls_name", ls_name);
                                            bundle.putString("rs_name", rs_name);

                                            bundle.putString("team_name", team);
                                            bundle.putString("total_points", points);

                                            intent.putExtras(bundle);
                                            startActivity(intent);

                                        }
                                        else if(code.equals("No_Team"))
                                        {

                                            Intent intent = new Intent(HomeActivity.this, CreateTeam.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("userId", u);
                                            intent.putExtras(bundle);
                                            startActivity(intent);

                                        }


                                    } catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("username", "hello");
                            params.put("password", "test");
                            return params;

                        }
                    };

                    MySingleton.getInstance(HomeActivity.this).addToRequestQueue(stringRequest);

                }



        });




        myLeagues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //new sendMyLeagues().execute();

                sendMyLeagues sendLeagues = new sendMyLeagues();
                sendLeagues.execute(u);


            }



        });


        newsfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //new sendMyLeagues().execute();

                sendNewsfeed sendnewsfeed = new sendNewsfeed();
                sendnewsfeed.execute(u);


            }



        });


    }




    //
    //Run php script to send news headlines
    //
    class sendNewsfeed extends AsyncTask<String,Void,String>
    {

        //String json_url;
        String JSON_STRING;
        String json_url = url+"/getNewsfeed.php";

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

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(HomeActivity.this, Newsfeed.class);
                Bundle b = new Bundle();

                String u = user_id.getText().toString();
                b.putString("json_data", json_string);
                b.putString("uid", u);
                intent.putExtras(b);
                startActivity(intent);

            }

        }
    }


    class sendMyLeagues extends AsyncTask<String,Void,String>
    {

        //String json_url;
        String JSON_STRING;
        String json_url = url+"/myLeagues.php";

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

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(HomeActivity.this, MyLeagues.class);
                Bundle b = new Bundle();

                String u = user_id.getText().toString();
                b.putString("json_data", json_string);
                b.putString("uid", u);
                intent.putExtras(b);
                startActivity(intent);

            }

        }
    }


























}
