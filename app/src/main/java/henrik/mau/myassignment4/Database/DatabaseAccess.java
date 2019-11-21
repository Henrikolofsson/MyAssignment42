package henrik.mau.myassignment4.Database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import henrik.mau.myassignment4.Entities.DataStepModel;
import henrik.mau.myassignment4.Entities.User;

@Dao
public interface DatabaseAccess {
    @Insert
    void addUser(User... user);

    @Query("SELECT user_name FROM user_table WHERE user_name= :name")
    String getName(String name);

    @Insert
    void addStepEntry(DataStepModel... dataStepModel);

    @Query("SELECT * FROM data_step_model WHERE user_id= :user_id")
    List<DataStepModel> getStepEntries(int user_id);

    @Query("SELECT user_id FROM user_table WHERE user_name= :name AND user_password= :password")
    int getUserId(String name, String password);

    @Query("UPDATE data_step_model SET steps=steps+1 WHERE user_id= :id AND date =:date")
    void incrementSteps(int id, String date);

    @Query("SELECT steps FROM data_step_model WHERE user_id=:id AND date= :date")
    int getTodaySteps(int id, String date);
}
