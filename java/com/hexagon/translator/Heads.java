package com.hexagon.translator;

import android.app.ActionBar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.Notification;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.hexagon.translator.R;

import androidx.work.WorkerParameters;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class Heads extends Service {
    private WindowManager windowManager;
    private View translatorhead1;
    Context Head=this;
    RecyclerView recyclerView;
    CoordinatorLayout initial_parent;
    private Toolbar toolbar;
    private Menu menu;
    public int checkerfrom=Integer.parseInt(getfromsdcard(4)),checkerto=Integer.parseInt(getfromsdcard(4)),translatorhead=0;
    public NotificationManager notificationManager;
    public NotificationCompat.Builder notify;
    public Notification notification;
    public static String picked;
    public int checkforchecked=Integer.parseInt(getfromsdcard(4)),checkforchecked1=Integer.parseInt(getfromsdcard(5));
    EditText fromedit;
    public String height_root;
    WebView webView;
    String translation1,translation2;
    TextView from,to,toedit,toedit1;
    RelativeLayout loader;
    ImageView image;
    public int checkservice=0;
    Toast toast,toast1;
    Call call;
    CoordinatorLayout coordinatorLayout;
    private List<Appdetails> applist;
    private adapter adapter;
    public Intent intent11;

    final int CODE_DRAW_OVER_OTHER_APP_PERMISSION=2084;
    static String fromtext[]={"Detect language","Afrikaans","Albanian","Amharic","Arabic","Armenian","Azerbaijani","Basque","Belarusian","Bengali","Bosnian","Bulgarian","Catalan","Cebuano","Chichewa","Chinese (Simplified)","Corsican","Croatian","Czech","Danish","Dutch","English","Esperanto","Estonian","Filipino","Finnish","French","Frisian","Galician","Georgian","German","Greek","Gujarati","Haitian Creole","Hausa","Hawaiian","Hebrew","Hindi","Hmong","Hungarian","Icelandic","Igbo","Indonesian","Irish","Italian","Japanese","Javanese","Kannada","Kazakh","Khmer","Korean","Kurdish (Kurmanji)","Kyrgyz","Lao","Latin","Latvian","Lithuanian","Luxembourgish","Macedonian","Malagasy","Malay","Malayalam","Maltese","Maori","Marathi","Mongolian","Myanmar (Burmese)","Nepali","Norwegian","Pashto","Persian","Polish","Portuguese","Punjabi","Romanian","Russian","Samoan","Scots Gaelic","Serbian","Sesotho","Shona","Sindhi","Sinhala","Slovak","Slovenian","Somali","Spanish","Sundanese","Swahili","Swedish","Tajik","Tamil","Telugu","Thai","Turkish","Ukrainian","Urdu","Uzbek","Vietnamese","Welsh","Xhosa","Yiddish","Yoruba","Zulu"};
    static String fromshort[]={"auto","af","sq","am","ar","hy","az","eu","be","bn","bs","bg","ca","ceb","ny","zh-CN","co","hr","cs","da","nl","en","eo","et","tl","fi","fr","fy","gl","ka","de","el","gu","ht","ha","haw","iw","hi","hmn","hu","is","ig","id","ga","it","ja","jw","kn","kk","km","ko","ku","ky","lo","la","lv","lt","lb","mk","mg","ms","ml","mt","mi","mr","mn","my","ne","no","ps","fa","pl","pt","pa","ro","ru","sm","gd","sr","st","sn","sd","si","sk","sl","so","es","su","sw","sv","tg","ta","te","th","tr","uk","ur","uz","vi","cy","xh","yi","yo","zu"};
    static String totext[]={"Afrikaans","Albanian","Amharic","Arabic","Armenian","Azerbaijani","Basque","Belarusian","Bengali","Bosnian","Bulgarian","Catalan","Cebuano","Chichewa","Chinese (Simplified)","Chinese (Traditional)","Corsican","Croatian","Czech","Danish","Dutch","English","Esperanto","Estonian","Filipino","Finnish","French","Frisian","Galician","Georgian","German","Greek","Gujarati","Haitian Creole","Hausa","Hawaiian","Hebrew","Hindi","Hmong","Hungarian","Icelandic","Igbo","Indonesian","Irish","Italian","Japanese","Javanese","Kannada","Kazakh","Khmer","Korean","Kurdish (Kurmanji)","Kyrgyz","Lao","Latin","Latvian","Lithuanian","Luxembourgish","Macedonian","Malagasy","Malay","Malayalam","Maltese","Maori","Marathi","Mongolian","Myanmar (Burmese)","Nepali","Norwegian","Pashto","Persian","Polish","Portuguese","Punjabi","Romanian","Russian","Samoan","Scots Gaelic","Serbian","Sesotho","Shona","Sindhi","Sinhala","Slovak","Slovenian","Somali","Spanish","Sundanese","Swahili","Swedish","Tajik","Tamil","Telugu","Thai","Turkish","Ukrainian","Urdu","Uzbek","Vietnamese","Welsh","Xhosa","Yiddish","Yoruba","Zulu"};
    static String toshort[]={"af","sq","am","ar","hy","az","eu","be","bn","bs","bg","ca","ceb","ny","zh-CN","zh-TW","co","hr","cs","da","nl","en","eo","et","tl","fi","fr","fy","gl","ka","de","el","gu","ht","ha","haw","iw","hi","hmn","hu","is","ig","id","ga","it","ja","jw","kn","kk","km","ko","ku","ky","lo","la","lv","lt","lb","mk","mg","ms","ml","mt","mi","mr","mn","my","ne","no","ps","fa","pl","pt","pa","ro","ru","sm","gd","sr","st","sn","sd","si","sk","sl","so","es","su","sw","sv","tg","ta","te","th","tr","uk","ur","uz","vi","cy","xh","yi","yo","zu"};

    public Heads(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            String action = intent.getAction();

            height_root=action;

            }

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    int ifclick=0;

    @Override
    public void onCreate() {
        super.onCreate();
        startNotify();

        coordinatorLayout=new CoordinatorLayout(this){
            public void onCloseSystemDialogs(String reason){
                stopSelf();
            }

            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
                    par_Close();
                    return true;
                }
                return super.dispatchKeyEvent(event);
            }

        };
        coordinatorLayout.setFocusable(true);

            translatorhead1 = LayoutInflater.from(this).inflate(R.layout.dragndrop,coordinatorLayout);

        WindowManager.LayoutParams params;
       if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
         params = new WindowManager.LayoutParams(
                   WindowManager.LayoutParams.MATCH_PARENT,
                   WindowManager.LayoutParams.MATCH_PARENT,
                   WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                   WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                   PixelFormat.TRANSLUCENT);
       }else {
           params = new WindowManager.LayoutParams(
                   WindowManager.LayoutParams.MATCH_PARENT,
                   WindowManager.LayoutParams.MATCH_PARENT,
                   WindowManager.LayoutParams.TYPE_PHONE,
                   WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                   PixelFormat.TRANSLUCENT);
       }
