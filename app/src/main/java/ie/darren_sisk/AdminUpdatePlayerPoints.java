package ie.darren_sisk.fantasyfootballdraft;

import android.content.DialogInterface;
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

public class AdminUpdatePlayerPoints extends AppCompatActivity {



    String url = "http://192.168.8.101";
    String update_url = url + "/adminUpdatePlayerDetails.php";
    String json_string, pid;
    TextView playerName, weeklyPoints, totalPoints;
    JSONObject jsonObject;
    JSONArray jsonArray;
    AlertDialog.Builder builder;

    String games_played, goals_scored, assists_string;

    Button updateBn;

    EditText gamesPlayed, goalsScored, assists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_player_points);



        builder = new AlertDialog.Builder(AdminUpdatePlayerPoints.this);

        Bundle b = getIntent().getExtras();


        playerName = (TextView)findViewById(R.id.player_name);
        weeklyPoints = (TextView)findViewById(R.id.current_weekly_points);
        totalPoints = (TextView)findViewById(R.id.current_total_points);

        json_string = getIntent().getExtras().getString("json_data");



        gamesPlayed = (EditText)findViewById(R.id.gamesPlayed);
        goalsScored = (EditText)findViewById(R.id.goalsScored);
        assists = (EditText)findViewById(R.id.assists);

        updateBn = (Button)findViewById(R.id.update_bn);




        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;


            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);

                playerName.setText(jo.getString("lastname"));
                weeklyPoints.setText(jo.getString("weekly_points"));
                totalPoints.setText(jo.getString("total_points"));
                pid = (jo.getString("player_id"));


                count++;


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



        updateBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                games_played = gamesPlayed.getText().toString();
                goals_scored = goalsScored.getText().toString();
                assists_string = assists.getText().toString();



                if(games_played.equals("") || goals_scored.equals("") || assists_string.equals(""))
                {
                    builder.setTitle("Oops..");
                    builder.setMessage("Please fill all the fields");
                    displayAlert("input_error");
                }
                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, update_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{

                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        String message = jsonObject.getString("message");
                                        builder.setTitle("Updated");
                                        builder.setMessage(message);
                                        displayAlert(code);





                                    } catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(AdminUpdatePlayerPoints.this, "Error", Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("games_played", games_played);
                            params.put("goals_scored", goals_scored);
                            params.put("pid", pid);
                            params.put("assists", assists_string);
                            return params;

                        }
                    };

                    MySingleton.getInstance(AdminUpdatePlayerPoints.this).addToRequestQueue(stringRequest);

                }


            }




        });



    }

    public void displayAlert(final String code){


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which){

                if(code.equals("input_error")){

                    goalsScored.setText("");
                    gamesPlayed.setText("");

                }
                else if (code.equals("reg success"))
                {

                    goalsScored.setText("");
                    gamesPlayed.setText("");
                    finish();

                }

            }

        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
