package com.hoversfw.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

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
        Button exit=findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button generate=findViewById(R.id.generate);
        final RadioGroup group=findViewById(R.id.group);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(group.getCheckedRadioButtonId()==R.id.squar){
                    customView.makeSquare();
                }
                if(group.getCheckedRadioButtonId()==R.id.image){
                    customView.makeImage();
                }
            }
        });
    }

}
