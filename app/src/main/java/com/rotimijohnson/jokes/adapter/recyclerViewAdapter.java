package com.rotimijohnson.jokes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rotimijohnson.jokes.R;
import com.rotimijohnson.jokes.models.jokes;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.recyclerHolder> {

    private Context context;
    private List<jokes> jokesArrayList;

    public recyclerViewAdapter(Context context, List<jokes> jokesArrayList) {
        this.context = context;
        this.jokesArrayList = jokesArrayList;
    }

    @NonNull
    @Override
    public recyclerViewAdapter.recyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jokes_row, parent, false);
        recyclerHolder recyclerHolder = new recyclerHolder(view);
        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapter.recyclerHolder holder, int position) {
        jokes alljokes = jokesArrayList.get(position);
        holder.body.setText(alljokes.getJoke());
        holder.category.setText(alljokes.getCategory());
        holder.id.setText(String.valueOf(alljokes.getId()));
    }

    @Override
    public int getItemCount() {
        return jokesArrayList.size();
    }

    public static class recyclerHolder extends RecyclerView.ViewHolder {
        private TextView body,category, id;
        public recyclerHolder(@NonNull View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.jokes_body);
            category = itemView.findViewById(R.id.category_joke);
            id = itemView.findViewById(R.id.joke_number);
        }
    }
}
