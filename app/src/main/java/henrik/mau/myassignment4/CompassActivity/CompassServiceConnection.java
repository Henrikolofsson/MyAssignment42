package henrik.mau.myassignment4.CompassActivity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class CompassServiceConnection implements ServiceConnection {
    private final CompassActivity activity;

    public CompassServiceConnection(CompassActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        CompassService.CompassServiceBinder binder = (CompassService.CompassServiceBinder) service;
        activity.myService = binder.getService();
        activity.serviceBound = true;
        activity.myService.setListenerActivity(activity);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
