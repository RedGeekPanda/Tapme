package com.example.darkeli.tapme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewById(R.id.menu_start).setOnClickListener(this);
        findViewById(R.id.menu_exit).setOnClickListener(this);
    }

    public void onBackPressed() {
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_start: {
                startActivity(new Intent(MenuActivity.this, GameActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }
            break;
            case R.id.menu_exit: {
                finishAffinity();
                break;
            }
        }
    }
}
