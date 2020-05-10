package com.hoversfw.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hoversfw.test.views.CustomView;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        final CustomView customView=findViewById(R.id.customView);
        findViewById(R.id.swap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customView.swap();
                Log.d("CustomActivity","button clicked");
            }
        });

    }
}
