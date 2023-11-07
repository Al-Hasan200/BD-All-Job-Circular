package com.mrcompanyltd.bdalljobcircular.nexamresult;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.mrcompanyltd.bdalljobcircular.R;
import com.mrcompanyltd.bdalljobcircular.allclass.NetworkChangeReciver;

public class ExamresultviewActivity extends AppCompatActivity {

    //======== variables ========
    CardView jobviewquizCaed, jobCircularCard;
    ImageView bannerQuiz1;
    Button btn;
    TextView examNoticeTittle;
    TextView examNoticeDate;
    TextView examNoticeDetails;
    TextView examNoticeSource;
    BroadcastReceiver broadcastReceiver;
    private ImageView backArrowJobView;
    MediaPlayer mediaPlayer;
    boolean sound;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int checkedItem;
    private String selected;
    private final String CHECKEDITEM = "checked_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examresultview);

        //========== dark mode implement code here ==========
        sharedPreferences = this.getSharedPreferences("themes", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        switch (getCheckedItem()){
            case 0:
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }

        //========== find variables id method call here ==========
        findVariablesId();

        //========== back button click method call here ==========
        backButtonClick();

        //========== set Govt Job Data method call here ==========
        setGovtJobData();

        //======== open pdf activity method call here ========
        openPdfActivity();

        //======== internet connection check dialog method call here ========
        broadcastReceiver = new NetworkChangeReciver();
        registerNetworkbroadcastReceiver();

        //========== set tap sound media player code here ==========
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
    }

    //========== find variables id method code here ==========
    private void findVariablesId(){
        backArrowJobView = findViewById(R.id.backArrowJobView1);

        jobCircularCard = findViewById(R.id.jobCircularCard);

        examNoticeTittle = findViewById(R.id.examNoticeTittle);
        examNoticeDate = findViewById(R.id.examNoticeDate);
        examNoticeDetails = findViewById(R.id.examNoticeDetails);
        examNoticeSource = findViewById(R.id.examNoticeSource);

    }

    //========== set Govt Job Data method code here ==========
    @SuppressLint("SetTextI18n")
    private void setGovtJobData(){
        examNoticeTittle.setText("পরীক্ষার ফলাফলঃ " + getIntent().getStringExtra("examNoticeTittle"));
        examNoticeDate.setText("তারিখঃ " + getIntent().getStringExtra("examNoticeDate"));
        examNoticeDetails.setText("বিস্তারিতঃ " + getIntent().getStringExtra("examNoticeDetails"));
        examNoticeSource.setText("উৎসঃ " + getIntent().getStringExtra("examNoticeSource"));

        /*String applyUrl = getIntent().getStringExtra("jobApplyLink");
        jobApplyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadUrl(applyUrl);
            }
        });*/
    }

    //======== method loadUrl job apply ========
    private void loadUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    //======== open pdf activity method code here ========
    private void openPdfActivity(){
        jobCircularCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pdf = getIntent().getExtras().getString("pdf", "defaultKey");
                String jobTitle = getIntent().getExtras().getString("examNoticeTittle", "defaultKey");

                Intent intent = new Intent(new Intent(ExamresultviewActivity.this, ExamresultPdfviewActivity.class));
                intent.putExtra("p", pdf);
                intent.putExtra("jt", jobTitle);
                startActivity(intent);
                //finish();
            }
        });
    }

    //========== back button click method code here ==========
    private void backButtonClick(){
        backArrowJobView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksound();
                startActivity(new Intent(ExamresultviewActivity.this, ExamresultActivity.class));
                finish();
            }
        });
    }

    //======== click sound method code here ========
    public void clicksound(){
        if (sound){
            mediaPlayer.start();
        }else {

        }
    }

    //======== internet connection check dialog method code here ========
    protected void registerNetworkbroadcastReceiver() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unRegisterNetwork() {
        try {
            unregisterReceiver(broadcastReceiver);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterNetwork();
    }

    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences = getSharedPreferences(" "+getString(R.string.app_name), MODE_PRIVATE);
        sound = sharedPreferences.getBoolean("sound", false);
        super.onResume();
    }

    //======== dark mode code here ========
    private int getCheckedItem(){
        return sharedPreferences.getInt(CHECKEDITEM, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clicksound();
        startActivity(new Intent(ExamresultviewActivity.this, ExamresultActivity.class));
        finish();
    }
}