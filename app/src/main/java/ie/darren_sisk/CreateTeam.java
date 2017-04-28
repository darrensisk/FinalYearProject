package ie.darren_sisk.fantasyfootballdraft;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class CreateTeam extends AppCompatActivity {


    TextView userid;
    EditText teamName;
    Button createTeam;
    String url = "http://192.168.8.101";
    String team_url = url + "/createTeam.php?uid=";
    String points_url = url + "/createWeeklyPoints.php?uid=";
    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        builder = new AlertDialog.Builder(CreateTeam.this);

        userid = (TextView)findViewById(R.id.user_id);
        teamName = (EditText)findViewById(R.id.teamName);
        createTeam = (Button)findViewById(R.id.createTeam);

        Bundle bundle = getIntent().getExtras();
        userid.setText(bundle.getString("userId"));

        final String u = userid.getText().toString();
        final int value = Integer.valueOf(u);

        final String et = teamName.getText().toString();

        createTeam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                String teamNamee = teamName.getText().toString();
                final String user = userid.getText().toString();

                if(teamNamee.equals(""))
                {
                    builder.setTitle("Something went wrong");
                    displayAlert("Enter a valid Team name");
                }
                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, team_url+u+"&teamname="+teamNamee,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                            Intent intent = new Intent(CreateTeam.this, MyTeamActivity.class);
                                            Bundle b = new Bundle();
                                            b.putString("userId", user);
                                            intent.putExtras(b);

                                            startActivity(intent);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(CreateTeam.this, "Error", Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("username", "fff");
                            params.put("password", "ttt");
                            return params;

                        }
                    };

                    MySingleton.getInstance(CreateTeam.this).addToRequestQueue(stringRequest);


                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, points_url+u,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Intent intent = new Intent(CreateTeam.this, MyTeamActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("userId", user);
                                    intent.putExtras(b);

                                    startActivity(intent);



                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(CreateTeam.this, "Error", Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("username", "fff");
                            params.put("password", "ttt");
                            return params;

                        }
                    };

                    MySingleton.getInstance(CreateTeam.this).addToRequestQueue(stringRequest2);

                }






            }
        });



        }

    public void displayAlert(String message){


        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){


            @Override
            public void onClick(DialogInterface dialog , int which){
                //userid.setText("nahh");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
