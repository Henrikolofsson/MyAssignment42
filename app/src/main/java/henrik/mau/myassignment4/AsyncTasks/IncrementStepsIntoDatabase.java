package henrik.mau.myassignment4.AsyncTasks;

import android.os.AsyncTask;

import henrik.mau.myassignment4.Main.DBAccess;
import henrik.mau.myassignment4.StepActivity.StepController;

public class IncrementStepsIntoDatabase extends AsyncTask<String, String, Boolean> {
    private StepController controller;
    private DBAccess dbAccess;

    public IncrementStepsIntoDatabase(StepController controller, DBAccess dbAccess) {
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
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        dbAccess.incrementSteps(controller.getUserId(), controller.getTodayDate());
        return true;
    }
}
