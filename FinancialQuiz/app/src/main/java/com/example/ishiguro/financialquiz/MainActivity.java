package com.example.ishiguro.financialquiz;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.TypefaceProvider;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

public class MainActivity extends AppCompatActivity {

    //ディスプレイの横幅
    public static int sizeX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG,Thread.currentThread().getStackTrace()[1].getClassName() + "," + Thread.currentThread().getStackTrace()[1].getMethodName() + "," + Thread.currentThread().getStackTrace()[1].getLineNumber());

        TypefaceProvider.registerDefaultIconSets();

        //画面サイズ取得
        Log.d("size_x","size" + DisplaySizeCheck.getRealSize(MainActivity.this).x);
        sizeX = DisplaySizeCheck.getRealSize(MainActivity.this).x;

        //各ビューのサイズを取得
        int TitleTextSize = sizeX/100 * 3 -5;
        int subTitleTextSize = sizeX/100 * 2 -5;
        int ButtonSize = sizeX/2;

        //問題の初期化
        QuizModel.init();

        //*************************************************************************:
        //画面サイズに合わせたテキストサイズになるように調整する
        //*************************************************************************:
        TextView mainTitleText = (TextView) findViewById(R.id.mainTitle);
        mainTitleText.setTextSize(TitleTextSize);
        TextView subTitleText1 = (TextView) findViewById(R.id.subTitle1);
        subTitleText1.setTextSize(subTitleTextSize -2);
        TextView subTitleText2 = (TextView) findViewById(R.id.subTitle2);
        subTitleText2.setTextSize(subTitleTextSize -2);
        TextView subTitleText3 = (TextView) findViewById(R.id.subTitle3);
        subTitleText3.setTextSize(subTitleTextSize -2);

        //*************************************************************************:
        //クイズ画面へ遷移するためのボタン
        //*************************************************************************:
        Button nextQuizButton = (Button) findViewById(R.id.startBtn);
        nextQuizButton.setWidth(ButtonSize);
        nextQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG,Thread.currentThread().getStackTrace()[1].getClassName() + "," + Thread.currentThread().getStackTrace()[1].getMethodName() + "," + Thread.currentThread().getStackTrace()[1].getLineNumber());
                //クイズ画面へ遷移する
                Intent QAIntent = new Intent(MainActivity.this, QuizActivity.class);
                QAIntent.putExtra("QuizKey", QuizModel.getQuiz(QuizModel.QuizeArray_index));//表示する問題番号を指定
                startActivity(QAIntent);
            }
        });

        //*************************************************************************:
        //シミュレーションへ遷移するためのボタン
        //*************************************************************************:
        Button nextSimulationButton = (Button) findViewById(R.id.nextSimulationBtn);
        nextSimulationButton.setWidth(ButtonSize);
        nextSimulationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG,Thread.currentThread().getStackTrace()[1].getClassName() + "," + Thread.currentThread().getStackTrace()[1].getMethodName() + "," + Thread.currentThread().getStackTrace()[1].getLineNumber());

                Intent SAIntent = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(SAIntent);
            }
        });
    }
}