package com.example.ishiguro.financialquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.ishiguro.financialquiz.MainActivity.sizeX;
import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int CorrectAnswer =0; //正解数

        //各ビューのサイズを取得
        int TitleTextSize = sizeX/100 * 3 -5;
        int subTitleTextSize = sizeX/100 * 2 -5;
        int ButtonSize = sizeX/2;

        //正解数を受け取る
        Intent intent = getIntent();
        if (intent != null) {
            CorrectAnswer = intent.getIntExtra("CorrectAnswerKey",0);
            Log.d(getPackageName(),"correctAnswer =" + CorrectAnswer);
        }

        //タイトルテキストを表示する
        TextView resultTitleText = (TextView) findViewById(R.id.ResultTitleText);
        resultTitleText.setTextSize(TitleTextSize);

        //正解数を表示する
        TextView resultSubText = (TextView) findViewById(R.id.ResultSubText);
        resultSubText.setTextSize(subTitleTextSize);
        resultSubText.setText("あなたは10問中、" + CorrectAnswer + "問正解しました。");

        //正解数から金融知識を評価し、コメントする
        String Evaluation = "";
        if(CorrectAnswer == 0){
            Evaluation = "あなたの金融リテラシーがまだまだ足りないようです。";
        }else if(CorrectAnswer < 3){
            Evaluation = "あなたの金融リテラシーが足りないようです。";
        }else if(CorrectAnswer < 5){
            Evaluation = "あなたの金融リテラシーが少し足りないようです。";
        }else if(CorrectAnswer < 7){
            Evaluation = "あなたの金融リテラシーは平均的です。";
        }else if(CorrectAnswer < 9){
            Evaluation = "あなたは金融リテラシーは平均以上です！";
        }else if(CorrectAnswer == 10){
            Evaluation = "あなたは素晴らしい金融リテラシーをお持ちですね！";
        }
        TextView commentText = (TextView) findViewById(R.id.commentText);
        commentText.setTextSize(subTitleTextSize);
        commentText.setText(Evaluation);

        //タイトルテキストを表示する
        TextView resultSubTitle2Text = (TextView) findViewById(R.id.comment2Text);
        resultSubTitle2Text.setTextSize(subTitleTextSize);

        //*************************************************************************:
        //シミュレーションへ遷移するためのボタン
        //*************************************************************************:
        Button fromResultNextSimulationButton = (Button) findViewById(R.id.nextSimulationBtn);
        fromResultNextSimulationButton.setWidth(ButtonSize);
        fromResultNextSimulationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG,Thread.currentThread().getStackTrace()[1].getClassName() + "," + Thread.currentThread().getStackTrace()[1].getMethodName() + "," + Thread.currentThread().getStackTrace()[1].getLineNumber());
                Intent SAIntent = new Intent(ResultActivity.this, QuestionActivity.class);
                startActivity(SAIntent);
            }
        });
    }
}
