package rilma.example.com.sweetculinary.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
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

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<Step> stepList;
    public OnStepClick onStepClick;
    private int rowNo;

    public StepAdapter(Context context, ArrayList<Step> stepArrayList, OnStepClick onStepClick, int rowNo) {
        this.context = context;
        this.stepList = stepArrayList;
        this.onStepClick = onStepClick;
        this.rowNo = rowNo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.step_number_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.stepTitle.setText(stepList.get(position).getShortDescription());
        holder.stepNumber.setText(String.valueOf(position+1));
        holder.stepTotal.setText(String.valueOf(position+1) + "/" + String.valueOf(stepList.size()-1));


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

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_step_number)
        TextView stepNumber;

        @BindView(R.id.tv_step_title)
        TextView stepTitle;

        @BindView(R.id.tv_step_total)
        TextView stepTotal;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}

