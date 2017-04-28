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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PickPlayerActivity extends AppCompatActivity {

    String url = "http://192.168.8.101";
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    PlayerAdapter playerAdapter;
    ListView listview;
    Button button;
    String firstname, surname, id;
    TextView uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_player);
        listview = (ListView) findViewById(R.id.listview);
        listview.setClickable(true);
        playerAdapter = new PlayerAdapter(this, R.layout.row_layout);



        //button = (Button) findViewById(R.id.playerDetails);

        listview.setAdapter(playerAdapter);
        listview.setClickable(true);


        uid = (TextView)findViewById(R.id.uid);
        TextView posid = (TextView)findViewById(R.id.pos_id);

        Bundle b = getIntent().getExtras();
        uid.setText(b.getString("userId"));
        posid.setText(b.getString("posId"));

        json_string = getIntent().getExtras().getString("json_data");





        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;


            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);

                firstname = jo.getString("firstname");
                surname = jo.getString("surname");
                id = jo.getString("player_id");


                Players players = new Players(firstname, surname, id);

                playerAdapter.add(players);
                count++;


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i , long l){

                Intent returnIntent = new Intent();
                String selected =((TextView)view.findViewById(R.id.tx_lastname)).getText().toString();
                String selectedId =((TextView)view.findViewById(R.id.tx_player_id)).getText().toString();

                Bundle b = new Bundle();
                b.putString("surname", selected);
                b.putString("id", selectedId);

                returnIntent.putExtras(b);
                setResult(Activity.RESULT_OK,returnIntent);


            finish();

            }


        });

    }


}
