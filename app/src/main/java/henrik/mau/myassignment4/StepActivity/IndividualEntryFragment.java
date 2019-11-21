package henrik.mau.myassignment4.StepActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import henrik.mau.myassignment4.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndividualEntryFragment extends Fragment {


    public IndividualEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_individual_entry, container, false);
    }

}
