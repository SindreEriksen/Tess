package com.hfad.tess;



import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.graphics.Color.BLUE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mImages, ArrayList<String> mNames, ArrayList<String> mTypes, Context mContext) {
        this.mImages = mImages;
        this.mNames = mNames;
        this.mDescriptions = mTypes;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item, parent, false);
        ViewHolder holder = new ViewHolder(cv);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        final String intentName = mNames.get(position);

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(viewHolder.header_image);

        viewHolder.txt_name.setText(mNames.get(position));
        //viewHolder.txt_type.setText(mDescriptions.get(position));

        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("id", intentName);
                view.getContext().startActivity(intent);
            }
        });

        viewHolder.btn_lesMer.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(v.getContext(), DetailActivity.class);
                  intent.putExtra("id", intentName);
                  v.getContext().startActivity(intent);
              }
          }
        );
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        ImageView header_image;
        TextView txt_name;
        TextView txt_type;
        ConstraintLayout parent_layout;
        Button btn_lesMer;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            header_image = itemView.findViewById(R.id.img_header);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_type = itemView.findViewById(R.id.txt_type);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            btn_lesMer = itemView.findViewById(R.id.btn_material);
        }
    }

}
