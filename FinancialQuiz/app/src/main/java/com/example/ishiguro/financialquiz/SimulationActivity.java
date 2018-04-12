package com.example.ishiguro.financialquiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.ishiguro.financialquiz.MainActivity.sizeX;

public class SimulationActivity extends AppCompatActivity {

    int g_deposit = 0; //毎年積立金
    float g_AnnualInterestRate = 0.06f; //年利率
    int g_year = 20; //投資年数

    int stockRatio = 0;
    int bandRatio =0;

    //ティッカーとその説明の2次元配列
    String tickerDescriptionArray[][] = {{"VT"," 米国を含む全世界の先進国株式市場および新興国株式市場への幅広いエクスポージャーを提供します。先進国や新興国市場を含む約47ヵ国の約8,000銘柄で構成されます。"},
            {"VOO"," 米国の大型株を投資対象とし、S&P500指数のパフォーマンスへの連動を目指します。米国の主要業種を代表する500銘柄で構成されています"},
            {"VWO","中国と新興国の株式を投資対象とします。全21カ国約2,000銘柄から構成されています。"},
            {"BND","米国の投資適格債券市場全体への幅広く分散したエクスポージャーを提供します。"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        Log.d(getPackageName(),"step1");

        //Activity開始時にキーボートを開かない
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //************************************************************************************
        //QuestionActivityで得た株式と債券比率、積立金、運用年数を受け取る
        //************************************************************************************
        Intent intent = getIntent();

        if (intent != null) {
            //株式と債券比率
            stockRatio = intent.getIntExtra("stock_band_ratio", 0);
            bandRatio = 100 - stockRatio;

            //積立金
            g_deposit = intent.getIntExtra("deposit", 0) * 12; //毎月積立金から毎年積立金に変換

            int p = intent.getIntExtra("purposeIndex", 20); //運用年数
            if(p == 0){
                g_year = 3;
            }else if(p == 1){
                g_year = 5;
            }else {
                g_year = 20;
            }

            Log.d(getPackageName(),"stockRatio ="+stockRatio);
            Log.d(getPackageName(),"bandRatio ="+bandRatio);
            Log.d(getPackageName(),"g_deposit ="+g_deposit);
        }

        //Activityの描画
        ActivityDrowing(g_deposit,g_AnnualInterestRate,g_year);

        //************************************************************************************
        //ETFの説明表の作成
        //************************************************************************************
        //表のベースレイアウト
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayout);

        //レイアウトの余白
        TableRow.LayoutParams layout_params = new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT );
        layout_params.setMargins(20,10,20,10);

