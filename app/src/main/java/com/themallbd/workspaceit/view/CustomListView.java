package com.themallbd.workspaceit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Mausum on 4/22/2016.
 */
public class CustomListView extends ListView {
    public CustomListView(Context context) {
        super(context);
    }


    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
