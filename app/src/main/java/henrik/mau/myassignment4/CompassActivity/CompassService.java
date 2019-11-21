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


public class CompassService extends Service implements SensorEventListener {
    private CompassActivity activity;
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

    private static final float SHAKE_THRESHOLD = 2.7F;
    private static final int SHAKE_SLOP_TIME_MS = 500;

    private float x;
    private float y;
    private float z;
    private float last_x;
    private float last_y;
    private float last_z;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null && sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            isOrientAPI = true;
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null) {
            mOrient = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            Log.d("MORIENT", "MORIENT" + mOrient);
            isOrientAPI = false;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new CompassService.CompassServiceBinder();
        if(isOrientAPI) {
            sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_UI);
            //sensorManager.registerListener(this, mOrient, SensorManager.SENSOR_DELAY_UI);
        } else {

            sensorManager.registerListener(this, mOrient, SensorManager.SENSOR_DELAY_UI);
        }

        return mBinder;
    }

    public void setListenerActivity(CompassActivity activity) {
        this.activity = activity;
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
                Log.d("SHAKE DETECTED", "SHAKE DETECTED");
                //activity.animate(event.values[0], event.values[1], event.values[2]);
            }
            last_x = x;
            last_y = y;
            last_z = z;
        }
        else if(event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, lastMagnetometerValues, 0, event.values.length);
            lastMagnetometerSet = true;
        }
        else if(event.sensor == mOrient) {
            //Using orientation sensor
            Log.d("SENSORORIENTDETECTED", "SENSORISORIENT");
            Log.d("SENSORORIENTDETECTED", ""+lastAccelerometerSet);
            Log.d("SENSORORIENTDETECTED", ""+lastMagnetometerSet);
            Log.d("SENSORORIENTDETECTED", String.valueOf(System.currentTimeMillis() - lastTimeUpdate));
            if(lastAccelerometerSet && lastMagnetometerSet && System.currentTimeMillis() - lastTimeUpdate > 250) {

            SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometerValues, lastMagnetometerValues);
            SensorManager.getOrientation(rotationMatrix, orientationValues);

            float azimuthInRadians = orientationValues[0];
            float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians + 360) % 360;
                Log.d("SENSORORIENTDETECTED", "BEFOREROTATEACTIVITY");
            activity.rotateCompass(azimuthInDegrees);
            lastTimeUpdate = System.currentTimeMillis();


            //float angleDegree = event.values[0];
           // activity.setPictureNorth(angleDegree);

            }
        }

        }
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
