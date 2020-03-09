package com.hexagon.translator;


import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;



import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.hexagon.translator.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class MainActivity extends AppCompatActivity{
Activity Head;
    EasyImage easyImage;
static int permission=0;
boolean voicee=false;
    CoordinatorLayout initial_parent;
    private Toolbar toolbar;
    private Menu menu;
    public TextToSpeech voice;
    public TextToSpeech.OnInitListener listen;

    public final int Camera=99,File=50,Voice=47;
    public int checkerfrom=0,checkerto=0;
    public NotificationManager notificationManager;
    public NotificationCompat.Builder notify;
    public Notification notification;
    public static String picked;
EditText fromedit;
    WebView webView;
String translation1,translation2;
    TextView from,to,toedit,toedit1;
    ImageView image;
    Timer timer=new Timer();
    public static final String CLOSE_ACTION="Remove";
public LinearLayout linearparent;
    public int checkservice=0;
    Toast toast,toast1;
    public Intent intent11;
    public static MainActivity instance;
    Call call;
    final int CODE_DRAW_OVER_OTHER_APP_PERMISSION=2084;
    static String fromtext[]={"Detect language","Afrikaans","Albanian","Amharic","Arabic","Armenian","Azerbaijani","Basque","Belarusian","Bengali","Bosnian","Bulgarian","Catalan","Cebuano","Chichewa","Chinese (Simplified)","Corsican","Croatian","Czech","Danish","Dutch","English","Esperanto","Estonian","Filipino","Finnish","French","Frisian","Galician","Georgian","German","Greek","Gujarati","Haitian Creole","Hausa","Hawaiian","Hebrew","Hindi","Hmong","Hungarian","Icelandic","Igbo","Indonesian","Irish","Italian","Japanese","Javanese","Kannada","Kazakh","Khmer","Korean","Kurdish (Kurmanji)","Kyrgyz","Lao","Latin","Latvian","Lithuanian","Luxembourgish","Macedonian","Malagasy","Malay","Malayalam","Maltese","Maori","Marathi","Mongolian","Myanmar (Burmese)","Nepali","Norwegian","Pashto","Persian","Polish","Portuguese","Punjabi","Romanian","Russian","Samoan","Scots Gaelic","Serbian","Sesotho","Shona","Sindhi","Sinhala","Slovak","Slovenian","Somali","Spanish","Sundanese","Swahili","Swedish","Tajik","Tamil","Telugu","Thai","Turkish","Ukrainian","Urdu","Uzbek","Vietnamese","Welsh","Xhosa","Yiddish","Yoruba","Zulu"};
static String fromshort[]={"auto","af","sq","am","ar","hy","az","eu","be","bn","bs","bg","ca","ceb","ny","zh-CN","co","hr","cs","da","nl","en","eo","et","tl","fi","fr","fy","gl","ka","de","el","gu","ht","ha","haw","iw","hi","hmn","hu","is","ig","id","ga","it","ja","jw","kn","kk","km","ko","ku","ky","lo","la","lv","lt","lb","mk","mg","ms","ml","mt","mi","mr","mn","my","ne","no","ps","fa","pl","pt","pa","ro","ru","sm","gd","sr","st","sn","sd","si","sk","sl","so","es","su","sw","sv","tg","ta","te","th","tr","uk","ur","uz","vi","cy","xh","yi","yo","zu"};
static String totext[]={"Afrikaans","Albanian","Amharic","Arabic","Armenian","Azerbaijani","Basque","Belarusian","Bengali","Bosnian","Bulgarian","Catalan","Cebuano","Chichewa","Chinese (Simplified)","Chinese (Traditional)","Corsican","Croatian","Czech","Danish","Dutch","English","Esperanto","Estonian","Filipino","Finnish","French","Frisian","Galician","Georgian","German","Greek","Gujarati","Haitian Creole","Hausa","Hawaiian","Hebrew","Hindi","Hmong","Hungarian","Icelandic","Igbo","Indonesian","Irish","Italian","Japanese","Javanese","Kannada","Kazakh","Khmer","Korean","Kurdish (Kurmanji)","Kyrgyz","Lao","Latin","Latvian","Lithuanian","Luxembourgish","Macedonian","Malagasy","Malay","Malayalam","Maltese","Maori","Marathi","Mongolian","Myanmar (Burmese)","Nepali","Norwegian","Pashto","Persian","Polish","Portuguese","Punjabi","Romanian","Russian","Samoan","Scots Gaelic","Serbian","Sesotho","Shona","Sindhi","Sinhala","Slovak","Slovenian","Somali","Spanish","Sundanese","Swahili","Swedish","Tajik","Tamil","Telugu","Thai","Turkish","Ukrainian","Urdu","Uzbek","Vietnamese","Welsh","Xhosa","Yiddish","Yoruba","Zulu"};
static String toshort[]={"af","sq","am","ar","hy","az","eu","be","bn","bs","bg","ca","ceb","ny","zh-CN","zh-TW","co","hr","cs","da","nl","en","eo","et","tl","fi","fr","fy","gl","ka","de","el","gu","ht","ha","haw","iw","hi","hmn","hu","is","ig","id","ga","it","ja","jw","kn","kk","km","ko","ku","ky","lo","la","lv","lt","lb","mk","mg","ms","ml","mt","mi","mr","mn","my","ne","no","ps","fa","pl","pt","pa","ro","ru","sm","gd","sr","st","sn","sd","si","sk","sl","so","es","su","sw","sv","tg","ta","te","th","tr","uk","ur","uz","vi","cy","xh","yi","yo","zu"};
    //    private adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       setTheme(R.style.MyMaterialTheme_Base);
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
        checkerfrom=Integer.parseInt(getfromsdcard(4));
        checkerto=Integer.parseInt(getfromsdcard(5));

        sdcardpermission();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Head = this;
        instance = this;
        Head.setTitle("Hexagon Translator");
        intent11 = new Intent(MainActivity.this, Heads.class);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFffFFff, PorterDuff.Mode.MULTIPLY);
        toedit = (TextView) findViewById(R.id.toedit);
        toedit.setText(getfromsdcard(2));
        toedit1 = (TextView) findViewById(R.id.toedit1);
        toedit1.setText(getfromsdcard(3));
        from = (TextView) findViewById(R.id.from);
        from.setText(fromtext[checkerfrom]);
        to = (TextView) findViewById(R.id.to);
        to.setText(totext[checkerto]);




        fromedit = (EditText) findViewById(R.id.fromedit);
        fromedit.setText(getfromsdcard(1));
        if(getIntent().getAction().startsWith("**")){
            fromedit.setText(getIntent().getAction().replaceAll("\\*\\*",""));
        }
        getifisShowhelp(true);



        image = (ImageView) findViewById(R.id.switch1);
        toast = Toast.makeText(getApplicationContext(), "Connection Error, Please review your Network Settings", Toast.LENGTH_SHORT);
        toast1 = Toast.makeText(getApplicationContext(), "Copied To Clipboard", Toast.LENGTH_SHORT);
        OneTimeWorkRequest oneTimeWorkRequest=new OneTimeWorkRequest.Builder(Coolworker.class).build();
        WorkManager.getInstance(MainActivity.this).enqueue(oneTimeWorkRequest);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
