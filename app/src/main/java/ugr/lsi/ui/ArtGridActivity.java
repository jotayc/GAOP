package ugr.lsi.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import ph.edu.dlsu.cmt.CameraActivity;
import ph.edu.dlsu.cmt.Data;
import ph.edu.dlsu.cmt.R;
import ugr.lsi.ui.Adapters.ArtAdapter;
import ugr.lsi.ui.Model.Art;

public class ArtGridActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<Art> artList;
    private FloatingActionButton floatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_grid);

        gridView = (GridView) findViewById(R.id.gridVw);
        artList  = (ArrayList<Art>) Data.artList;
        floatBtn = (FloatingActionButton) findViewById(R.id.floatBtn);

        ArtAdapter adapter = new ArtAdapter(this, (ArrayList) Data.artList);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ArtGridActivity.this, CameraActivity.class);
                i.putExtra(Data.INFO_ID, position);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);

                //Toast.makeText(ArtGridActivity.this, "Pulsada obra: "+ Data.artList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ArtGridActivity.this, QrActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
            }
        });

    }



    public void showLog(String msg){
        Log.i("GAOP", msg);
    }
}
