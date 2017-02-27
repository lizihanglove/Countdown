package com.lizihanglove.countdown;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
/*
实现简单倒计时功能
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.input_time)
    EditText inputTime;
    @BindView(R.id.get_time)
    Button getTime;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.pause)
    Button pause;
    @BindView(R.id.show_time)
    TextView showTime;

    private int totalTime;
    Timer timer;
    TimerTask task;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            showTime.setText(msg.arg1+"");
            startTime();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getTime.setOnClickListener(this);
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.get_time:
                initTime();
                break;
            case R.id.start:
                initTime();
                startTime();
                break;
            case R.id.pause:
                stopTime();
                break;
        }
    }

    private void initTime() {
        if (timer == null) {
            getTime.setEnabled(true);
            String totalTimeString = inputTime.getText().toString().trim();
            showTime.setText(totalTimeString);
            totalTime = getTime(totalTimeString);
        } else {
            getTime.setEnabled(false);
            showTime.setText(totalTime+"");
        }
    }

    private void stopTime() {
      timer.cancel();
    }
    private void startTime() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                if (totalTime == 0) {
                    timer.cancel();
                    return;
                }
                totalTime--;
                Message message = Message.obtain();
                message.arg1 = totalTime;
                mHandler.sendMessage(message);
            }
        };
        timer.schedule(task, 1000);
    }

    private int getTime(String totalTimeString) {
        if (!TextUtils.isEmpty(totalTimeString)) {
            int total = Integer.parseInt(totalTimeString);
            return total;
        } else {
            Toast.makeText(MainActivity.this, "无时间输入", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }



}
