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

public class AdminWeeklyMaintenance extends AppCompatActivity {

    TextView week;
    Button update, back;
    String week_num;
    String url = "http://192.168.8.101/adminUpdateWeeklyPointsTable.php?week_num=";
    String json_string;
    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_weekly_maintenance);


        Intent i = getIntent();
        String o = i.getStringExtra("week");


       week = (TextView)findViewById(R.id.wk_number);
        week.setText(o);

        update = (Button) findViewById(R.id.update_weekly_points_bn);
        back = (Button)findViewById(R.id.back_bn);
        //week = (TextView) findViewById(R.id.wk_number);

        week_num ="week"+week.getText();

        builder = new AlertDialog.Builder(AdminWeeklyMaintenance.this);

        String wk = "week";
        String num = week.getText().toString();

        final String w = wk+num;

        int foo = Integer.parseInt(o);

        if(foo<0||foo>38){

            update.setVisibility(View.GONE);
        }


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new updateWeeklyColumn().execute(w);

            }
        });



    }


    class updateWeeklyColumn extends AsyncTask<String,Void,String>
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

                final String week = params[0];


                URL url = new URL(json_url+week);
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
                Intent intent = new Intent(AdminWeeklyMaintenance.this, AdminHome.class);

                intent.putExtra("code", 2);


                startActivity(intent);

            }

        }
    }
}
