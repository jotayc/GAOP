package ph.edu.dlsu.cmt;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.HOGDescriptor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import com.jc.recognition.*;

import ugr.lsi.ui.Model.Art;
import ugr.lsi.ui.Model.SectionArt;

public class CameraActivity extends AppCompatActivity implements
        CvCameraViewListener2,
        View.OnClickListener, TextToSpeech.OnInitListener{
    private static final String TAG = "CameraActivity";

    private static final int VIEW_MODE_FEATURES = 3;
    private static final int START_TLD = 2;
    private static final int START_RECOGNITION = 1;


    static final int WIDTH  = 400;
    static final int HEIGHT = 240;

    static final boolean ACTIVATE_VISIBILITY = true;

    private int _canvasImgYOffset;
    private int _canvasImgXOffset;

    static boolean uno = true;

    private int mViewMode;
    private Mat mRgba;
    private Mat mIntermediateMat;
    private Mat mGray;


    private Art artItem;
    private ArrayList<TextView> textViews;
    private FrameLayout mFrameView;
    private FrameLayout.LayoutParams lParamsT;



    private OpenCvCameraView mOpenCvCameraView;
    private ImageDetectionFilter mDetectionFilter;
    SurfaceHolder _holder;

    private Rect _trackedBox = null;
    private Mat mSceneCorners = null;

    HOGDescriptor mHog;

    CascadeClassifier mJavaDetector;

    private TextToSpeech textToSpeech;


    //Callback que tiene como misión cargar la biblioteca de openCV
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");

                    // Load native library after(!) OpenCV initialization
                    System.loadLibrary("opencv_native_module");

                    //Congiguraciones previas para mostrar los FPS
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.enableFpsMeter();
                    try {

                        //Preparamos el filtro con la imagen que debe detectar
                        mDetectionFilter = new ImageDetectionFilter(
                                    CameraActivity.this,
                                    artItem.getArtId());


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public CameraActivity() {
        Log.i(TAG, "TAG: Instantiated new " + this.getClass());

    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "TAG: called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.opencv_surface_view);

        //Declaración e inicialización de componentes
        mFrameView = (FrameLayout) findViewById(R.id.frameLy);
        textViews = new ArrayList<>();

        textToSpeech = new TextToSpeech(this, this);

        //Se declara el objeto para control de la camara
        mOpenCvCameraView = (OpenCvCameraView) findViewById(R.id.camera_activity_surface_view);

        //Se asigna un listener para que esté pendiente de los eventos
        mOpenCvCameraView.setCvCameraViewListener(this);

        //Asignamos que se empieza con el reconocimiento para saber la ROI
        mViewMode = START_RECOGNITION;

        //Comprobamos si el dispositivo tiene camara
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int cameraId = 0;
        int camerasCount = Camera.getNumberOfCameras();
        for ( int camIndex = 0; camIndex < camerasCount; camIndex++ ) {
            Camera.getCameraInfo(camIndex, cameraInfo );
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK  ) {
                cameraId = camIndex;
                break;
            }
        }

        // Asignamos la cámara trasera
        mOpenCvCameraView.setCameraIndex(cameraId);

        //Cuando pulsa una obra recoges la posicion de la obra en la lista (ArtList)
        int posArtItem = getIntent().getIntExtra(Data.INFO_ID, -1);


        if(posArtItem != -1) {

            artItem = Data.artList.get(posArtItem);

            //Se prepara la interfaz con el número de componentes que se debe
            // superponer en la camara en tiempo real
            for (SectionArt sec: artItem.getSectionArts()) {

                TextView txtView = new TextView(CameraActivity.this);
                txtView.setText(sec.getComment());
                txtView.setBackgroundColor(getResources().getColor(android.R.color.white));
                txtView.setBackground(getResources().getDrawable(R.drawable.bubble_mute));
                txtView.setLayoutParams(sec.getLayoutParams());
                txtView.setMaxLines(2);
                txtView.measure(0,0);

                txtView.setId(sec.getSectID());
                txtView.setOnClickListener(this);
                mFrameView.addView(txtView);
                textViews.add(txtView);

                Log.i("TAG", "ImageViewID: "+txtView.getId());
                txtView.setOnClickListener(this);
            }

        }else{
            Toast.makeText(this, "Error al cargar la imagen!", Toast.LENGTH_SHORT).show();
        }



    }


    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }


    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
        if(textToSpeech.isSpeaking())
            textToSpeech.stop();
    }

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mIntermediateMat = new Mat(height, width, CvType.CV_8UC4);
        mGray = new Mat(height, width, CvType.CV_8UC1);
    }

    public void onCameraViewStopped() {
        mRgba.release();
        mGray.release();
        mIntermediateMat.release();
    }


    /**
     * Método encargado de recibir cada frame procedente de la captura en tiempo real antes de ser
     * mostrado
     * @param inputFrame: Frame recibido
     * @return matriz del frame que debe mostrar al usuario
     */
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        Log.i(TAG, "TAG: on camera frame here");
        final int viewMode = mViewMode;

        //En función de un caso u otro se activará el modo de reconocimiento y o el modo detección
        switch (viewMode) {
            case START_RECOGNITION:
                Log.i(TAG,"TAG: case START_RECOGNITION");
                mRgba = inputFrame.rgba();

                if(mSceneCorners != null){


                    _trackedBox = new Rect(new Point(mSceneCorners.get(0,0)),  new Point(mSceneCorners.get(2,0)));

                    mViewMode = START_TLD;
                }else {
                    mSceneCorners = mDetectionFilter.apply2(mRgba);
                }

                break;
            case START_TLD: {
                Log.i(TAG,"TAG: case START_TLD");
                mRgba = inputFrame.rgba();
                mGray = Reduce(inputFrame.gray());
                double w = mGray.width();
                double h = mGray.height();
                if (_trackedBox == null) {
                    Log.i(TAG,"TAG: if_1");
                    OpenTLD(mGray.getNativeObjAddr(), mRgba.getNativeObjAddr(),
                            (long) (w / 2 - w / 4), (long) (h / 2 - h / 4),
                            (long) w / 2, (long) h / 2);
                }else {
                    Log.i(TAG,"TAG: else_1");
                    Log.i("TAG", "TAG: TLD START DEFINED: " + _trackedBox.x / 2 + " "
                            + _trackedBox.y / 2 + " " + _trackedBox.width / 2 + " "
                            + _trackedBox.height / 2);

                    double px = (w) / (double) (mOpenCvCameraView.getWidth());
                    double py = (h) / (double) (mOpenCvCameraView.getHeight());
                    //
                    OpenTLD(mGray.getNativeObjAddr(), mRgba.getNativeObjAddr(),
                            (long) (_trackedBox.x * px),
                            (long) (_trackedBox.y * py),
                            (long) (_trackedBox.width * px),
                            (long) (_trackedBox.height * py));
                }
                uno = false;
                mViewMode = VIEW_MODE_FEATURES;
            }
            break;
            case VIEW_MODE_FEATURES:
                Log.i(TAG,"TAG: case VIEW_MODE_FEATURES");
                // input frame has RGBA format
                mRgba = inputFrame.rgba();
                mGray = inputFrame.gray();
                mGray = Reduce(mGray);

                Mat mRgba2 = ReduceColor(mRgba);

                // FindFeatures(mGray.getNativeObjAddr(), mRgba.getNativeObjAddr());
                if (uno) {
                    Log.i(TAG,"TAG: if_2");
                    int w = mGray.width();
                    int h = mGray.height();
                    OpenTLD(mGray.getNativeObjAddr(), mRgba.getNativeObjAddr(),
                            (long) w - w / 4, (long) h / 2 - h / 4, (long) w / 2,
                            (long) h / 2);
                    uno = false;

                    /*if((mFrameView.getVisibility() == View.VISIBLE) && ACTIVATE_VISIBILITY) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mFrameView.setVisibility(View.INVISIBLE);
                            }
                        });
                    }*/
                } else {
                    Log.i(TAG,"TAG: else_2");
                    ProcessTLD(mGray.getNativeObjAddr(), mRgba2.getNativeObjAddr());
                    double px = (double) mRgba.width() / (double) mRgba2.width();
                    double py = (double) mRgba.height() / (double) mRgba2.height();
                    int[] l = getRect();
                    if (l != null) {
                        Log.i(TAG,"TAG: if_3");
                        Rect r = new Rect();
                        r.x = (int) (l[0] * px);
                        r.y = (int) (l[1] * py);
                        r.width = (int) (l[2] * px);
                        r.height = (int) (l[3] * py);
                        Log.i(TAG,"TAG: TLD box defined at x: " + r.x + ", y: " + r.y + " with width = "
                         + r.width + " and height = " + r.height);
                        Imgproc.rectangle(mRgba, r.tl(), r.br(), new Scalar(0, 0, 255,
                                0), 5);

                        createAR(r.tl(),r.width, r.height);
                        if((mFrameView.getVisibility() == View.INVISIBLE) && ACTIVATE_VISIBILITY) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mFrameView.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    }else{
                        if((mFrameView.getVisibility() == View.VISIBLE) && ACTIVATE_VISIBILITY ) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mFrameView.setVisibility(View.INVISIBLE);
                                    textToSpeech.stop();

                                }
                            });
                        }
                    }
                    uno = false;


                    // Mat mTemp=mRgba;

                    // mRgba=UnReduceColor(mRgba2,mTemp.width(),mTemp.height());
                    // mTemp.release();
                }
                // mRgba2.release();
                // mGray.release();
                break;
        }
        return mRgba;
    }




    //********************************************//
    //************ CUSTOM METHODS ****************//
    //********************************************//

    Point pointIcon;
    FrameLayout.LayoutParams layoutParams;
    TextView txtVw;
    public void createAR(Point point00, int width, int height){

        /* TITLE DETECTION */

        int textHeigh, textWidth;
        for (int i = 0; i < artItem.getSectionArts().size(); i++){

            SectionArt sc1 = artItem.getSectionArtAt(i);
            pointIcon = sc1.getPerCenterPoint().percToPixelFromPoint(point00,width,height);
            txtVw = textViews.get(i);
            textHeigh = txtVw.getMeasuredHeight();
            textWidth = txtVw.getMeasuredWidth() / 2;

            layoutParams = (FrameLayout.LayoutParams)txtVw.getLayoutParams();
            layoutParams.leftMargin =  (int) pointIcon.x;
            layoutParams.topMargin  =  (int) pointIcon.y - textHeigh;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtVw.setLayoutParams(layoutParams);
                    mFrameView.invalidate();
                }
            });

        }

    }

    public Point getSubPoint(Point pt00, double pWidth, double pHeight, int perWidth, int perHeight){
        return new Point( pt00.x + perDif(pWidth,perWidth), pt00.y + perDif(pHeight,perHeight));
    }

    public double perDif(double num, int perc){
        return (num*perc)/100;
    }

    public Point getCenter(Point pt00, double pHeight, double pWidth){

        return new Point(pt00.x + (pWidth / 2), pt00.y + (pHeight / 2));
    }

    @Override
    public void onClick(View v) {

        v.bringToFront();
        if(textToSpeech.isSpeaking()){
            textToSpeech.stop();
            SectionArt sec;
            for(int i = 0; i<artItem.getSectionArts().size(); i++){
                sec = artItem.getSectionArts().get(i);
                if(sec.isSound()){
                    textViews.get(i).setBackground(getResources().getDrawable(R.drawable.bubble_mute));
                    sec.setSound(false);
                }

            }

        }


        int pos = v.getId();
        SectionArt sectionArt = artItem.getSectionArtAt(pos);
        sectionArt.setSound(true);
        textViews.get(pos).setBackground(getResources().getDrawable(R.drawable.bubble_volume));
        textToSpeech.speak(sectionArt.getComment(), TextToSpeech.QUEUE_FLUSH,null,null);


    }

    @Override
    public void onInit(int status) {
        Locale locSpa = new Locale("es", "ES");
        textToSpeech.setLanguage(locSpa);

    }


    public void log(String msg){
        Log.i("Detection", msg);
    }

    //********************************************//
    //******************* END ********************//
    //********************************************//

    private void LoadTLD(String Path) {
        TLDLoad( Path);
    }

    private void SaveTLD(String Path) {
        TLDSave( Path);
    }

    Mat Reduce(Mat m) {
        // return m;
        Mat dst = new Mat();
        Imgproc.resize(m, dst, new org.opencv.core.Size(WIDTH, HEIGHT));
        return dst;
    }

    Mat ReduceColor(Mat m) {
        Mat dst = new Mat();
        Bitmap bmp = Bitmap.createBitmap(m.width(), m.height(),
                Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(m, bmp);
        Bitmap bmp2 = Bitmap.createScaledBitmap(bmp, WIDTH, HEIGHT, false);
        Utils.bitmapToMat(bmp2, dst);
        return dst;
    }

    Mat UnReduceColor(Mat m, int w, int h) {
        // return m;

        Mat dst = new Mat();
        Bitmap bmp = Bitmap.createBitmap(m.width(), m.height(),
                Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(m, bmp);
        Bitmap bmp2 = Bitmap.createScaledBitmap(bmp, w, h, false);
        Utils.bitmapToMat(bmp2, dst);
        m.release();
        return dst;
    }


    boolean load_cascade()
    {
        try
        {
            // load cascade file from application resources
            InputStream is = getResources().openRawResource(R.raw.haarcascade_upperbody);
            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "haarcascade_lowerbody.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();
            mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            if (mJavaDetector.empty()) return false;

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
        }
        return true;
    }

    public native void FindFeatures(long matAddrGr, long matAddrRgba);

    public native void OpenTLD(long matAddrGr, long matAddrRgba, long x,
                               long y, long w, long h);

    public native void ProcessTLD(long matAddrGr, long matAddrRgba);

    private static native int[] getRect();

    public native void OpenCMT(long matAddrGr, long matAddrRgba, long x,
                               long y, long w, long h);

    public native void ProcessCMT(long matAddrGr, long matAddrRgba);

    public native void TLDLoad(String Path);

    public native void TLDSave(java.lang.String Path);

    public native void CMTSave(java.lang.String Path);
    public native void CMTLoad(java.lang.String Path);


    private static native int[] CMTgetRect();



}