timer.scheduleAtFixedRate(new TimerTask() {
    TimerTask timerr=this;
    @Override
    public void run() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        AppOpsManager appOpsManager = (AppOpsManager) MainActivity.this.getSystemService(Context.APP_OPS_SERVICE);
                        final int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW, android.os.Process.myUid(), MainActivity.this.getPackageName());
                        boolean granted = mode == AppOpsManager.MODE_DEFAULT ? (MainActivity.this.checkCallingOrSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW) == PackageManager.PERMISSION_GRANTED) : (mode == AppOpsManager.MODE_ALLOWED);
                        if (granted) {
                            finish();
                            timerr.cancel();
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                        }
                    }
            }
        });
    }
},0,120);
        }else{
            startwork();
        }
image.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       startwork();
       //startService(new Intent(getApplicationContext(),Webviewone.class));

    }
});
        View overflow_hidden=findViewById(R.id.oveflow_hidden);
        findViewById(R.id.oveflow_visible).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Head, overflow_hidden);
                MenuInflater inflater = Head.getMenuInflater();
                inflater.inflate(R.menu.menu_main, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_settings:  getifisShowhelp(false);
                                return true;
                            case R.id.contact: Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:hexagontranslator@gmail.com"));
                                startActivity(intent);
                                return true;
                            case R.id.terms:Intent intent1=new Intent(Intent.ACTION_VIEW, Uri.parse("http://hexagontranslator.blogspot.com"));
                                startActivity(intent1);
                                return true;
                            case R.id.offers:Intent intent2=new Intent(MainActivity.this,Offers.class);
                                startActivity(intent2);
                                return true;
                        }

                        return false;
                    }
                });

                popupMenu.show();
            }
        });




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(fromedit.getWindowToken(), 0);
                loadurl();
            }
        });

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Head);
                builder.setTitle("Select Language");
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        changeText(fromtext[checkerfrom], from);
                        loadurl();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setSingleChoiceItems(fromtext, getselected(fromtext, from), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkerfrom = which;
                        picked = fromtext[checkerfrom];

                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Head);
                builder.setTitle("Select Language");
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        changeText(totext[checkerto], to);
                        loadurl();


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setSingleChoiceItems(totext, getselected(totext, to), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkerto = which;

                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        });

        //checking if the text matches on both sides before switching

        toedit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        toedit.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.navigationBarColor));
                        toedit.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.textColorPrimary));
                        return true;
                    case MotionEvent.ACTION_UP:
                        toedit.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.ttt));
                        toedit.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorAccent1));
                        toast1.show();
                        copynpaste(toedit.getText().toString());
                        return true;
                }
                return false;
            }
        });
        toedit1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        toedit1.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.navigationBarColor));
                        toedit1.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.textColorPrimary));
                        return true;
                    case MotionEvent.ACTION_UP:
                        toedit1.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.textColorPrimary));
                        toedit1.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorAccent1));
                        toast1.show();
                        copynpaste(toedit1.getText().toString());
                        return true;
                }
                return false;
            }
        });

        findViewById(R.id.show_hide).setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                               PopupMenu popupMenu = new PopupMenu(Head, v);
                                                                MenuInflater inflater = Head.getMenuInflater();
                                                                inflater.inflate(R.menu.menu_main2, popupMenu.getMenu());
popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem item) {
      switch (item.getItemId()){
          case R.id.camera: CameraPermission();
          return true;
          case R.id.file: sdcardpermissionPick();
          return true;
      }
        return false;
    }
});

popupMenu.show();
                                                            }
                                                        });
        fromedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
if(fromedit.getText().toString().length() >= 3000){
    TextView error=findViewById(R.id.error);
    error.setText("The Text is too long and will be Truncated");
    error.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.colorAccent));
}else{
    TextView error=findViewById(R.id.error);
    error.setText("The Maximum Inputtable Text is 3000");
error.setTextColor(Color.argb(255,66,65,65));
}
            }
        });



        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay();
            }
        });

        listen=new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
               if(status==TextToSpeech.SUCCESS){
//                                     CoordinatorLayout initial_parent=findViewById(R.id.initial_parent);
//                   Snackbar.make(initial_parent,"The Required file for "+totext[getselected(toshort,to)]+" is not completely Installed yet",Snackbar.LENGTH_LONG).setAction("Install Now", new View.OnClickListener() {
//                       @Override
//                       public void onClick(View v) {
//                           Intent install=new Intent();
//                           install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//                           startActivity(install);
//                       }
//                   }).setActionTextColor(ContextCompat.getColor(MainActivity.this,R.color.colorAccent)).show();

                   String []arr=toshort[getselected(toshort,to)].split("-");
                   CoordinatorLayout initial_parent=findViewById(R.id.initial_parent);

                   Snackbar initilizer= Snackbar.make(initial_parent,"Initializing the Speeck Output, Please wait....",Snackbar.LENGTH_INDEFINITE);
voice.setLanguage(new Locale(arr[0]));
                   //Toast.makeText(Head, arr[0], Toast.LENGTH_SHORT).show();
if(voice.isLanguageAvailable(new Locale(arr[0]))==TextToSpeech.LANG_MISSING_DATA){
    Snackbar.make(initial_parent,"The required files for "+totext[getselected(toshort,to)]+" have not been properly installed, ",Snackbar.LENGTH_LONG).setAction("Install Now", new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent install=new Intent();
            install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(install);
        }
    }).setActionTextColor(ContextCompat.getColor(MainActivity.this,R.color.colorAccent)).show();

}
else if(voice.isLanguageAvailable(new Locale(arr[0]))==TextToSpeech.LANG_NOT_SUPPORTED){
    Toast.makeText(Head, "Speech Output is Not Available for "+totext[getselected(toshort,to)], Toast.LENGTH_SHORT).show();

}
else if(voice.isLanguageAvailable(new Locale(arr[0]))==TextToSpeech.LANG_AVAILABLE){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        voice.speak(toedit1.getText().toString(),TextToSpeech.QUEUE_FLUSH,null,"voice");
        initilizer.show();

    }else{
        HashMap<String,String> params=new HashMap<String,String>();
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"voice");
        voice.speak(toedit1.getText().toString(),TextToSpeech.QUEUE_FLUSH,params);
    }
    voice.setOnUtteranceProgressListener(new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {
           runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   initilizer.dismiss();
                   CoordinatorLayout initial_parent=findViewById(R.id.initial_parent);
                   Snackbar.make(initial_parent,"Now Listening to "+totext[getselected(toshort,to)]+". If You cannot hear anything, ",Snackbar.LENGTH_LONG).setAction("Click Here", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                         AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                         builder.setTitle("I Cannot hear any speech output and i'm not not seeing any errors.")
                                 .setMessage("This happens only when you've selected a language that is not equal to your translated text's language. For example, if you select \"English\" from the Right language dropdown and your translation result's text is in \"Chinese\", you won't hear anything since the speech output file for \"English\" would'nt work for \"Chinese\". Your selected language from the \"Right\" dropdown must match your translation result's language before any speech output is possible. ")
                                 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {

                                     }
                                 }).show();
                       }
                   }).setActionTextColor(ContextCompat.getColor(MainActivity.this,R.color.colorAccent)).show();

               }
           });
        }

        @Override
        public void onDone(String utteranceId) {


        }

        @Override
        public void onError(String utteranceId) {
            initilizer.dismiss();

            runOnUiThread(new Runnable() {
    @Override
    public void run() {
                      CoordinatorLayout initial_parent=findViewById(R.id.initial_parent);
                   Snackbar.make(initial_parent,"An Error has Occured, You may not have properly installed the required files for "+totext[getselected(toshort,to)]+", or there is a problem with the Speech Output.",Snackbar.LENGTH_LONG).setAction("Install Now", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent install=new Intent();
                           install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                           startActivity(install);
                       }
                   }).setActionTextColor(ContextCompat.getColor(MainActivity.this,R.color.colorAccent)).show();

    }
});
        }
    });

}


               }
            }
        };
    }
