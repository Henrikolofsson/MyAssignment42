package henrik.mau.myassignment4.AsyncTasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import henrik.mau.myassignment4.Entities.DataStepModel;
import henrik.mau.myassignment4.Main.DBAccess;
import henrik.mau.myassignment4.StepActivity.StepController;


public class FetchDataStepModelEntries extends AsyncTask<String, String, Boolean> {
    private StepController controller;
    private DBAccess dbAccess;

    public FetchDataStepModelEntries(StepController controller, DBAccess dbAccess) {
        this.controller = controller;
        this.dbAccess = dbAccess;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        //if(!controller.isDateTodayExist()) {
            //controller.checkIfDateTodayIsInListOfStepModels();
       // }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        List<DataStepModel> userEntryList = dbAccess.getStepEntries(controller.getUserId());
        controller.setListOfDataStepModels(userEntryList);
        return true;
    }
}
