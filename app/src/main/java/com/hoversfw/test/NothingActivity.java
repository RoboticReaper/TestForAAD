package com.hoversfw.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class NothingActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private RecyclerviewAdapter adapter;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nothing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<RecyclerviewItem> list=new ArrayList<>();
        list.add(new RecyclerviewItem(R.drawable.hoversfw,"Hover Software","Hover Software is made by civil software developers, in order to release pure and powerful softwares."));
        manager=new LinearLayoutManager(this);
        adapter=new RecyclerviewAdapter(list);
        recycler=findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
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
                                RadioGroup group=dialogView.findViewById(R.id.actionGroup);
                                switch (group.getCheckedRadioButtonId()){
                                    case R.id.add:
                                        EditText inputTitle=dialogView.findViewById(R.id.inputTitle);
                                        EditText inputDescription=dialogView.findViewById(R.id.inputDescription);
                                        adapter.add(inputTitle.getText().toString(),inputDescription.getText().toString());
                                    case R.id.remove:
                                        adapter.remove();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                
                            }
                        }).create().show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.nothing_menu,menu);
        return true;
    }
}