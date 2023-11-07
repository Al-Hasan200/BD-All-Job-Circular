package com.mrcompanyltd.bdalljobcircular.nexamnotice;

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
import com.mrcompanyltd.bdalljobcircular.allclass.ExamnoticeModel;
import com.mrcompanyltd.bdalljobcircular.allclass.JobModel;

import java.util.ArrayList;

public class ExamnoticeAdapter extends RecyclerView.Adapter<ExamnoticeAdapter.JobViewHolder> {
    Context context;
    ArrayList<ExamnoticeModel> arrayList;

    public ExamnoticeAdapter(Context context, ArrayList<ExamnoticeModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_nnotice, parent, false);
        return new JobViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        ExamnoticeModel examnoticeModel = arrayList.get(position);

        holder.job_tittle.setText(examnoticeModel.getExamNoticeTittle());
        holder.viewCount.setText("মোট ভিউঃ " + examnoticeModel.getViewCount());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExamnoticeviewActivity.class);
                intent.putExtra("pdf", examnoticeModel.getPdf());
                intent.putExtra("examNoticeTittle", examnoticeModel.getExamNoticeTittle());
                intent.putExtra("examNoticeDate", examnoticeModel.getExamNoticeDate());
                intent.putExtra("examNoticeDetails", examnoticeModel.getExamNoticeDetails());
                intent.putExtra("examNoticeSource", examnoticeModel.getExamNoticeSource());
                context.startActivity(intent);

                String id1 = examnoticeModel.getId();
                //count++;
                //holder.viewCount.setText(Integer.toString(count));
                //String c = holder.viewCount.getText().toString();

                String view1 = examnoticeModel.getViewCount();
                String url = "https://spinier-worms.000webhostapp.com/bdjob/examnotice/examnotice_update.php?id=" + id1 + "&view=" + view1;
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
        TextView job_tittle, viewCount;
        CardView cardView;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            job_tittle = itemView.findViewById(R.id.tittle);
            viewCount = itemView.findViewById(R.id.viewCount);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