params.screenOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        params.gravity= Gravity.TOP|Gravity.LEFT;
   params.x=0;
   params.y=0;
   windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
   windowManager.addView(translatorhead1,params);
       final View dragndrop =translatorhead1.findViewById(R.id.dragndrop);

        ProgressBar progressBar = (ProgressBar) translatorhead1.findViewById(R.id.progressBar_cyclic);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFffFFff, PorterDuff.Mode.MULTIPLY);
        toedit=(TextView)translatorhead1.findViewById(R.id.toedit);
        toedit.setText(getfromsdcard(2));
        toedit1=(TextView)translatorhead1.findViewById(R.id.toedit1);
        toedit1.setText(getfromsdcard(3));
        loader=translatorhead1.findViewById(R.id.relforloadingbar);
        from = (TextView)translatorhead1.findViewById(R.id.from);
        from.setText(fromtext[checkforchecked]);
        to = (TextView)translatorhead1.findViewById(R.id.to);
        to.setText(totext[checkforchecked1]);

        fromedit=(EditText)translatorhead1.findViewById(R.id.fromedit);
        fromedit.setText(getfromsdcard(1));
        toedit=(TextView)translatorhead1.findViewById(R.id.toedit);
        image = (ImageView)translatorhead1.findViewById(R.id.switch1);
        toast= Toast.makeText(Heads.this,"Connection Error, Please review your Network Settings",Toast.LENGTH_SHORT);
        toast1=Toast.makeText(Heads.this, "Copied To Clipboard", Toast.LENGTH_SHORT);
       setuprecycler(R.id.recycler_viewfrom,fromtext,"from");
        setuprecycler(R.id.recycler_viewto,totext,"to");


