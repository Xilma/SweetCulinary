package rilma.example.com.sweetculinary.utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import rilma.example.com.sweetculinary.models.Recipe;

public interface BakingService {
    //Get list of recipes
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipes();
}
