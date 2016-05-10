package com.themallbd.workspaceit.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Mausum on 4/11/2016.
 */
public class AllProductGridView extends GridView {
    public AllProductGridView(Context context) {
        super(context);
    }

    public AllProductGridView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public AllProductGridView(Context context,AttributeSet attrs,  int defStyle){
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
