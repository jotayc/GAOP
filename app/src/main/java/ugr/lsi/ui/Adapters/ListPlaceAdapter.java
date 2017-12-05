package ugr.lsi.ui.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import ugr.lsi.ui.Model.Exposition;
import ugr.lsi.ui.Model.Place;

/**
 * Created by JotaC on 18/11/17.
 */

public class ListPlaceAdapter extends BaseAdapter {

    private ArrayList<Place> list;
    private Context context;

    public ListPlaceAdapter(ArrayList<Place> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}



