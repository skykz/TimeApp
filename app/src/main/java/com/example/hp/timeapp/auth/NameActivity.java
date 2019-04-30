package com.example.hp.timeapp.auth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.timeapp.R;
import com.github.jorgecastilloprz.FABProgressCircle;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.Gravity.LEFT;

public class NameActivity extends AppCompatActivity {

    @BindView(R.id.ivback)
    ImageView ivBack;

    @BindView(R.id.etPhoneNo)
    EditText etPhoneNo;

    @BindView(R.id.tvMoving)
    TextView tvMoving;

    @BindView(R.id.ivFlag)
    ImageView ivFlag;

    @BindView(R.id.tvCode)
    TextView tvCode;

    @BindView(R.id.fabProgressCircle)
    FABProgressCircle fabProgressCircle;

    @BindView(R.id.rootFrame)
    FrameLayout rootFrame;

    @BindView(R.id.llphone)
    LinearLayout llPhone;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_input);

        ButterKnife.bind(this);

        setupWindowAnimations();


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {

        ChangeBounds enterTransition = new ChangeBounds();
        enterTransition.setDuration(500);
        enterTransition.setInterpolator(new DecelerateInterpolator(4));
        enterTransition.addListener(enterTransitionListener);
        getWindow().setSharedElementEnterTransition(enterTransition);

        ChangeBounds returnTransition = new ChangeBounds();
        returnTransition.setDuration(500);
        returnTransition.addListener(returnTransitionListener);
        getWindow().setSharedElementReturnTransition(returnTransition);

        Slide exitSlide = new Slide(LEFT);
        exitSlide.setDuration(500);
        exitSlide.addListener(exitTransitionListener);
        exitSlide.addTarget(R.id.llphone);
        exitSlide.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(exitSlide);

        Slide reenterSlide = new Slide(LEFT);
        reenterSlide.setDuration(500);
        reenterSlide.addListener(reenterTransitionListener);
        reenterSlide.setInterpolator(new DecelerateInterpolator(2));
        reenterSlide.addTarget(R.id.llphone);
        getWindow().setReenterTransition(reenterSlide);


    }

    Transition.TransitionListener enterTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {
            ivBack.setImageAlpha(0);
        }

        @Override
        public void onTransitionEnd(Transition transition) {

            ivBack.setImageAlpha(255);
            Animation animation = AnimationUtils.loadAnimation(NameActivity.this, R.anim.slide_in_right);
            ivBack.startAnimation(animation);

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };


    Transition.TransitionListener returnTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {

            InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etPhoneNo.getWindowToken(), 0);
            tvMoving.setText(null);
            tvMoving.setHint(getString(R.string.enter_no));
            ivFlag.setImageAlpha(0);
            tvCode.setAlpha(0);
            etPhoneNo.setVisibility(View.GONE);
            etPhoneNo.setCursorVisible(false);
            etPhoneNo.setBackground(null);
            Animation animation = AnimationUtils.loadAnimation(NameActivity.this, R.anim.slide_out_left);
            ivBack.startAnimation(animation);
        }

        @Override
        public void onTransitionEnd(Transition transition) {


        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };

    Transition.TransitionListener exitTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {

            rootFrame.setAlpha(1f);
            fabProgressCircle.hide();
            llPhone.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }

        @Override
        public void onTransitionEnd(Transition transition) {


        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };


    Transition.TransitionListener reenterTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {


        }

        @Override
        public void onTransitionEnd(Transition transition) {

            llPhone.setBackgroundColor(Color.parseColor("#FFFFFF"));
            etPhoneNo.setCursorVisible(true);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };


    @OnClick(R.id.fabProgressCircle)
    void nextPager() {

        etPhoneNo.setCursorVisible(false);
        rootFrame.setAlpha(0.4f);
        fabProgressCircle.show();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String phoneNumber = "+7"+etPhoneNo.getText().toString();


                if (isValidMobile(etPhoneNo.getText().toString())) {

                    Intent intent = new Intent(NameActivity.this, PhoneLoginActivity.class);
                    intent.putExtra("PHONE_NUMBER", phoneNumber);

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(NameActivity.this);
                    startActivity(intent, options.toBundle());
                }
            }
        }, 500);
    }

    @OnClick(R.id.ivback)
    void startReturnTransition() {
        super.onBackPressed();
    }

    private boolean isValidMobile(String phone) {
        boolean check = false;
        if(!Pattern.matches("[a-zA-Z] ", phone)) {
            if(phone.length() < 11 && phone.length() > 9) {
                // if(phone.length() != 10) {
                check = true;
            } else {
                check = false;
                Toast.makeText(getBaseContext(), "Неверный формат номера", Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), "Пожалуйста,повторите...", Toast.LENGTH_LONG).show();

            }
        } else {
            check=false;
            Toast.makeText(getBaseContext(), "Неверный формат номера...", Toast.LENGTH_LONG).show();

        }
        return check;
    }
//    public boolean checkForEmail(Context c, String edit) {
////        String str = edit.getText().toString();
//        if (Patterns.PHONE.matcher(edit).matches()) {
//            Toast.makeText(c, "Telephone is valid...", Toast.LENGTH_LONG).show();
//
//            return true;
//        }
//        Toast.makeText(c, "Telephone is not valid...", Toast.LENGTH_LONG).show();
//        return false;
//    }

}
