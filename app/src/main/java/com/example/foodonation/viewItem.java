package com.example.foodonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class viewItem extends AppCompatActivity {

    TextView title,name, address, description;
    ImageView img;
    Button request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        title=(TextView)findViewById(R.id.title);
        name=(TextView)findViewById(R.id.name);
        address=(TextView)findViewById(R.id.address);
        description=(TextView)findViewById(R.id.description);
        img=(ImageView)findViewById(R.id.image);
        request=(Button)findViewById(R.id.request);


        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));


        name.setText(getIntent().getStringExtra("name")+" is giving away");

        title.setText(getIntent().getStringExtra("title"));

        description.setText(getIntent().getStringExtra("description"));

        address.setText(getIntent().getStringExtra("address"));

        img.setImageURI(Uri.parse(getIntent().getStringExtra("image")));

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(viewItem.this, done.class);
                startActivity(i);
            }
        });
    }
}