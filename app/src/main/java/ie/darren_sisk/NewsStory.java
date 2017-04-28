package ie.darren_sisk.fantasyfootballdraft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsStory extends AppCompatActivity {

    TextView story, headline, date, user_id;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_story);

        headline = (TextView)findViewById(R.id.headline_tv);
        user_id = (TextView)findViewById(R.id.user_id);

        Bundle b = getIntent().getExtras();
        user_id.setText(b.getString("userid"));
        //posid.setText(b.getString("posId"));

        json_string = getIntent().getExtras().getString("json_data");
        //story.setText("hello");

        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String rank = "1";


            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);


                headline.setText(jo.getString("story"));
                //System.out.println(jo.getString("story"));


                count++;



            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Bundle bundle = getIntent().getExtras();

        //String s = bundle.getString("story");
        //story.setText(s);

    }
}
