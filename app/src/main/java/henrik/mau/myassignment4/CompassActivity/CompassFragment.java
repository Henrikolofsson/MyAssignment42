package henrik.mau.myassignment4.CompassActivity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import henrik.mau.myassignment4.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompassFragment extends Fragment {
    private CompassController compassController;
    private ImageView ivCompass;
    private boolean isRotating = false;
    private float currentDegree = 0;
    private int time;
    private static Timer timer;
    private Handler mHandler = new Handler(Looper.getMainLooper());


    public CompassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compass, container, false);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view) {
        ivCompass = (ImageView) view.findViewById(R.id.ivCompass);
    }

    public void setCompassController(CompassController compassController) {
        this.compassController = compassController;
    }

    public void rotateCompass(float azimuthInDegrees) {
        Log.d("ROTATE", ""+isRotating);
        if(!isRotating) {
            Log.d("ROTATE", "ROTATE");
            RotateAnimation rotateAnimation = new RotateAnimation(currentDegree, -azimuthInDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(250);
            rotateAnimation.setFillAfter(true);

            ivCompass.startAnimation(rotateAnimation);
            currentDegree = -azimuthInDegrees;
        }

    }

    public void rotateCompass(final float azimuthInDegrees, final long duration) {
        Log.d("DEBUG", "ROTATE COMPASS");
        isRotating = true;
        time = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        RotateAnimation rotateAnimation = new RotateAnimation(azimuthInDegrees, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        rotateAnimation.setDuration(duration);
                        rotateAnimation.setInterpolator(new LinearInterpolator());
                        ivCompass.startAnimation(rotateAnimation);

                        if(time > duration) {
                            timer.cancel();
                            isRotating = false;
                        }
                        time += 500;
                    }
                });

            }
        }, 0, 500);

    }

    public void setPictureNorth(float angleDegree) {
        RotateAnimation mRotateAnimation = new RotateAnimation(currentDegree, -angleDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(250);
        mRotateAnimation.setFillAfter(true);
        ivCompass.startAnimation(mRotateAnimation);
        currentDegree = -angleDegree;

    }
}
