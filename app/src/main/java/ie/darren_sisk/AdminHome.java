package ie.darren_sisk.fantasyfootballdraft;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.MalformedJsonException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminHome extends AppCompatActivity {

    String url = "http://192.168.8.101";
    String json_string;
    Button logout, updateColumn, updatePlayers, updateTeams;
    EditText week;
    TextView enter_week;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);




        Intent intent = getIntent();
        int code = intent.getIntExtra("code", 0);





        updateColumn = (Button)findViewById(R.id.update_column_bn);
        updatePlayers = (Button)findViewById(R.id.update_players_bn);
        updateTeams = (Button)findViewById(R.id.update_teams_bn);
        logout = (Button)findViewById(R.id.logout_bn);



        enter_week = (TextView)findViewById(R.id.textView27);

        week = (EditText)findViewById(R.id.week_no);


        final String w = week.getText().toString();



        updateColumn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                Intent in = new Intent(AdminHome.this, AdminWeeklyMaintenance.class);

                in.putExtra("week",week.getText().toString());
                startActivity(in);


            }
        });


        updatePlayers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                new getPremTeams().execute();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                Intent i = new Intent(AdminHome.this, LoginActivity.class);
                startActivity(i);

            }
        });


        updateTeams.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                Intent i = new Intent(AdminHome.this, AdminUpdateTeamPoints.class);
                i.putExtra("code", 0);
                startActivity(i);

            }
        });




        updateColumn.setClickable(false);
        updatePlayers.setClickable(false);
        updateTeams.setClickable(false);


        if(w!=""){
            updateColumn.setClickable(true);
            updatePlayers.setClickable(true);
            updateTeams.setClickable(true);
        }


        if(code==2){
            updateColumn.setText("Done!");
            updateColumn.setClickable(false);
            enter_week.setVisibility(View.INVISIBLE);
            week.setVisibility(View.INVISIBLE);
        }
        else if (code==3){

            updateColumn.setText("Done!");
            updateColumn.setClickable(false);
            enter_week.setVisibility(View.INVISIBLE);
            week.setVisibility(View.INVISIBLE);

            updatePlayers.setText("Done!");
            updatePlayers.setClickable(false);

        }
        else if (code==4){

            updateColumn.setText("Done!");
            updateColumn.setClickable(false);
            enter_week.setVisibility(View.INVISIBLE);
            week.setVisibility(View.INVISIBLE);

            updatePlayers.setText("Done!");
            updatePlayers.setClickable(false);

            updateTeams.setText("Done!");
            updateTeams.setClickable(false);
        }

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
                Intent intent = new Intent(AdminHome.this, AdminSelectTeam.class);
                intent.putExtra("json_data", json_string);
                startActivity(intent);

            }


        }
    }
}
