package com.raghu.moviereminder.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.raghu.moviereminder.R;
import com.raghu.moviereminder.pojos.VenueDetails;
import com.raghu.moviereminder.pojos.VenueNames;
import com.raghu.moviereminder.pojos.VenuePojo;

import java.util.ArrayList;

/**
 * Display list in recycler view
 * Created by sragh_000 on 016 16/09/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> implements View.OnClickListener{
    private static final String TAG = "MovieListAdapter";

    //https://in.bookmyshow.com/booktickets/PVHY/74586
    private static final String BMS_BASE_URL = "https://in.bookmyshow.com/booktickets/";

    private ArrayList<String> theatres;
    private ArrayList<VenueDetails> venues;

    public MovieListAdapter(ArrayList<String> theatres) {
        this.theatres = theatres;
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_element, parent, false);
        return new MovieListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, int position) {
        TextView tv = holder.textView;
        tv.setText(VenueNames.getTheatreName(theatres.get(position)));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.cardView.getLayoutParams();
        @SuppressLint("RtlHardcoded") int gravity = (position %2 == 0) ? Gravity.LEFT : Gravity.RIGHT;
        params.gravity = gravity;
        holder.cardView.setLayoutParams(params);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Integer pos = (Integer) view.getTag();
        if(pos == null) {
            return;
        }
        if(pos < 0 || pos >= venues.size()) {
            return;
        }
        VenueDetails vd = venues.get(pos);
        if(vd == null) {
            return;
        }
        int index = 0;
        if(vd.SessionId == null || vd.SessionId.isEmpty()) {
            return;
        }
        int showTime;
        switch (vd.SessionId.size()) {
            case 1:
                index = 0;
                break;
            case 2:
                showTime = vd.ShowTimeCode.get(1);
                if(showTime <= 2200) {
                    index = 1;
                }
                else {
                    index = 0;
                }
                break;
            case 3:
                showTime = vd.ShowTimeCode.get(1);
                if(showTime <= 2200) {
                    index = 2;
                }
                else {
                    index = 1;
                }
                break;
            case 4:
                index = 2;
                break;
            default:
                index = vd.SessionId.size() - 1;
                while(index >= 0) {
                    showTime = vd.ShowTimeCode.get(index);
                    if(showTime <= 2200) {
                        break;
                    }
                    else {
                        index--;
                    }
                }
                break;
        }
        String url = BMS_BASE_URL + vd.VenueCode + "/" + vd.SessionId.get(index);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        view.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return this.theatres == null? 0 : this.theatres.size();
    }

    public void setVenues(ArrayList<VenueDetails> venues) {
        this.venues = venues;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.list_element_name);
            cardView = itemView.findViewById(R.id.list_element_card);
        }
    }
}
