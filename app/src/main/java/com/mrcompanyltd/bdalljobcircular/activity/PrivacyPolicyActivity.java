package com.mrcompanyltd.bdalljobcircular.activity;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.mrcompanyltd.bdalljobcircular.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    //======== variables ========
    private ImageButton backarroPP;
    WebView webView;
    MediaPlayer mediaPlayer;
    boolean sound;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int checkedItem;
    private String selected;
    private final String CHECKEDITEM = "checked_item";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

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

        //======== privacy policy show code ========
        webView = findViewById(R.id.webView);
        //webView.setBackgroundColor(R.color.ccolor);
        webView.getSettings().getJavaScriptEnabled();
        webView.loadUrl("file:///android_asset/privacyPolicy.html");

        //======== back arrow code ========
        backarroPP = findViewById(R.id.backarrowPP);
        backarroPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound();
                Intent intent = new Intent(PrivacyPolicyActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //========== set tap sound media player code here ==========
        mediaPlayer = MediaPlayer.create(PrivacyPolicyActivity.this, R.raw.sound);
    }

    //======== click sound method code here ========
    public void clicksound(){
        if (sound){
            mediaPlayer.start();
        }else {

        }
    }

    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences = getSharedPreferences(" "+getString(R.string.app_name), MODE_PRIVATE);
        sound = sharedPreferences.getBoolean("sound", false);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clicksound();
        startActivity(new Intent(PrivacyPolicyActivity.this, MainActivity.class));
        finish();
    }

    //======== dark mode code here ========
    private int getCheckedItem(){
        return sharedPreferences.getInt(CHECKEDITEM, 0);
    }
}