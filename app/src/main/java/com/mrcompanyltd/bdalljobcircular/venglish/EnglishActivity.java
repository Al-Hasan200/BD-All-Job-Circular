package com.mrcompanyltd.bdalljobcircular.venglish;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mrcompanyltd.bdalljobcircular.R;
import com.mrcompanyltd.bdalljobcircular.activity.PreparationActivity;
import com.mrcompanyltd.bdalljobcircular.activity.TopicActivity;
import com.mrcompanyltd.bdalljobcircular.allclass.NetworkChangeReciver;
import com.mrcompanyltd.bdalljobcircular.allclass.NewspaperModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EnglishActivity extends AppCompatActivity {

    //======== variables ========
    TextView titleText;
    BroadcastReceiver broadcastReceiver;
    ImageView backArrowJobView, bannerQuiz3;
    MediaPlayer mediaPlayer;
    boolean sound;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int checkedItem;
    private String selected;
    private final String CHECKEDITEM = "checked_item";

    LottieAnimationView loadingAnimation;
    RecyclerView recyclerView;
    EnglishAdapter englishAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<NewspaperModel> arrayList = new ArrayList<>();
    NewspaperModel newspaperModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);

        //========== server data show in recyclerView code here ==========
        loadingAnimation = findViewById(R.id.loadingAnimation);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(EnglishActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        englishAdapter = new EnglishAdapter(EnglishActivity.this, arrayList);
        //myAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(englishAdapter);
        //========== load govt job data method call here ==========
        loadNewspaperData();

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

        titleText = findViewById(R.id.textView);

    }

    //======== method loadUrl job apply ========
    private void loadUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    //========== load govt job data method code here ==========
    private void loadNewspaperData() {
        loadingAnimation.setVisibility(View.VISIBLE);
        loadingAnimation.playAnimation();

        String url = "https://spinier-worms.000webhostapp.com/bdjob/english/english_view.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(JSONArray response) {
                loadingAnimation.setVisibility(View.GONE);

                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String pdf = jsonObject.getString("pdf");
                        String tittle = jsonObject.getString("tittle");
                        String date = jsonObject.getString("date");
                        String view = jsonObject.getString("view");

                        newspaperModel = new NewspaperModel(id, pdf, tittle, date, view);
                        arrayList.add(newspaperModel);
                        englishAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EnglishActivity.this, "এমসিকিউ লোড হতে সমস্যা হচ্ছে...", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(EnglishActivity.this);
        requestQueue.add(jsonArrayRequest);

    }

    //========== back button click method code here ==========
    private void backButtonClick(){
        backArrowJobView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksound();
                startActivity(new Intent(EnglishActivity.this, TopicActivity.class));
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
        startActivity(new Intent(EnglishActivity.this, TopicActivity.class));
        finish();
    }
}