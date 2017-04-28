package ie.darren_sisk.fantasyfootballdraft;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.MalformedJsonException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminUpdateTeamPoints extends AppCompatActivity {


    Button update, updateTotal, updateUser, fin;
    String url = "http://192.168.8.101/adminUpdateTeamsWeeklyPoints.php";
    String url2 = "http://192.168.8.101/adminUpdateTeamsTotalPoints.php";
    String url3 = "http://192.168.8.101/adminUpdateUserTotalPoints.php";

    String json_string;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_team_points);



        Intent intent = getIntent();
        int code = intent.getIntExtra("code", 0);



        fin = (Button)findViewById(R.id.finished);

        fin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                Intent i = new Intent(AdminUpdateTeamPoints.this, AdminHome.class);
                i.putExtra("code", 4);
                startActivity(i);
            }
        });


        update = (Button)findViewById(R.id.update_teams_weekly);

        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                new updatePointsWeekly().execute();
            }
        });

        updateTotal = (Button)findViewById(R.id.update_teams_total);


        updateTotal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){


                new updatePointsTotal().execute();
            }
        });


        updateUser = (Button)findViewById(R.id.update_user_pts);


        updateUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){



                updateTotal.setText("Done!");
                new updateUserPoints().execute();
            }
        });





        if(code==1){
            update.setText("Done!");
            update.setClickable(false);

        }else if(code==2){
            update.setText("Done!");
            update.setClickable(false);

            updateTotal.setText("Done!");
            updateTotal.setClickable(false);
        }
        else if(code==3){
            update.setText("Done!");
            update.setClickable(false);

            updateTotal.setText("Done!");
            updateTotal.setClickable(false);

            updateUser.setText("Done!");
            updateUser.setClickable(false);
        }



    }


    class updatePointsWeekly extends AsyncTask<String,Void,String>
    {

        //String json_url;
        String JSON_STRING;
        String json_url = url;

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... params){

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
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(AdminUpdateTeamPoints.this, AdminUpdateTeamPoints.class);

                intent.putExtra("code", 1);


                startActivity(intent);

            }

        }
    }




    class updatePointsTotal extends AsyncTask<String,Void,String>
    {

        //String json_url;
        String JSON_STRING;
        String json_url = url2;

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... params){

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
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(AdminUpdateTeamPoints.this, AdminUpdateTeamPoints.class);

                intent.putExtra("code", 2);


                startActivity(intent);

            }

        }
    }





    class updateUserPoints extends AsyncTask<String,Void,String>
    {

        //String json_url;
        String JSON_STRING;
        String json_url = url3;

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... params){

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
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(AdminUpdateTeamPoints.this, AdminUpdateTeamPoints.class);

                intent.putExtra("code", 3);


                startActivity(intent);

            }

        }
    }





}
