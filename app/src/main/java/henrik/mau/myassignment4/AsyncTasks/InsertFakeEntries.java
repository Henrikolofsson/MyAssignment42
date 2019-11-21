package henrik.mau.myassignment4.AsyncTasks;

import android.os.AsyncTask;

import henrik.mau.myassignment4.Entities.DataStepModel;
import henrik.mau.myassignment4.Main.Controller;
import henrik.mau.myassignment4.Main.DBAccess;


public class InsertFakeEntries extends AsyncTask<String, String, Boolean> {
    private Controller controller;
    private DBAccess dbAccess;
    private int userId;
    private String date;
    private int steps;

    public InsertFakeEntries(Controller controller, DBAccess dbAccess, int userId, String date, int steps) {
        this.controller = controller;
        this.dbAccess = dbAccess;
        this.userId = userId;
        this.date = date;
        this.steps = steps;
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
        DataStepModel dataStepModel = new DataStepModel(userId, date, steps);
        dbAccess.addStepEntry(dataStepModel);
        return true;
    }
}