        for(int i=0; i<tickerDescriptionArray.length; i++){
            TableRow tableRow = new TableRow(this);

            //ティッカーシンボル
            TextView tickerText = new TextView(this);
            tickerText.setText(tickerDescriptionArray[i][0]);
            tickerText.setLayoutParams(layout_params);

            TextView descriptionText = new TextView(this);
            descriptionText.setText(tickerDescriptionArray[i][1]);
            descriptionText.setLayoutParams(layout_params);

            tableRow.addView(tickerText);
            tableRow.addView(descriptionText);

            tableLayout.addView(tableRow);
        }
    }

    /*グラフの描画と必要な数値の生成
    int 毎年積立金, float 年利率, int 投資年数
    */
    private void ActivityDrowing(final int dep, float rate, int year){
        //各ビューのサイズを取得
        int TitleTextSize = sizeX/100 * 2 -5;
        int subTitleTextSize = sizeX/100 * 2 -8;

        //積立年数のUI
        final SeekBar sneekBarYear = (SeekBar)findViewById(R.id.SeekBar_year);
        final TextView YearText = (TextView)findViewById(R.id.yearText);
        //積立年数の初期値をバーとテキストに表示
        YearText.setText("積立年数：" + year + "年");
        sneekBarYear.setProgress(year);

        //予想運用結果
        final TextView assetExpectationText = (TextView)findViewById(R.id.AssetExpectation);

        //毎月の積立金のUI
        final TextView depositText = (TextView)findViewById(R.id.DepositText);
        final TextView depositEdit = (TextView)findViewById(R.id.DepositEdit);//入力フィールド

        //円グラフ
        final PieChart SPchart = (PieChart) findViewById(R.id.SimulationPieChart);

        //折れ線グラフ
        final LineChart SLchart = (LineChart) findViewById(R.id.SimulationLineChart);

        //************************************************************************************
        //タイトル・サブタイトル
        //************************************************************************************
        TextView SimulationPieTitleText = (TextView) findViewById(R.id.SimulationPieTitle);
        SimulationPieTitleText.setTextSize(TitleTextSize);

        TextView SimulationPieSubTitleText = (TextView) findViewById(R.id.SimulationPieSubTitle);
        SimulationPieSubTitleText.setTextSize(subTitleTextSize);

        //************************************************************************************
        //円グラフの生成
        //************************************************************************************
        SPchart.setUsePercentValues(true);
        //SPchart.setDescription("ポートフォリオ");

        //グラフの縦幅・横幅
        SPchart.setLayoutParams( new LinearLayout.LayoutParams(sizeX,sizeX-(sizeX/3)));

        Legend legend = SPchart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

        // 円グラフに表示するデータ
        int VT = stockRatio/2;//全世界株 リターン:4%
        int VOO = stockRatio/2;//S&P500 リターン:7%
        int VWO = 0; //新興国株 リターン:5%
        int BND = bandRatio;//債券 リターン:3%

        if(bandRatio >70){
            VWO = 5;
        }else if(bandRatio > 40){
            VWO = 7;
        }else if(bandRatio > 20){
            VWO = 10;
        }else {
            VWO = 13;
        }

        rate = (float) (VT*0.04 + VOO*0.07 + VWO*0.05 + BND*0.03) /(VT+VOO+VWO+BND);
        Log.d("リターン", "rate = "+rate);

        List<Integer> values = Arrays.asList(VT, VOO, VWO , BND);
        List<Entry> pie_entries = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            pie_entries.add(new Entry(values.get(i), i));
        }

        PieDataSet pieDataSet = new PieDataSet(pie_entries, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setDrawValues(true);

        List<String> pie_labels = Arrays.asList(tickerDescriptionArray[0][0],
                tickerDescriptionArray[1][0],
                tickerDescriptionArray[2][0],
                tickerDescriptionArray[3][0]);
        PieData pieData = new PieData(pie_labels, pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);

        SPchart.setData(pieData);

        //************************************************************************************
        //タイトル(資産の予想増加率)
        //************************************************************************************
        TextView SimulationLineTitleText = (TextView) findViewById(R.id.SimulationLineTitle);
        SimulationLineTitleText.setTextSize(TitleTextSize);

        //************************************************************************************
        //折れ線グラフの生成
        //************************************************************************************
        //X軸
        XAxis xAxis = SLchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//ラベルの表示位置
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);

        //グラフの縦幅・横幅
        SLchart.setLayoutParams( new LinearLayout.LayoutParams(sizeX,sizeX-(sizeX/6)));

        //グラフ用データ
        ArrayList<Entry> entries = new ArrayList<Entry>();
        float dep_j = 0f;
        for(int i=0;i<year;i++){
            if(i==0) {
                entries.add(new Entry(dep, i)); //初年度は元本のみ
            }else{
                float r = (float) Math.pow((1 + rate), i+1); //引数(年利, X年目)
                dep_j = dep * ((r - 1f) / rate);
                entries.add(new Entry(dep_j, i));
            }
        }

        //データをセット
        LineDataSet dataSet = new LineDataSet(entries,"予想運用成績");

        //ラベル
        //String[] labels = {"初年度","1年目","2年目","3年目","4年目","5年目"};
        String[] labels = new String[year];
        for(int i=0;i<year;i++){
            labels[i]= i + "年目";
        }

        //LineDataインスタンス生成
        LineData data = new LineData(labels, dataSet);

        //LineDataをLineChartにセット
        SLchart.setData(data);

        //説明分
        //SLchart.setDescription("体重の遷移");

        //背景色
        //SLchart.setBackgroundColor(Color.WHITE);

        //アニメーション
        SLchart.animateX(1200);

        //************************************************************************
        //予想運用結果(サブタイトル)
        //************************************************************************
        int ganpon = dep*year;
        assetExpectationText.setText(year + "年後" + "に投資元本" + ganpon/10000 +"万円が" + (int)dep_j/10000 + "万円になります");
        assetExpectationText.setTextSize(subTitleTextSize);

        //************************************************************************
        //積立て年数
        //************************************************************************
        //SneekBar(積立年数)のリスナー
        sneekBarYear.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        // ツマミをドラッグしたときに呼ばれる
                        YearText.setText("積立年数："+sneekBarYear.getProgress() + "年");
                        g_year = sneekBarYear.getProgress(); //年数を更新

                        //グラフを再描画
                        ActivityDrowing(g_deposit,g_AnnualInterestRate,g_year);
                        SLchart.notifyDataSetChanged();
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたときに呼ばれる
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれる
                    }
                }
        );

        //************************************************************************
        //積立て金のTextView
        //************************************************************************
        //積立金の初期値
        depositText.setText("毎月積立金：" + dep/12 + "円"); //depは毎年積立金で、表示は毎月積立金なので12で割る

        //************************************************************************
        //積立て金のEditText
        //************************************************************************
        depositEdit.setText(String.valueOf(dep/12)); //depは毎年積立金で、表示は毎月積立金なので12で割る

        //入力された時に呼ばれるコールバックリスナー
        depositEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //テキストを更新
                depositText.setText("毎月積立金：" + depositEdit.getText() + "円");

                //積立金のグローバル変数を更新
                g_deposit = Integer.parseInt(depositEdit.getText().toString()) * 12; //毎月お積立金から年間の金額を算出

                //グラフを再描画
                ActivityDrowing(g_deposit,g_AnnualInterestRate,g_year);
                SLchart.notifyDataSetChanged();
                return false;
            }
        });
    }
}
