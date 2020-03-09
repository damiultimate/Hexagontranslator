package com.hexagon.translator;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BitmapCompat;

public class Image extends AppCompatActivity implements TessBaseAPI.ProgressNotifier{
    Bitmap thumbnail=null;
    boolean completed=false;
    String text="";
    int percentage=0;
    Thread thread;
    Snackbar snackbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent intent = getIntent();
        ImageView image = findViewById(R.id.image);
        Uri uri = intent.getData();
        image.setImageURI(uri);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.show();
            }
        });
findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        snackbar.show();
    }
});

findViewById(R.id.languages).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder=new AlertDialog.Builder(Image.this);
        builder.setMessage("Note: You can only get text from Latin based languages at the moment e.g English,German,Yoruba...etc, more languages are coming soon");
        LayoutInflater layoutInflater = getLayoutInflater();
        final View customAlertview = layoutInflater.inflate(R.layout.languages, null);
        builder.setView(customAlertview);
        CheckBox checkbox=customAlertview.findViewById(R.id.checkbox);
        checkbox.setChecked(false);

        String returnu="";
        try {
            InputStream inputStream = new FileInputStream(new File(getExternalFilesDir(null)+"/checkbox.hxg"));
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receive = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receive = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receive);
                }
                inputStream.close();

                if(stringBuilder.toString().matches("true")){
                    checkbox.setChecked(true);
                }else{
                    checkbox.setChecked(false);
                }
            }
        }catch(FileNotFoundException e){
            checkbox.setChecked(false);
        }
        catch (Exception e){
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try{
                    OutputStreamWriter outputStreamWriter=new OutputStreamWriter(new FileOutputStream(new File(getExternalFilesDir(null)+"","checkbox.hxg")));
                    outputStreamWriter.write(checkbox.isChecked()+"");
                    outputStreamWriter.close();
                }catch (IOException e){

                }
            }
        });
        builder.show();

    }
});


        try {
            InputStream stream = getContentResolver().openInputStream(intent.getData());
             thumbnail = BitmapFactory.decodeStream(stream);
            stream.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

       }

        FrameLayout parent=findViewById(R.id.parent);

        snackbar=Snackbar.make(parent,"The image have been Selected Successfully, ",Snackbar.LENGTH_INDEFINITE).setAction("Get Text", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Image.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                final View customAlertview = layoutInflater.inflate(R.layout.loading, null);
                TextView title=customAlertview.findViewById(R.id.title);
                title.setText("Loading...");

               thread =  new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap=thumbnail;
                        try {
                            InputStream inputStream = new FileInputStream(new File(getExternalFilesDir(null)+"/checkbox.hxg"));
                            if (inputStream != null) {
                                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                String receive = "";
                                StringBuilder stringBuilder = new StringBuilder();
                                while ((receive = bufferedReader.readLine()) != null) {
                                    stringBuilder.append(receive);
                                }
                                inputStream.close();

                                if(stringBuilder.toString().matches("true")){
                                   bitmap= new BitmapEditor().GetBitmap(thumbnail,thumbnail.getWidth()*1,thumbnail.getHeight()*1);
                                }else{

                                }
                            }
                        }catch(FileNotFoundException e){

                        }
                        catch (Exception e){

                        }


                        TessBaseAPI tessBaseAPI=new TessBaseAPI();
                        tessBaseAPI.init(Environment.getExternalStorageDirectory()+"/.Hexagon/","eng+equ");
                        tessBaseAPI.setImage(bitmap);
                        text=tessBaseAPI.getUTF8Text();
                    }
                });
                thread.start();
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent1=new Intent(Image.this,MainActivity.class);
                        intent1.setAction("**"+text);
                        finish();
                        startActivity(intent1);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                Handler handler= new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(text !=""){
                            title.setText("Process Completed");
                            EditText result=customAlertview.findViewById(R.id.result);
                            result.setText(text);
                            result.setVisibility(View.VISIBLE);
                            customAlertview.findViewById(R.id.progressBar_cyclic).setVisibility(View.GONE);

                        }else{
                            title.setText("Loading...");
                            handler.post(this);
                        }
                    }
                });
                builder.setView(customAlertview);

                builder.show();


         }
        }).setActionTextColor(ContextCompat.getColor(Image.this,R.color.colorAccent));
