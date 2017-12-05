package ph.edu.dlsu.cmt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;



public class DetailActivity extends Activity {

    private ImageView mImageView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageView  =   (ImageView) findViewById(R.id.detImgVw);
        mTextView   =   (TextView)  findViewById(R.id.detTxtVw);

        Intent i = getIntent();

        switch (i.getIntExtra(Data.INFO_ID,Data.TITLE_CODE)){

            case Data.EYE_CODE:
                mImageView.setImageResource(R.drawable.big_brother);
                mTextView.setText(Data.info_eye);
                break;
            case Data.TITLE_CODE:
                mImageView.setImageResource(R.drawable.title);
                mTextView.setText(Data.info_title);
                break;
        }


    }
}

