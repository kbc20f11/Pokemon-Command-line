package pokemon02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * ポケモンの情報をダウンロードしてくるクラスです
 *
 */



public class DownloadInfo {

    /* --- 定数 --- */

    // URL
    private final String pokemonApi = "https://yakkun.com/swsh/zukan/n<pokemonId>";
    private final String imageUrl = "https://sp4.raky.net/poke/icon96/n<pokemonId>.gif";

    // Encording
    private final String encording = "EUC-JP";

    // Regex Patterns
    private final String regexName = "<title>(.*?)｜ポケモン図鑑(.*?)｜ポケモン徹底攻略</title>";
    private final String regexType = "タイプ</td><td><ul class=\"type\"><li><a href=\"(.*?)\"><img src=\"(.*?)\" alt=\"(.*?)\" /></a></li>(.*?)</ul></td></tr>";
    private final String regexType2 = "<a href=\"(.*?)\"><img src=\"(.*?)\" alt=\"(.*?)\" />";
    private final String regexHp = "HP</td>(.*?)&nbsp;(\\d+)</td></tr>";
    private final String regexAttack = "こうげき</td>(.*?)&nbsp;(\\d+)</td></tr>";
    private final String regexDefence = "ぼうぎょ</td>(.*?)&nbsp;(\\d+)</td></tr>";
    private final String regexSAttack = "とくこう</td>(.*?)&nbsp;(\\d+)</td></tr>";
    private final String regexSDefence = "とくぼう</td>(.*?)&nbsp;(\\d+)</td></tr>";
    private final String regexSpeed = "すばやさ</td>(.*?)&nbsp;(\\d+)</td></tr>";
    private final String regexWaza = "data-power=\"(\\d+)\"><td class=\"move_condition_cell\">(.*?)</td><td colspan=\"7\" class=\"move_name_cell\"><a href=\"\\./search/\\?move=(\\d+)\">(.*?)</a>(.*?)</td></tr><tr class=\"move_detail_row\"><td><span class=\"type (.*?)\">(.*?)</span></td><td class=\"(.*?)\"><span class=\"(.*?)\">(.*?)</span></td>(.*?)<td>(\\d+)</td>(.*?)</td><td>(\\d+)</td><td>(\\d+)</td><td>(.*?)</td><td class=\"move_ex_cell\">(.*?)</td></tr>";

    /* --- フィールド --- */

    private String pokemonId = null;
    private String name = null;
    private String type = null;
    private String type2 = null;
    private String hp = null;
    private String attack = null;
    private String defence = null;
    private String sAttack = null;
    private String sDefence = null;
    private String speed = null;
    private String waza1 = null;
    private String waza2 = null;
    private String waza3 = null;
    private String waza4 = null;

    /* --- コンストラクタ --- */

