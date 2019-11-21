package henrik.mau.myassignment4.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import henrik.mau.myassignment4.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private Controller controller;
    private LinearLayout layoutStep;
    private LinearLayout layoutCompass;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initializeComponents(view);
        registerListeners();
        return view;
    }

    private void initializeComponents(View view) {
        layoutStep = (LinearLayout) view.findViewById(R.id.layoutStep);
        layoutCompass = (LinearLayout) view.findViewById(R.id.layoutCompass);
    }

    private void registerListeners() {
        layoutStep.setOnClickListener(new LayoutStepListener());
        layoutCompass.setOnClickListener(new LayoutCompassListener());
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class LayoutStepListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Step clicked", Toast.LENGTH_LONG).show();
            controller.startStepActivity();
        }
    }

    private class LayoutCompassListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Compass clicked", Toast.LENGTH_LONG).show();
            controller.startCompassActivity();
        }
    }

}
