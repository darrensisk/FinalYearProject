package ie.darren_sisk.fantasyfootballdraft;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.MalformedJsonException;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyTeamActivity extends AppCompatActivity {


    String url = "http://192.168.8.101";
    String json_string, user_id;
    View v;
    ImageButton ls, rs, lm, rm, cm1, cm2, lb, rb, cb1, cb2, gk;
    ImageButton ls2, rs2, lm2, rm2, cm12, cm22, lb2, rb2, cb12, cb22, gk2;
    TextView gksurname, rbsurname, cb1surname, cb2surname, lbsurname, rmsurname,
            cm1surname, cm2surname, lmsurname, rssurname, lssurname, uid , teamname, totalpoints;
    String gkId, rbId, cb1Id, cb2Id, lbId, rmId, cm1Id, cm2Id, lmId, rsId, lsId, gkName, rbName
            ,cb1Name, cb2Name, lbName, rmName, cm1Name, cm2Name, lmName, lsName, rsName, totalPoints, teamName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);

        uid = (TextView)findViewById(R.id.uid);

        Bundle bundle = getIntent().getExtras();

        gkId = bundle.getString("gk_id");
        rbId = bundle.getString("rb_id");
        cb1Id = bundle.getString("cb1_id");
        cb2Id = bundle.getString("cb2_id");
        lbId = bundle.getString("lb_id");
        rmId = bundle.getString("rm_id");
        cm1Id = bundle.getString("cm1_id");
        cm2Id = bundle.getString("cm2_id");
        lmId = bundle.getString("lm_id");
        lsId = bundle.getString("ls_id");
        rsId = bundle.getString("rs_id");

        gkName = bundle.getString("gk_name");
        rbName = bundle.getString("rb_name");
        cb1Name = bundle.getString("cb1_name");
        cb2Name = bundle.getString("cb2_name");
        lbName = bundle.getString("lb_name");
        rmName = bundle.getString("rm_name");
        cm1Name = bundle.getString("cm1_name");
        cm2Name = bundle.getString("cm2_name");
        lmName = bundle.getString("lm_name");
        lsName = bundle.getString("ls_name");
        rsName = bundle.getString("rs_name");


        teamName = bundle.getString("team_name");
        totalPoints = bundle.getString("total_points");


        uid.setText(bundle.getString("userId"));
        uid.setVisibility(View.GONE);


        teamname = (TextView)findViewById(R.id.team);
        totalpoints = (TextView)findViewById(R.id.pts);

        teamname.setText(teamName);
        totalpoints.setText(totalPoints);


        user_id = uid.getText().toString();



        ls = (ImageButton)findViewById(R.id.leftStrikerButton);
        rs = (ImageButton)findViewById(R.id.rightStrikerButton);
        lm = (ImageButton)findViewById(R.id.leftMidButton);
        rm = (ImageButton)findViewById(R.id.rightMidButton);
        cm1 = (ImageButton)findViewById(R.id.centreMidOneButton);
        cm2 = (ImageButton)findViewById(R.id.centreMidTwoButton);
        rb = (ImageButton)findViewById(R.id.rightBackButton);
        lb = (ImageButton)findViewById(R.id.leftBackButton);
        cb1 = (ImageButton)findViewById(R.id.centreBackOneButton);
        cb2 = (ImageButton)findViewById(R.id.centreBackTwoButton);
        gk = (ImageButton)findViewById(R.id.goalkeeper);



        gk2 = (ImageButton)findViewById(R.id.goalkeeper2nd);
        ls2 = (ImageButton)findViewById(R.id.leftStrikerButton2);
        rs2 = (ImageButton)findViewById(R.id.rightStrikerButton2);
        lm2 = (ImageButton)findViewById(R.id.leftMidButton2);
        rm2 = (ImageButton)findViewById(R.id.rightMidButton2);
        cm12 = (ImageButton)findViewById(R.id.centreMidOneButton2);
        cm22 = (ImageButton)findViewById(R.id.centreMidTwoButton2);
        rb2 = (ImageButton)findViewById(R.id.rightBackButton2);
        lb2 = (ImageButton)findViewById(R.id.leftBackButton2);
        cb12 = (ImageButton)findViewById(R.id.centreBackOneButton2);
        cb22 = (ImageButton)findViewById(R.id.centreBackTwoButton2);


        gksurname = (TextView)findViewById(R.id.gkSurname);
        rbsurname = (TextView)findViewById(R.id.rbSurname);
        cb1surname = (TextView)findViewById(R.id.cb1Surname);
        cb2surname = (TextView)findViewById(R.id.cb2Surname);
        lbsurname = (TextView)findViewById(R.id.lbSurname);
        cm2surname = (TextView)findViewById(R.id.cm2Surname);
        cm1surname = (TextView)findViewById(R.id.cm1Surname);
        rmsurname = (TextView)findViewById(R.id.rmSurname);
        lmsurname = (TextView)findViewById(R.id.lmSurname);
        lssurname = (TextView)findViewById(R.id.lsSurname);
        rssurname = (TextView)findViewById(R.id.rsSurname);






        if(!gkId.equals("null"))
        //|| !lmId.equals("null") || !cm1Id.equals("null") || !cm2Id.equals("null")  || !lsId.equals("null") || !rsId.equals("null"))

        {
            gk.setVisibility(View.GONE);
            gksurname.setText(gkName);

            /*rb.setVisibility(View.GONE);
            cb1.setVisibility(View.GONE);
            cb2.setVisibility(View.GONE);
            lb.setVisibility(View.GONE);
            rm.setVisibility(View.GONE);
            cm1.setVisibility(View.GONE);
            cm2.setVisibility(View.GONE);
            lm.setVisibility(View.GONE);
            ls.setVisibility(View.GONE);
            rs.setVisibility(View.GONE);



            gksurname.setText(gkId);
            cb1surname.setText(cb1Id);
            cb2surname.setText(cb2Id);
            lbsurname.setText(lbId);
            rmsurname.setText(rmId);
            cm1surname.setText(cm1Id);
            cm2surname.setText(cm2Id);
            lmsurname.setText(lmId);
            lssurname.setText(lsId);
            rssurname.setText(rsId);*/




        }
         if (!rbId.equals("null")){

            rb.setVisibility(View.GONE);

            rbsurname.setText(rbName);


        }
        if (!lbId.equals("null")){

            lb.setVisibility(View.GONE);

            lbsurname.setText(lbName);

        }
        if (!cb1Id.equals("null") ){

            cb1.setVisibility(View.GONE);
            cb1surname.setText(cb1Name);
        }

        if (!cb2Id.equals("null") ){

            cb2.setVisibility(View.GONE);
            cb2surname.setText(cb2Name);
        }

        if (!lbId.equals("null")){

            lb.setVisibility(View.GONE);

            lbsurname.setText(lbName);

        }
        if (!rmId.equals("null") ){

            rm.setVisibility(View.GONE);
            rmsurname.setText(rmName);
        }

        if (!cm1Id.equals("null") ){

            cm1.setVisibility(View.GONE);
            cm1surname.setText(cm1Name);
        }

        if (!cm2Id.equals("null") ){

            cm2.setVisibility(View.GONE);
            cm2surname.setText(cm2Name);
        }


        if (!lmId.equals("null") ){

            lm.setVisibility(View.GONE);
            lmsurname.setText(lmName);
        }

        if (!lsId.equals("null") ){

            ls.setVisibility(View.GONE);
            lssurname.setText(lsName);
        }

        if (!rsId.equals("null") ){

            rs.setVisibility(View.GONE);
            rssurname.setText(rsName);
        }

    }



    //
    //get Left Striker in app
    //
    public void getLeftStriker(View view)  {

        new LoadLeftStriker().execute();


    }


    //
    //get Right Striker in app
    //
    public void getRightStriker(View view)  {

        new LoadRightStriker().execute();

    }

    //
    //get Left Mid in app
    //
    public void getLeftMid(View view)  {

        new LoadLeftMid().execute();
    }


    //
    //get Right Mid in app
    //
    public void getRightMid(View view)  {

        new LoadRightMid().execute();
    }



    //
    //get First Centre Mid in app
    //
    public void getCentreMidOne(View view)  {

        new LoadCentreMidOne().execute();
    }

    //
    //get Second Centre Mid in app
    //
    public void getCentreMidTwo(View view)  {

        new LoadCentreMidTwo().execute();
    }


    //
    //get Left Back in app
    //
    public void getLeftBack(View view)  {

        new LoadLeftBack().execute();
    }


    //
    //get right Back in app
    //
    public void getRightBack(View view)  {

        new LoadRightBack().execute();
    }


    //
    //get Centre Back One in app
    //
    public void getCentreBackOne(View view)  {

        new LoadCentreBackOne().execute();
    }

    //
    //get Centre Back Two in app
    //
    public void getCentreBackTwo(View view)  {

        new LoadCentreBackTwo().execute();
    }


    //
    //get Goalkeeper in app
    //
    public void getGoalkeeper(View view)  {

        new LoadGoalkeeper().execute();
    }













    //
    //Run php script for Left Striker
    //
    class LoadLeftStriker extends AsyncTask<Void,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/getLeftStriker.php";
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
            TextView textview = (TextView) findViewById(R.id.textview);
            textview.setText("");
            json_string = result;

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(MyTeamActivity.this, PickPlayerActivity.class);
                intent.putExtra("json_data", json_string);
                startActivityForResult(intent, 11);

            }

        }
    }


    //
    //Run php script for Right Striker
    //
    class LoadRightStriker extends AsyncTask<Void,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/getRightStriker.php";
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
            TextView textview = (TextView) findViewById(R.id.textview);
            textview.setText("");
            json_string = result;

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(MyTeamActivity.this, PickPlayerActivity.class);
                intent.putExtra("json_data", json_string);
                startActivityForResult(intent, 10);

            }

        }
    }









    //
    //Run php script for Left Mid
    //
    class LoadLeftMid extends AsyncTask<Void,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/getLeftMid.php";
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
            TextView textview = (TextView) findViewById(R.id.textview);
            textview.setText("");
            json_string = result;

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "First get JSON Data", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(MyTeamActivity.this, PickPlayerActivity.class);
                intent.putExtra("json_data", json_string);
                startActivityForResult(intent, 9);

            }

        }
    }




    //
    //Run php script for Right Mid
    //
    class LoadRightMid extends AsyncTask<Void,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/getRightMid.php";
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
            TextView textview = (TextView) findViewById(R.id.textview);
            textview.setText("");
            json_string = result;

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "First get JSON Data", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(MyTeamActivity.this, PickPlayerActivity.class);
                intent.putExtra("json_data", json_string);
                startActivityForResult(intent, 8);

            }

        }
    }



    //
    //Run php script for First Centre Mid
    //
    class LoadCentreMidOne extends AsyncTask<Void,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/getCentreMidOne.php";
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
            TextView textview = (TextView) findViewById(R.id.textview);
            textview.setText("");
            json_string = result;

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "First get JSON Data", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(MyTeamActivity.this, PickPlayerActivity.class);
                intent.putExtra("json_data", json_string);
                startActivityForResult(intent, 7);

            }

        }
    }


    //
    //Run php script for Second Centre Mid
    //
    class LoadCentreMidTwo extends AsyncTask<Void,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/getCentreMidTwo.php";
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
            TextView textview = (TextView) findViewById(R.id.textview);
            textview.setText("");
            json_string = result;

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "First get JSON Data", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(MyTeamActivity.this, PickPlayerActivity.class);
                intent.putExtra("json_data", json_string);
                startActivityForResult(intent, 6);

            }

        }
    }



    //
    //Run php script for Left Back
    //
    class LoadLeftBack extends AsyncTask<Void,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/getLeftBack.php";
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
            TextView textview = (TextView) findViewById(R.id.textview);
            textview.setText("");
            json_string = result;

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "First get JSON Data", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(MyTeamActivity.this, PickPlayerActivity.class);
                intent.putExtra("json_data", json_string);
                startActivityForResult(intent, 5);

            }

        }
    }


    //
    //Run php script for Right Back
    //
    class LoadRightBack extends AsyncTask<Void,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/getRightBack.php";
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
            TextView textview = (TextView) findViewById(R.id.textview);
            textview.setText("");
            json_string = result;

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "First get JSON Data", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(MyTeamActivity.this, PickPlayerActivity.class);
                intent.putExtra("json_data", json_string);
                startActivityForResult(intent, 4);

            }

        }
    }


    //
    //Run php script for Centre Back One
    //
    class LoadCentreBackOne extends AsyncTask<Void,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/getCentreBackOne.php";
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
            TextView textview = (TextView) findViewById(R.id.textview);
            textview.setText("");
            json_string = result;

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "First get JSON Data", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(MyTeamActivity.this, PickPlayerActivity.class);
                intent.putExtra("json_data", json_string);
                startActivityForResult(intent, 3);

            }

        }
    }


    //
    //Run php script for Second Centre Back
    //
    class LoadCentreBackTwo extends AsyncTask<Void,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/getCentreBackTwo.php";
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
            TextView textview = (TextView) findViewById(R.id.textview);
            textview.setText("");
            json_string = result;

            if(json_string==null){
                Toast.makeText(getApplicationContext(), "First get JSON Data", Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(MyTeamActivity.this, PickPlayerActivity.class);
                Bundle b = new Bundle();
                b.putString("json_data", json_string);
                intent.putExtras(b);
                startActivityForResult(intent, 2);

            }
        }
    }






    //
    //Run php script for Goalkeeper
    //
    class LoadGoalkeeper extends AsyncTask<Void,Void,String>
    {
        String JSON_STRING;
        String json_url = url+"/getGoalkeeper.php";

        @Override
        protected void onPreExecute() {}

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
            TextView textview = (TextView) findViewById(R.id.textview);
            textview.setText("");
            json_string = result;


            if(json_string==null){
                Toast.makeText(getApplicationContext(), "First get JSON Data", Toast.LENGTH_LONG).show();

            }
            else{

                Intent intent = new Intent(MyTeamActivity.this, PickPlayerActivity.class);
                Bundle b = new Bundle();
                b.putString("json_data", json_string);
                b.putString("userId", user_id);
                b.putString("posId", "gk_id");
                intent.putExtras(b);
                startActivityForResult(intent, 1);

            }


        }
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String uid = user_id;


        //GKreturn
        if(requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                Bundle b = data.getExtras();

                String gkSurname = b.getString("surname");
                String gkID = b.getString("id");
                gksurname.setText(gkSurname);
                gkId = (gkID);
                //gkId.setVisibility(View.INVISIBLE);
                gk.setVisibility(View.GONE);




                String posn = "gk_id";

                savePlayerChoice savePlayer = new savePlayerChoice();

                System.out.println(uid + uid + uid + "...." + gkId + posn);

                savePlayer.execute(uid, gkID, posn);


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                gksurname.setText("");
            }
        }



        if(requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();

                String cb1Surname = b.getString("surname");
                String cb1ID = b.getString("id");
                cb1surname.setText(cb1Surname);
                cb1Id = (cb1ID);
                cb1.setVisibility(View.GONE);


                String posn = "cb1_id";

                savePlayerChoice savePlayer = new savePlayerChoice();



                savePlayer.execute(uid, cb1ID, posn);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                cb2surname.setText("");
            }


        }

        if(requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();

                String cb2Surname = b.getString("surname");
                String cb2ID = b.getString("id");
                cb2surname.setText(cb2Surname);
                cb2Id = (cb2ID);
                cb2.setVisibility(View.GONE);

                String posn = "cb2_id";

                savePlayerChoice savePlayer = new savePlayerChoice();



                savePlayer.execute(uid, cb2ID, posn);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                cb2surname.setText("");
            }
        }


        if(requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();

                String rbSurname = b.getString("surname");
                String rbID = b.getString("id");
                rbsurname.setText(rbSurname);
                rbId = (rbID);
                rb.setVisibility(View.GONE);



                String posn = "rb_id";
                savePlayerChoice savePlayer = new savePlayerChoice();
                savePlayer.execute(uid, rbID, posn);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                rbsurname.setText("");
            }
        }

        if(requestCode == 5) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();

                String lbSurname = b.getString("surname");
                String lbID = b.getString("id");
                lbsurname.setText(lbSurname);
                lbId = (lbID);
                lb.setVisibility(View.GONE);


                String posn = "lb_id";
                savePlayerChoice savePlayer = new savePlayerChoice();
                savePlayer.execute(uid, lbID, posn);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                lbsurname.setText("");
            }
        }

        if(requestCode == 6) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();

                String cm2Surname = b.getString("surname");
                String cm2ID = b.getString("id");
                cm2surname.setText(cm2Surname);
                cm2Id = (cm2ID);
                cm2.setVisibility(View.GONE);

                String posn = "cm2_id";
                savePlayerChoice savePlayer = new savePlayerChoice();
                savePlayer.execute(uid, cm2ID, posn);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                cm2surname.setText("");
            }
        }

        if(requestCode == 7) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();

                String cm1Surname = b.getString("surname");
                String cm1ID = b.getString("id");
                cm1surname.setText(cm1Surname);
                cm1Id = (cm1ID);
                cm1.setVisibility(View.GONE);

                String posn = "cm1_id";
                savePlayerChoice savePlayer = new savePlayerChoice();
                savePlayer.execute(uid, cm1ID, posn);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                cm1surname.setText("");
            }
        }


        if(requestCode == 8) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();

                String rmSurname = b.getString("surname");
                String rmID = b.getString("id");
                rmsurname.setText(rmSurname);
                rmId = (rmID);
                rm.setVisibility(View.GONE);

                String posn = "rm_id";
                savePlayerChoice savePlayer = new savePlayerChoice();
                savePlayer.execute(uid, rmID, posn);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                rmsurname.setText("");
            }
        }

        if(requestCode == 9) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();

                String lmSurname = b.getString("surname");
                String lmID = b.getString("id");
                lmsurname.setText(lmSurname);
                lmId = (lmID);
                lm.setVisibility(View.GONE);

                String posn = "lm_id";
                savePlayerChoice savePlayer = new savePlayerChoice();
                savePlayer.execute(uid, lmID, posn);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                lmsurname.setText("");
            }
        }

        if(requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();

                String rsSurname = b.getString("surname");
                String rsID = b.getString("id");
                rssurname.setText(rsSurname);
                rsId = (rsID);
                rs.setVisibility(View.GONE);

                String posn = "rs_id";
                savePlayerChoice savePlayer = new savePlayerChoice();
                savePlayer.execute(uid, rsID, posn);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                rssurname.setText("");
            }
        }

        if(requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();

                String lsSurname = b.getString("surname");
                String lsID = b.getString("id");
                lssurname.setText(lsSurname);
                lsId = (lsID);
                ls.setVisibility(View.GONE);

                String posn = "ls_id";
                savePlayerChoice savePlayer = new savePlayerChoice();
                savePlayer.execute(uid, lsID, posn);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                lssurname.setText("");
            }
        }
    }





    //
    //display in listview
    //
    public void parseJSON(View view)
    {

        if(json_string==null){
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

        }
        else{
            Intent intent = new Intent(this, PickPlayerActivity.class);
            intent.putExtra("json_data", json_string);
            startActivity(intent);

        }


    }



































    //
    //Run php script for saving gk to db
    //gkAsync
    class savePlayerChoice extends AsyncTask<String,Void,String>
    {

        String save_url = url+"/saveGkPickToDb.php?uid=";

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... params){

            try{

                String userid = (params[0]);
                String playerid = (params[1]);
                String posn = (params[2]);
                URL url = new URL(save_url+userid+"&pid="+playerid+"&position="+posn);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();



                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return null;

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

        }
    }






    //This section is where the code will be placed
    //that will run php scripts for the details of
    //the players selected, and also where the OnClick
    // methods will be to send to player details screen



    public void showGkDetails(View view){



        new runGkDetails().execute(gkId);


    }


    class runGkDetails extends AsyncTask<String,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/gkDetails.php";
        }

        @Override
        protected String doInBackground(String ... params){

            try{


                String id = params[0];
                URL url = new URL(json_url+"?id="+id);
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
                Intent intent = new Intent(MyTeamActivity.this, playerDetails.class);
                intent.putExtra("json_data", json_string);
                startActivity(intent);

            }


        }
    }



    public void showRbDetails(View view){



        new runPlayerDetails().execute(rbId);


    }

    public void showCb2Details(View view){



        new runPlayerDetails().execute(cb2Id);


    }

    public void showCb1Details(View view){



        new runPlayerDetails().execute(cb1Id);


    }

    public void showLbDetails(View view){



        new runPlayerDetails().execute(lbId);


    }

    public void showCm2Details(View view){



        new runPlayerDetails().execute(cm2Id);


    }

    public void showCm1Details(View view){



        new runPlayerDetails().execute(cm1Id);


    }

    public void showLmDetails(View view){



        new runPlayerDetails().execute(lmId);


    }

    public void showRmDetails(View view){



        new runPlayerDetails().execute(rmId);


    }

    public void showLsDetails(View view){



        new runPlayerDetails().execute(lsId);


    }

    public void showRsDetails(View view){



        new runPlayerDetails().execute(rsId);


    }


    class runPlayerDetails extends AsyncTask<String,Void,String>
    {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {

            json_url = url+"/gkDetails.php";
        }

        @Override
        protected String doInBackground(String ... params){

            try{


                String id = params[0];
                URL url = new URL(json_url+"?id="+id);
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
                Intent intent = new Intent(MyTeamActivity.this, playerDetails.class);
                intent.putExtra("json_data", json_string);
                startActivity(intent);

            }


        }
    }

}
