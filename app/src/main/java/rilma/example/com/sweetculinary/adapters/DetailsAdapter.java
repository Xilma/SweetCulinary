package rilma.example.com.sweetculinary.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rilma.example.com.sweetculinary.R;
import rilma.example.com.sweetculinary.models.Ingredient;
import rilma.example.com.sweetculinary.utils.ConstantValues;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder>{

    private final Context context;
    private final List<Ingredient> ingredientList;

    public DetailsAdapter(Context context, List<Ingredient> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);

        holder.ingredientName.setText(ingredient.getIngredient());
        holder.unitQuantity.setText(context.getString(R.string.quantity) + String.valueOf(ingredient.getQuantity()));

        String measure = ingredient.getMeasure();
        int unitNo = 0;

        for(int i = 0; i < ConstantValues.units.length; i++){
            if(measure.equals(ConstantValues.units[i])){
                unitNo = i;
                break;
            }
        }
        int unitImage = ConstantValues.unitImages[unitNo];
        Log.d("UNIT_NO: ", String.valueOf(unitImage));
        String unitLongName = ConstantValues.unitName[unitNo];

        holder.unitImage.setImageResource(unitImage);
        holder.ingredientMeasureName.setText(unitLongName);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.unit_image)
        ImageView unitImage;
        
        @BindView(R.id.tv_ingredient_name)
        TextView ingredientName;
        
        @BindView(R.id.tv_quantity)
        TextView unitQuantity;
        
        @BindView(R.id.tv_measure_name)
        TextView ingredientMeasureName;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

}

