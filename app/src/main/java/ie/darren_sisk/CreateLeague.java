package ie.darren_sisk.fantasyfootballdraft;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.MalformedJsonException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class CreateLeague extends AppCompatActivity {

    String uid;
    TextView user;
    String json_string;
    EditText leagueName, leaguePin;
    String leaguename, leaguepin;
    Button createLeague, myLeagues, joinLeagues;
    String url = "http://192.168.8.101";
    String create_league_url = url + "/createleague.php";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_league);


        myLeagues = (Button)findViewById(R.id.bn_Football);
        joinLeagues = (Button)findViewById(R.id.bn_joinLeagues);

        user = (TextView)findViewById(R.id.textView10);

        json_string = getIntent().getExtras().getString("json_data");
        uid = getIntent().getExtras().getString("uid");
        user.setText(uid);



        builder = new AlertDialog.Builder(CreateLeague.this);

        leagueName = (EditText)findViewById(R.id.et_leaguename);
        leaguePin = (EditText)findViewById(R.id.et_leaguepin);
        createLeague = (Button)findViewById(R.id.createLeague);










        createLeague.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                leaguename = leagueName.getText().toString();
                leaguepin = leaguePin.getText().toString();


                if(leaguename.equals("")||leaguepin.equals(""))
                {

                    builder.setTitle("Oops..");
                    builder.setMessage("Please fill all the fields");
                    displayAlert("input_error");
                }

                else{

                    if(leaguename.length() > 25){
                        builder.setTitle("Oops");
                        builder.setMessage("League name cannot exceed 25 characters");
                        displayAlert("input_error_name");
                    }
                    else if(leaguepin.length() != 5){
                        builder.setTitle("Oops");
                        builder.setMessage("League pin needs to be 5 numbers");
                        displayAlert("input_error_pin");
                    }
                    else
                    {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, create_league_url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");
                                            String message = jsonObject.getString("message");
                                            builder.setTitle("Response");
                                            builder.setMessage(message);
                                            displayAlert(code);

                                        } catch(JSONException e) {
                                            e.printStackTrace();
                                        }



                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("leaguename", leaguename);
                                params.put("leaguepin", leaguepin);

                                return params;
                            }
                        };
                        MySingleton.getInstance(CreateLeague.this).addToRequestQueue(stringRequest);


                    }


                }



            }





        });

        myLeagues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openMyLeagues myLeagues = new openMyLeagues();
                myLeagues.execute(uid);

            }
        });

        joinLeagues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openJoinLeagues joinLeagues = new openJoinLeagues();
                joinLeagues.execute(uid);


            }
        });

    }



    public void displayAlert(final String code){

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which){

                if(code.equals("input_error")){

                    leagueName.setText("");
                    leaguePin.setText("");

                }
                else if(code.equals("input_error_pin")){

                    leaguePin.setText("");

                }
                else if(code.equals("input_error_name")){

                    leagueName.setText("");

                }
                else if (code.equals("reg success"))
                {

                    leagueName.setText("");
                    leaguePin.setText("");
                    finish();

                }
                else if (code.equals("reg failed"))
                {
                    leagueName.setText("");
                    leaguePin.setText("");


                }
            }

        });

        AlertDialog alertDialog = builder.create();
            alertDialog.show();
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
                Intent intent = new Intent(CreateLeague.this, MyLeagues.class);
                Bundle b = new Bundle();

                b.putString("json_data", json_string);
                b.putString("uid", uid);
                intent.putExtras(b);

                startActivity(intent);

            }

        }
    }

    //
    //Run php script for Right Striker
    //
    class openJoinLeagues extends AsyncTask<String,Void,String>
    {


        String json_url = url+"/myNotLeagues.php";
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
                Intent intent = new Intent(CreateLeague.this, JoinLeague.class);
                Bundle b = new Bundle();

                b.putString("json_data", json_string);
                b.putString("uid", uid);
                intent.putExtras(b);

                startActivity(intent);

            }

        }
    }

}
