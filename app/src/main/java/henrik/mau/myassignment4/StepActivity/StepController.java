package henrik.mau.myassignment4.StepActivity;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import henrik.mau.myassignment4.AsyncTasks.FetchDataStepModelEntries;
import henrik.mau.myassignment4.AsyncTasks.IncrementStepsIntoDatabase;
import henrik.mau.myassignment4.AsyncTasks.InsertStepEntryForToday;
import henrik.mau.myassignment4.Entities.DataStepModel;
import henrik.mau.myassignment4.Main.DBAccess;

public class StepController {
    private StepActivity activity;
    private DBAccess dbAccess;

    private StepFragment stepFragment;

    private String userName;
    private String userPassword;
    private int userId;

    private ArrayList<DataStepModel> listOfEntries;

    private String dateToday;
    private boolean dateTodayExist = false;

    public StepController(StepActivity activity) {
        this.activity = activity;
        initializeDatabase();
        setDateToday();
        initializeStepFragment();
    }

    private void initializeDatabase() {
        dbAccess = new DBAccess(activity, this);
    }

    private void initializeStepFragment() {
        stepFragment = (StepFragment) activity.getFragment("StepFragment");
        if(stepFragment == null) {
            stepFragment = new StepFragment();
            stepFragment.setStepController(this);
            activity.setFragment(stepFragment, "StepFragment");
        }
    }

    public void initializeListOfDataStepModel() {
       FetchDataStepModelEntries fetchDataStepModelEntries = new FetchDataStepModelEntries(this, dbAccess);
       fetchDataStepModelEntries.execute();
    }

    public void setListOfDataStepModels(final List<DataStepModel> listOfDataStepModels) {
        this.listOfEntries = (ArrayList)listOfDataStepModels;

       for(int i = 0; i < listOfEntries.size(); i++) {
           Log.d("LISTOFDATASTEPMODELS", listOfEntries.get(i).toString());
       }

       checkIfDateTodayIsInListOfStepModels();

       activity.runOnUiThread(new Runnable() {
           @Override
           public void run() {
               stepFragment.setListOfEntries(listOfEntries);
           }
       });
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

    public String getDateToday() {
        return dateToday;
    }

    public void setDateToday() {
        this.dateToday = getTodayDate();
    }

    public boolean isDateTodayExist() {
        return dateTodayExist;
    }

    public void setDateTodayExist(boolean dateTodayExist) {
        this.dateTodayExist = dateTodayExist;
    }

    public void checkIfDateTodayIsInListOfStepModels() {
            for(int i = 0; i < listOfEntries.size(); i++) {
                if (listOfEntries.get(i).getDate().equals(getDateToday())) {
                    dateTodayExist = true;
                }
            }

            if (!dateTodayExist) {
                InsertStepEntryForToday insertStepEntryForToday = new InsertStepEntryForToday(this, dbAccess);
                insertStepEntryForToday.execute();
            }
    }

    public String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public void setSecondsPassed(int secondsPassed) {
        stepFragment.setSecondsPassed(secondsPassed);
    }

    public void setStepsTakenThisSession(int steps) {
        stepFragment.setStepsTakenThisSession(steps);
    }

    public void updateStepsPerSecond() {
        stepFragment.updateStepsPerSecond();
    }

    public void incrementSteps() {
        IncrementStepsIntoDatabase incrementStepsIntoDatabase = new IncrementStepsIntoDatabase(this, dbAccess);
        incrementStepsIntoDatabase.execute();
    }

    public void getStepsFromToday() {
        FetchDataStepModelEntries fetchDataStepModelEntries = new FetchDataStepModelEntries(this, dbAccess);
        fetchDataStepModelEntries.execute();
    }
}
