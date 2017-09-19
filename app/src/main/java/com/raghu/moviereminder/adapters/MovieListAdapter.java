package com.raghu.moviereminder.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.raghu.moviereminder.R;
import com.raghu.moviereminder.pojos.VenueDetails;
import com.raghu.moviereminder.pojos.VenueNames;

import java.util.ArrayList;
import java.util.Map;

/**
 * Display list in recycler view
 * Created by sragh_000 on 016 16/09/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> implements View.OnClickListener, Filterable {
    private static final String TAG = "MovieListAdapter";

    //https://in.bookmyshow.com/booktickets/PVHY/74586
    private static final String BMS_BASE_URL = "https://in.bookmyshow.com/booktickets/";

    private ArrayList<String> theatres;
    private ArrayList<String> filteredList;
    private String theatreCode;
    private ArrayList<VenueDetails> venues;

    public MovieListAdapter(ArrayList<String> theatres, String theatreCode) {
        this.theatres = theatres;
        this.theatreCode = theatreCode;
        this.filteredList = theatres;
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
        String theatre = filteredList.get(position);
        tv.setText(VenueNames.getTheatreName(theatre));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.cardView.getLayoutParams();
        @SuppressLint("RtlHardcoded") int gravity = (position %2 == 0) ? Gravity.LEFT : Gravity.RIGHT;
        params.gravity = gravity;
        holder.cardView.setLayoutParams(params);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);

        if(theatre.equals(theatreCode)) {
            holder.cardView.setCardElevation(30);
            int bgColor = ContextCompat.getColor(tv.getContext(), R.color.cardview_dark_background);
            holder.cardView.setBackgroundColor(bgColor);
        }
        else {
            holder.cardView.setCardElevation(10);
            int bgColor = ContextCompat.getColor(tv.getContext(), R.color.cardview_light_background);
            holder.cardView.setBackgroundColor(bgColor);
        }
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
        int index;
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
        return this.filteredList == null? 0 : this.filteredList.size();
    }

    public void setVenues(ArrayList<VenueDetails> venues) {
        this.venues = venues;
    }

    public void setTheatreCode(@NonNull String theatreCode) {
        this.theatreCode = theatreCode;
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

    @Override
    public Filter getFilter() {
        return mNameFilter;
    }

    private final Filter mNameFilter = new NameFilter();

    private final class NameFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (TextUtils.isEmpty(charSequence)) {
                results.values = theatres;
                results.count = theatres.size();
                return results;
            }
            Log.d(TAG, "Filtering");
            Map<String, String> movieNamesMap = VenueNames.getTheatreMap(theatres);
            ArrayList<String> resultValues = new ArrayList<>();
            for(Map.Entry<String, String> entry : movieNamesMap.entrySet()) {
                String name = entry.getValue();
                if(TextUtils.isEmpty(name)) {
                    continue;
                }

                if(name.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                    resultValues.add(entry.getKey());
                }
            }
            results.values = resultValues;
            results.count = resultValues.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            //noinspection unchecked
            MovieListAdapter.this.filteredList = (ArrayList<String>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
