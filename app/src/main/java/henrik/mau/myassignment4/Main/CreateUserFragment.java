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
public class CreateUserFragment extends Fragment {
    private Controller controller;
    private EditText etUserName;
    private EditText userPassword;
    private Button btnCreateUser;

    public CreateUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_user, container, false);
        initializeComponents(view);
        registerListeners();
        return view;
    }

    private void initializeComponents(View view) {
        etUserName = (EditText) view.findViewById(R.id.et_create_username);
        userPassword = (EditText) view.findViewById(R.id.et_create_password);
        btnCreateUser = (Button) view.findViewById(R.id.btn_create_user);
    }

    private void registerListeners() {
        btnCreateUser.setOnClickListener(new CreateUserListener());
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class CreateUserListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String username = etUserName.getText().toString();
            String password = userPassword.getText().toString();

            if(username.length() > 0 && password.length() > 0) {
                controller.createUser(username, password);
            }
        }
    }

}
