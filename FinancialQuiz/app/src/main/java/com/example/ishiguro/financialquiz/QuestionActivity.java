package com.example.ishiguro.financialquiz;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.ishiguro.financialquiz.MainActivity.sizeX;

public class QuestionActivity extends AppCompatActivity {

    int g_age = 0; //年齢
    int g_assetIndex = 0; //金融資産
    int g_purposeIndex = 0;//資産運用の目的

    //各ビューのサイズを取得
    int QuestionTextSize = sizeX/100 * 2;   //質問文
    int ButtonSize = sizeX/3;                //ボタン

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //Activity開始時にキーボートを開かない
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //画面生成
        createActivity();
    }

    private void createActivity(){
        //**************************************************************************
        //Text(年齢)
        //**************************************************************************
        TextView ageTextView = (TextView) findViewById(R.id.ageText);
        ageTextView.setTextSize(QuestionTextSize);

        //**************************************************************************
        //スピナー(年齢)
        //**************************************************************************
        //アダプター
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテムを追加します
        for(int i=18;i<100;i++){
            ageAdapter.add(i + "歳");
        }
        Spinner ageSpinner = (Spinner) findViewById(R.id.ageSpinner);
        // アダプターを設定します
        ageSpinner.setAdapter(ageAdapter);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner = (Spinner) parent;
                // 選択されたアイテムを取得します
                g_age = 18 + spinner.getSelectedItemPosition(); //年齢を保存
                Log.d(getPackageName(),"g_age = " + g_age);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //**************************************************************************
        //Text(毎月の積立額)
        //**************************************************************************
        TextView questionDepositText = (TextView) findViewById(R.id.QuestionDepositText);
        questionDepositText.setTextSize(QuestionTextSize);

        //**************************************************************************
        //EditText(毎月の積立額)
        //**************************************************************************
        final EditText depositEditText = (EditText) findViewById(R.id.QuestionDepositEdit);

        //**************************************************************************
        //Text(金融資産)
        //**************************************************************************
        TextView QuestionAssetText = (TextView) findViewById(R.id.assetText);
        QuestionAssetText.setTextSize(QuestionTextSize);

        //**************************************************************************
        //スピナー(金融資産)
        //**************************************************************************
        //アダプター
        ArrayAdapter<String> assetAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // アイテムを追加
        assetAdapter.add("500万円未満");
        assetAdapter.add("500万～1000万未満");
        assetAdapter.add("1000万～3000万未満");
        assetAdapter.add("3000万から1億未満");
        assetAdapter.add("1億以上");

        Spinner assetSpinner = (Spinner) findViewById(R.id.assetSpinner);
        // アダプターを設定します
        assetSpinner.setAdapter(assetAdapter);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録
        assetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner = (Spinner) parent;
                // 選択されたアイテムを取得
                 g_assetIndex = spinner.getSelectedItemPosition(); //選択されたインデックスを保存
                Log.d(getPackageName(),"g_assetIndex = " + g_assetIndex);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //**************************************************************************
        //Text(資産運用の目的)
        //**************************************************************************
        TextView QuestionPurposeText = (TextView) findViewById(R.id.purposeText);
        QuestionPurposeText.setTextSize(QuestionTextSize);

        //**************************************************************************
        //スピナー(資産運用の目的)
        //**************************************************************************
        //アダプター
        ArrayAdapter<String> purposeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // アイテムを追加
        purposeAdapter.add("3年以内の大きな支出(住宅購入費、医療費など)に備える");
        purposeAdapter.add("3～5年以内の中期的な資産形成");
        purposeAdapter.add("5年を超える長期的な資産形成で、将来に備える");

        Spinner purposeSpinner = (Spinner) findViewById(R.id.purposeSpinner);
        // アダプターを設定します
        purposeSpinner.setAdapter(purposeAdapter);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録
        purposeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner = (Spinner) parent;
                // 選択されたアイテムを取得
                g_purposeIndex = spinner.getSelectedItemPosition(); //選択されたインデックスを保存
                Log.d(getPackageName(),"g_purposeIndex = " + g_purposeIndex);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //**************************************************************************
        //アラートの生成
        //**************************************************************************
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("毎月の積立金額を入力してください。")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // ボタンをクリックしたときの動作
                    }
                });

        //**************************************************************************
        //「次へ」ボタン
        //**************************************************************************
        Button nextSimulationButton = (Button) findViewById(R.id.nextSimulationActivityBtn);
        nextSimulationButton.setWidth(ButtonSize);
        nextSimulationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(getPackageName(),"nextSimulationButton Click!");

                if(depositEditText.getText().toString().equals("")){
                    //積立金額の入力欄に何も入力されていなかった場合、アラートを表示
                    builder.show();
                }else {
                    //株式と債券比率を求める
                    int stockRatio = PortfolioDecision(g_age, g_assetIndex, g_purposeIndex);

                    //積立金額を取得
                    int dep = Integer.parseInt(depositEditText.getText().toString()); //毎月お積立金から年間の金額を算出

                    //シミュレーション画面へ遷移する
                    Intent SAIntent = new Intent(QuestionActivity.this, SimulationActivity.class);
                    SAIntent.putExtra("stock_band_ratio", stockRatio); //株式比率
                    SAIntent.putExtra("deposit", dep); //積立金
                    SAIntent.putExtra("purposeIndex", g_purposeIndex); //資産運用の目的 (0:3年以内, 1:5年以内, 2:5年以上)
                    startActivity(SAIntent);
                }
            }
        });
    }

    //入力された内容を元に適切なポートフォリオを判定する
    private int PortfolioDecision(int age, int asset, int purpose){
        //株式と債券の比率(100の場合株式100%で債券0%、0の場合株式0%で債券100%)
        int StockBondRatio = 0;

        //100-年齢を基本の株式と債券の比率とする
        StockBondRatio = 100 - age;

        switch (asset){
            case 0:
                StockBondRatio +=10;
                break;
            case 1:
                StockBondRatio +=5;
                break;
            case 2:
                StockBondRatio +=0;
                break;
            case 3:
                StockBondRatio -=5;
                break;
            case 4:
                StockBondRatio -=10;
                break;
        }

        switch (purpose){
            case 0:
                StockBondRatio -=10;
                break;
            case 1:
                StockBondRatio +=0;
                break;
            case 2:
                StockBondRatio +=10;
                break;
        }

        //株式債券比率が90以上の場合は100に修正
        if(StockBondRatio > 90){
            StockBondRatio = 90;
        }
        //株式債券比率が10未満の場合は0に修正
        if(StockBondRatio < 10){
            StockBondRatio = 10;
        }

        return  StockBondRatio;
    }
}
