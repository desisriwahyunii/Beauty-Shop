package com.example.beautyshop.ui.tutorial;

import android.os.Bundle;

import com.example.beautyshop.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class TutorialActivity extends AppCompatActivity {
    RecyclerView Mrecyclerview;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        Mrecyclerview = findViewById(R.id.recyclerview_video);
        Mrecyclerview.setHasFixedSize(true);
        Mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        reference = database.getReference("video");
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerAdapter<Video, ViewHolder> firebaseRecyclerAdapter =
//                new FirebaseRecyclerAdapter<Video, ViewHolder>(
//                        Video.class,
//                        R.layout.row,
//                        ViewHolder.class,
//                        reference
//                ) {
//
//                    @Override
//                    protected void onBindViewHolder(ViewHolder viewHolder, int i, @NonNull Video video) {
//                        viewHolder.setVideo(getApplication(), video.getTitle(), video.getUrl());
//                    }
//
////                    protected void populateViewHolder(ViewHolder viewHolder, Video video, int i) {
////                        viewHolder.setVideo(getApplication(), video.getTitle(), video.getUrl());
////                    }
//                };
//
//        Mrecyclerview.setAdapter(firebaseRecyclerAdapter);
//    }
}