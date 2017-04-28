package ie.darren_sisk.fantasyfootballdraft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class PlayerGrowth extends AppCompatActivity {


    LineGraphSeries<DataPoint> series;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_growth);


        double yy [] = {72, 73, 79 , 82, 86, 87, 89, 90, 89};
        double y, x;
        x = 11;

        int pos = 0;

        GraphView graph = (GraphView)findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i<9; i++){
            x = x + 1;
            y= yy[i];
            series.appendData(new DataPoint(x,y), true , 9);

        }

        graph.addSeries(series);
    }

}

