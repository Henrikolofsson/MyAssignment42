package henrik.mau.myassignment4.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import henrik.mau.myassignment4.Entities.DataStepModel;
import henrik.mau.myassignment4.Main.Controller;
import henrik.mau.myassignment4.R;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.Holder> {
    private LayoutInflater inflater;
    private List<DataStepModel> content;
    private Controller controller;

    public EntriesAdapter(Context context){
        this(context, new ArrayList<DataStepModel>());
    }

    public EntriesAdapter(Context context, List<DataStepModel> content){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.content = content;
    }

    public void setContent(List<DataStepModel> content){
        this.content = content;
        super.notifyDataSetChanged();
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = inflater.inflate(R.layout.fragment_individual_entry, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tvDate.setText(content.get(position).getDate());
        holder.tvSteps.setText(String.valueOf(content.get(position).getSteps()));
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class Holder extends RecyclerView.ViewHolder  {
        private TextView tvDate;
        private TextView tvSteps;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tv_individual_date);
            tvSteps = (TextView) itemView.findViewById(R.id.tv_individual_steps);
        }

    }
}
