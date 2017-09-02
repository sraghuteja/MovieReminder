package com.raghu.moviereminder;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Activity";
    private MovieListAdapter adapter;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver receiver;

    private ArrayList<String> theatres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theatres = getIntent().getStringArrayListExtra("theatreList");

        RecyclerView listView = (RecyclerView) findViewById(R.id.listView);
        broadcastManager = LocalBroadcastManager.getInstance(this);
        receiver = new MovieReceiver();

        if(theatres == null) {
            theatres = new ArrayList<>();
        }

        adapter = new MovieListAdapter(theatres);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        findViewById(R.id.bms_launch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.bt.bms");
                try {
                    startActivity(launchIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchContents();
            }
        });
    }

    private void fetchContents() {
        Intent fetch = new Intent(MovieService.ACTION_FETCH);
        broadcastManager.sendBroadcast(fetch);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent serviceIntent = new Intent(this, MovieService.class);
        serviceIntent.putExtra(MovieService.URL, MovieService.movieUrl);
        serviceIntent.putExtra(MovieService.THEATRES, "INHY");
        startService(serviceIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        broadcastManager.registerReceiver(receiver, new IntentFilter(MovieService.ACTION_NOTIFY));
        fetchContents();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fetchContents();
    }

    @Override
    protected void onPause() {
        super.onPause();
        broadcastManager.unregisterReceiver(receiver);
    }

    class MovieReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Received Broadcast");
            ArrayList<String> arrayList = intent.getStringArrayListExtra("result");
            Log.d(TAG, "Size of array: " + (arrayList == null?-1:arrayList.size()));
            if(theatres == null) {
                theatres = new ArrayList<>();
            }
            Set<String> currentTheatres = new HashSet<>(theatres);
            for(String string:intent.getStringArrayListExtra("result")) {
                if(currentTheatres.contains(string)) {
                    continue;
                }
                theatres.add(string);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private class MovieListAdapter extends RecyclerView.Adapter<ViewHolder> {

        private ArrayList<String> theatres;

        MovieListAdapter(ArrayList<String> theatres) {

            this.theatres = theatres;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View view = inflater.inflate(R.layout.list_element, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TextView tv = holder.textView;
            tv.setText(VenueNames.getTheatreName(theatres.get(position)));
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.cardView.getLayoutParams();
            @SuppressLint("RtlHardcoded") int gravity = (position %2 == 0) ? Gravity.LEFT : Gravity.RIGHT;
            params.gravity = gravity;
            holder.cardView.setLayoutParams(params);
        }

        @Override
        public int getItemCount() {
            return this.theatres == null? 0 : this.theatres.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.list_element_name);
            cardView = (CardView) itemView.findViewById(R.id.list_element_card);
        }
    }
}
