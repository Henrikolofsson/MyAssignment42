package henrik.mau.myassignment4.StepActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import henrik.mau.myassignment4.Adapters.EntriesAdapter;
import henrik.mau.myassignment4.Entities.DataStepModel;
import henrik.mau.myassignment4.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {
    private StepController stepController;
    private View view;
    private RecyclerView rvStepEntries;
    private EntriesAdapter entriesAdapter;
    private TextView tvShowSecondsTotal;
    private TextView tvTotalSteps;
    private TextView tvStepsPerSecond;
    private Button btnReset;
    private List<DataStepModel> listOfEntries;

    public StepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_step, container, false);
        initializeComponents(view);
        stepController.initializeListOfDataStepModel();
        registerListeners();
        return view;
    }

    //TODO: Initialize all the components, - add user name, password and id in bundle, get the information in datastepmodel and present in fragment
    // Also add the service and sensor listeners

    private void initializeComponents(View view) {
        if(listOfEntries == null) {
            listOfEntries = new ArrayList<>();
        }
        entriesAdapter = new EntriesAdapter(getActivity(), listOfEntries);
        rvStepEntries = (RecyclerView) view.findViewById(R.id.rv_entries);
        rvStepEntries.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvStepEntries.setAdapter(entriesAdapter);

        tvTotalSteps = (TextView) view.findViewById(R.id.tv_total_steps);
        tvStepsPerSecond = (TextView) view.findViewById(R.id.tv_steps_second);
        tvShowSecondsTotal = (TextView) view.findViewById(R.id.tv_show_seconds_total);
        btnReset = (Button) view.findViewById(R.id.btn_reset);
    }

    private void registerListeners() {
        btnReset.setOnClickListener(new ResetListener());
    }

    public void setStepController(StepController stepController) {
        this.stepController = stepController;
    }

    public void setListOfEntries(List<DataStepModel> listOfEntries) {
        if(listOfEntries != null) {
            this.listOfEntries = listOfEntries;
            entriesAdapter.setContent(listOfEntries);
            entriesAdapter.notifyDataSetChanged();
        }
    }

    public void setSecondsPassed(int secondsPassed) {
        Log.d("SECONDSPASSED" , "SECONDSPASSED");
        tvShowSecondsTotal.setText(String.valueOf(secondsPassed));
    }

    public void setStepsTakenThisSession(int steps) {
        tvTotalSteps.setText(String.valueOf(steps));
    }

    public List<DataStepModel> getListOfEntries() {
        return listOfEntries;
    }

    public void updateStepsPerSecond() {
        int steps = Integer.valueOf(tvTotalSteps.getText().toString());
        int seconds = Integer.valueOf(tvShowSecondsTotal.getText().toString());
        float stepsPerSecond = ((float) ((float)steps / seconds));
        String stepsPerSecondsString = String.valueOf(stepsPerSecond);
        String formatted = String.format(Locale.ENGLISH, "%.2f", new BigDecimal(stepsPerSecondsString));
        Log.d("Steps", "Steps:" + steps + " Seconds:" + seconds + " StepsPerSecond:" + stepsPerSecond);
        tvStepsPerSecond.setText(formatted);
    }

    private class ResetListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            stepController.initializeListOfDataStepModel();
        }
    }

}
