package com.mrcompanyltd.bdalljobcircular.activity;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mrcompanyltd.bdalljobcircular.R;

public class SettingActivity extends AppCompatActivity {

    //======== variables ========
    ImageView backArrowJobView;
    SwitchCompat switchCompat;
    MediaPlayer mediaPlayer;
    boolean sound;
    RelativeLayout relayout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int checkedItem;
    private String selected;
    private final String CHECKEDITEM = "checked_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

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

        relayout = findViewById(R.id.relayout);
        relayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksound();
                dialogShow();
            }
        });

        //========== find variables id method call here ==========
        findVariablesId();

        //========== back button click method call here ==========
        backButtonClick();

        //========== click sound implement code here ==========
        switchCompat = findViewById(R.id.soundSwitch);
        SharedPreferences sharedPreferences = getSharedPreferences(" "+getString(R.string.app_name), MODE_PRIVATE);
        switchCompat.setChecked(sharedPreferences.getBoolean("sound", false));

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksound();

                if (switchCompat.isChecked()){
                    SharedPreferences.Editor sharedPreferences = getSharedPreferences(" "+getString(R.string.app_name), MODE_PRIVATE).edit();
                    sharedPreferences.putBoolean("sound", true);
                    sharedPreferences.apply();
                    switchCompat.setChecked(true);
                }else {
                    SharedPreferences.Editor sharedPreferences = getSharedPreferences(" "+getString(R.string.app_name), MODE_PRIVATE).edit();
                    sharedPreferences.putBoolean("sound", false);
                    sharedPreferences.apply();
                    switchCompat.setChecked(false);
                }

            }
        });

        //========== set tap sound media player code here ==========
        mediaPlayer = MediaPlayer.create(SettingActivity.this, R.raw.sound);
    }

    //========== find variables id method code here ==========
    private void findVariablesId(){
        backArrowJobView = findViewById(R.id.backArrowJobView);

    }

    //========== back button click method code here ==========
    private void backButtonClick(){
        backArrowJobView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksound();
                startActivity(new Intent(SettingActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    //========== dark mode implement method code here ==========
    private void dialogShow() {
        String[] themes = this.getResources().getStringArray(R.array.theme);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this, R.style.myCheckedTextView);
        builder.setTitle("বাছাই করুন");
        builder.setSingleChoiceItems(R.array.theme, getCheckedItem(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clicksound();
                selected = themes[i];
                checkedItem = i;

            }
        });

        builder.setPositiveButton("ওকে", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clicksound();
                try {
                    if (selected == null){
                        selected = themes[i];
                        checkedItem = i;
                    }
                }catch (Exception e){
                    Toast.makeText(SettingActivity.this, "বাছাই করা আছে", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SettingActivity.this, SettingActivity.class));
                }


                switch (selected){
        case "সিস্টেম ডিফল্ট":
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
            break;
        case "ডার্ক মোড":
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            break;
        case "লাইট মোড":
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            break;
    }

                setCheckedItem(checkedItem);

            }
        });

        builder.setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clicksound();
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private int getCheckedItem(){
        return sharedPreferences.getInt(CHECKEDITEM, 0);
    }

    private void setCheckedItem(int i){
        editor.putInt(CHECKEDITEM, i);
        editor.apply();
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
        startActivity(new Intent(SettingActivity.this, MainActivity.class));
        finish();
    }
}