package com.example.hp.timeapp.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.hp.timeapp.R;
import com.github.jorgecastilloprz.FABProgressCircle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class WaitingActivity extends AppCompatActivity {

    int myProgress = 0;
    private ProgressBar progressBarView;


    @BindView(R.id.waiting_fabProgressCircle)
    FABProgressCircle fabProgressCircle;

    TextView tv_time;
    int progress;

    CountDownTimer countDownTimer;
    int endTime = 250;
    private long lastClickTime = 0;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        //init all butter views
        ButterKnife.bind(this);

        progressBarView = (ProgressBar) findViewById(R.id.view_progress_bar);
        tv_time= (TextView)findViewById(R.id.tv_timer);
//        et_timer = (EditText)findViewById(R.id.et_timer);


        /* Animation of countDownTimer */
        RotateAnimation makeVertical = new RotateAnimation(0, -90, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        makeVertical.setFillAfter(true);
        progressBarView.startAnimation(makeVertical);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(0);

        //counter timer
        fn_countdown();
    }

    @OnClick(R.id.waiting_fabProgressCircle)
    void BackToPage() {
        // preventing double, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - lastClickTime < 1500){
            return;
        }
        lastClickTime = SystemClock.elapsedRealtime();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent intent = new Intent(WaitingActivity.this, SingleActivity.class);
                    startActivity(intent);
                    finish();
                }
        }, 700);

    }
    private void fn_countdown() {

            myProgress = 0;

            try {
                countDownTimer.cancel();
            } catch (Exception e) {

            }
            progress = 1;
            endTime = 30; // up to finish time

            countDownTimer = new CountDownTimer(endTime * 1000, 1000) {

                @SuppressLint("SetTextI18n")//added some correction to the code
                @Override
                public void onTick(long millisUntilFinished) {
                    setProgress(progress, endTime);
                    progress = progress + 1;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                    int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                    String newTime = hours + ":" + minutes + ":" + seconds;

                    if (newTime.equals("0:0")) {
                        tv_time.setText("00:00");
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText("0" + minutes + ":0" + seconds);
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1)) {
                        tv_time.setText("0" + minutes + ":" + seconds);
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText("0" + minutes + ":0" + seconds);
                    } else if ((String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText("0" + minutes + ":0" + seconds);
                    } else if (String.valueOf(hours).length() == 1) {
                        tv_time.setText("0" + minutes + ":" + seconds);
                    } else if (String.valueOf(minutes).length() == 1) {
                        tv_time.setText("0" + minutes + ":" + seconds);
                    } else if (String.valueOf(seconds).length() == 1) {
                        tv_time.setText("0" + minutes + ":0" + seconds);
                    } else {
                        tv_time.setText( "0" + minutes + ":" + seconds);
                    }
                }

                @Override
                public void onFinish() {

                    setProgress(progress, endTime);

                    buildAlertMessage();
                }
            };
            countDownTimer.start();
    }


    //TODO: make goof approach to show dialog window
    //just added to Test
    private void buildAlertMessage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Если не получили ответа, то повторите попытку еще раз.")
                .setCancelable(false)
                .setPositiveButton("Готово", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        //if User will agree with it, so go back to specialist's information page.
                        Intent goBack = new Intent(WaitingActivity.this,SingleActivity.class);
                        startActivity(goBack);
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void setProgress(int startTime, int endTime) {
        progressBarView.setMax(endTime);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(startTime);
    }
}


