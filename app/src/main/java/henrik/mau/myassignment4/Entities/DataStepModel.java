package henrik.mau.myassignment4.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_step_model")
public class DataStepModel {
    @PrimaryKey(autoGenerate = true)
    private int data_step_model_id;

    @ColumnInfo(name = "user_id")
    private int user_id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "steps")
    private int steps;

    public DataStepModel(int user_id, String date, int steps) {
        this.user_id = user_id;
        this.date = date;
        this.steps = steps;
    }

    public int getData_step_model_id() {
        return data_step_model_id;
    }

    public void setData_step_model_id(int data_step_model_id) {
        this.data_step_model_id = data_step_model_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "DataStepModel{" +
                "data_step_model_id=" + data_step_model_id +
                ", user_id=" + user_id +
                ", date='" + date + '\'' +
                ", steps=" + steps +
                '}';
    }
}
