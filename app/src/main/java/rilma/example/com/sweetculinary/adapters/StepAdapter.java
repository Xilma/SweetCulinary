package rilma.example.com.sweetculinary.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rilma.example.com.sweetculinary.R;
import rilma.example.com.sweetculinary.models.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepNumberHolder>{

    private final Context context;
    private final ArrayList<Step> stepList;
    public OnStepClick onStepClick;
    private int rowNo = 0;

    public StepAdapter(Context context, ArrayList<Step> stepArrayList, OnStepClick onStepClick, int rowNo) {
        this.context = context;
        this.stepList = stepArrayList;
        this.onStepClick = onStepClick;
        this.rowNo = rowNo;
    }

    public class StepNumberHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.tv_step_number)
        TextView stepNumber;

        @Nullable
        @BindView(R.id.tv_step_title)
        TextView stepTitle;

        public StepNumberHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    @NonNull
    @Override
    public StepNumberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.step_number_item, parent, false);

        return new StepNumberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepNumberHolder holder, int position) {

        holder.stepTitle.setText(stepList.get(position).getShortDescription());
        holder.stepNumber.setText(String.valueOf(position+1));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStepClick.onStepClick(holder.getAdapterPosition());
                rowNo = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

        if(rowNo == position){
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorBackground2));
        }
       /* else if(position == 0){
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }*/
        else
        {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorBackground3));
        }
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }


    public interface OnStepClick {
        void onStepClick(int position);
    }
}

