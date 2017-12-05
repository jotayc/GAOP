package ugr.lsi.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import ph.edu.dlsu.cmt.Data;
import ph.edu.dlsu.cmt.R;
import ugr.lsi.ui.Model.Place;

public class PlaceListActivity extends AppCompatActivity {


    private ArrayList<Place> places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        places = Data.placeList;



    }
}