public void startPlay(){
    TextView text=findViewById(R.id.toedit1);
    String value=text.getText().toString();
    Intent intent=new Intent();
    intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
    startActivityForResult(intent,Voice);

}

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(Head)) {
            startwork();
        }
        if(Build.VERSION.SDK_INT <Build.VERSION_CODES.M){
            startwork();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(permission==0) {
            //startNotify();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(permission == 0) {

        }
        if(voicee) {
            voice.stop();
            voice.shutdown();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
            startwork();
        }
        if(Build.VERSION.SDK_INT <Build.VERSION_CODES.M){
            startwork();
        }
    }

    private void initializeView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);

        } else {


        }

    }

    public void startNotify(){
        Intent intent=new Intent(MainActivity.this, Closer.class);

      Head.startService(intent);
    }

public static MainActivity getInstance(){
        return instance;
}
   public void loadurl(){

        ScrollView scrollView=(ScrollView)findViewById(R.id.scrollview);
        scrollView.setVisibility(View.GONE);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relforloadingbar);
        relativeLayout.setVisibility(View.VISIBLE);
        String gettext = fromedit.getText().toString();
       if(fromedit.getText().toString().length() >= 3000) {
           gettext=gettext.substring(0,3000);
           Toast.makeText(Head, "The Text is too long and will be Truncated", Toast.LENGTH_SHORT).show();
       }
        gettext = gettext.replaceAll(" ", "+");
        String url="https://translate.google.com/m?hl=en&sl="+fromshort[checkerfrom]+"&tl="+toshort[checkerto]+"&ie=UTF-8&prev=_m&q="+gettext;
       try {
           run(url);
       } catch (IOException e) {
          // e.printStackTrace();
       }


   }


   public void getifisShowhelp(boolean show_startup){

       AlertDialog.Builder builder=new AlertDialog.Builder(Head);
       builder.setTitle("Welcome To The Hexagon Translator");
       builder.setMessage(""+
               "\n" +
               "WHAT IS HEXAGON TRANSLATOR? : \n" +
               "\n" +
               " It is an app created for easy translation between languages.\n" +
               "\n" +
               "\n" +
               "HOW DO I TRANSLATE WITHIN THE TRANSLATOR? : \n" +
               "\n" +
               "Click on the left hand side of language and select the language you want to translate from and select the other language you want to translate to by clicking on the right hand side of the language selection tab. Input your text or statements in the input field and click on the arrow for it to start the translation. \n" +
               "\n" +
               "\n" +
               "HOW DO I TRANSLATE IF I’M IN ANOTHER APP? :  \n" +
               "\n" +
               "Hexagon Translator provides a way to translator even if you’re not within the translator. When the app is closed by pressing the “BACK KEY”,  it will create a notification at the notification bar on your device and a draggable app icon. Whn you click on the icon, it will pop-up the translator. This makes it easy as translations can be done easily with the App Icon without having to go back to the translator app. The App Icon can be removed if you don’t want it running in the background by clicking the \"STOP\" on the notification. \n" +
               "NOTE:  THE  TRANSLATOR APP ICON(BUBBLE) CAN ONLY BE LAUNCHED BY CLOSING THE APP BY PRESSING THE BACK KEY…..\n" +
               "\n" +
               "\n" +
               "HOW DO I COPY TRANSLATED TEXT? : \n" +
               "\n" +
               "Click on the translated language and it will be copied to the clipboard automatically. Some languages (e.g Arabic, Chinese) produce more than one translated result, the first being the result in Alphabet-like texts and the second being the native language. You can select any one of the results for copying. Note: You cannot edit the translated language within the translator app, you can only edit it on your own after copying it.\n" +
               "\n");
       builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.cancel();
           }
       });
       builder.setCancelable(false);
       if(show_startup){
if(getfromsdcard(6).matches("1")) {
    builder.show();
}
       }else{
           builder.show();
       }
   }
