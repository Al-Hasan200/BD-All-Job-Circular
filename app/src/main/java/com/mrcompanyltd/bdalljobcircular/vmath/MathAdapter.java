package com.mrcompanyltd.bdalljobcircular.vmath;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mrcompanyltd.bdalljobcircular.R;
import com.mrcompanyltd.bdalljobcircular.allclass.NewspaperModel;

import java.util.ArrayList;

public class MathAdapter extends RecyclerView.Adapter<MathAdapter.JobViewHolder> {
    Context context;
    ArrayList<NewspaperModel> arrayList;
    int count = 0;

    public MathAdapter(Context context, ArrayList<NewspaperModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.newspaper_tittle, parent, false);
        return new JobViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        NewspaperModel newspaperModel = arrayList.get(position);

        holder.tittle.setText(newspaperModel.getTittle());
        holder.date.setText("তারিখঃ " + newspaperModel.getDate());
        holder.viewCount.setText("মোট ভিউঃ " + newspaperModel.getViewCount());
        //holder.idV.setText("মোট ভিউঃ " + newspaperModel.getId());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MathPdfviewActivity.class);
                intent.putExtra("pdf", newspaperModel.getPdf());
                intent.putExtra("tittle", newspaperModel.getTittle());
                context.startActivity(intent);
                String id1 = newspaperModel.getId();
                //count++;
                //holder.viewCount.setText(Integer.toString(count));
                //String c = holder.viewCount.getText().toString();

                String view1 = newspaperModel.getViewCount();
                String url = "https://spinier-worms.000webhostapp.com/bdjob/math/math_update.php?id=" + id1 + "&view=" + view1;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class JobViewHolder extends RecyclerView.ViewHolder {
        TextView tittle, date, viewCount;
        CardView cardView;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.tittle);
            date = itemView.findViewById(R.id.date);
            viewCount = itemView.findViewById(R.id.viewCount);
            //idV = itemView.findViewById(R.id.idV);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
