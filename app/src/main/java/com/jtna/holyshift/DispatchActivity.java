package com.jtna.holyshift;

import com.parse.ui.ParseLoginDispatchActivity;

/**
 * Created by Nishad on 12/21/14.
 */

public class DispatchActivity extends ParseLoginDispatchActivity {

    @Override
    protected Class<?> getTargetClass() {
        return MainActivity.class;
    }
}