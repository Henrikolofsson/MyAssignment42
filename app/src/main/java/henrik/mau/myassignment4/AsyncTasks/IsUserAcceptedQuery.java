package henrik.mau.myassignment4.AsyncTasks;


import android.os.AsyncTask;

import henrik.mau.myassignment4.Main.Controller;
import henrik.mau.myassignment4.Main.DBAccess;

public class IsUserAcceptedQuery extends AsyncTask<String, String, Boolean> {
    private Controller controller;
    private DBAccess dbAccess;
    private int userId;
    private String username;
    private String password;
    private boolean userCredentialsAccepted;

    public IsUserAcceptedQuery(Controller controller, DBAccess dbAccess, String username, String password) {
        this.controller = controller;
        this.dbAccess = dbAccess;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean userAccepted) {
        super.onPostExecute(userAccepted);
        if (userAccepted) {
            controller.setUserName(username);
            controller.setUserPassword(password);
            controller.toastMessage(userAccepted);
            controller.setMainFragment();
        } else {
            controller.toastMessage(userAccepted);
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        userId = dbAccess.getUserId(username, password);
        if (userId == 0) {
            userCredentialsAccepted = false;
        } else {
            userCredentialsAccepted = true;
            controller.setUserId(userId);
        }
        return userCredentialsAccepted;
    }
}
