package com.meshine.letsstudyclient.tools;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Ming on 2016/5/4.
 */
@SharedPref
public interface MyPrefs {
    String username();
    String password();
}
