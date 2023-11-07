package com.mrcompanyltd.bdalljobcircular.pdesignnews;

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
import com.mrcompanyltd.bdalljobcircular.allclass.JobModel;

import java.util.ArrayList;

public class DesignJobAdapter extends RecyclerView.Adapter<DesignJobAdapter.JobViewHolder> {
    Context context;
    ArrayList<JobModel> arrayList;

    public DesignJobAdapter(Context context, ArrayList<JobModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.job_tittle, parent, false);
        return new JobViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobModel jobModel2 = arrayList.get(position);

        holder.job_tittle.setText(jobModel2.getJob_tittle());
        holder.viewCount.setText("মোট ভিউঃ " + jobModel2.getViewCount());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DesignjobviewActivity.class);
                intent.putExtra("pdf", jobModel2.getPdf());
                intent.putExtra("jobTittle", jobModel2.getJob_tittle());
                intent.putExtra("jobApplyDate", jobModel2.getJob_apply_date());
                intent.putExtra("jobApplyEndDate", jobModel2.getJob_apply_end_date());
                intent.putExtra("jobSource", jobModel2.getJob_source());
                intent.putExtra("jobPlace", jobModel2.getJob_place());
                intent.putExtra("jobApplyLink", jobModel2.getJob_apply_link());
                context.startActivity(intent);

                String id1 = jobModel2.getId();
                //count++;
                //holder.viewCount.setText(Integer.toString(count));
                //String c = holder.viewCount.getText().toString();

                String view1 = jobModel2.getViewCount();
                String url = "https://spinier-worms.000webhostapp.com/bdjob/designjob/designjob_update.php?id=" + id1 + "&view=" + view1;
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
            job_tittle = itemView.findViewById(R.id.job_tittle);
            viewCount = itemView.findViewById(R.id.viewCount);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