private String getfromsdcard(int a){
        String returnu="";
        try{
            InputStream inputStream=new FileInputStream(new File(getExternalFilesDir(null)+"/.Hexagon/translated.hxg"));
       if(inputStream != null){
           InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
           BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
           String receive="";
           StringBuilder stringBuilder=new StringBuilder();
           while((receive=bufferedReader.readLine()) != null){
             stringBuilder.append(receive);
           }
          inputStream.close();

           returnu=stringBuilder.toString();

           String text1="<text>(.*?)</text>";
           String head="<head>(.*?)</head>";
           String translated1="<translated1>(.*?)</translated1>";
           String translated2="<translated2>(.*?)</translated2>";
           String checkerfrom1="<checkerfrom>(.*?)</checkerfrom>";
           String checkerto1="<checkerto>(.*?)</checkerto>";

           Pattern text=Pattern.compile(text1,Pattern.MULTILINE|Pattern.CASE_INSENSITIVE);
           Pattern translated=Pattern.compile(translated1,Pattern.MULTILINE|Pattern.CASE_INSENSITIVE);
           Pattern translated22=Pattern.compile(translated2,Pattern.MULTILINE|Pattern.CASE_INSENSITIVE);
           Pattern checkerfrom11=Pattern.compile(checkerfrom1,Pattern.MULTILINE|Pattern.CASE_INSENSITIVE);
           Pattern checkerto11=Pattern.compile(checkerto1,Pattern.MULTILINE|Pattern.CASE_INSENSITIVE);
           Pattern head1=Pattern.compile(head,Pattern.MULTILINE|Pattern.CASE_INSENSITIVE);

           Matcher edittext4=text.matcher(returnu);
           Matcher firsttranslated=translated.matcher(returnu);
           Matcher sectranslated=translated22.matcher(returnu);
           Matcher checkerfrom5=checkerfrom11.matcher(returnu);
           Matcher checkerto5=checkerto11.matcher(returnu);
           Matcher head2=head1.matcher(returnu);

           if(edittext4.find() && a==1) {
    return edittext4.group(1);
}

if(firsttranslated.find() && a==2) {
               return firsttranslated.group(1);
}

if(sectranslated.find() && a==3) {
    return sectranslated.group(1);

}

if(checkerfrom5.find() && a==4) {
    return checkerfrom5.group(1);
}

if(checkerto5.find() && a==5) {
    return checkerto5.group(1);
}
           if(head2.find() && a==6) {
               return head2.group(1);
           }

if(a==0){
    return "";
}
       }
        }catch(FileNotFoundException e){
            if(a==1){
                return "Hi, and welcome to the Hexagon Translator!!!";
            }
            if(a==2){
                return "Hāi, huānyíng lái dào hǎi kè sī kāng fānyì!";
            }
            if(a==3){
                return "嗨，欢迎来到海克斯康翻译！";
            }
            if(a==4){
                return "0";
            }
            if(a==5){
                return "14";
            }
            if(a==6){
                return "1";
            }
        }catch(IOException d){

        }
        catch(Exception e){

        }

return "";
}

