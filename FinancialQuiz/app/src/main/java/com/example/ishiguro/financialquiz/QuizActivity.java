package com.example.ishiguro.financialquiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import static com.example.ishiguro.financialquiz.MainActivity.sizeX;

public class QuizActivity extends AppCompatActivity {

    private RadioButton[] RButtons;//問題の選択肢のRadioButton型の配列
    QuizModel quiz;
    TextView CAtext;//「正解」又は「不正解」と表示する
    TextView ans_comment;//「正解」又は「不正解」と表示する
    TextView QuizeNumText;//クイズ番号（第X問）

    public static int CorrectAnsewerNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //各ビューのサイズを取得
        int ProblemStatementTextSize = sizeX/100 * 2;   //問題文
        int ChoiceTextSize = sizeX/100 * 2 -5;          //選択肢
        int CommentTextSize = sizeX/100 * 2 -6;        //コメント
        int CorrectOrWrongTextSize = sizeX/100 * 2;     //正誤
        int ButtonSize = sizeX/3;                       //ボタン

        // クイズデータを受け取る
        Intent intent = getIntent();
        if (intent != null) {
            quiz = (QuizModel) intent.getSerializableExtra("QuizKey");
        }

        //問題番号の表示
        QuizeNumText = (TextView) findViewById(R.id.QuizNumberText);
        QuizeNumText.setTextSize(ProblemStatementTextSize);
        QuizeNumText.setText(quiz.q_string);

        //問題文の表示
        TextView problemStatementText = (TextView) findViewById(R.id.ProblemStatementText);
        problemStatementText.setText(quiz.q_ProblemStatement); //問題文が格納されているインデックスは0
        problemStatementText.setTextSize(ProblemStatementTextSize);

        //RadioButton1~4の取得
        RButtons = new RadioButton[4];
        RButtons[0] = (RadioButton) findViewById(R.id.radiobutton_1);
        RButtons[1] = (RadioButton) findViewById(R.id.radiobutton_2);
        RButtons[2] = (RadioButton) findViewById(R.id.radiobutton_3);
        RButtons[3] = (RadioButton) findViewById(R.id.radiobutton_4);
        for(int i=0;i<4;i++) {
            RButtons[i].setText(quiz.choices_string[i]);//問題文のインデックスが0、選択肢はインデックス1から5
            RButtons[i].setTextSize(ChoiceTextSize);
        }

        //回答した場合に表示するテキスト（「正解」又は「不正解」の表示）
        CAtext = (TextView) findViewById(R.id.CheckAnswerText);
        CAtext.setTextSize(CorrectOrWrongTextSize);

        //回答した後に表示するコメント
        ans_comment = (TextView) findViewById(R.id.AnswerCommentText);
        ans_comment.setTextSize(CommentTextSize);

        //各種ボタンの取得
        final Button AnswerButton = (Button) findViewById(R.id.AnswerBtn);
        final Button NextQuizButton = (Button) findViewById(R.id.NextQuizBtn);

        //回答ボタン
        AnswerButton.setWidth(ButtonSize);
        AnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(getPackageName(),"Anser Button Click!");

                //正誤の判定
                CAtext.setVisibility(View.VISIBLE);
                if(CheckAnswer()){
                     CAtext.setText("正解");
                     CAtext.setTextColor(Color.BLUE);
                     CorrectAnsewerNum += 1; //正解数を加算
                }else{
                    CAtext.setText("不正解");
                    CAtext.setTextColor(Color.RED);
                }

                //コメントの表示
                ans_comment.setVisibility(View.VISIBLE);
                ans_comment.setText(quiz.q_ans_comment);

                //RadioButtonを変更する
                for(int i=0;i<4;i++) {
                    if( i == quiz.answer_index ) {
                        //正解のラジオボタンは文字色を変化させる
                        RButtons[i].setTextColor(Color.BLUE);
                    }
                    //不正解のラジオボタンは操作不能にする
                    RButtons[i].setEnabled(false);
                }

                //次の問題へ遷移するためのボタンを押下可能にする
                NextQuizButton.setEnabled(true);
                AnswerButton.setEnabled(false);

                //ボタンのバックカラーを変更する
                NextQuizButton.setBackgroundColor(Color.rgb(65,105,225));
                AnswerButton.setBackgroundColor(Color.rgb(169,169,169));
            }
        });

        //次の問題へ遷移するボタン
        NextQuizButton.setWidth(ButtonSize);
        NextQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(getPackageName(), "NextQuiz Button Click!");

                //10問目未満の場合、次のクイズ画面へ遷移する
                if(QuizModel.QuizeArray_index < 9) {
                    Intent NextQuizActivityIntent = new Intent(QuizActivity.this, QuizActivity.class);
                    QuizModel.QuizeArray_index += 1;//次の問題を参照する
                    NextQuizActivityIntent.putExtra("QuizKey", QuizModel.getQuiz(QuizModel.QuizeArray_index));
                    startActivity(NextQuizActivityIntent);
                }else{
                    //10問目の場合、クイズ結果画面へ遷移する
                    Log.d(getPackageName(),"Next ResultActivity");
                    Intent ResultActivityIntent = new Intent(QuizActivity.this, ResultActivity.class);
                    ResultActivityIntent.putExtra("CorrectAnswerKey", CorrectAnsewerNum);
                    startActivity(ResultActivityIntent);
                }
            }
        });
    }

    /*
    *ユーザーが選択した答えが正しいか判定する
    */
    public boolean CheckAnswer() {
        boolean re = false; //正解：true ,不正解：false

        for (int i = 0; i < RButtons.length; i++) {
            if (RButtons[i].isChecked()) {
                if (i == quiz.answer_index) {
                    Log.d(getPackageName(),"正解！");
                    re = true;
                } else {
                    Log.d(getPackageName(),"不正解！");
                    re = false;
                }
            }
        }
        return  re;
    }
}
