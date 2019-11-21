package henrik.mau.myassignment4.StepActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import henrik.mau.myassignment4.Interface.ChangeListener;
import henrik.mau.myassignment4.R;

public class StepActivity extends AppCompatActivity implements ChangeListener {
    private FragmentManager fragmentManager;
    private StepController stepController;

    public StepService myService;
    public boolean serviceBound = false;
    private StepServiceConnection mServiceConnection;
    private Intent stepsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        initializeActivity();
        setUserInformation();
        setUpServiceConnection();
    }

    private void initializeActivity() {
        fragmentManager = getSupportFragmentManager();
        stepController = new StepController(this);
    }

    private void setUserInformation() {
        String userName = getIntent().getExtras().getString("Username");
        String userPassword = getIntent().getExtras().getString("Password");
        int userId = getIntent().getExtras().getInt("Id");
        stepController.setUserName(userName);
        stepController.setUserPassword(userPassword);
        stepController.setUserId(userId);
    }

    public void setFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.step_main_container, fragment, tag);
        ft.commit();
    }

    public Fragment getFragment(String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }

    private void setUpServiceConnection() {
        mServiceConnection = new StepServiceConnection(this);
    }

    @Override
    public void update() {
        stepController.incrementSteps();
        stepController.getStepsFromToday();
    }

    public void sendSecondsPassedToController(int secondsPassed) {
        stepController.setSecondsPassed(secondsPassed);
        stepController.initializeListOfDataStepModel();
    }

    public void sendStepsTakenThisSessionToController(int steps) {
        stepController.setStepsTakenThisSession(steps);
    }

    public void updateStepsPerSecond() {
        stepController.updateStepsPerSecond();
    }

    @Override
    protected void onStart() {
        super.onStart();
        stepsIntent = new Intent(this, StepService.class);
        bindService(stepsIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serviceBound) {
            mServiceConnection.unregisterListenersAndStopTimer();
            unbindService(mServiceConnection);
            serviceBound = false;
        }
    }
}
