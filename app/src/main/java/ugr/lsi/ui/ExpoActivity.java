package ugr.lsi.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import ph.edu.dlsu.cmt.Data;
import ph.edu.dlsu.cmt.R;
import ugr.lsi.ui.Model.Exposition;

public class ExpoActivity extends AppCompatActivity {


    private ArrayList<Exposition> expositions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        expositions = Data.expoList;

    }
}