ImageView idol=translatorhead1.findViewById(R.id.idol_closer);
idol.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        RelativeLayout alert_boxr=translatorhead1.findViewById(R.id.alert_box);
alert_boxr.setVisibility(View.VISIBLE);
    }

});
Button demon=translatorhead1.findViewById(R.id.demon_closer);
demon.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        stopSelf();
        Intent intent1=new Intent(Heads.this, Closer.class);
        intent1.setAction("stop");
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            startForegroundService(intent1);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Head, "Translation Over Other Apps Have Been Shut Down", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            startService(intent1);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Head, "Translation Over Other Apps Have Been Shut Down", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
});
RelativeLayout alert=translatorhead1.findViewById(R.id.alert_box);
alert.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        alert.setVisibility(View.GONE);
    }
});

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout fromparent = translatorhead1.findViewById(R.id.fromparent);
                fromparent.setVisibility(View.VISIBLE);

            }
                });


        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout toparent = translatorhead1.findViewById(R.id.toparent);
                toparent.setVisibility(View.VISIBLE);


            }
        });

        ImageView switch1 = translatorhead1.findViewById(R.id.switch1);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ImageView fab = translatorhead1.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(fromedit.getWindowToken(), 0);
                loadurl();
            }
        });
        //checking if the text matches on both sides before switching
        fromedit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                show_Toast1("You can Double click on the text editor to paste a copied text",4000);
                return false;
            }
        });
        Handler handler5=new Handler(Looper.getMainLooper());
        int[] counter=new int[1];
        counter[0]=0;
        Runnable runnable5=new Runnable() {
            @Override
            public void run() {
counter[0]=0;

            }
        };
        fromedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter[0]++;

                if(counter[0]==2) {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    if (clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
                        fromedit.setText(fromedit.getText() + " " + item.getText().toString());
                    }

                }
                handler5.removeCallbacks(runnable5);
                handler5.removeMessages(0);
                handler5.postDelayed(runnable5,300);
            }
        });

        toedit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:toedit.setTextColor(ContextCompat.getColor(Heads.this,R.color.navigationBarColor)); toedit.setBackgroundColor(ContextCompat.getColor(Heads.this,R.color.textColorPrimary));
                        return true;
                    case MotionEvent.ACTION_UP:toedit.setTextColor(ContextCompat.getColor(Heads.this,R.color.ttt)); toedit.setBackgroundColor(ContextCompat.getColor(Heads.this,R.color.colorAccent1));
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                                show_Toast("Copied to clipboard ",Gravity.BOTTOM,Gravity.CENTER,2000);

                            }else{
                                toast1.show();
                            }

                        }
                    });
                        copynpaste(toedit.getText().toString());
                        return true;

                }
                return false;
            }
        });
        toedit1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:toedit1.setTextColor(ContextCompat.getColor(Heads.this,R.color.navigationBarColor)); toedit1.setBackgroundColor(ContextCompat.getColor(Heads.this,R.color.textColorPrimary));
                        return true;
                    case MotionEvent.ACTION_UP:toedit1.setTextColor(ContextCompat.getColor(Heads.this,R.color.textColorPrimary)); toedit1.setBackgroundColor(ContextCompat.getColor(Heads.this,R.color.colorAccent1));
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                                    show_Toast("Copied to clipboard ",Gravity.BOTTOM,Gravity.CENTER,2000);

                                }else{
                                    toast1.show();
                                }


                            }
                        });
                        copynpaste(toedit1.getText().toString());
                        return true;
                }
                return false;
            }
        });

final View heightnw = translatorhead1.findViewById(R.id.parlinearheight);
LinearLayout linearLayout=(LinearLayout) translatorhead1.findViewById(R.id.parlinearheight);
        heightnw.post(new Runnable() {
            @Override
            public void run() {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                int height1 = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;
          dragndrop.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height1));
            }
        });

    setclickingforfrom();
        setclickingforto();

    }

private void par_Close() {
    RelativeLayout alert_box = translatorhead1.findViewById(R.id.alert_box);
   if(alert_box.getVisibility()==View.GONE){
       stopp(true);
   }else{
       alert_box.setVisibility(View.INVISIBLE);
       new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
           @Override
           public void run() {
               alert_box.setVisibility(View.GONE);
           }
       },500);
   }
}
public void stopp(boolean stop){
        if(stop){
            stopSelf();
        }
}
private void setuprecycler(int id,String b[],String from){
    recyclerView=(RecyclerView) translatorhead1.findViewById(id);
    applist = new ArrayList<>();
    adapter = new adapter(Heads.this, applist);
    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(Heads.this, 1);
    recyclerView.setLayoutManager(mLayoutManager);

    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(adapter);
    for(int n=0; n<fromtext.length;n++){
        Appdetails appdetails = new Appdetails(b[n],n,from);
        applist.add(appdetails);

    }
}
private void show_Toast(String value,int gravity,int gravity2,int delay){
 TextView textView=translatorhead1.findViewById(R.id.toaster);
 textView.setText(value);
 textView.setVisibility(View.VISIBLE);
 textView.setGravity(gravity|gravity2);
 textView.postDelayed(new Runnable() {
     @Override
     public void run() {
         textView.setVisibility(View.GONE);
     }
 },delay);
}
    private void show_Toast1(String value,int delay){
        TextView textView=translatorhead1.findViewById(R.id.toaster1);
        textView.setText(value);

        textView.setVisibility(View.VISIBLE);

        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setVisibility(View.GONE);
            }
        },delay);
    }