    public DownloadInfo(int pokemonId) {

        // 引数をフィールドに代入
        this.pokemonId = String.valueOf(pokemonId);

        // 読んだhtmlの内容を格納する変数を用意
        String html = null;

        // BufferedReaderで一行ずつ読み込んだ際にそれを連結するtameためのSBを用意
        StringBuilder sb = new StringBuilder();

        // ---

        try {

            // ポケモンのURLを取得
            URL url = new URL(getPokemonUrl());  // これはMalformedURLExceptionを投げる

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), encording));) {


                // 最終行まで一行ずつ読んで連結
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                //StringBuilderを文字列に変換してhtmlに格納
                html = sb.toString();

            } catch (IOException e) {
                e.printStackTrace(); //例外
            }

        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        // regex match

        // 名前のパターン
        Pattern pName = Pattern.compile(regexName);
        Matcher mName = pName.matcher(html);

        // 名前のパターンに一致する部分を取り出してフィールドに代入
        if (mName.find()){
            this.name = mName.group(1);
        }

        // タイプのパターン
        Pattern pType = Pattern.compile(regexType);
        Matcher mType = pType.matcher(html);



        //タイプのパターンに一致する部分を取り出してフィールドに代入
        if (mType.find()) {
            this.type = mType.group(3);
            String temp = mType.group(4);
            Pattern pType2 = Pattern.compile(regexType2);
            Matcher mType2 = pType2.matcher(temp);
            if (mType2.find()) {
                this.type2 = mType2.group(3);
            }
        }


        // HPのパターン
        Pattern pHp = Pattern.compile(regexHp);
        Matcher mHp = pHp.matcher(html);

        // HPのパターンに一致する部分を取り出してフィールドに代入
        if(mHp.find()) {
            this.hp = mHp.group(2);
        }

        // 攻撃力のパターン
        Pattern pAttack = Pattern.compile(regexAttack);
        Matcher mAttack = pAttack.matcher(html);

        // 攻撃力のパターンに一致する部分を取り出してフィールドに代入
        if (mAttack.find()) {
            this.attack = mAttack.group(2);
        }

        // 防御力のパターン
        Pattern pDefence = Pattern.compile(regexDefence);
        Matcher mDefence = pDefence.matcher(html);

        // 防御力のパターンに一致する部分を取り出してフィールドに代入
        if (mDefence.find()) {
            this.defence = mDefence.group(2);
        }

        // とくこうのパターン
        Pattern pSAttack = Pattern.compile(regexSAttack);
        Matcher mSAttack = pSAttack.matcher(html);

        // とくこうのパターンに一致する部分を取り出してフィールドに代入
        if (mSAttack.find()) {
            this.sAttack = mSAttack.group(2);
        }

        // とくぼうのパターン
        Pattern pSDefence = Pattern.compile(regexSDefence);
        Matcher mSDefence = pSDefence.matcher(html);

        // とくぼうのパターンに一致する部分を取り出してフィールドに代入
        if (mSDefence.find()) {
            this.sDefence = mSDefence.group(2);
        }

        // スピードのパターン
        Pattern pSpeed = Pattern.compile(regexSpeed);
        Matcher mSpeed = pSpeed.matcher(html);

        // スピードのパターンに一致する部分を取り出してフィールドに代入
        if (mSpeed.find()) {
            this.speed = mSpeed.group(2);
        }

        //技のパターン
        Pattern pWaza = Pattern.compile(regexWaza);
        Matcher mWaza = pWaza.matcher(html);

        // 文字結合用のStringBuilederを新しく用意
        StringBuilder sb2 = new StringBuilder();

        // 技を格納する配列を用意
        String[] wazaArray = new String[300]; 

        // マッチした技の数をカウントするためのカウンタを用意
        int counter = 0;

        // 技のパターンに一致する部分をループしてすべて取り出して配列に格納
        while (mWaza.find()) {  // マッチしている間はループ

            // ひとつずつsb2にAppendしていく
            sb2.append("名前:");
            sb2.append(mWaza.group(4));
            sb2.append(", タイプ:");
            sb2.append(mWaza.group(7));
            sb2.append(", 物/特:");
            sb2.append(mWaza.group(10));
            sb2.append(", 威力:");
            sb2.append(mWaza.group(12));
            sb2.append(", 命中率:");
            sb2.append(mWaza.group(14));
            sb2.append(", 説明:");
            sb2.append(mWaza.group(17));

            // 配列に格納
            wazaArray[counter] =sb2.toString();

            // sb2をクリア
            sb2.setLength(0);

            // counterをインクリメント
            counter++;
        }

        // わざが4つない場合は処理を中断
        try {
            if (counter < 4) {
            	throw new SkillNotFoundException("4つの技が見つかりませんでした");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 技の配列の中からランダムに4つを選ぶ
        // 乱数を準備
        Random r =new Random();  // import java.util.Random

        // 技が4つしかなくその中で重複があった場合
        try {
        	if (counter == 4 && (wazaArray[0].equals(wazaArray[1]) || wazaArray[0].equals(wazaArray[2]) || wazaArray[0].equals(wazaArray[3]) || wazaArray[1].equals(wazaArray[2]) || wazaArray[1].equals(wazaArray[3]) || wazaArray[2].equals(wazaArray[3]))) {
        		throw new SkillNotFoundException("4つの技が見つかりませんでした");
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }

        // 乱数を添え字に使って技をランダムに4つ選ぶ
        do {

            // 必ず一回は実行
            this.waza1 = wazaArray[r.nextInt(counter)];
            this.waza2 = wazaArray[r.nextInt(counter)];
            this.waza3 = wazaArray[r.nextInt(counter)];
            this.waza4 = wazaArray[r.nextInt(counter)];

        }while((this.waza1.equals(this.waza2) || this.waza1.equals(this.waza3)) || this.waza1.equals(this.waza4) || this.waza2.equals(this.waza3) || this.waza2.equals(this.waza4) || this.waza3.equals(this.waza4));

    }

	/* --- メソッド --- */

    // ポケモンのURLのゲッタ
    public String getPokemonUrl(){
        return pokemonApi.replaceAll("<pokemonId>", this.pokemonId);
    }

    // ポケモンの画像URLのゲッタ
    public String getImageUrl() {
        return imageUrl.replaceAll("<pokemonId>", this.pokemonId);
    }

    // Idのゲッタ
    public String getPokemonId() {
        return this.pokemonId;
    }

    // 名前のゲッタ
    public String getName() {
        return this.name;
    }

    // タイプのゲッタ
    public String getType() {
        return this.type;
    }

    // タイプ2のゲッタ
    public String getType2() {
        return this.type2;
    }

    // HPのゲッタ
    public int getHp() {
        return Integer.parseInt(this.hp); //フィールドはStringなのでintに変換
    }

    // 攻撃力のゲッタ
    public int getAttack() {
        return Integer.parseInt(this.attack);
    }

    // 防御力のゲッタ
    public int getDefence() {
        return Integer.parseInt(this.defence);
    }

    // とくこうのゲッタ
    public int getSAttack() {
        return Integer.parseInt(this.sAttack);
    }

    // とくぼうのゲッタ
    public int getSDefence() {
        return Integer.parseInt(this.sDefence);
    }

    // スピードのゲッタ
    public int getSpeed() {
        return Integer.parseInt(this.speed);
    }

    // 技1のゲッタ
    public String getWaza1() {
        return this.waza1;
    }

    // 技2のゲッタ
    public String getWaza2() {
        return this.waza2;
    }
    // 技3のゲッタ
    public String getWaza3() {
        return this.waza3;
    }

    // 技4のゲッタ
    public String getWaza4() {
        return this.waza4;
    }
}

