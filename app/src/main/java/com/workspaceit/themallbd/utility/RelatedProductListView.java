package com.workspaceit.themallbd.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Mausum on 4/22/2016.
 */
public class RelatedProductListView extends ListView {
    public RelatedProductListView(Context context) {
        super(context);
    }


    public RelatedProductListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public RelatedProductListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
