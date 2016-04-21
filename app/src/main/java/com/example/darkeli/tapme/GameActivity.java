package com.example.darkeli.tapme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import static java.lang.String.format;

public class GameActivity extends Activity implements View.OnTouchListener {

    private boolean key1_clicked, key2_clicked, game_over;
    private int key1_clicks, key2_clicks,
            key1_power, key2_power, total_power;
    private FrameLayout key1_frame, key2_frame;
    private TextView key1_text, key2_text;
    private LinearLayout.LayoutParams key1_params, key2_params;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViewById(R.id.button1_btn).setOnTouchListener(this);
        findViewById(R.id.button2_btn).setOnTouchListener(this);
        key1_frame = (FrameLayout) findViewById(R.id.button1_frm);
        key2_frame = (FrameLayout) findViewById(R.id.button2_frm);
        key1_text = (TextView) findViewById(R.id.button1_txt);
        key2_text = (TextView) findViewById(R.id.button2_txt);
        key1_params = (LinearLayout.LayoutParams) key1_frame.getLayoutParams();
        key2_params = (LinearLayout.LayoutParams) key2_frame.getLayoutParams();
    }

    protected void onResume() {
        super.onResume();
        key1_clicked = false;
        key2_clicked = false;
        game_over = false;
        key1_clicks = 0;
        key2_clicks = 0;
        key1_power = 100;
        key2_power = 100;
        total_power = key1_power + key2_power;
        key1_text.setText(R.string.onstart);
        key2_text.setText(R.string.onstart);
        key1_params.weight = key1_power;
        key1_frame.setLayoutParams(key1_params);
        key2_params.weight = key2_power;
        key2_frame.setLayoutParams(key2_params);
    }

    public void onBackPressed() {
        startActivity(new Intent(GameActivity.this, MenuActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    public boolean onTouch(View vw, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            if (key1_clicked && key2_clicked && !game_over) {
                // x*=div
                int click_power = total_power / 20;
                switch (vw.getId()) {
                    case R.id.button1_btn:
                        clickButton1(click_power);
                        break;
                    case R.id.button2_btn:
                        clickButton2(click_power);
                        break;
                }
                key1_params.weight = key1_power;
                key1_frame.setLayoutParams(key1_params);
                key2_params.weight = key2_power;
                key2_frame.setLayoutParams(key2_params);
            } else if (!game_over)
                switch (vw.getId()) {
                    case R.id.button1_btn: {
                        key1_clicked = true;
                        key1_text.setText(R.string.onready);
                    }
                    break;
                    case R.id.button2_btn: {
                        key2_clicked = true;
                        key2_text.setText(R.string.onready);
                        break;
                    }
                }
        return false;
    }

    private void clickButton1(int click_pow) {
        key1_clicks++;
        key1_power += click_pow;
        // (1/17)dp
        if (key1_power < total_power) {
            key2_power -= click_pow;
            key1_text.setText(format("%s", key1_clicks));
        } else {
            key1_text.setText(R.string.button_1_win);
            key1_power = total_power;
            key2_power = 0;
            gameOver(key1_text);
        }
    }

    private void clickButton2(int click_pow) {
        key2_clicks++;
        key2_power += click_pow;
        //(1/17)dp
        if (key2_power < total_power) {
            key1_power -= click_pow;
            key2_text.setText(format("%s", key2_clicks));
        } else {
            key2_text.setText(R.string.button_2_win);
            key2_power = total_power;
            key1_power = 0;
            gameOver(key2_text);
        }
    }

    private void gameOver(TextView key_text) {
        game_over = true;
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        anim.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(GameActivity.this, MenuActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        key_text.startAnimation(anim);
    }
}
