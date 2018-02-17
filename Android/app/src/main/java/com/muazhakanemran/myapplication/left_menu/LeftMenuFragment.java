package com.muazhakanemran.myapplication.left_menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muazhakanemran.myapplication.R;

/**
 * Created by muazekici on 16.02.2018.
 */

public class LeftMenuFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.left_menu_fragment, container, false);
        return inflate;
    }
}
