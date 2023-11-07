package com.mrcompanyltd.bdalljobcircular.activity;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.mrcompanyltd.bdalljobcircular.R;
import com.mrcompanyltd.bdalljobcircular.allclass.NetworkChangeReciver;
import com.mrcompanyltd.bdalljobcircular.sgovtjob.SlidergovtjobActivity;
import com.mrcompanyltd.bdalljobcircular.slidernewspaper.SlidernewspaperActivity;
import com.mrcompanyltd.bdalljobcircular.sliderprivatejob.SliderprivatejobActivity;
import com.mrcompanyltd.bdalljobcircular.slidertodayjob.SlidertodayjobActivity;
import com.mrcompanyltd.bdalljobcircular.todayjobnews.TodayjobActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //======== variables ========
    ImageSlider imageSlider;
    MediaPlayer mediaPlayer;
    ImageView bannerQuiz1;
    boolean sound;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int checkedItem;
    private String selected;
    private final String CHECKEDITEM = "checked_item";
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //======== navigation drawer method call here ========
        drawerLayout();

        //========== image slider method call here ==========
        imageSlider();

        //======== internet connection check dialog method call here ========
        broadcastReceiver = new NetworkChangeReciver();
        registerNetworkbroadcastReceiver();

        //========== set tap sound media player code here ==========
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sound);
    }

    //========== find variables id method code here ==========
    private void findVariablesId(){
        imageSlider = findViewById(R.id.image_slider);

        //======== card view find here for click listener ========
        findViewById(R.id.jobNewsCardView).setOnClickListener(this);
        findViewById(R.id.qualificationJobNewsCardView).setOnClickListener(this);
        findViewById(R.id.noticeCardView).setOnClickListener(this);
        findViewById(R.id.companyJobCardView).setOnClickListener(this);
        findViewById(R.id.preparationCardView).setOnClickListener(this);
        findViewById(R.id.topicCardView).setOnClickListener(this);
    }

    //======== navigation drawer method code here ========
    private void drawerLayout(){
        ImageButton imageButton = findViewById(R.id.menu);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound();
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {

                    case R.id.nav_home:
                        clicksound();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_shayari:
                        clicksound();
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        finish();
                        break;

                    default:
                        return true;

                }
                return true;
            }
        });
    }

    //========== image slider method code here ==========
    private void imageSlider(){
        ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();
        slideModelArrayList.add(new SlideModel(R.drawable.slidertodayjob, "আজকের চাকরির খবর", ScaleTypes.FIT));
        slideModelArrayList.add(new SlideModel(R.drawable.slidergovtjob, "সরকারি চাকরির খবর", ScaleTypes.FIT));
        slideModelArrayList.add(new SlideModel(R.drawable.sliderjobnews, "চাকরির পত্রিকা", ScaleTypes.FIT));
        slideModelArrayList.add(new SlideModel(R.drawable.sliderprivatejob, "বেসরকারি চাকরির খবর", ScaleTypes.FIT));

        imageSlider.setImageList(slideModelArrayList);

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case 0:
                        clicksound();
                        //Toast.makeText(HomeActivity.this, "Clicked " + i, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SlidertodayjobActivity.class));
                        finish();
                        break;
                    case 1:
                        clicksound();
                        startActivity(new Intent(MainActivity.this, SlidergovtjobActivity.class));
                        break;
                    case 2:
                        clicksound();
                        //Toast.makeText(HomeActivity.this, "Clicked " + i, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SlidernewspaperActivity.class));
                        break;
                    case 3:

                        clicksound();
                        //Toast.makeText(HomeActivity.this, "Clicked " + i, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SliderprivatejobActivity.class));
                        break;
                }
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

    //======== exit dialog method code here ========
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        clicksound();
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations
                = android.R.style.Animation_Dialog;
        //dialog.setContentView(R.layout.exit_dialog);
        dialog.setCancelable(false);
        //LottieAnimationView exitAnimation = dialog.findViewById(R.id.exitAnimation);
        //exitAnimation.playAnimation();

        /*ImageButton rate_btn = dialog.findViewById(R.id.rate_btn);
        ImageButton no_btn = dialog.findViewById(R.id.no_btn);
        ImageButton yes_btn = dialog.findViewById(R.id.yes_btn);*/

        /*rate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound();
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());
                Intent rate = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(rate);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Unable to Open", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        /*no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound();
                dialog.cancel();
            }
        });

        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound();
                finishAffinity();
            }
        });

        dialog.show();*/
    }

    //======== card view click code here ========
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.jobNewsCardView:
                clicksound();
                startActivity(new Intent(MainActivity.this, JobnewsActivity.class));
                finish();
                break;
            case R.id.qualificationJobNewsCardView:
                clicksound();
                startActivity(new Intent(MainActivity.this, QualificationActivity.class));
                finish();
                break;
            case R.id.noticeCardView:
                clicksound();
                startActivity(new Intent(MainActivity.this, NoticeActivity.class));
                finish();
                break;
            case R.id.companyJobCardView:
                clicksound();
                startActivity(new Intent(MainActivity.this, CompanyActivity.class));
                finish();
                break;
            case R.id.preparationCardView:
                clicksound();
                startActivity(new Intent(MainActivity.this, PreparationActivity.class));
                finish();
                break;
            case R.id.topicCardView:
                clicksound();
                startActivity(new Intent(MainActivity.this, TopicActivity.class));
                finish();
                break;
        }

    }

    //======== method loadUrl in queraka ========
    private void loadUrl(String string){
        Uri uri = Uri.parse(string);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
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
}