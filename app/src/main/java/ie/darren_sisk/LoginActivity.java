package ie.darren_sisk.fantasyfootballdraft;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.MalformedJsonException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

public class LoginActivity extends AppCompatActivity {

    TextView textview;
    Button login_button;
    EditText UserName, Password;
    String username, password;
    String url = "http://192.168.8.101";
    String login_url = url + "/login.php";
    AlertDialog.Builder builder;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        textview = (TextView)findViewById(R.id.tvRegister);
        textview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        builder = new AlertDialog.Builder(LoginActivity.this);
        login_button = (Button)findViewById(R.id.bnLogin);
        UserName = (EditText)findViewById(R.id.login_name);
        Password = (EditText)findViewById(R.id.login_password);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = UserName.getText().toString();
                password = Password.getText().toString();

                if(username.equals("")||password.equals(""))
                {
                    builder.setTitle("Something went wrong");
                    displayAlert("Enter a valid username and password");
                }
                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{

                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        String admin = jsonObject.getString("is_admin");
                                        if(code.equals("login_failed")){
                                            builder.setTitle("Login error");
                                            displayAlert(jsonObject.getString("message"));
                                        }
                                        else if(code.equals("login_success") && admin.equals("0"))
                                        {
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);


                                            Bundle bundle = new Bundle();

                                            bundle.putString("username", jsonObject.getString("username"));
                                            bundle.putString("email", jsonObject.getString("email"));
                                            bundle.putString("user_id", jsonObject.getString("user_id"));
                                            bundle.putString("total_points", jsonObject.getString("total_points"));

                                            intent.putExtras(bundle);



                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            //new getPremTeams().execute();
                                            Intent in = new Intent(LoginActivity.this, AdminHome.class);
                                            in.putExtra("code", 1);
                                            startActivity(in);

                                        }


                                    } catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("username", username);
                            params.put("password", password);
                            return params;

                        }
                    };

                    MySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);

                }


            }
        });


    }




    public void displayAlert(String message){


        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){


            @Override
            public void onClick(DialogInterface dialog , int which){
                UserName.setText("");
                Password.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }



    class getPremTeams extends AsyncTask<Void,Void,String>
    {

        String json_url = url+"/getPremTeams.php";
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void ... voids){

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
                Intent intent = new Intent(LoginActivity.this, AdminSelectTeam.class);
                intent.putExtra("json_data", json_string);
                startActivity(intent);

            }


        }
    }


}


