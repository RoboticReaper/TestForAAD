package com.hoversfw.test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void toast(View v){
        startActivity(new Intent(this,NothingActivity.class ));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.add:
                ViewGroup viewGroup=findViewById(android.R.id.content);
                final View dialogView=LayoutInflater.from(this).inflate(R.layout.choose_layout,viewGroup,false);
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setView(dialogView)
                        .setTitle("Choose what to do")
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Vibrator vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                                EditText editText=dialogView.findViewById(R.id.length);
                                if(editText.getText().toString().equals("")){}
                                else {
                                    if (Integer.parseInt(editText.getText().toString()) <= 5000) {
                                        vibrator.vibrate(Integer.parseInt(editText.getText().toString()));
                                    } else {
                                        Snackbar.make(dialogView, "Vibration time should be shorter than 5000ms.", Snackbar.LENGTH_SHORT);
                                    }
                                }
                            }
                        }).create().show();
                return true;
            case R.id.item1:
                MediaPlayer player=MediaPlayer.create(this,R.raw.shortbeep);
                player.start();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                dialog.setTitle("Item 2 selected").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
                return true;
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

}