private void writetosdcard(){

        int checkerfrom1=checkerfrom;
    int checkerto1=checkerto;

    String text=fromedit.getText().toString();
    String translated1=toedit.getText().toString();
    String translated2=toedit1.getText().toString();

    File file = new File(getExternalFilesDir(null)+"/.Hexagon/");
    file.mkdirs();
        try{
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(new FileOutputStream(new File(getExternalFilesDir(null)+"/.Hexagon/","translated.hxg")));
            outputStreamWriter.write("<head>0</head> <text>"+text+"</text> <translated1>"+translated1+"</translated1> <translated2>"+translated2+"</translated2> <checkerfrom>"+checkerfrom+"</checkerfrom> <checkerto>"+checkerto+"</checkerto>");
            outputStreamWriter.close();
        }catch (IOException e){
            Toast.makeText(Head, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
}
    void run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
       call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScrollView scrollView=(ScrollView)findViewById(R.id.scrollview);
                        scrollView.setVisibility(View.VISIBLE);
                        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relforloadingbar);
                        relativeLayout.setVisibility(View.GONE);
toast.show();



                    }

                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.isSuccessful()){
                            ScrollView scrollView=(ScrollView)findViewById(R.id.scrollview);
                            scrollView.setVisibility(View.VISIBLE);
                            RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relforloadingbar);
                            relativeLayout.setVisibility(View.GONE);


                            String originaltext=res;
                            String o="<div dir=\"(.*?)\" class=\"o1\">(.*?)</div>";
                            String o1="<div dir=\"(.*?)\" class=\"t0\">(.*?)</div>";
                            Pattern p=Pattern.compile(o,Pattern.MULTILINE|Pattern.CASE_INSENSITIVE);
                            Pattern p1=Pattern.compile(o1,Pattern.MULTILINE|Pattern.CASE_INSENSITIVE);

                            Matcher m=p.matcher(originaltext);
                            Matcher m1=p1.matcher(originaltext);

                            if(m.find()){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    toedit.setText(Html.fromHtml(m.group(2),Html.FROM_HTML_MODE_LEGACY));
                                }else{
                                    toedit.setText(Html.fromHtml(m.group(2)));
                                }

                            }else{
                                toedit.setText("");

                            }
                            if(m1.find()){
                                if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.N){
                                    toedit1.setText(Html.fromHtml(m1.group(2),Html.FROM_HTML_MODE_LEGACY));

                                }else{
                                    toedit1.setText(Html.fromHtml(m1.group(2)));

                                }
