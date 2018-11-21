package rilma.example.com.sweetculinary.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rilma.example.com.sweetculinary.R;
import rilma.example.com.sweetculinary.adapters.RecipeAdapter;
import rilma.example.com.sweetculinary.models.Recipe;
import rilma.example.com.sweetculinary.utils.BakingClient;
import rilma.example.com.sweetculinary.utils.BakingService;
import rilma.example.com.sweetculinary.utils.Network;

public class MainActivity extends AppCompatActivity {
    
    public static final String RECIPE_JSON_STATE = "recipe_json_state";
    public static final String RECIPE_ARRAYLIST_STATE = "recipe_arraylist_state";

    BakingService bakingService;
    RecipeAdapter recipeAdapter;
    String jsonResult;
    ArrayList<Recipe> recipeList = new ArrayList<>();

    @BindView(R.id.rv_recipe) RecyclerView recyclerView;

    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
/*
        if(findViewById(R.id.recipe_tablet) != null){
            isTablet = true;
        }
        else{
            isTablet = false;
        }
*/
        if(savedInstanceState != null){
            jsonResult = savedInstanceState.getString(RECIPE_JSON_STATE);
            recipeList = savedInstanceState.getParcelableArrayList(RECIPE_ARRAYLIST_STATE);
            recipeAdapter = new RecipeAdapter(MainActivity.this, recipeList, jsonResult);
            RecyclerView.LayoutManager layoutManager;
            if(isTablet){
                layoutManager = new GridLayoutManager(MainActivity.this, 2);
            }
            else{
                layoutManager = new LinearLayoutManager(MainActivity.this);
            }

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recipeAdapter);
        }
        else{
            if(Network.isConnected(this)){
                bakingService = new BakingClient().bakingService;
                new FetchRecipesAsync().execute();
            }
            else{
                displayErrorMessage();
            }

        }

    }

    private void displayErrorMessage() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(rilma.example.com.sweetculinary.R.string.no_network);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                rilma.example.com.sweetculinary.R.string.reload_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                        startActivity(getIntent());
                    }
                });

        builder1.setNegativeButton(
                rilma.example.com.sweetculinary.R.string.close_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        MainActivity.this.finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchRecipesAsync extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            fetchRecipes();
            return null;
        }
    }

    // Fetch recipes
    private void fetchRecipes() {
        Call<ArrayList<Recipe>> call = bakingService.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {

                recipeList = response.body();

                jsonResult = new Gson().toJson(response.body());

                recipeAdapter = new RecipeAdapter(MainActivity.this, recipeList, jsonResult);
                RecyclerView.LayoutManager layoutManager;
                if(isTablet){
                    layoutManager = new GridLayoutManager(MainActivity.this, 2);
                }
                else{
                    layoutManager = new LinearLayoutManager(MainActivity.this);
                }

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(recipeAdapter);

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d("Failed to import json", t.toString());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RECIPE_JSON_STATE, jsonResult);
        outState.putParcelableArrayList(RECIPE_ARRAYLIST_STATE, recipeList);
    }
}