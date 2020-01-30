package henrik.mau.myassignment4.CompassActivity;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class CompassService extends Service implements SensorEventListener {
    private CompassActivity activity;
    private CompassController controller;
    private IBinder mBinder;

    private SensorManager sensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private Sensor mOrient;
    private boolean isOrientAPI;
    private boolean lastAccelerometerSet = false;
    private boolean lastMagnetometerSet = false;
    private float[] rotationMatrix = new float[16];
    private float[] lastAccelerometerValues = new float[3];
    private float[] lastMagnetometerValues = new float[3];
    private float[] orientationValues = new float[3];
    private long lastTimeUpdate = System.currentTimeMillis();
    private float currentDegree;

    private static final float SHAKE_THRESHOLD = 10F;
    private static final int SHAKE_SLOP_TIME_MS = 500;

    private float x;
    private float y;
    private float z;
    private float last_x;
    private float last_y;
    private float last_z;

    private long lastTimeShaked = 0;
    private long timeShaked;
    private boolean shakeAble = true;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        //If accelerometer and magnetometer is existing in phone - IsOrientAPI is true
        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null && sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            isOrientAPI = true;
            Log.d("SENSOR", "USING ACCELEROMETER AND MAGNETOMETER");
        }

        //If accelerometer and magnetometer does not exist in phone - IsOrientAPI is false - It will use Orient sensor instead
        if(!isOrientAPI) {
            if(sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null) {
                mOrient = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
                isOrientAPI = false;
                Log.d("SENSOR", "USING ORIENT SENSOR");
            }
            else {
                Log.d("SENSOR", "There is no accelerometer, magnetometer or orient sensor");
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new CompassService.CompassServiceBinder();
        if(isOrientAPI) {
            sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_UI);
        } else {
            sensorManager.registerListener(this, mOrient, SensorManager.SENSOR_DELAY_UI);
        }
        return mBinder;
    }

    public void setListenerActivity(CompassActivity activity) {
        this.activity = activity;
    }

    public void setController(CompassController compassController) {
        this.controller = compassController;
    }

    public void unregisterListener() {
        if(isOrientAPI) {
            sensorManager.unregisterListener(this, mAccelerometer);
            sensorManager.unregisterListener(this, mMagnetometer);
        } else {
            sensorManager.unregisterListener(this, mOrient);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(activity!= null) {
        //Using orientation api

            if(isOrientAPI) {
                if(event.sensor == mAccelerometer) {
                    System.arraycopy(event.values, 0, lastAccelerometerValues, 0, event.values.length);
                    lastAccelerometerSet = true;

                    x = event.values[0];
                    y = event.values[1];
                    z = event.values[2];
                    float deltaX = Math.abs(last_x - x);
                    float deltaY = Math.abs(last_y - y);
                    float deltaZ = Math.abs(last_z - z);


                    if((deltaX > SHAKE_THRESHOLD && deltaY > SHAKE_THRESHOLD)
                            || (deltaX > SHAKE_THRESHOLD && deltaZ > SHAKE_THRESHOLD)
                            || (deltaY > SHAKE_THRESHOLD && deltaZ > SHAKE_THRESHOLD)) {
                        timeShaked = System.currentTimeMillis() / 1000;

                        //If 5 seconds passed since last shake, the animation can begin
                        if(timeShaked - lastTimeShaked > 5) {
                            rotateUsingOrientationAPI(event);
                        }

                    }

                    last_x = x;
                    last_y = y;
                    last_z = z;
                    lastTimeShaked = timeShaked;
                }
                else if(event.sensor == mMagnetometer) {
                    System.arraycopy(event.values, 0, lastMagnetometerValues, 0, event.values.length);
                    lastMagnetometerSet = true;
                }
            }

            if(!isOrientAPI) {

            }

        }
    }

    private void rotateUsingOrientationAPI(SensorEvent event) {
        if(event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, lastAccelerometerValues, 0, event.values.length);
            lastAccelerometerSet = true;
        } else if(event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, lastMagnetometerValues, 0, event.values.length);
            lastMagnetometerSet = true;
        }

        if(lastAccelerometerSet && lastMagnetometerSet && System.currentTimeMillis() - lastTimeUpdate > 250) {
            SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometerValues, lastMagnetometerValues);
            SensorManager.getOrientation(rotationMatrix, orientationValues);
            float azimuthInRadians = orientationValues[0];
            float azimuthInDegrees = (float) (Math.toDegrees(azimuthInRadians) + 360) % 360;

            for(int i = 0; i < orientationValues.length; i++) {
                Log.d("SENSOR", String.valueOf(orientationValues[i]));
            }
            Log.d("SENSOR", String.valueOf(azimuthInRadians));
            Log.d("SENSOR", String.valueOf(azimuthInDegrees));
            /*
            RotateAnimation rotateAnimation = new RotateAnimation(currentDegree, -azimuthInDegrees,
                                                                    Animation.RELATIVE_TO_SELF, 0.5F,
                                                                    Animation.RELATIVE_TO_SELF, 0.5F);
*/
            RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                    Animation.RELATIVE_TO_SELF, 0.5F,
                    Animation.RELATIVE_TO_SELF, 0.5F);
            rotateAnimation.setDuration(250);
            rotateAnimation.setFillAfter(true);
            controller.startAnimation(rotateAnimation);
            currentDegree = -azimuthInDegrees;
            lastTimeUpdate = System.currentTimeMillis();
        }
    }

    private void rotateUsingOnlyOrientation(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public class CompassServiceBinder extends Binder {
        CompassService getService() {
            return CompassService.this;
        }
    }

}
