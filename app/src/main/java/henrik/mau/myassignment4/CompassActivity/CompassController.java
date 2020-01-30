package henrik.mau.myassignment4.CompassActivity;


import android.hardware.SensorManager;
import android.util.Log;
import android.view.animation.RotateAnimation;

import java.util.Random;

import henrik.mau.myassignment4.Main.DBAccess;

public class CompassController {
    private CompassActivity activity;
    private DBAccess dbAccess;

    private CompassFragment compassFragment;
    private static final float SHAKE_THRESHOLD = 2.7F;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private long mTimeStamp;
    private int animationFactor;
    private Random rand = new Random();

    private String userName;
    private String userPassword;
    private int userId;

    public CompassController(CompassActivity activity) {
        this.activity = activity;
        initializeDatabase();
        initializeCompassFragment();
    }

    private void initializeDatabase() {
        dbAccess = new DBAccess(activity, this);
    }

    private void initializeCompassFragment() {
        compassFragment = (CompassFragment) activity.getFragment("CompassFragment");
        if(compassFragment == null) {
            compassFragment = new CompassFragment();
            compassFragment.setCompassController(this);
            activity.setFragment(compassFragment, "CompassFragment");
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



    public void startAnimation(RotateAnimation rotateAnimation) {
        compassFragment.rotateCompass(rotateAnimation);
    }


}