private void setclickingforfrom(){
    Button cancel = translatorhead1.findViewById(R.id.cancel);
    Button okay = translatorhead1.findViewById(R.id.okay);

    okay.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:okay.setBackgroundColor(ContextCompat.getColor(Heads.this,R.color.ttt));
                    return true;
                case MotionEvent.ACTION_UP:okay.setBackgroundColor(ContextCompat.getColor(Heads.this,R.color.colorAccent1));
                    changeText(fromtext[checkforchecked],from);
                    loadurl();
                    LinearLayout fromparent = translatorhead1.findViewById(R.id.fromparent);
                    fromparent.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    });
    cancel.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:cancel.setBackgroundColor(ContextCompat.getColor(Heads.this,R.color.ttt));
                    return true;
                case MotionEvent.ACTION_UP:cancel.setBackgroundColor(ContextCompat.getColor(Heads.this,R.color.colorAccent1));
                    LinearLayout fromparent = translatorhead1.findViewById(R.id.fromparent);
                    fromparent.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    });

}

    private void setclickingforto(){
        Button cancel = translatorhead1.findViewById(R.id.cancel1);
        Button okay = translatorhead1.findViewById(R.id.okay1);

        okay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:okay.setBackgroundColor(ContextCompat.getColor(Heads.this,R.color.ttt));
                        return true;
                    case MotionEvent.ACTION_UP:okay.setBackgroundColor(ContextCompat.getColor(Heads.this,R.color.colorAccent1));
                        changeText(totext[checkforchecked1],to);
                        loadurl();
                        LinearLayout toparent = translatorhead1.findViewById(R.id.toparent);
                        toparent.setVisibility(View.GONE);
                        return true;
                }
                return false;
            }
        });
        cancel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:cancel.setBackgroundColor(ContextCompat.getColor(Heads.this,R.color.ttt));
                        return true;
                    case MotionEvent.ACTION_UP:cancel.setBackgroundColor(ContextCompat.getColor(Heads.this,R.color.colorAccent1));
                        LinearLayout toparent = translatorhead1.findViewById(R.id.toparent);
                        toparent.setVisibility(View.GONE);
                        return true;
                }
                return false;
            }
        });

    }



private int getWidth(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;
}
    private int getHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (translatorhead1 != null) {
         windowManager.removeView(translatorhead1);
        }
    }

    public void loadurl(){
showLoader();
        String gettext = fromedit.getText().toString();
        gettext = gettext.replaceAll(" ", "+");
        String url="https://translate.google.com/m?hl=en&sl="+fromshort[checkforchecked]+"&tl="+toshort[checkforchecked1]+"&ie=UTF-8&prev=_m&q="+gettext;
        try {
            run(url);
        } catch (IOException e) {
            // e.printStackTrace();
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
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
hideLoader();
if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
    show_Toast("Connection Error, Please review your Network Settings ",Gravity.BOTTOM,Gravity.CENTER,2000);
}else{
    toast.show();

}
                    }
                });

            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = response.body().string();
