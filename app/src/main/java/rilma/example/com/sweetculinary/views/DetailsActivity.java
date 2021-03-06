package rilma.example.com.sweetculinary.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import rilma.example.com.sweetculinary.IdlingResource.SimpleIdlingResource;
import rilma.example.com.sweetculinary.R;
import rilma.example.com.sweetculinary.adapters.DetailsAdapter;
import rilma.example.com.sweetculinary.models.Ingredient;
import rilma.example.com.sweetculinary.models.Recipe;
import rilma.example.com.sweetculinary.models.Step;
import rilma.example.com.sweetculinary.utils.ConstantValues;

public class DetailsActivity extends AppCompatActivity {

    public static final String RECIPE_LIST_STATE = "recipe_details_list";
    public static final String RECIPE_JSON_STATE = "recipe_json_list";

    @Nullable
    @BindView(R.id.rv_ingredients)
    RecyclerView mRecyclerView;

    @Nullable
    @BindView(R.id.bv_begin)
    Button beginCooking;

    DetailsAdapter detailsAdapter;
    ArrayList<Recipe> recipeList;
    ArrayList<Step> stepList;
    String jsonResult;
    ArrayList<Ingredient> ingredientList;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * From test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        boolean isTablet = findViewById(R.id.recipe_details_tablet) != null;

        if (getIntent().getStringExtra(ConstantValues.WIDGET_EXTRA) != null) {
            SharedPreferences sharedpreferences =
                    getSharedPreferences(ConstantValues.SWEET_CULINARY_SHARED_PREF, MODE_PRIVATE);
            String jsonRecipe = sharedpreferences.getString(ConstantValues.JSON_RESULT_EXTRA, "");
            jsonResult = jsonRecipe;

            Gson gson = new Gson();
            Recipe recipe = gson.fromJson(jsonRecipe, Recipe.class);

            stepList = (ArrayList<Step>) recipe.getSteps();
            ingredientList = (ArrayList<Ingredient>) recipe.getIngredients();
        } else {

            // Check if state saved
            if (savedInstanceState != null) {
                recipeList = savedInstanceState.getParcelableArrayList(RECIPE_LIST_STATE);
                jsonResult = savedInstanceState.getString(RECIPE_JSON_STATE);
                stepList = (ArrayList<Step>) recipeList.get(0).getSteps();
                ingredientList = (ArrayList<Ingredient>) recipeList.get(0).getIngredients();
            } else {
                // Get recipe from intent extra
                Intent recipeIntent = getIntent();
                recipeList = recipeIntent.getParcelableArrayListExtra(ConstantValues.RECIPE_INTENT_EXTRA);
                jsonResult = recipeIntent.getStringExtra(ConstantValues.JSON_RESULT_EXTRA);
                if(recipeList != null){
                    stepList = (ArrayList<Step>) recipeList.get(0).getSteps();
                    ingredientList = (ArrayList<Ingredient>) recipeList.get(0).getIngredients();
                    String title = recipeList.get(0).getName() + " - Ingredients";
                    setActionBarTitle(title);
                }else{
                    Log.e("NullList", "Recipe list is null");
                }

            }
        }

        detailsAdapter = new DetailsAdapter(this, ingredientList);

        RecyclerView.LayoutManager mLayoutManager;
        if (isTablet) {
            mLayoutManager = new GridLayoutManager(this, 2);
        } else {
            mLayoutManager = new LinearLayoutManager(this);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(detailsAdapter);


        beginCooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipeList!=null){
                    Intent intent = new Intent(DetailsActivity.this, TutorialActivity.class);
                    intent.putParcelableArrayListExtra(ConstantValues.STEP_INTENT_EXTRA, stepList);
                    intent.putExtra(ConstantValues.RECIPE_INTENT_EXTRA, recipeList.get(0).getName());
                    intent.putExtra(ConstantValues.JSON_RESULT_EXTRA, jsonResult);
                    startActivity(intent);
                }
                else{
                    Log.e("Null List", "Recipe list is null");
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_LIST_STATE, recipeList);
        outState.putString(RECIPE_JSON_STATE, jsonResult);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void setActionBarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }
}
