package com.example.beautyshop.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.beautyshop.EminaaActivity;
import com.example.beautyshop.MadamgieActivity;
import com.example.beautyshop.PixyActivity;
import com.example.beautyshop.R;
import com.example.beautyshop.WardahActivity;

public class HomeFragment extends Fragment {

//    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView Wardah = (ImageView) view.findViewById(R.id.wardah_menu);
        ImageView Emina = (ImageView) view.findViewById(R.id.emina_menu);
        ImageView MadamGie = (ImageView) view.findViewById(R.id.madamgie_menu);
        ImageView Pixy = (ImageView) view.findViewById(R.id.pixy_menu);

        Wardah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), WardahActivity.class);
                a.putExtra("Beauty", " Wardah");
                startActivity(a);
            }
        });

        Emina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(getActivity(), EminaaActivity.class);
                b.putExtra("Beauty", " Emina");
                startActivity(b);
            }
        });

        MadamGie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(getActivity(), MadamgieActivity.class);
                c.putExtra("Beauty", " Madamegie");
                startActivity(c);
            }
        });

        Pixy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(getActivity(), PixyActivity.class);
                d.putExtra("Beauty", " Pixy");
                startActivity(d);
            }
        });

        return view;
    }

//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.textintro);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }
}
