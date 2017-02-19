package com.example.kelly79126.flickster;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by kelly79126 on 2017/2/19.
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_detail_poster) ImageView detailPoster;
    @BindView(R.id.txt_detail_title) TextView txtTitle;
    @BindView(R.id.txt_detail_date) TextView txtDate;
    @BindView(R.id.txt_detail_language) TextView txtLanguage;
    @BindView(R.id.txt_detail_overview) TextView txtOverview;
    @BindView(R.id.txt_detail_popularity) TextView txtPopularity;
    @BindView(R.id.rb_vote_avg) RatingBar rbVoteAvg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        String posterPath = getIntent().getStringExtra("posterPath");
        String backdropPath = getIntent().getStringExtra("backdropPath");
        String tilte = getIntent().getStringExtra("tilte");
        String overview = getIntent().getStringExtra("overview");
        String releaseDate = getIntent().getStringExtra("releaseDate");
        String originalLanguage = getIntent().getStringExtra("originalLanguage");
        String popularity = getIntent().getStringExtra("popularity");
        String voteAverage = getIntent().getStringExtra("voteAverage");

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(this).load(backdropPath).fit().transform(new RoundedCornersTransformation(15, 15)).placeholder(R.drawable.placeholderland).into(detailPoster);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(this).load(posterPath).fit().transform(new RoundedCornersTransformation(15, 15)).placeholder(R.drawable.placeholder).into(detailPoster);
        }

        txtTitle.setText(tilte);
        txtDate.setText(releaseDate);
        txtLanguage.setText(originalLanguage);
        txtPopularity.setText(popularity);
        txtOverview.setText(overview);

        float rating = Float.parseFloat(voteAverage);
        rbVoteAvg.setRating(rating);
    }

}