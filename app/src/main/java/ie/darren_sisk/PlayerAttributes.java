package ie.darren_sisk.fantasyfootballdraft;


        import android.app.Activity;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.SeekBar;
        import android.widget.TextView;

        import com.numetriclabz.numandroidcharts.ChartData;
        import com.numetriclabz.numandroidcharts.RadarChart;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;
public class PlayerAttributes extends AppCompatActivity {



    RadarChart radarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_attributes);


        radarChart = (RadarChart) findViewById(R.id.radar_chart);

        List<ChartData> values = new ArrayList<>();

        ArrayList<String> label = new ArrayList();
        label.add("Diving");
        label.add("Handling");
        label.add("Kicking");
        label.add("Reflexes");
        label.add("Speed");
        label.add("Positioning");

        ArrayList<Float> entries = new ArrayList<>();
        entries.add(4.4f);
        entries.add(4.25f);
        entries.add(4.35f);
        entries.add(4.5f);
        entries.add(2.8f);
        entries.add(4.4f);



        try {
            JSONObject dataSet = new JSONObject();
            dataSet.put("labels", label.toString());

            JSONObject val = new JSONObject();
            val.put("De Gea", entries.toString());

            dataSet.put("values", val.toString());

            values.add(new ChartData(dataSet));
            radarChart.setData(values);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    };
}