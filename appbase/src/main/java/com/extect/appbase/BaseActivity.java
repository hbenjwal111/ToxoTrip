package com.extect.appbase;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.splunk.mint.Mint;

import java.util.List;

import listeners.OnFragmentInteractionListener;
import widgets.CustomTypefaceSpan;

public abstract class BaseActivity extends AppCompatActivity implements OnFragmentInteractionListener, View.OnClickListener {


    /* Network change broadcast receiver object */
    public static boolean isConnected = false;
    protected NetworkChangeReceiver receiver;

    public static boolean isActive = false;
    public static CoordinatorLayout coordinatiorLayout;
    public View mControlsView;
    public ActionBar mActionBar;
    private Typeface newJuneRegular;
//    private Typeface openSansRegular;
//    private Typeface openSansSemibold;
    private Typeface fontAwesome;
    public static String TAG = "DKR";
    private Dialog dialog;
    public static ProgressBar progressBar;
    protected boolean isExit;
    private ProgressDialog progressDialog;

    public static void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(coordinatiorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        progressBar = new ProgressBar(this);
        progressBar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        dialog = new Dialog(this);
        dialog.setContentView(progressBar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        fontAwesome = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");

        newJuneRegular = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");

        Mint.initAndStartSession(this.getApplication(), "ddfc9dde");


        //registerNetworkReciever();
    }

    public void showProgressDialog() {
        if (dialog != null && !dialog.isShowing() && !this.isFinishing()) {
            dialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing() && !this.isFinishing()) {
            dialog.dismiss();
        }
    }


    public boolean isOnline(boolean showWarning) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                int numActiveNetwork = cm.getAllNetworks().length;

                for (int i = 0; i < numActiveNetwork; i++) {
                    if (cm.getNetworkCapabilities(cm.getAllNetworks()[i]).hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                        connected = true;
                        return connected;
                    }
                }
            } else {
                connected = true;
            }
        }
        if (!connected && showWarning) {
            Toast.makeText(this, "No connection", Toast.LENGTH_LONG).show();
        }
        return connected;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        ActivityManager mngr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(35);
        if (taskList.get(0).numActivities == 1 &&
                taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
            Log.i(TAG, "This is last activity in the stack");

            if (isExit) {

                finish();

            } else {
                Toast.makeText(this, getResources().getString(R.string.back_again),
                        Toast.LENGTH_SHORT).show();
                isExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2 * 1000);
            }
        } else {
            super.onBackPressed();
        }
    }


    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }



    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        isActive = true;
        if(receiver!=null)
        {
            unregisterReceiver(receiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;

    }

    public void replaceView(int layout, BaseFragment fragment, boolean isAddToBackStack, boolean... isAnim) {

        try {
            if (fragment != null) {

                String fragmentName = fragment.getClass().getSimpleName();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_left, R.anim.slide_in_left,
                        R.anim.slide_out_right);
                fragmentTransaction.replace(layout, fragment, fragmentName);
                //if (isAddToBackStack) {
                String s = fragment.getClass().getSimpleName();
                fragmentTransaction.addToBackStack(fragmentName);
                // }
                // else {
                //     fragmentTransaction.addToBackStack(null);
                // }
                fragmentTransaction.commitAllowingStateLoss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void popBackStackFromName(String fragment) {
        getSupportFragmentManager().popBackStack(fragment, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void setFitSystemLayoutMode(boolean mode) {

        if (coordinatiorLayout != null){
            coordinatiorLayout.setFitsSystemWindows(mode);
        }
    }

    public ActionBar getmActionBar() {

        if (mActionBar == null) {
            mActionBar = getSupportActionBar();

            SpannableString s = new SpannableString("My Title");
            s.setSpan( new CustomTypefaceSpan("", getNewJuneRegular()), 0, s.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Update the action bar title with the TypefaceSpan instance
            mActionBar.setTitle(s);
        }
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.show();
        exitFullScreenMode();
        return mActionBar;
    }

    public void showActionBar() {
        getmActionBar();
    }

    public void setFullScreenMode() {

        mControlsView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void exitFullScreenMode() {

       /* mControlsView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);*/
    }


    protected void hideKeyboard() {
        if (getCurrentFocus() != null
                && getCurrentFocus().getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus()
                            .getWindowToken(),
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }

    public abstract void replaceView();

    public Typeface getNewJuneRegular() {
        return newJuneRegular;
    }

    public Typeface getFontAwesome() {
        return fontAwesome;
    }
    /**
     * @desc method to registering network change receiver
     * @return null
     */
   protected void registerNetworkReciever() {

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
       // registerReceiver(receiver, filter);
    }
    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            isNetworkAvailable(context);
        }

        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            if (!isConnected) {
                                Log.v("Connection available", "Now you are connected to Internet!");
                                isConnected = true;
                               // showSnackBar(getResources().getString(R.string.internet_connection_found));
// do your processing here â€”
// if you need to post any data to the server or
// get status
// update from the server
                            }
                            return true;
                        }
                    }
                }
            }

            Log.v("Connection Lost", "You are not connected to Internet!");
            isConnected = false;
            //showToast("Connection Lost");
            //showSnackBar(getResources().getString(R.string.internet_connection_Lost));
            return false;
        }
    }
}
