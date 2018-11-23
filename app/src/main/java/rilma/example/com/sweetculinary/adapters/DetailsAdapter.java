package rilma.example.com.sweetculinary.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rilma.example.com.sweetculinary.R;
import rilma.example.com.sweetculinary.models.Ingredient;
import rilma.example.com.sweetculinary.utils.ConstantValues;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Ingredient> mIngredientList;

    public DetailsAdapter(Context context, ArrayList<Ingredient> ingredientList) {
        this.context = context;
        this.mIngredientList = ingredientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_list_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if(mIngredientList!=null){
            holder.ingredientName.setText(mIngredientList.get(position).getIngredient());
            holder.unitQuantity.setText(context.getString(R.string.quantity) + String.valueOf(mIngredientList.get(position).getQuantity()));

            String measure = mIngredientList.get(position).getMeasure();
            int unitNo = 0;

            for (int i = 0; i < ConstantValues.units.length; i++) {
                if (measure.equals(ConstantValues.units[i])) {
                    unitNo = i;
                    break;
                }
            }
            int unitImage = ConstantValues.unitImages[unitNo];
            Log.d("UNIT_NO: ", String.valueOf(unitImage));
            String unitLongName = ConstantValues.unitName[unitNo];

            holder.unitImage.setImageResource(unitImage);
            holder.ingredientMeasureName.setText(unitLongName);
        }else{
            Log.e("NullList", "Ingredient List is null");
        }
    }

    @Override
    public int getItemCount() {
        if(mIngredientList!=null){
            return mIngredientList.size();
        }
        return Log.e("NullList", "Ingredient List is null");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.unit_image)
        ImageView unitImage;

        @Nullable
        @BindView(R.id.tv_ingredient_name)
        TextView ingredientName;

        @Nullable
        @BindView(R.id.tv_quantity)
        TextView unitQuantity;

        @Nullable
        @BindView(R.id.tv_measure_name)
        TextView ingredientMeasureName;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

}

