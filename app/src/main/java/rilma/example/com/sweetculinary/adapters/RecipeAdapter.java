package rilma.example.com.sweetculinary.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rilma.example.com.sweetculinary.R;
import rilma.example.com.sweetculinary.models.Recipe;
import rilma.example.com.sweetculinary.utils.ConstantValues;
import rilma.example.com.sweetculinary.views.DetailsActivity;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{

    private final Context mContext;
    private final ArrayList<Recipe> recipeNameList;
    private String mJsonResult;
    String recipeJson;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeList, String jsonResult) {
        this.mContext = context;
        this.recipeNameList = recipeList;
        this.mJsonResult = jsonResult;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.recipeName.setText(recipeNameList.get(position).getName());

        switch (position){
            case 0 : holder.recipeImage.setImageResource(R.drawable.nutella_pie);
                break;
            case 1 : holder.recipeImage.setImageResource(R.drawable.brownies);
                break;
            case 2 : holder.recipeImage.setImageResource(R.drawable.yellow_cake);
                break;
            case 3 : holder.recipeImage.setImageResource(R.drawable.cheese_cake);
                break;
        }

        // On recipe click, get recipe details as parcelable and send to Details activity
        holder.recipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe recipe = recipeNameList.get(holder.getAdapterPosition());
                recipeJson = jsonToString(mJsonResult, holder.getAdapterPosition());
                Intent intent = new Intent(mContext, DetailsActivity.class);
                ArrayList<Recipe> recipeArrayList = new ArrayList<>();
                recipeArrayList.add(recipe);
                intent.putParcelableArrayListExtra(ConstantValues.RECIPE_INTENT_EXTRA, recipeArrayList);
                intent.putExtra(ConstantValues.JSON_RESULT_EXTRA, recipeJson);
                mContext.startActivity(intent);
                /*
                SharedPreferences.Editor editor = mContext.getSharedPreferences(ConstantValues.YUMMIO_SHARED_PREF, MODE_PRIVATE).edit();
                editor.putString(ConstantValues.JSON_RESULT_EXTRA, recipeJson);
                editor.apply();

                if(Build.VERSION.SDK_INT > 25){
                    //Start the widget service to update the widget
                    YummioWidgetService.startActionOpenRecipeO(mContext);
                }
                else{
                    //Start the widget service to update the widget
                    YummioWidgetService.startActionOpenRecipe(mContext);
                }*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.tv_recipe_name)
        TextView recipeName;

        @Nullable
        @BindView(R.id.recipe_image)
        ImageView recipeImage;

        @Nullable
        @BindView(R.id.parent_layout)
        LinearLayout recipeLayout;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }


    // Get selected Recipe as Json String
    private String jsonToString(String jsonResult, int position){
        JsonElement jsonElement = new JsonParser().parse(jsonResult);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        JsonElement recipeElement = jsonArray.get(position);
        return recipeElement.toString();
    }

}
