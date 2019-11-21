package henrik.mau.myassignment4.Main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import henrik.mau.myassignment4.CompassActivity.CompassActivity;
import henrik.mau.myassignment4.R;
import henrik.mau.myassignment4.StepActivity.StepActivity;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeSystem();
    }

    private void initializeSystem() {
        fragmentManager = getSupportFragmentManager();
        controller = new Controller(this);
    }

    public void setFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.main_container, fragment, tag);
        ft.commit();
    }

    public void addFragment(Fragment fragment, String tag){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(fragment, tag);
        ft.commit();
    }

    public Fragment getFragment(String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }

    @Override
    public void onBackPressed() {
        if(controller.onBackPressed()){
            super.onBackPressed();
        }
    }

    public void startStepActivity() {
        Intent stepIntent = new Intent(this, StepActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Username", controller.getUserName());
        bundle.putString("Password", controller.getUserPassword());
        bundle.putInt("Id", controller.getUserId());
        onSaveInstanceState(bundle);
        stepIntent.putExtras(bundle);
        startActivity(stepIntent);
    }

    public void startCompassActivity() {
        Intent compassIntent = new Intent(this, CompassActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Username", controller.getUserName());
        bundle.putString("Password", controller.getUserPassword());
        bundle.putInt("Id", controller.getUserId());
        onSaveInstanceState(bundle);
        compassIntent.putExtras(bundle);
        startActivity(compassIntent);
    }


}
