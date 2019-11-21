package henrik.mau.myassignment4.Database;
import androidx.room.RoomDatabase;
import henrik.mau.myassignment4.Entities.DataStepModel;
import henrik.mau.myassignment4.Entities.User;

@androidx.room.Database(entities = {User.class, DataStepModel.class}, version = 14, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract DatabaseAccess databaseAccess();
}
