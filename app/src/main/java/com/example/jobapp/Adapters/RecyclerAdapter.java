package com.example.jobapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jobapp.Models.Ad;
import com.example.jobapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class RecyclerAdapter extends FirestoreRecyclerAdapter <Ad, RecyclerAdapter.AdViewHolder> {
    private OnItemClickListener listener;

    public RecyclerAdapter(@NonNull FirestoreRecyclerOptions<Ad> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdViewHolder holder, int position, @NonNull Ad model) {
        holder.ad_employer.setText(model.getUsername());
        holder.ad_position.setText(model.getPosition());
        holder.ad_highlight.setText(model.getHighlightedText());

    }

    @NonNull
    @Override
    public AdViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_content, viewGroup, false);
        return new AdViewHolder(v);

    }



    public class AdViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "AdViewHolder";
        TextView ad_employer;
        TextView ad_position;
        TextView ad_highlight;



        public AdViewHolder(View view){
            super(view);
            Log.d(TAG, "AdViewHolder: starts");
            this.ad_employer = itemView.findViewById(R.id.recycler_employer_name);
            this.ad_position = itemView.findViewById(R.id.recycler_position);
            this.ad_highlight = itemView.findViewById(R.id.recycler_higlight);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }

                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}

