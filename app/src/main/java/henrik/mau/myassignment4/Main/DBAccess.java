package henrik.mau.myassignment4.Main;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import androidx.room.Room;
import henrik.mau.myassignment4.CompassActivity.CompassController;
import henrik.mau.myassignment4.Database.Database;
import henrik.mau.myassignment4.Database.DatabaseAccess;
import henrik.mau.myassignment4.Entities.DataStepModel;
import henrik.mau.myassignment4.Entities.User;
import henrik.mau.myassignment4.StepActivity.StepController;

public class DBAccess {
    private static final String DATABASE_NAME = "MyAssignment4";
    private static Database database;
    private DatabaseAccess databaseAccess;

    public DBAccess(Context context, Controller controller) {
        database = Room.databaseBuilder(context, Database.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        databaseAccess = database.databaseAccess();
        Log.d("THISDATABASE", database.toString());
    }

    public DBAccess(Context context, StepController stepController) {
        database = Room.databaseBuilder(context, Database.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        databaseAccess = database.databaseAccess();
        Log.d("THISDATABASE", database.toString());
    }

    public DBAccess(Context context, CompassController compassController) {
        database = Room.databaseBuilder(context, Database.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        databaseAccess = database.databaseAccess();
        Log.d("THISDATABASE", database.toString());
    }

    public void addUser(User user) {
        databaseAccess.addUser(user);
    }

    public String getName(String name) {
        return databaseAccess.getName(name);
    }

    public void addStepEntry(DataStepModel dataStepModel) {
        databaseAccess.addStepEntry(dataStepModel);
    }

    public List<DataStepModel> getStepEntries(int userId) {
        return databaseAccess.getStepEntries(userId);
    }

    public int getUserId(String name, String password) {
        return databaseAccess.getUserId(name, password);
    }

    public void incrementSteps(int userId, String todaysDate) {
        databaseAccess.incrementSteps(userId, todaysDate);
    }

    public int getSteps(int userId, String date) {
        return databaseAccess.getTodaySteps(userId, date);
    }
}
