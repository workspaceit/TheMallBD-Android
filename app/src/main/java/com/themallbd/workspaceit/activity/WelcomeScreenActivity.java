package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.themallbd.workspaceit.preferences.WelcomeTrack;
import com.themallbd.workspaceit.view.CustomSliderView;
import com.workspaceit.themall.R;

import java.util.HashMap;

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

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("woman_fashion",R.drawable.woman_welcome);
        file_maps.put("Man_fashion",R.drawable.man_wecome);
        file_maps.put("woman_2",R.drawable.woman_2);

        HashMap<String,String> url_maps = new HashMap<String, String>();

        url_maps.put("Brands", "https://letstalkcosmetics.files.wordpress.com/2014/05/the_top_50_most_valuable_cosmetics_brands_2014.jpg");
        url_maps.put("Man Style", "http://www.menstylefashion.com/wp-content/uploads/2016/02/Italian-Men-Fashion-Sense-Sunglasses-and-cup-of-Coffee.jpg");
        url_maps.put("Woman's Style", "http://womenonthefence.com/wp-content/uploads/2013/05/2.jpg");
        url_maps.put("Cosmetics", "http://images.wisegeek.com/group-of-cosmetics-items-against-white-background.jpg");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
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
