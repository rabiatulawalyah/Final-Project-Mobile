package com.example.finalproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.activities.PlanetDetailActivity;
import com.example.finalproject.model.Planet;

import java.io.Serializable;
import java.util.List;

public class PlanetAdapter extends RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder> {
    private List<Planet> planetList;
    private Context context;

    public PlanetAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Planet> planetList) {
        this.planetList = planetList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planet, parent, false);
        return new PlanetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanetViewHolder holder, int position) {
        Planet planet = planetList.get(position);
        holder.bind(planet);
    }

    @Override
    public int getItemCount() {
        return planetList != null ? planetList.size() : 0;
    }

    public class PlanetViewHolder extends RecyclerView.ViewHolder {
        private ImageView planetImageView;
        private TextView planetNameTextView;

        public PlanetViewHolder(@NonNull View itemView) {
            super(itemView);
            planetImageView = itemView.findViewById(R.id.planetImageView);
            planetNameTextView = itemView.findViewById(R.id.nameTextView);

            planetNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Planet planet = planetList.get(position);
                        Intent intent = new Intent(context, PlanetDetailActivity.class);
                        intent.putExtra("planet", (Serializable) planet);
                        context.startActivity(intent);
                    }
                }
            });

            planetImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Planet planet = planetList.get(position);
                        Intent intent = new Intent(context, PlanetDetailActivity.class);
                        intent.putExtra("planet", (Serializable) planet);
                        context.startActivity(intent);
                    }
                }
            });
        }

        public void bind(Planet planet) {
            Glide.with(context)
                    .load(planet.getImgSrc().getImg())
                    .into(planetImageView);
            planetNameTextView.setText(planet.getName());
        }
    }
}
