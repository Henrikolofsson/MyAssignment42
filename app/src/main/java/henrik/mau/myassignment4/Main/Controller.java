package henrik.mau.myassignment4.Main;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import henrik.mau.myassignment4.AsyncTasks.InsertFakeEntries;
import henrik.mau.myassignment4.AsyncTasks.InsertUserIntoDatabase;
import henrik.mau.myassignment4.AsyncTasks.IsUserAcceptedQuery;


public class Controller {
    private MainActivity activity;
    private DBAccess dbAccess;

    private DataFragment dataFragment;
    private StartFragment startFragment;
    private CreateUserFragment createUserFragment;
    private MainFragment mainFragment;

    private String userName;
    private String userPassword;
    private int userId;

    public Controller(MainActivity activity) {
        this.activity = activity;
        initializeDatabase();
        initializeMainActivityFragments();
    }

    private void initializeDatabase() {
        dbAccess = new DBAccess(activity, this);
    }

    private void initializeMainActivityFragments() {
        initializeDataFragment();
        initializeStartFragment();
        initializeCreateUserFragment();
        initializeMainFragment();
    }

    private void initializeDataFragment() {
        dataFragment = (DataFragment) activity.getFragment("DataFragment");
        if(dataFragment == null){
            dataFragment = new DataFragment();
            activity.addFragment(dataFragment, "DataFragment");
            dataFragment.setActiveFragment("StartFragment");
        }
    }

    private void initializeStartFragment() {
        startFragment = (StartFragment) activity.getFragment("StartFragment");
        if(startFragment == null) {
            startFragment = new StartFragment();
            startFragment.setController(this);
            setFragment("StartFragment");
        }
    }

    private void initializeCreateUserFragment() {
        createUserFragment = (CreateUserFragment) activity.getFragment("CreateUserFragment");
        if(createUserFragment == null) {
            createUserFragment = new CreateUserFragment();
            createUserFragment.setController(this);
        }
    }

    private void initializeMainFragment() {
        mainFragment = (MainFragment) activity.getFragment("MainFragment");
        if(mainFragment == null) {
            mainFragment = new MainFragment();
            mainFragment.setController(this);
        }
    }

    public void setMainFragment() {
        setFragment("MainFragment");
    }

    public void setCreateUserFragment() {
        setFragment("CreateUserFragment");
    }

    public void setStartFragment() {
        setFragment("StartFragment");
    }

    private void setFragment(String tag) {
        switch (tag) {
            case "StartFragment":
                setFragment(startFragment, tag);
                break;

            case "CreateUserFragment":
                setFragment(createUserFragment, tag);
                break;

            case "MainFragment":
                setFragment(mainFragment, tag);
                break;

        }
    }

    private void setFragment(Fragment fragment, String tag) {
        activity.setFragment(fragment, tag);
        dataFragment.setActiveFragment(tag);
    }

    public boolean onBackPressed() {
        String activeFragment = dataFragment.getActiveFragment();

        switch(activeFragment){


        }
        return false;
    }

    public void logIn(String username, String password) {
        IsUserAcceptedQuery isUserAcceptedQuery = new IsUserAcceptedQuery(this, dbAccess, username, password);
        isUserAcceptedQuery.execute();
    }

    public void toastMessage(boolean userAccepted) {
        if(userAccepted) {
            Toast.makeText(activity, "The user is accepted, logged in", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "The user already exists, or can't log in", Toast.LENGTH_LONG).show();
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void generateFakeEntries() {
        InsertFakeEntries insertFakeEntry = new InsertFakeEntries(this, dbAccess, 1, "2019-11-04", 5000);
        insertFakeEntry.execute();

        InsertFakeEntries insertFakeEntry2 = new InsertFakeEntries(this, dbAccess, 1, "2019-11-05", 7500);
        insertFakeEntry2.execute();

        InsertFakeEntries insertFakeEntry3 = new InsertFakeEntries(this, dbAccess, 1, "2019-11-06", 9000);
        insertFakeEntry3.execute();
    }

    public void createUser(String username, String password) {
        InsertUserIntoDatabase insertUserIntoDatabase = new InsertUserIntoDatabase(this, dbAccess, username, password);
        insertUserIntoDatabase.execute();
    }

    public void userInsertedInDatabase(boolean userExisted) {
        if(!userExisted) {
            Toast.makeText(activity, "User added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "User already exist", Toast.LENGTH_LONG).show();
        }
    }

    public void startStepActivity() {
        activity.startStepActivity();
    }

    public void startCompassActivity() {
        activity.startCompassActivity();
    }
}