snackbar.show();
    }

    @Override
    public void onProgressValues(TessBaseAPI.ProgressValues progressValues) {

if(progressValues.getPercent()==100){
    completed=true;

}
percentage=progressValues.getPercent();
    }
    private File[] GetAllFiles(){
    File file=new File(getExternalFilesDir(null)+"/tessdata/");
    file.mkdirs();
    return file.listFiles();
}


    private void Download(String source,String title){
        File file=new File(getExternalFilesDir(null)+"/tessdata/",title+".tessdata");
       file.mkdirs();
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(source))
                .setTitle(title)
                .setDescription("Downloading the required files for "+title)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationUri(Uri.fromFile(file))
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);
        if(Build.VERSION.SDK_INT >=24){
            request.setRequiresCharging(false);
        }
                DownloadManager downloadManager=(DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    public class BitmapEditor{
        public Bitmap GetBitmap(Bitmap bitmap,double width,double height){
            Bitmap resize=resize(bitmap,width,height);
            Bitmap scaled=setGrayscale(resize);
            Bitmap removenoise=removeNoise(scaled);
            return removenoise;
        }
        public Bitmap resize(Bitmap img, double newWidth, double
                newHeight) {
            Bitmap bmap = img.copy(img.getConfig(), true);
            double nWidthFactor = (double) img.getWidth() /
                    (double) newWidth;
            double nHeightFactor = (double) img.getHeight() /
                    (double) newHeight;
            double fx, fy, nx, ny;
            int cx, cy, fr_x, fr_y;
            int color1;
            int color2;
            int color3;
            int color4;
            byte nRed, nGreen, nBlue;
            byte bp1, bp2;
            for (int x = 0; x < bmap.getWidth(); ++x) {
                for (int y = 0; y < bmap.getHeight(); ++y) {
                    fr_x = (int) Math.floor(x * nWidthFactor);
                    fr_y = (int) Math.floor(y * nHeightFactor);
                    cx = fr_x + 1;
                    if (cx >= img.getWidth())
                        cx = fr_x;
                    cy = fr_y + 1;
                    if (cy >= img.getHeight())
                        cy = fr_y;
                    fx = x * nWidthFactor - fr_x;
                    fy = y * nHeightFactor - fr_y;
                    nx = 1.0 - fx;
                    ny = 1.0 - fy;
                    color1 = img.getPixel(fr_x, fr_y);
                    color2 = img.getPixel(cx, fr_y);
                    color3 = img.getPixel(fr_x, cy);
                    color4 = img.getPixel(cx, cy);
                    // Blue
                    bp1 = (byte) (nx * Color.blue(color1) + fx *
                            Color.blue(color2));
                    bp2 = (byte) (nx * Color.blue(color3) + fx *
                            Color.blue(color4));
                    nBlue = (byte) (ny * (double) (bp1) + fy * (double)
                            (bp2));
                    // Green
                    bp1 = (byte) (nx * Color.green(color1) + fx *
                            Color.green(color2));
                    bp2 = (byte) (nx * Color.green(color3) + fx *
                            Color.green(color4));
                    nGreen = (byte) (ny * (double) (bp1) + fy * (double)
                            (bp2));
                    // Red
                    bp1 = (byte) (nx * Color.red(color1) + fx * Color.red
                            (color2));
                    bp2 = (byte) (nx * Color.red(color3) + fx * Color.red
                            (color4));
                    nRed = (byte) (ny * (double) (bp1) + fy * (double)
                            (bp2));
                    bmap.setPixel(x, y, Color.argb(255, nRed, nGreen,
                            nBlue));
                }
            }
            bmap = setGrayscale(bmap);
            bmap = removeNoise(bmap);
            return bmap;
        }
        // SetGrayscale
        private Bitmap setGrayscale(Bitmap img) {
            Bitmap bmap = img.copy(img.getConfig(), true);
            int c;
            for (int i = 0; i < bmap.getWidth(); i++) {
                for (int j = 0; j < bmap.getHeight(); j++) {
                    c = bmap.getPixel(i, j);
                    byte gray = (byte) (.299 * Color.red(c) + .587 *
                            Color.green(c)
                            + .114 * Color.blue(c));
                    bmap.setPixel(i, j, Color.argb(255, gray, gray, gray)
                    );
                }
            }
            return bmap;
        }
        // RemoveNoise
        private Bitmap removeNoise(Bitmap bmap) {
            for (int x = 0; x < bmap.getWidth(); x++) {
                for (int y = 0; y < bmap.getHeight(); y++) {
                    int pixel = bmap.getPixel(x, y);
                    if (Color.red(pixel) < 162 && Color.green(pixel) <
                            162 && Color.blue(pixel) < 162) {
                        bmap.setPixel(x, y, Color.BLACK);
                    }
                }
            }
            for (int x = 0; x < bmap.getWidth(); x++) {
                for (int y = 0; y < bmap.getHeight(); y++) {
                    int pixel = bmap.getPixel(x, y);
                    if (Color.red(pixel) > 162 && Color.green(pixel) >
                            162 && Color.blue(pixel) > 162) {
                        bmap.setPixel(x, y, Color.WHITE);
                    }
                }
            }
            return bmap;
        }

    }

}
