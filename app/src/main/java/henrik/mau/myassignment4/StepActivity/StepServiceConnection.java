package henrik.mau.myassignment4.StepActivity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class StepServiceConnection implements ServiceConnection {
    private final StepActivity activity;

    public StepServiceConnection(StepActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        StepService.StepServiceBinder binder = (StepService.StepServiceBinder) service;
        activity.myService = binder.getService();
        activity.serviceBound = true;
        activity.myService.setListenerActivity(activity);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        activity.serviceBound = false;
        activity.myService.unregisterListener();
        activity.myService.stopTimer();
    }

    public void unregisterListenersAndStopTimer() {
        activity.serviceBound = false;
        activity.myService.unregisterListener();
        activity.myService.stopTimer();
    }
}
