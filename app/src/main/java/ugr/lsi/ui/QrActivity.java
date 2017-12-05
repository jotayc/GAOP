package ugr.lsi.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import ph.edu.dlsu.cmt.CameraActivity;
import ph.edu.dlsu.cmt.Data;
import ph.edu.dlsu.cmt.R;
import ugr.lsi.ui.Model.Art;

public class QrActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener{


    private QRCodeReaderView qrObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_qr);

        qrObj = (QRCodeReaderView) findViewById(R.id.qrReader);

        qrObj.setQRDecodingEnabled(true);
        qrObj.setBackCamera();

        qrObj.setOnQRCodeReadListener(this);
        qrObj.setAutofocusInterval(2000L);

    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        qrObj.setQRDecodingEnabled(false);
        try {
            int id = Integer.parseInt(text);
            int pos = getPositionById(id);
            if(pos != -1) {

                Intent i = new Intent(QrActivity.this, CameraActivity.class);
                i.putExtra(Data.INFO_ID, pos);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
            }else{
                Toast.makeText(this, "QR no válido", Toast.LENGTH_SHORT).show();
            }

        }catch (NumberFormatException e){

            Toast.makeText(this, "QR no válido", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrObj.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrObj.startCamera();
    }


    private int getPositionById(int id){

        for(int i=0; i<Data.artList.size(); i++){
            if(Data.artList.get(i).getArtId() == id){
                return i;
            }
        }

        return -1;

    }
}
