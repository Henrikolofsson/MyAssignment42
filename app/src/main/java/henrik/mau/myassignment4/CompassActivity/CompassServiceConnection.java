package henrik.mau.myassignment4.CompassActivity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class CompassServiceConnection implements ServiceConnection {
    private final CompassActivity activity;
    private final CompassController controller;

    public CompassServiceConnection(CompassActivity activity, CompassController controller) {
        this.activity = activity;
        this.controller = controller;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        CompassService.CompassServiceBinder binder = (CompassService.CompassServiceBinder) service;
        activity.myService = binder.getService();
        activity.serviceBound = true;
        activity.myService.setListenerActivity(activity);
        activity.myService.setController(controller);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
