package henrik.mau.myassignment4.CompassActivity;


import android.hardware.SensorManager;
import android.util.Log;

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

    public void rotateCompass(float azimuthInDegrees) {
        Log.d("SENSORORIENTDETECTED", "INSIDEROTATECONTROLLER");
        compassFragment.rotateCompass(azimuthInDegrees);
    }

    public void animate(float x, float y, float z) {
        Log.d("DEBUG", "ANIMATION START");
        float gravityX = x / SensorManager.GRAVITY_EARTH;
        float gravityY = y / SensorManager.GRAVITY_EARTH;
        float gravityZ = z / SensorManager.GRAVITY_EARTH;

        float gravityForce = (float) Math.sqrt((gravityX * gravityX) + (gravityY * gravityY) + (gravityZ * gravityZ));

        if(gravityForce > SHAKE_THRESHOLD) {
            final long newTimeStamp = System.currentTimeMillis();
            if(mTimeStamp + SHAKE_SLOP_TIME_MS > newTimeStamp) {
                return;
            }
            mTimeStamp = newTimeStamp;

            animationFactor = rand.nextInt((5) + 1);
            compassFragment.rotateCompass(animationFactor *  360, animationFactor * 500);
            //compassFragment.rotateCompass(360, animationFactor * 500);
        }
    }

    public void setPictureNorth(float angleDegree) {
        compassFragment.setPictureNorth(angleDegree);
    }
}
