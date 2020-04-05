//package com.example.osman.grandresturant;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.os.Build;
//import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//public class splash extends AppCompatActivity {
//
//ProgressBar progress;
//Button retry;
//    private final int splash=3000;
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (Build.VERSION.SDK_INT < 16)
//        {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
//        else
//        {
//            View decorView = getWindow().getDecorView();
//   Hide the status bar.
//            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(uiOptions);
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.splash);
//
//        retry=(Button)findViewById(R.id.retry);
//        progress = (ProgressBar)  findViewById(R.id.progress_splash);
//        progress.setProgress(10);
//
//
//
//        if (stautes() == true) {
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    Intent intent = new Intent(splash.this, HomeScreen.class);
//                    splash.this.startActivity(intent);
//  splash.this.finish();
//                }
//            }, splash);
//
//
//        } else {
//
//
//
//            Toast.makeText(this, "Check internet connection", Toast.LENGTH_SHORT).show();
//    progress.setVisibility(View.INVISIBLE);
//    retry.setVisibility(View.VISIBLE);
//        }
//    retry.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            Intent intent=new Intent(splash.this,splash.class);
//            startActivity(intent);
//
//        }
//    });
//    }
//
//
//    private boolean stautes(){
//        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (conMgr.getActiveNetworkInfo() != null
//                && conMgr.getActiveNetworkInfo().isAvailable()
//                && conMgr.getActiveNetworkInfo().isConnected()) {
//            return true;
//        }else{
//            return false;
//        }
//
//    }
//
//
//  }
package com.example.osman.grandresturant;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//package com.example.osman.grandresturant;
//
//import android.content.Context;

//import com.daimajia.androidanimations.library.Techniques;
//import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.codetail.animation.ViewAnimationUtils;
import io.github.yuweiguocn.lib.squareloading.SquareLoading;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class splash extends AppCompatActivity implements InternetCheck.InternetCheckListener {
    public static final String FIRST_TIME = "FirstTime";
    private Boolean versionCheck;
    private final String VERSION = "1";
    private ImageView splashLogo;
    private Button retryBtn;
    private InternetCheck internetCheck;

    private final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    public SquareLoading progressBar;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar
//
//            // Note that some of these constants are new as of API 16 (Jelly Bean)
//            // and API 19 (KitKat). It is safe to use them, as they are inlined
//            // at compile-time and do nothing on earlier devices.
//            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    //    private final Runnable mShowPart2Runnable = new Runnable() {
//        @Override
//        public void run() {
//            // Delayed display of UI elements
//            ActionBar actionBar = getActionBar();
//            if (actionBar != null) {
//                actionBar.show();
//            }
//        }
//    };
    private boolean mVisible;
//    private final Runnable mHideRunnable = new Runnable() {
//        @Override
//        public void run() {
//            hide();
//        }
//    };

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_splash);
        mContentView = findViewById(R.id.fullscreen_content);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        progressBar = (SquareLoading) findViewById(R.id.progress_loader);
        startSplash();
        retryBtn = (Button) findViewById(R.id.retry_btn);
        checkConnectivity();

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                checkConnectivity();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private void checkVersion() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("version");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.getValue(String.class).equals(VERSION)) {
                    showAlert();
                } else {
                    startApplication();
                }
                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showAlert() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Old Version")
                .setMessage("You need to update to the latest version, Update now ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        gotoUpdate();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void gotoUpdate() {
        Uri uri;
        String appPackageName = getPackageName();
        try {
            uri = Uri.parse("market://details?id=" + appPackageName);
        } catch (android.content.ActivityNotFoundException anfe) {
            uri = Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName);
        }
        Intent updateIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(updateIntent);
        finish();
    }

    private void startApplication() {
        Intent intent;

        intent = new Intent(this, HomeScreen.class);
        startReveal(intent);

    }

    private void checkConnectivity() {
        internetCheck = new InternetCheck(this, this);
        internetCheck.execute();
    }

    @Override
    public void onComplete(boolean connected) {
        if (connected) {
            startApplication();
        } else {
            retryBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    public void startSplash() {
        splashLogo = (ImageView) findViewById(R.id.splash_logo);
        final ImageView animatedBar = (ImageView) findViewById(R.id.animated_bar);
        final TextView animatedText = (TextView) findViewById(R.id.animated_text);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        animatedBar.setVisibility(View.VISIBLE);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            YoYo.with(Techniques.FadeIn)
                                    .duration(500)
                                    .repeat(2)
                                    .playOn(animatedBar);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                })
                .repeat(0)
                .playOn(splashLogo);
        splashLogo.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animatedText.startAnimation(AnimationUtils.loadAnimation(splash.this, R.anim.slide_in_right));
                animatedText.setVisibility(View.VISIBLE);
            }
        }, 2000);

    }

    public void startReveal(final Intent intent) {
        final View splashScreen = findViewById(R.id.splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int cx = (splashLogo.getLeft() + splashLogo.getRight()) / 2;
                int cy = (splashLogo.getTop() + splashLogo.getBottom()) / 2;

                // get the final radius for the clipping circle
                int dx = Math.max(cx, mContentView.getWidth() - cx);
                int dy = Math.max(cy, mContentView.getHeight() - cy);
                float finalRadius = (float) Math.hypot(dx, dy);
                // Android native animator
                final Animator animator =
                        ViewAnimationUtils.createCircularReveal(splashScreen, cx, cy, 0, finalRadius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(500);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        YoYo.with(Techniques.Pulse)
                                .duration(2000)
                                .repeat(0)
                                .playOn(splashLogo);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        YoYo.with(Techniques.FadeOut)
                                .duration(500)
                                .repeat(0)
                                .withListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        startActivity(intent);
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                        finish();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                })
                                .playOn(splashLogo);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                animator.start();
                splashScreen.setVisibility(View.VISIBLE);

            }
        }, 3000);
    }


//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        delayedHide(0);
//    }
//
//    private void toggle() {
//        if (mVisible) {
//            hide();
//        } else {
//            show();
//        }
//    }
//
//    private void hide() {
//        // Hide UI first
//        ActionBar actionBar = getActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
//        mVisible = false;
//
//        // Schedule a runnable to remove the status and navigation bar after a delay
//        mHideHandler.removeCallbacks(mShowPart2Runnable);
//        mHideHandler.postDelayed(mHidePart2Runnable,0);
//    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
//        mHideHandler.removeCallbacks(mHidePart2Runnable);
//        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
//        mHideHandler.removeCallbacks(mHideRunnable);
//        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}





