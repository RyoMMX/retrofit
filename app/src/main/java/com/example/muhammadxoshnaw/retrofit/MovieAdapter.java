package com.example.muhammadxoshnaw.retrofit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.Hoder> {
    List<Movie> movies = new ArrayList<>();
    OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public Hoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Hoder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.movie_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Hoder holder, int position) {
        final Movie movie = movies.get(position);

        if (movie != null) {
            if (movie.getTitle() != null) {
                holder.name.setText(movie.getTitle());
            }

            if (movie.getPosterPath() != null) {
                if (position % 2 == 0) {
                    Picasso.get()
                            .load("https://image.tmdb.org/t/p/w500/" + movie.getPosterPath())
                            .into(holder.image);
                }

                if (position % 2 == 0) {
                    Picasso.get()
                            .load("https://image.tmdb.org/t/p/w500/" + movie.getBackdropPath())
                            .into(holder.image);
                }

            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(view, movie);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public class Hoder extends RecyclerView.ViewHolder {
        final TextView name;
        final ImageView image;

        public Hoder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
        }
    }

    interface OnItemClickListener {
        void onClick(View view, Movie movie);
    }
}
