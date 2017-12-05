package ugr.lsi.ui.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import ugr.lsi.ui.Model.Exposition;

/**
 * Created by JotaC on 18/11/17.
 */

public class ListExpoAdapter extends BaseAdapter {

    private ArrayList<Exposition> list;
    private Context context;

    public ListExpoAdapter(ArrayList<Exposition> list, Context context) {
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
