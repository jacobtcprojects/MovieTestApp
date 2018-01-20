package com.challenge.jacobtcantera.movietestapp.presentation.view.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.challenge.jacobtcantera.movietestapp.R;
import com.challenge.jacobtcantera.movietestapp.domain.model.Movie;
import com.challenge.jacobtcantera.movietestapp.rest.response.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jacob on 20/01/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    List<Movie> listOfMovies = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie,
                        parent,
                        false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewTitle.setText(listOfMovies.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return listOfMovies.size();
    }

    public void setMovieList(List<Movie> list) {
        listOfMovies.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title) TextView textViewTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,
                    itemView);
        }
    }
}