new Handler(Looper.getMainLooper()).post(new Runnable() {
    @Override
    public void run() {
        if(response.isSuccessful()){
hideLoader();
            String originaltext=res;
            String o="<div dir=\"(.*?)\" class=\"o1\">(.*?)</div>";
            String o1="<div dir=\"(.*?)\" class=\"t0\">(.*?)</div>";
            Pattern p=Pattern.compile(o,Pattern.MULTILINE|Pattern.CASE_INSENSITIVE);
            Pattern p1=Pattern.compile(o1,Pattern.MULTILINE|Pattern.CASE_INSENSITIVE);

            Matcher m=p.matcher(originaltext);
            Matcher m1=p1.matcher(originaltext);

            if(m.find()){
if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.N){
    toedit.setText(Html.fromHtml(m.group(2),Html.FROM_HTML_MODE_LEGACY));

}else{
    toedit.setText(Html.fromHtml(m.group(2)));

}
            }else{
                toedit.setText("");

            }
            if(m1.find()){
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
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
hideLoader();
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                show_Toast("Connection Error, Please review your Network Settings ",Gravity.BOTTOM,Gravity.CENTER,2000);
            }else{
                toast.show();
            }
        }


    }
});

            }
        });
    }
    public void showLoader(){
        toedit.setVisibility(View.GONE);
        toedit1.setVisibility(View.GONE);

        loader.setVisibility(View.VISIBLE);
    }
    public void hideLoader(){
        toedit.setVisibility(View.VISIBLE);
        toedit1.setVisibility(View.VISIBLE);

        loader.setVisibility(View.GONE);
    }



    protected void changeText(String text,TextView v){


        v.setText(text);

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


    private void writetosdcard(){

        int checkerfrom1=checkforchecked;
        int checkerto1=checkforchecked1;

        String text=fromedit.getText().toString();
        String translated1=toedit.getText().toString();
        String translated2=toedit1.getText().toString();

        File file = new File(Environment.getExternalStorageDirectory()+"/.Hexagon/");
        file.mkdirs();
        try{
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(new FileOutputStream(new File(Environment.getExternalStorageDirectory()+"/.Hexagon/","translated.hxg")));
            outputStreamWriter.write("<head>0</head> <text>"+text+"</text> <translated1>"+translated1+"</translated1> <translated2>"+translated2+"</translated2> <checkerfrom>"+checkerfrom1+"</checkerfrom> <checkerto>"+checkerto1+"</checkerto>");
            outputStreamWriter.close();
        }catch (IOException e){

        }
    }
    private String getfromsdcard(int a){
        String returnu="";
        try{
            InputStream inputStream=new FileInputStream(new File(Environment.getExternalStorageDirectory()+"/.Hexagon/translated.hxg"));
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

        return "";
    }

    public class Appdetails {
        public String title,from;
        public int index;

        public Appdetails(){

        }
        public Appdetails(String title,int index,String from){
            this.title=title;
            this.index=index;
            this.from=from;
        }
        public String gettitle(){
            return title;
        }

        public int getIndex(){
            return index;
        }
        public void setTitle(String title){
            this.title=title;
        }
        public void setIndex(int index){
            this.index=index;
        }
        public String getfrom(){
            return from;
        }
    }

    public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {
        private Context mContext;
        private List<Appdetails> applist;

        public  int checker;
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public RadioButton title;
            public  int index;


            public MyViewHolder(View view) {
                super(view);
                title=(RadioButton) view.findViewById(R.id.radiogroup);

            }


        }
        public adapter(Context mContext, List<Appdetails> applist){
            this.mContext = mContext;
            this.applist = applist;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.fromrecycler, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)  {
            Appdetails appdetails = applist.get(i);
            int a = appdetails.getIndex();
            String getfrom=appdetails.getfrom();
            myViewHolder.title.setText(appdetails.gettitle());
            if(getfrom.matches("from")) {
                myViewHolder.title.setChecked(checkforchecked == i);
            }else{
                myViewHolder.title.setChecked(checkforchecked1 == i);

            }

            myViewHolder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(getfrom.matches("from")) {
                       checkforchecked = i;
                       notifyDataSetChanged();
                   }else{
                       checkforchecked1 = i;
                       notifyDataSetChanged();
                   }

                }
            });
        }

        @Override
        public int getItemCount() {
            return applist.size();
        }

    }
    public void startNotify(){

         notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notify= new NotificationCompat.Builder(getApplicationContext(),"notify over apps")
                .setSmallIcon(R.drawable.ic_launcher_round1)
                .setContentTitle("Hexagon Translator")
                .setContentText("Translator is running.......")
                .setVibrate(null)
                .setSound(null)
                .setPriority(Notification.PRIORITY_MIN);
        notify.build().flags=Notification.FLAG_FOREGROUND_SERVICE|Notification.PRIORITY_MIN;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelid="Hexagon Translator over apps";
            NotificationChannel channel=new NotificationChannel(channelid,"Hexagon Translator over apps", NotificationManager.IMPORTANCE_MIN);
            channel.enableVibration(false);
            channel.setSound(null,null);
            notificationManager.createNotificationChannel(channel);
            notify.setChannelId(channelid);
        }
        notification=notify.build();

        startForeground(1300,notification);
    }

}
