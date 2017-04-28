package ie.darren_sisk.fantasyfootballdraft;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class playerStats extends AppCompatActivity {

    String url = "http://192.168.8.101";
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    TextView id_tx, win_tx, lose_tx, draw_tx;

    PlayerAdapter playerAdapter;
    ListView listview;
    Button button;
    private String TAG = "playerDetails";


    private int[] yData;
    private String[] xData = {"Win", "Draw", "Lose"};
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_stats);

        Log.d(TAG, "onCreate: Starting to create chart");




        json_string = getIntent().getExtras().getString("json_data");

        id_tx = (TextView)findViewById(R.id.id);
        win_tx = (TextView)findViewById(R.id.win);
        draw_tx = (TextView)findViewById(R.id.draw);
        lose_tx = (TextView)findViewById(R.id.lose);






        try {
        jsonObject = new JSONObject(json_string);
        jsonArray = jsonObject.getJSONArray("php_response-player_details");
        int count = 0;

        String win, draw, lose, id;

        while (count < jsonArray.length()) {

            JSONObject jo = jsonArray.getJSONObject(count);

            id = jo.getString("id");
            win = jo.getString("win");
            draw = jo.getString("draw");
            lose = jo.getString("lose");

            id_tx.setText(id);
            win_tx.setText(win);
            draw_tx.setText(draw);
            lose_tx.setText(lose);


            int w = Integer.parseInt(win_tx.getText().toString());
            int d = Integer.parseInt(draw_tx.getText().toString());
            int l = Integer.parseInt(lose_tx.getText().toString());
            yData = new int[] {w, d, l};
            count++;


        }


    } catch (JSONException e) {
        e.printStackTrace();
    }

        pieChart = (PieChart) findViewById(R.id.idPieChart);

        //pieChart.setDescription("Sales by employee (In Thousands $) ");
        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Win Ratio");
        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
//More options just check out the documentation!


        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());

                int pos1 = e.toString().indexOf("y: ");
                String sales = e.toString().substring(pos1 + 3);

                for(int i = 0; i <= yData.length; i++){
                    if(yData[i] == (Float.parseFloat(sales))){
                        pos1 = i;
                        break;
                    }
                }
                String result = xData[pos1];
                Toast.makeText(playerStats.this, result + ": "+  Math.round(Float.parseFloat(sales)), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet Started ");


        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }


        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Player Win Ratio");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend l = pieChart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }


}


