package com.example.kelly79126.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kelly79126.flickster.R;
import com.example.kelly79126.flickster.movies.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by kelly79126 on 2017/2/18.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie>{
    // View lookup cache
    static class ViewHolder {
        @BindView(R.id.iv_poster) ImageView poster;
        @BindView(R.id.txt_title) TextView title;
        @BindView(R.id.txt_overview) TextView overview;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }

    private Context mContext;

    public MovieArrayAdapter(Context context, List<Movie> movies){
        super(context, android.R.layout.simple_list_item_1, movies);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder(convertView);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data from the data object via the viewHolder object
        // into the template view.
        int orientation = mContext.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getPosterPath()).fit().transform(new RoundedCornersTransformation(15, 15)).placeholder(R.drawable.placeholder).into(viewHolder.poster);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext()).load(movie.getBackdropPath()).fit().transform(new RoundedCornersTransformation(15, 15)).placeholder(R.drawable.placeholderland).into(viewHolder.poster);
        }

        viewHolder.title.setText(movie.getTitle());
        viewHolder.overview.setText(movie.getOverview());

        return convertView;
    }
}
