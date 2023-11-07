package com.mrcompanyltd.bdalljobcircular.activity;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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
import android.widget.ImageView;

import com.mrcompanyltd.bdalljobcircular.R;
import com.mrcompanyltd.bdalljobcircular.allclass.NetworkChangeReciver;
import com.mrcompanyltd.bdalljobcircular.classeight.ClasseightjobActivity;
import com.mrcompanyltd.bdalljobcircular.classgraduate.ClassgraduatejobActivity;
import com.mrcompanyltd.bdalljobcircular.classhsc.ClasshscjobActivity;
import com.mrcompanyltd.bdalljobcircular.classten.ClasstenjobActivity;
import com.mrcompanyltd.bdalljobcircular.todayjobnews.TodayjobActivity;

public class QualificationActivity extends AppCompatActivity implements View.OnClickListener {

    //======== variables ========
    BroadcastReceiver broadcastReceiver;
    ImageView backArrowJobView;
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
        setContentView(R.layout.activity_qualification);

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

        //======== internet connection check dialog method call here ========
        broadcastReceiver = new NetworkChangeReciver();
        registerNetworkbroadcastReceiver();

        //========== set tap sound media player code here ==========
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
    }

    //========== find variables id method code here ==========
    private void findVariablesId(){
        backArrowJobView = findViewById(R.id.backArrowJobView);

        //======== card view find here for click listener ========
        findViewById(R.id.eightPassJobNewsCardView).setOnClickListener(this);
        findViewById(R.id.sscPassJobNewsCardView).setOnClickListener(this);
        findViewById(R.id.hscPassJobCardView).setOnClickListener(this);
        findViewById(R.id.graduatePassJobCardView).setOnClickListener(this);

    }

    //========== back button click method code here ==========
    private void backButtonClick(){
        backArrowJobView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksound();
                startActivity(new Intent(QualificationActivity.this, MainActivity.class));
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
        startActivity(new Intent(QualificationActivity.this, MainActivity.class));
        finish();
    }

    //======== card view click code here ========
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.eightPassJobNewsCardView:
                clicksound();
                startActivity(new Intent(QualificationActivity.this, ClasseightjobActivity.class));
                finish();
                break;
            case R.id.sscPassJobNewsCardView:
                clicksound();
                startActivity(new Intent(QualificationActivity.this, ClasstenjobActivity.class));
                finish();
                break;
            case R.id.hscPassJobCardView:
                clicksound();
                startActivity(new Intent(QualificationActivity.this, ClasshscjobActivity.class));
                finish();
                break;
            case R.id.graduatePassJobCardView:
                clicksound();
                startActivity(new Intent(QualificationActivity.this, ClassgraduatejobActivity.class));
                finish();
                break;
        }
    }

    //======== method loadUrl in queraka ========
    private void loadUrl(String string){
        Uri uri = Uri.parse(string);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}