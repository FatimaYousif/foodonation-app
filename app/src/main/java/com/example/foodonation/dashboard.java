package com.example.foodonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;


//has recycler view

public class dashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add;
    recyclerview_db db;
    adapter adapter1;
    ArrayList<Food> food_list=new ArrayList<>();

//    adapter a=new adapter(dashboard.this,food_list);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().setTitle("DASHBOARD");


        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        db=new recyclerview_db(this);
        
//        showRecord();

         adapter1=new adapter(dashboard.this, db.getAllData("_id DESC" ));
        recyclerView.setLayoutManager(new LinearLayoutManager(dashboard.this));
        recyclerView.setAdapter(adapter1);



        add=(FloatingActionButton)findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(dashboard.this, add_item.class);
                startActivity(i);
            }
        });

    }

    //for menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem search=menu.findItem(R.id.menu_item);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(search);

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
           adapter1.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //------------------------------
    private void showRecord() {
        adapter adapter=new adapter(dashboard.this, db.getAllData("_id DESC" ));
        recyclerView.setLayoutManager(new LinearLayoutManager(dashboard.this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showRecord();
    }
}