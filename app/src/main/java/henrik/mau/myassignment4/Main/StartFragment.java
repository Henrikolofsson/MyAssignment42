package henrik.mau.myassignment4.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import henrik.mau.myassignment4.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    private Controller controller;
    private EditText et_username;
    private EditText et_password;
    private Button btn_log_in;
    private Button btn_create_user;
    private Button btn_generate;


    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        initializeComponents(view);
        registerListeners();
        return view;
    }

    private void initializeComponents(View view) {
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_password = (EditText) view.findViewById(R.id.et_password);
        btn_log_in = (Button) view.findViewById(R.id.btn_login);
        btn_create_user = (Button) view.findViewById(R.id.btn_create_user);
        btn_generate = (Button) view.findViewById(R.id.btn_generate);
    }

    private void registerListeners() {
        btn_log_in.setOnClickListener(new ButtonLogInListener());
        btn_create_user.setOnClickListener(new ButtonCreateUserListener());
        btn_generate.setOnClickListener(new ButtonGenerateListener());
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class ButtonLogInListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String username = et_username.getText().toString();
            String password = et_password.getText().toString();
            controller.logIn(username, password);
        }
    }

    private class ButtonCreateUserListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.setCreateUserFragment();
        }
    }

    private class ButtonGenerateListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.generateFakeEntries();
        }
    }

}
