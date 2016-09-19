package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.themallbd.workspaceit.preferences.WelcomeTrack;
import com.themallbd.workspaceit.view.CustomSliderView;
import com.workspaceit.themall.R;

public class WelcomeScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private SliderLayout sliderLayout;
    private Button loginButton, signupButton;
    private ImageButton crossButton;
    private WelcomeTrack welcomeTrack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        welcomeTrack=new WelcomeTrack(this);
        if (welcomeTrack.getFirstTimeStatus()){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_welcome_screen);
        sliderLayout = (SliderLayout) findViewById(R.id.welcome_slider);
        loginButton = (Button) findViewById(R.id.welcome_login_button);
        loginButton.setOnClickListener(this);
        signupButton = (Button) findViewById(R.id.welcome_signup_button);
        signupButton.setOnClickListener(this);
        crossButton = (ImageButton) findViewById(R.id.welcome_cross_image_button);
        crossButton.setOnClickListener(this);
        initializeSlider();


    }

    private void initializeSlider() {

        int[] imageId = {R.drawable.style_1, R.drawable.style_2, R.drawable.style_3};


        for (int i = 0; i < 3; i++) {

            CustomSliderView customSliderView = new CustomSliderView(this);
            customSliderView.image(imageId[i])
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderLayout.addSlider(customSliderView);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == loginButton) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("page_indicator", 1);
            startActivity(intent);
        } else if (v == signupButton) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("page_indicator", 2);
            startActivity(intent);
        } else if (v == crossButton) {
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }
}
