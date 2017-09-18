package com.raghu.moviereminder.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.raghu.moviereminder.MovieService;
import com.raghu.moviereminder.R;
import com.raghu.moviereminder.interfaces.ParameterListener;
import com.raghu.moviereminder.pojos.VenueNames;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieAndTheatreSelection extends DialogFragment implements View.OnClickListener{
    private static final String TAG = "MovieAndTheatreSelectio";
    private static final Pattern BMS_URL_PATTERN = Pattern.compile("https://in.bookmyshow.com/buytickets/[a-z\\-0-9]+/movie-hyd-ET[0-9]{8}-MT/[0-9]{8}");

    private AutoCompleteTextView theatreText;
    private EditText movieUrlText;
    private Button submit;
    private ProgressBar progressBar;


    public MovieAndTheatreSelection() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if(window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_and_theatre_selection, container, false);

        theatreText = view.findViewById(R.id.theatre_selection);
        ArrayList<String> theatreNames = new ArrayList<>(VenueNames.getTheatreNameSet());
        ArrayAdapter<String> theatreAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, theatreNames);
        theatreText.setAdapter(theatreAdapter);
        movieUrlText = view.findViewById(R.id.movie_url_text);
        progressBar = view.findViewById(R.id.progress_bar);
        submit = view.findViewById(R.id.action_submit);
        submit.setOnClickListener(this);

        Button cancel = view.findViewById(R.id.action_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextFromArgs();
                dismiss();
            }
        });
        setTextFromArgs();
        return view;
    }

    private void setTextFromArgs() {
        Bundle args = getArguments();
        if(args != null) {
            String movieUrl = args.getString(MovieService.URL);
            String theatreCode = args.getString(MovieService.THEATRES);
            if (!TextUtils.isEmpty(theatreCode)) {
                theatreText.setText(VenueNames.getTheatreName(theatreCode));
            }

            if (!TextUtils.isEmpty(movieUrl)) {
                movieUrlText.setText(movieUrl);
            }
        }

        movieUrlText.setError(null);
    }

    @Override
    public void onClick(View view) {
        String movie = movieUrlText.getText().toString();
        if(TextUtils.isEmpty(movie)) {
            movieUrlText.setError(getString(R.string.error_text_blank));
            return;
        }

        String theatre = theatreText.getText().toString();
        if(TextUtils.isEmpty(theatre)) {
            theatreText.setError(getString(R.string.error_text_blank));
            return;
        }

        String theatreCode = VenueNames.getTheatreCode(theatre);
        submit.setVisibility(View.INVISIBLE);
        new CheckValidUrl(movie, theatreCode).execute();
    }

    public static DialogFragment newInstance(String theatreCode, String movieUrl) {
        DialogFragment fragment = new MovieAndTheatreSelection();
        Bundle bundle = new Bundle();
        bundle.putString(MovieService.THEATRES, theatreCode);
        bundle.putString(MovieService.URL, movieUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    private class CheckValidUrl extends AsyncTask<Void, Void, Integer> {
        private String movieUrl;
        private String theatre;

        CheckValidUrl(String movieUrl, String theatre) {
            this.movieUrl = movieUrl;
            this.theatre = theatre;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected Integer doInBackground(Void... strings) {
            Matcher matcher = BMS_URL_PATTERN.matcher(movieUrl);
            if(!matcher.matches()) {
                Log.e(TAG, "Url " + movieUrl + "\ndidn't match");
                return -2;
            }
            try {
                URL url = new URL(movieUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                return connection.getResponseCode();
            } catch (IOException e) {
                Log.e(TAG, String.valueOf(e.getMessage()), e);
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer responseCode) {
            super.onPostExecute(responseCode);
            if(progressBar != null) {
                progressBar.setVisibility(View.INVISIBLE);
            }
            if(responseCode == null) {
                Log.e(TAG, "This case should never arise");
                return;
            }

            switch(responseCode) {
                case -1:
                    Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    movieUrlText.setError(getString(R.string.invalid_url));
                    break;
                case 200:
                case 201:
                    Activity activity = getActivity();
                    if(activity != null && activity instanceof ParameterListener) {
                        ((ParameterListener) activity).setMovieAndTheatre(movieUrl, theatre);
                    }
                    dismiss();
                    return;
                case 400:
                case 404:
                case 500:
                    movieUrlText.setError(getString(R.string.invalid_url));
                    break;
                default:
                    Log.e(TAG, "Response code is: " + responseCode);
                    Toast.makeText(getContext(), R.string.error_general, Toast.LENGTH_SHORT).show();
                    break;
            }
            submit.setVisibility(View.VISIBLE);
        }
    }
}
