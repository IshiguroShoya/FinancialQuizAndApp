package com.example.ishiguro.financialquiz;

import android.util.Log;

import java.io.Serializable;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

/**
 * Created by ishiguro on 2017/11/25.
 */

public class QuizModel implements Serializable{
    final static int QUESTIONS_NUM = 10;//全体の問題数
    final static int CHOISE_NUM = 4;//選択肢

    int q_num; // インデックス
    String q_string; // 第○問というString
    String q_ProblemStatement;//問題文
    String[] choices_string = new String[CHOISE_NUM]; // 選択肢
    int answer_index; // 正解の選択肢
    String q_ans_comment;

    public static int QuizeArray_index = 0;//他クラスから参照される、クイズ問題の配列を参照するインデックス
    private static QuizModel[] quizzes = new QuizModel[QUESTIONS_NUM]; //クイズ問題の配列

    private QuizModel(int q_num, String q_string, String q_ProblemStatement, String[] choices_string, int answer_index, String answer_comment) {
        this.q_num = q_num;
        this.q_string = q_string;
        this.q_ProblemStatement = q_ProblemStatement;
        this.choices_string = choices_string;
        this.answer_index = answer_index;
        this.q_ans_comment = answer_comment;
    }

    // 問題の登録
    public static void init() {
        Log.d(LOG_TAG,Thread.currentThread().getStackTrace()[1].getClassName() + "," + Thread.currentThread().getStackTrace()[1].getMethodName() + "," + Thread.currentThread().getStackTrace()[1].getLineNumber());

        //************************************************************************************************************************************************
        //問題文
        //************************************************************************************************************************************************
        String problemStatement[] = new String[QUESTIONS_NUM];
        problemStatement[0] =
                "「貯蓄から投資へ」は2000年代に入ってから政府が積極的に声を掛けてきたスローガンですが、なぜ政府が個人に投資を促すのでしょうか。";
        problemStatement[1] =
                "次の株式会社の説明で正しいものはどれでしょうか。";
        problemStatement[2] =
                "インフレ率が２％で、普通預金口座であなたが受け取る利息が１％なら、１年後にこの口座のお金を使ってどれくらいの物を購入することができますか。";
        problemStatement[3] =
                "次のうち、インフレに強い資産を並べかえた中で、正しいのはどれでしょうか？";
        problemStatement[4] =
                "2017年4月現在、日本銀行の黒田東彦総裁は、年間X%の物価上昇を目標としている。このX%にはいる数字は次の内どれでしょうか？";
        problemStatement[5] =
                "１００万円を年率２％の利息がつく預金口座に預け入れました。それ以外、この口座への入金や出金がなかった場合、５年後、口座の残高はいくらになっているでしょうか。利息にかかる税金は考慮しないでご回答ください。";
        problemStatement[6] =
                "次の投資と投機の説明の違いについて、正しいものはどれでしょうか？";
        problemStatement[7] =
                "次のうち、投資信託と上場投資信託(ETF)の説明で間違っているものはどれでしょうか？";
        problemStatement[8] =
                "株式の配当金の説明として、間違っているものは次のうちどれでしょうか？";
        problemStatement[9] =
                "聞いたことがない金融商品を購入するかどうかを判断する際の行動や考え方として、適切でないものはどれでしょうか。";


        //************************************************************************************************************************************************
        //選択肢
        //************************************************************************************************************************************************
        String ProblemStatementArray[][] = new String[QUESTIONS_NUM][CHOISE_NUM];
        //1問目の選択肢
        ProblemStatementArray[0][0] = "日本の企業が成長し、経済が活発化するため";
        ProblemStatementArray[0][1] = "国の借金を個人に押し付けるため";
        ProblemStatementArray[0][2] = "国民に楽して儲けてほしいから";
        ProblemStatementArray[0][3] = "国民にお金を消費して欲しいため";
        //2問目の選択肢
        ProblemStatementArray[1][0] = "自社の株式を発行することで、事業を営むうえで必要な資金を投資家から調達することが出来る。";
        ProblemStatementArray[1][1] = "株式会社は社長の所有物である。";
        ProblemStatementArray[1][2] = "株主は、出資した会社が倒産した場合、その会社の借金を負うことになる。";
        ProblemStatementArray[1][3] = "決算の公告義務はない";
        //3問目の選択肢
        ProblemStatementArray[2][0] = "今日以上に物が買える";
        ProblemStatementArray[2][1] = "今日と全く同じだけ物が買える";
        ProblemStatementArray[2][2] = "今日以下しか物が買えない";
        ProblemStatementArray[2][3] = "どれにも当てはまらない";
        //4問目の選択肢
        ProblemStatementArray[3][0] = "現金＞債券＞株式";
        ProblemStatementArray[3][1] = "株式＞現金＞債券";
        ProblemStatementArray[3][2] = "株式＞債券＞現金";
        ProblemStatementArray[3][3] = "債券＞株式＞現金";
        //5問目の選択肢
        ProblemStatementArray[4][0] = "1%";
        ProblemStatementArray[4][1] = "2%";
        ProblemStatementArray[4][2] = "3%";
        ProblemStatementArray[4][3] = "4%";
        //6問目の選択肢
        ProblemStatementArray[5][0] = "１１０万円より多い";
        ProblemStatementArray[5][1] = "ちょうど１１０万円";
        ProblemStatementArray[5][2] = "１１０万円より少ない";
        ProblemStatementArray[5][3] = "上記の条件だけでは答えられない";
        //7問目の選択肢
        ProblemStatementArray[6][0] = "投資とは株式や債券などに長期的に出資することである。";
        ProblemStatementArray[6][1] = "投資とは株式や債券などを短期的(ＦＸや株式のデイトレード、スイングトレードなど)売買することである。";
        ProblemStatementArray[6][2] = "投機とは株式や債券などに長期的に出資することである。";
        ProblemStatementArray[6][3] = "投機とは短期的に株式の売買をすることで確実に儲けを出すことが出来る手法である";
        //8問目の選択肢
        ProblemStatementArray[7][0] = "投資信託(ファンド)とは、投資家から集めたお金をひとつの大きな資金としてまとめ、運用のプロであるファンドマネージャーが株式や債券などに投資・運用しその運用の成果として生まれた利益を投資家に還元するという金融商品。";
        ProblemStatementArray[7][1] = "投資信託が証券取引所に上場しているので、株の売買と同じように証券会社を通して取引ができる。ETFは、日経平均株価やTOPIXの動きに合わせて、それと同じように動くように作られているので、株のように投資先の会社を選ぶ必要がない。";
        ProblemStatementArray[7][2] = "投資信託や上場投資信託(ETF)は少額から投資することが可能であり、これ1つで数十銘柄以上に投資することができるためリスクは低い。";
        ProblemStatementArray[7][3] = "投資信託や上場投資信託(ETF)を購入するためには最低10万円必要である。";
        //9問目の選択肢
        ProblemStatementArray[8][0] = "会社が得た利益は、決算ごとに株主に配当金を出すことを法律で決められている。";
        ProblemStatementArray[8][1] = "配当金の有無とその額は、株主総会で株主により決定される。";
        ProblemStatementArray[8][2] = "配当金額÷株価で求めた数値を配当利回りという。";
        ProblemStatementArray[8][3] = "配当金を受け取るために、特別な手続きは必要ない。";
        //10問目の選択肢
        ProblemStatementArray[9][0] = "トラブルが多発し、公的機関から注意喚起がなされていないか、情報を収集する";
        ProblemStatementArray[9][1] = "インターネットや書籍、複数の販売業者から情報を収集し、他の商品と比較する";
        ProblemStatementArray[9][2] = "中立的な立場から情報提供を行っている機関等に相談し、アドバイスを受ける";
        ProblemStatementArray[9][3] = "販売業者から高いリターンが期待できるとの情報が得られれば、商品を購入する";

        //************************************************************************************************************************************************
        //答えのインデックス
        //************************************************************************************************************************************************
        int ans[] = new int[QUESTIONS_NUM];
        ans[0] = 0; //問題文1の答え
        ans[1] = 0; //問題文2の答え
        ans[2] = 2; //問題文3の答え
        ans[3] = 2; //問題文4の答え
        ans[4] = 1; //問題文5の答え
        ans[5] = 0; //問題文6の答え
        ans[6] = 0; //問題文7の答え
        ans[7] = 3; //問題文8の答え
        ans[8] = 0; //問題文9の答え
        ans[9] = 3; //問題文10の答え

        //************************************************************************************************************************************************
        //答えの表示後のコメント
        //************************************************************************************************************************************************
        String quizComment[] = new String[QUESTIONS_NUM];
        quizComment[0] =
                "日本人とアメリカ人の家庭の資産は以下のようになっているんだ。日本人が貯蓄好きなのに対してアメリカ人は株式の比率が高くなっているのが分かるね。このうちの一部でも投資に回せば経済が大きく回復すると言われているよ。\n" +
                        "現預金比率　　　：日本 52% , 米国 13%\n" +
                        "株・投資信託比率：日本 16% , 米国 47%";
        quizComment[1] =
                "株式や債券に投資するということは、お金を必要としている人に資金を提供するという、社会的にも良いことなんだよ。";
        quizComment[2] =
                "インフレ率が２％という事は、物の値段が２％上がったことを意味するよ。物の値段が２％上がっているのに貯金が１％しか増えていないのであれば、買えるものは少なくなるよ。";
        quizComment[3] =
                "インフレとはお金の価値が減り、物の値段が上がることなんだ。株式に投資するということはその会社の一部を所有することと同じで、会社は物価が上昇すれば業績も上昇するんだ。その結果、株価は物価上昇分上がるから、株式は最もインフレに強い資産と言われているよ。\n" +
                        "債券はデフォルトしない限り元本＋利息が約束された金融商品なんだ。債券を保有していてもインフレが進行すれば将来のリターンは物価上昇率分だけ損をしてしまう事になるため、株式よりもインフレに弱いんだ。\n" +
                        "現金を保有している状態でインフレが進行すると、現金の価値はどんどん目減りしていってしまうため、最もインフレに弱いよ。";
        quizComment[4] =
                "経済の世界では２％程度の緩やかなインフレが最も良いとされているよ。インフレ状態になると企業はより多くの製品やサービスを提供し、その結果利益は増え、労働者の給料も増え、家計の消費も増え、経済が活性化するんだ。日本は長らく続いたデフレ脱却に向けて金融緩和をはじめとする様々な政策を行っているんだ。";
        quizComment[5] =
                "1年後は100万円に2%の利息がついて102万円になるね。2年後は102万円に2%の利息がつくので104万400円になるよ。このように利息が利息を呼ぶことを複利というんだ。そのため、この口座の預金は110万円よりも多くなるよ。\n" +
                        "ちなみに、かの有名なアインシュタインは複利のことを人類最大の発明と呼んだよ。";
        quizComment[6] =
                "投資とは、会社を経営したりするためにお金が必要な人に、自分の大切なお金を預けるということなんだ。投機とは自分の利益のためだけにお金を投じることを言うよ。誰かが儲かれば誰かが損する、ゼロサムゲームとも呼ばれるね。\n" +
                        "この2つをしっかりと区別することが大事なんだ。";
        quizComment[7] =
                "投資信託や上場投資信託は少額から投資出来ることがメリットのひとつだよ。最近では1000円から投資信託を買えるサービスもあるんだ。";
        quizComment[8] =
                "会社が出す配当金は会社のオーナーである株主が、株主総会で決定するよ。配当金を出している会社の株を買うと配当金を受け取ることができ、配当利回りは多いところで３％を超えることもあるんだ。\n" +
                        "３％というと、100万円を投資したら年間で3万円配当金が受け取れることになり、預金の金利と比べると断然魅力的だね！";
        quizComment[9] =
                "銀行や証券会社から金融商品を薦められても、すぐに信用して買ってはいけないよ。必ず自分自身でその金融商品を調べることが大切なんだ。";

        for(int i=0;i<QUESTIONS_NUM;i++) {
            int numString = i+1;
            quizzes[i] = new QuizModel(i, "第"+ numString +"問",problemStatement[i],
                    new String[]{ProblemStatementArray[i][0], ProblemStatementArray[i][1], ProblemStatementArray[i][2], ProblemStatementArray[i][3]},
                    ans[i], quizComment[i]);
        }
    }

    // 問題を取得する
    public static QuizModel getQuiz(int num) {
        Log.d(LOG_TAG,Thread.currentThread().getStackTrace()[1].getClassName() + "," + Thread.currentThread().getStackTrace()[1].getMethodName() + "," + Thread.currentThread().getStackTrace()[1].getLineNumber());
        if (num >= quizzes.length) {
            return null;
        }
        return quizzes[num];
    }
}
