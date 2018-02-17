package com.muazhakanemran.myapplication.left_menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muazhakanemran.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by muazekici on 16.02.2018.
 */

public class LeftMenuFragment extends Fragment {


    public interface onLeftMenuItemClicked{
        void onItemClicked(int id);
    }

    private onLeftMenuItemClicked mListener;

    public LeftMenuFragment(){

    }

    public static LeftMenuFragment newInstance(int layoutId) {
        LeftMenuFragment leftFragment = new LeftMenuFragment();
        Bundle args = new Bundle();
        args.putInt("layoutId", layoutId);
        leftFragment.setArguments(args);
        return leftFragment;
    }


    TextView tvItem1,tvItem2,tvItem3,tvItem4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int id = getArguments().getInt("layoutId");

        if(id == -1){
            View inflate = inflater.inflate(R.layout.left_menu_fragment, container, false);

            return inflate;
        }else{
            View inflate = inflater.inflate(id, container, false);

            return inflate;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvItem1 = view.findViewById(R.id.left_menu_item1);
        tvItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onItemClicked(view.getId());
                }
            }
        });

        tvItem2 = view.findViewById(R.id.left_menu_item2);
        tvItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onItemClicked(view.getId());
                }
            }
        });

        tvItem3 = view.findViewById(R.id.left_menu_item3);
        tvItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onItemClicked(view.getId());
                }
            }
        });

        tvItem4 = view.findViewById(R.id.left_menu_item4);
        tvItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onItemClicked(view.getId());
                }
            }
        });

    }



    public void setItemClickListener(onLeftMenuItemClicked listener){
        mListener = listener;
    }
}
