package com.hoversfw.test;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class NothingActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private RecyclerviewAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nothing);


        Toolbar toolbar=findViewById(R.id.toolbar);
        drawer=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setSupportActionBar(toolbar);
        ArrayList<RecyclerviewItem> list=new ArrayList<>();
        manager=new LinearLayoutManager(this);
        adapter=new RecyclerviewAdapter(list);
        recycler=findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        Gson gson=new Gson();
        SharedPreferences save=getSharedPreferences("save",MODE_PRIVATE);
        ArrayList<RecyclerviewItem> set;
        String json=save.getString("savedList",null);
        Type type=new TypeToken<ArrayList<RecyclerviewItem>>(){}.getType();
        set=gson.fromJson(json,type);
        adapter.setList(set);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.change:
                ViewGroup viewGroup=findViewById(android.R.id.content);
                final View dialogView= LayoutInflater.from(this).inflate(R.layout.change_recycler,viewGroup,false);
                android.app.AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setView(dialogView)
                        .setTitle("Make changes to RecyclerView")
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences save=getSharedPreferences("save",MODE_PRIVATE);
                                SharedPreferences.Editor editor=save.edit();
                                RadioGroup group=dialogView.findViewById(R.id.actionGroup);
                                Gson gson=new Gson();
                                switch (group.getCheckedRadioButtonId()){
                                    case R.id.add:
                                        EditText inputTitle=dialogView.findViewById(R.id.inputTitle);
                                        EditText inputDescription=dialogView.findViewById(R.id.inputDescription);
                                        if(inputTitle.getText().toString().equals("")&&inputDescription.getText().toString().equals("")){
                                            adapter.add("Hover Software","Hover Software is made by civil software developers, in order to release pure and powerful softwares.");
                                        }
                                        else {
                                            adapter.add(inputTitle.getText().toString(), inputDescription.getText().toString());
                                        }
                                        ArrayList<RecyclerviewItem> a = adapter.getlist();
                                        editor.putString("savedList", gson.toJson(a));
                                        editor.apply();
                                        break;
                                    case R.id.remove:
                                        adapter.remove();
                                        ArrayList<RecyclerviewItem> b=adapter.getlist();
                                        editor.putString("savedList",gson.toJson(b));
                                        editor.apply();
                                        break;
                                    default:
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                
                            }
                        }).create().show();
                return true;
            case R.id.back:
                finish();
                return true;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.nothing_menu,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}