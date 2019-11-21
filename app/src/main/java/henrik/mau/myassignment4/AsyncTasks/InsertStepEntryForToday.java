package henrik.mau.myassignment4.AsyncTasks;

import android.os.AsyncTask;

import henrik.mau.myassignment4.Entities.DataStepModel;
import henrik.mau.myassignment4.Main.DBAccess;
import henrik.mau.myassignment4.StepActivity.StepController;


public class InsertStepEntryForToday extends AsyncTask<String, String, Boolean> {
    private StepController stepController;
    private DBAccess dbAccess;

    public InsertStepEntryForToday(StepController controller, DBAccess dbAccess) {
        this.stepController = controller;
        this.dbAccess = dbAccess;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        DataStepModel dataStepModel = new DataStepModel(stepController.getUserId(), stepController.getTodayDate(), 0);
        dbAccess.addStepEntry(dataStepModel);
        stepController.setListOfDataStepModels(dbAccess.getStepEntries(stepController.getUserId()));
        return true;
    }
}
