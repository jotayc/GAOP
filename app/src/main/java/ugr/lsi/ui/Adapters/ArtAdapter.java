package ugr.lsi.ui.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import ph.edu.dlsu.cmt.R;
import ugr.lsi.ui.Model.Art;

/**
 * Created by JotaC on 17/11/17.
 */

public class ArtAdapter extends BaseAdapter{

    private ArrayList<Art> list;
    private Context context;


    public ArtAdapter(Context context,ArrayList<Art> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.image_item,parent,false);

        }

        ImageView image = (ImageView) convertView.findViewById(R.id.imageGr_item);

        Art art = list.get(position);
        image.setImageResource(art.getArtId());


        return convertView;

    }
}
