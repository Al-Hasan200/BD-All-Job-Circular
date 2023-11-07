package com.mrcompanyltd.bdalljobcircular.allclass;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.mrcompanyltd.bdalljobcircular.R;

public class NetworkChangeReciver extends BroadcastReceiver {
    Dialog dialog;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            if (isOnline(context)){
                //Toast.makeText(context, "Internet Connected...", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }else {
                dialog = new Dialog(context);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations
                        = android.R.style.Animation_Dialog;
                //dialog.setContentView(R.layout.nointernet_dialog);
                dialog.setCancelable(false);
                //LottieAnimationView noInternetTapAnimation = dialog.findViewById(R.id.noInternetTapAnimation);
                //noInternetTapAnimation.playAnimation();
                dialog.show();

                //Toast.makeText(context, "No Internet Connection...", Toast.LENGTH_SHORT).show();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public boolean isOnline(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());

        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;

        }
    }
}
