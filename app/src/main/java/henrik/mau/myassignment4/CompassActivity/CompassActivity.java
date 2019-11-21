package henrik.mau.myassignment4.CompassActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import henrik.mau.myassignment4.R;
import henrik.mau.myassignment4.StepActivity.StepController;

public class CompassActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private CompassController compassController;
    private CompassServiceConnection compassServiceConnection;
    public CompassService myService;
    public boolean serviceBound = false;
    private Intent compassIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        initializeActivity();
        setUserInformation();
        setCompassServiceConnection();
    }

    private void initializeActivity() {
        fragmentManager = getSupportFragmentManager();
        compassController = new CompassController(this);
    }

    private void setUserInformation() {
        String userName = getIntent().getExtras().getString("Username");
        String userPassword = getIntent().getExtras().getString("Password");
        int userId = getIntent().getExtras().getInt("Id");
        compassController.setUserName(userName);
        compassController.setUserPassword(userPassword);
        compassController.setUserId(userId);
    }

    private void setCompassServiceConnection() {
        compassServiceConnection = new CompassServiceConnection(this);
    }

    public void setFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.compass_main_container, fragment, tag);
        ft.commit();
    }

    public Fragment getFragment(String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }


    public void rotateCompass(float azimuthInDegrees) {
        Log.d("SENSORORIENTDETECTED", "INSIDEROTATEACTIVITY");
        compassController.rotateCompass(azimuthInDegrees);
    }

    public void animate(float value, float value1, float value2) {
        compassController.animate(value, value1, value2);
    }

    public void setPictureNorth(float angleInDegree) {
        compassController.setPictureNorth(angleInDegree);
    }

    @Override
    protected void onStart() {
        super.onStart();
        compassIntent = new Intent(this, CompassService.class);
        bindService(compassIntent, compassServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serviceBound) {
            unbindService(compassServiceConnection);
            serviceBound = false;
        }
    }
}
