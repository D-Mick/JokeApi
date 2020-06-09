package com.rotimijohnson.jokes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rotimijohnson.jokes.adapter.recyclerViewAdapter;
import com.rotimijohnson.jokes.models.jokes;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private ApiServices apiServices;
    private final String TAG = MainActivity.class.getSimpleName();
    private Button getJokesBtn, iAmFeelingLucky;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;
    private EditText jokeText;
    private ProgressDialog progressDialog;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://sv443.net/jokeapi/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiServices = retrofit.create(ApiServices.class);

        jokeText = findViewById(R.id.joke_text);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        getJokesBtn = findViewById(R.id.get_joke_btn);
        getJokesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = jokeText.getText().toString();
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading");
                progressDialog.setCancelable(false);
                progressDialog.show();
                getJoke(text);
            }
        });

        iAmFeelingLucky = findViewById(R.id.im_feeling_lucky_btn);
        iAmFeelingLucky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading");
                progressDialog.setCancelable(false);
                progressDialog.show();
                feelingLucky();
            }
        });
    }

    private void getJoke(String text) {
        apiServices.getJoke(text).enqueue(new Callback<jokes>() {
            @Override
            public void onResponse(Call<jokes> call, Response<jokes> response) {
                progressDialog.dismiss();
                int statusCode = response.code();
                jokes joke = response.body();

                recyclerViewAdapter recyclerViewAdapter1 = new recyclerViewAdapter(getApplicationContext(), Collections.singletonList(joke));
                recyclerView.setAdapter(recyclerViewAdapter1);
            }

            @Override
            public void onFailure(Call<jokes> call, Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG, t.getMessage());
            }
        });
    }

    private void feelingLucky() {
        apiServices.getRandomJoke().enqueue(new Callback<jokes>() {
            @Override
            public void onResponse(Call<jokes> call, Response<jokes> response) {
                progressDialog.dismiss();
                jokes joke = response.body();


                String title = "Random Joke";
                alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle(title)
                        .setMessage(joke.getJoke())
                        .setPositiveButton("Boring", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("Ha-ha!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                alertDialogBuilder.show();

            }

            @Override
            public void onFailure(Call<jokes> call, Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG, t.getMessage());
            }
        });
    }


}
