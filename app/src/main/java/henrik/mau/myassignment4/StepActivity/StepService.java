package henrik.mau.myassignment4.StepActivity;

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

import java.util.Timer;
import java.util.TimerTask;

public class StepService extends Service implements SensorEventListener {
    private static final String TAG = "DEBUG";
    private StepActivity activity;
    private IBinder mBinder;
    public int secondsPassed = 0;
    public int steps = 0;
    public static Timer timer;
    private SensorManager sensorManager;
    private Sensor mStepSensor;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            mStepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new StepService.StepServiceBinder();
        sensorManager.registerListener(this, mStepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        startServiceTasks();
        return mBinder;
    }

    public void unregisterListener() {
        sensorManager.unregisterListener(this, mStepSensor);
    }

    public void startServiceTasks() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                secondsPassed++;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("SECONDSPASSED", "SECONDSPASSED");
                        activity.sendSecondsPassedToController(secondsPassed);
                        activity.updateStepsPerSecond();
                    }
                });
            }
        }, 1000, 1000);
    }

    public void stopTimer() {
        timer.cancel();
    }

    public int getSecondsPassed() {
        return secondsPassed;
    }

    public void setSecondsPassed(int secondsPassed) {
        this.secondsPassed = secondsPassed;
    }

    public void setListenerActivity(StepActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(activity != null) {
            activity.update();
            steps++;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.sendStepsTakenThisSessionToController(steps);
                }
            });
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class StepServiceBinder extends Binder {
        StepService getService() {
            return StepService.this;
        }
    }
}
