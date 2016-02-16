package com.workspaceit.themallbd.fragment;

/**
 * Created by rajib on 2/16/16.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.workspaceit.themallbd.R;

/**
 * Created by Admin on 04-06-2015.
 */
public class ContentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_fragment,container,false);
    }
}
