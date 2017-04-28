package ie.darren_sisk.fantasyfootballdraft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class UsersWeeklyPoints extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    TextView userid;
    String uid, json_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_weekly_points);

        userid = (TextView)findViewById(R.id.uid);

        Bundle b = getIntent().getExtras();
        userid.setText(b.getString("userid"));
        //posid.setText(b.getString("posId"));

        json_string = getIntent().getExtras().getString("json_data");


        double yy [] = {65, 66 , 78 , 91, 50};
        double y, x;
        x = 0;

        int pos = 0;

        GraphView graph = (GraphView)findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i<5; i++){
            x = x + 1;
            y= yy[i];
            series.appendData(new DataPoint(x,y), true , 5);

        }

        graph.addSeries(series);
    }

}


