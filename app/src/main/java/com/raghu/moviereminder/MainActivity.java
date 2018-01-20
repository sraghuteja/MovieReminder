package com.raghu.moviereminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.raghu.moviereminder.adapters.MovieListAdapter;
import com.raghu.moviereminder.fragments.MovieAndTheatreSelection;
import com.raghu.moviereminder.interfaces.ParameterListener;
import com.raghu.moviereminder.pojos.VenueDetails;
import com.raghu.moviereminder.utils.CommonUtils;
import com.raghu.moviereminder.utils.Preferences;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements ParameterListener {
    private static final String TAG = "MainActivity";
    private MovieListAdapter adapter;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver receiver;

    private ArrayList<String> theatres;

    private String movieUrl;
    private Set<String> theatreCode;

    private DialogFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theatres = getIntent().getStringArrayListExtra("theatreList");

        RecyclerView listView = findViewById(R.id.listView);
        broadcastManager = LocalBroadcastManager.getInstance(this);
        receiver = new MovieReceiver();

        if (theatres == null) {
            theatres = new ArrayList<>();
        }
        movieUrl = Preferences.getMovieUrl(this);
        theatreCode = Preferences.getTheatreCode(this);

        adapter = new MovieListAdapter(theatres, theatreCode);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (TextUtils.isEmpty(movieUrl) || CommonUtils.isCollectionEmpty(theatreCode)) {
            getUserInput();
        } else {
            fetchData();
        }
        findViewById(R.id.bms_launch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.bt.bms");
                try {
                    startActivity(launchIntent);
                } catch (Exception e) {
                    Log.e(TAG, "Cannot start activity", e);
                }
            }
        });

        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchContents();
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopService = new Intent(MainActivity.this, MovieService.class);
                stopService.setAction(MovieService.ACTION_STOP);
                startService(stopService);
            }
        });
    }

    private void getUserInput() {
        if (fragment == null) {
            fragment = MovieAndTheatreSelection.newInstance(theatreCode, movieUrl);
        }
        fragment.show(getSupportFragmentManager(), TAG);
    }

    private void fetchContents() {
        Intent fetch = new Intent(MovieService.ACTION_FETCH);
        broadcastManager.sendBroadcast(fetch);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_change_parameters:
                getUserInput();
                break;
        }
        return true;
    }

    private void fetchData() {
        Intent serviceIntent = new Intent(this, MovieService.class);
        serviceIntent.setAction(MovieService.ACTION_NOTIFY);
        serviceIntent.putExtra(MovieService.URL, movieUrl);
        serviceIntent.putExtra(MovieService.THEATRES, theatreCode.toArray(new String[theatreCode
                .size()]));
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

    @Override
    public void setMovieAndTheatre(@NonNull String movieUrl, @NonNull Set<String> theatreCode) {
        Log.e(TAG, "Url: " + movieUrl);
        Log.e(TAG, "Theatre: " + theatreCode);
        this.movieUrl = movieUrl;
        this.theatreCode = theatreCode;
        Preferences.setMovieUrl(this, movieUrl);
        Preferences.setTheatreCode(this, theatreCode);
        if(this.adapter != null) {
            adapter.setTheatreCode(theatreCode);
        }
        fetchData();
    }

    class MovieReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Received Broadcast");
            ArrayList<String> arrayList = intent.getStringArrayListExtra("result");
            if (!theatres.isEmpty()) {
                theatres.clear();
            }
            if(arrayList == null) {
                return;
            }

            String movieName = intent.getStringExtra(MovieService.MOVIE_NAME);
            if(!TextUtils.isEmpty(movieName)) {
                setActionBarTitle(movieName);
            }

            ArrayList<VenueDetails> venues = intent.getParcelableArrayListExtra(MovieService.THEATRES);
            Log.d(TAG, "Size of array: " + arrayList.size());
            if (theatres == null) {
                theatres = new ArrayList<>();
            }
            theatres.addAll(arrayList);
            adapter.setVenues(venues);
            adapter.notifyDataSetChanged();
        }
    }

    private void setActionBarTitle(CharSequence title) {
        ActionBar bar = getSupportActionBar();
        if(bar == null) {
            return;
        }
        bar.setTitle(title);
    }
}
