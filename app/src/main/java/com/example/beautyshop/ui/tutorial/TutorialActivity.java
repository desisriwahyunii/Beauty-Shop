//package com.example.beautyshop.ui.tutorial;
//
//import android.os.Bundle;
//import android.view.ViewGroup;
//
//import com.example.beautyshop.R;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//public class TutorialActivity extends AppCompatActivity {
//    RecyclerView Mrecyclerview;
//    FirebaseDatabase database;
//    DatabaseReference reference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tutorial);
//
//        Mrecyclerview = findViewById(R.id.recyclerview_video);
//        Mrecyclerview.setHasFixedSize(true);
//        Mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
//        database = FirebaseDatabase.getInstance();
//        reference = database.getReference("video");
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerAdapter<video, ViewHolder> firebaseRecyclerAdapter =
//                new FirebaseRecyclerAdapter<video, ViewHolder>(
//                        video.class,
//                        R.layout.row,
//                        ViewHolder.class,
//                        reference
//                ) {
//
//                    @NonNull
//                    @Override
//                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        return null;
//                    }
//
//                    @Override
//                    protected void onBindViewHolder(ViewHolder viewHolder, int i, video video) {
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
//}