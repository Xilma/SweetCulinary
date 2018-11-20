package android.example.com.sweetculinary.views;

import android.example.com.sweetculinary.R;
import android.example.com.sweetculinary.models.Recipe;
import android.example.com.sweetculinary.utils.BakingClient;
import android.example.com.sweetculinary.utils.BakingService;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BakingService bakingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bakingService = new BakingClient().bakingService;
        fetchRecipes();
    }

    // Fetch recipes
    private void fetchRecipes() {
        Call<ArrayList<Recipe>> call = bakingService.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                //Test if response is succesfull
                ArrayList<Recipe> recipe = response.body();
                Log.d("BAKING_APP", recipe.get(0).getName());
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d("FAILURE", t.toString());
            }
        });
    }
}
