package android.example.com.sweetculinary.utils;

import android.example.com.sweetculinary.models.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingService {
    //Get list of recipes
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipes();
}
