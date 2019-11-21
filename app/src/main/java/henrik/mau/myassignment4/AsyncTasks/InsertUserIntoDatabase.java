package henrik.mau.myassignment4.AsyncTasks;

import android.os.AsyncTask;

import henrik.mau.myassignment4.Entities.User;
import henrik.mau.myassignment4.Main.Controller;
import henrik.mau.myassignment4.Main.DBAccess;


public class InsertUserIntoDatabase extends AsyncTask<String, String, Boolean> {
    private Controller controller;
    private DBAccess dbAccess;
    private boolean userExist;
    private String userName;
    private String userPassword;

    public InsertUserIntoDatabase(Controller controller, DBAccess dbAccess, String userName, String userPassword) {
        this.controller = controller;
        this.dbAccess = dbAccess;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean userExist) {
        super.onPostExecute(userExist);
        controller.userInsertedInDatabase(userExist);
        if(!userExist) {
            controller.setStartFragment();
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        String user_name_in_db = dbAccess.getName(userName);
        if(user_name_in_db == null) {
            userExist = false;
        } else {
            userExist = true;
        }

        if(!userExist) {
            User user = new User(userName, userPassword);
            dbAccess.addUser(user);
        }
        return userExist;
    }
}