if(m1.group(1).matches("rtl")){
    toedit.setGravity(Gravity.TOP|Gravity.RIGHT);
    toedit1.setGravity(Gravity.TOP|Gravity.RIGHT);

}else{
                                    toedit.setGravity(Gravity.TOP|Gravity.LEFT);
                                    toedit1.setGravity(Gravity.TOP|Gravity.LEFT);

                                }
                            }
                            writetosdcard();

                        }else{
                            ScrollView scrollView=(ScrollView)findViewById(R.id.scrollview);
                            scrollView.setVisibility(View.VISIBLE);
                            RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relforloadingbar);
                            relativeLayout.setVisibility(View.GONE);
toast.show();



                        }

                    }
                });
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
public void copynpaste(String text){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
            ClipboardManager clipboardManager=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
       clipboardManager.setText(text);

        }else{
            ClipboardManager clipboardManager=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData=ClipData.newPlainText("Copied Text",text);
            clipboardManager.setPrimaryClip(clipData);
        }
}

    @Override
    public void onBackPressed() {
        startNotify();
//stopService(new Intent(getApplicationContext(),Webviewone.class));
        super.onBackPressed();
    }
private void sdcardpermission(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 5);
    }
    }
    private void sdcardpermissionPick(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, File);
        }else{
            startActivityForResult(new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI),File);
        }

    }
    private void CameraPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, Camera);
        }else{
            try {
                 easyImage=new EasyImage.Builder(MainActivity.this)
                        .allowMultiple(false)
                        .setChooserTitle("Pick Image")
                        .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                        .setCopyImagesToPublicGalleryFolder(false)
                        .setFolderName("Images")
                        .build();
                easyImage.openCameraForImage(Head);
            }catch(Exception e){
                Toast.makeText(Head, "An error has occured, no external storage found!!! ", Toast.LENGTH_LONG).show();
            }

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==5){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                permission=0;
            }else{
                permission=1;
                Toast.makeText(getApplicationContext(), "The Requested Permission is Required for This app to Function Properly", Toast.LENGTH_SHORT).show();
                Head.finish();
            }
        }
        if(requestCode==Camera){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                try {
                    easyImage=new EasyImage.Builder(MainActivity.this)
                            .allowMultiple(false)
                            .setChooserTitle("Pick Image")
                            .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                            .setCopyImagesToPublicGalleryFolder(false)
                            .setFolderName("Images")
                            .build();
                    easyImage.openCameraForImage(Head);
                }catch(Exception e){
                    Toast.makeText(Head, "An error has occured, no external storage found!!! ", Toast.LENGTH_LONG).show();
                }
            }

            }
        if(requestCode==File){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI),File);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {



        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            AppOpsManager appOpsManager=(AppOpsManager)MainActivity.this.getSystemService(Context.APP_OPS_SERVICE);
            final int mode=appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW,android.os.Process.myUid(),MainActivity.this.getPackageName());
            boolean granted=mode==AppOpsManager.MODE_DEFAULT ? (MainActivity.this.checkCallingOrSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)==PackageManager.PERMISSION_GRANTED):(mode==AppOpsManager.MODE_ALLOWED);
         if(!granted) {
             Toast.makeText(Head, "The Overlay permission is required  for this app to function properly", Toast.LENGTH_LONG).show();
             finish();
         }
         }
        }else if(requestCode==Voice) {
if(resultCode==TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
    voice=new TextToSpeech(Head,listen);
voicee=true;
}else{
    CoordinatorLayout initial_parent=findViewById(R.id.initial_parent);
    Snackbar.make(initial_parent,"The Required file for Speech Output is not yet Installed",Snackbar.LENGTH_LONG).setAction("Install Now", new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Intent install=new Intent();
           install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
           startActivity(install);
        }
    }).setActionTextColor(ContextCompat.getColor(MainActivity.this,R.color.colorAccent)).show();

}

        }
        else if(requestCode==File){
            if(resultCode==RESULT_OK){
             Uri image=  data.getData();
Intent intent=new Intent(Head,Image.class);
intent.setData(image);
startActivity(intent);
            }
        }


        else
        {
            easyImage.handleActivityResult(requestCode, resultCode, data, Head, new DefaultCallback() {
                @Override
                public void onMediaFilesPicked(@NotNull MediaFile[] mediaFiles, @NotNull MediaSource mediaSource) {
                    Uri uri=mediaFiles[0].getUri$library_release();
                    Intent intent=new Intent(Head,Image.class);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getifisShowhelp(false);
        }
        if(id==R.id.contact){
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:hexagontranslator@gmail.com"));
        startActivity(intent);
        }
        if(id==R.id.terms){
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://hexagontranslator.blogspot.com"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


  protected int getselected(String text[],TextView v){

 if(v==from){
     return checkerfrom;
 }
 else{
     return checkerto;
 }

  }
    protected void changeText(String text,TextView v){


            v.setText(text);

    }
    protected int calculateNumberOfColumns(int base) {


        int columns = base;
        String screenSize = getScreenSizeCategory();
        if (screenSize.equals("small")) {
            if (base != 1) {
                columns = columns - 1;
            }
        } else if (screenSize.equals("normal")) {
            columns = columns - 1;
        } else if (screenSize.equals("large")) {

        } else if (screenSize.equals("xlarge")) {

        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columns = columns + 1;
        }

        return columns;
    }

    protected String getScreenOrientation() {
        String orientation = "undefined";

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            orientation = "landscape";
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            orientation = "portrait";
        }

        return orientation;
    }

    // Custom method to get screen size category
    protected String getScreenSizeCategory() {
        int screenLayout = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                // small screens are at least 426dp x 320dp
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                // normal screens are at least 470dp x 320dp
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                // large screens are at least 640dp x 480dp
                return "large";
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                // xlarge screens are at least 960dp x 720dp
                return "xlarge";
            default:
                return "undefined";
        }
    }
    protected void startwork(){

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest saveRequest =
                new PeriodicWorkRequest.Builder(Coolworker.class, 16, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        WorkManager.getInstance(MainActivity.this).enqueueUniquePeriodicWork("Mywork", ExistingPeriodicWorkPolicy.KEEP,saveRequest);

    }

}
