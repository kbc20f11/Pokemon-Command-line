package pokemon02;

import java.util.Random;

public class DamageCalc {


	/* --- フィールド --- */

	// ダメージ
	private int damage;

	// 攻撃側の名前
	private String attackerName;


	/* --- コンストラクタ --- */

	public DamageCalc(Waza w, Pokemon at, Pokemon df) {

		// 攻撃側の名前をフィールドに代入
		this.attackerName= at.getName();

		/* --- ダメージ計算処理 --- */

		/*
		 * ダメージ = 攻撃側のレベル × 2 ÷ 5 ＋ 2 → 切り捨て(Math.floor)
         * × 物理技の威力 × 攻撃側のこうげき ÷ 防御側のぼうぎょ → 切り捨て(Math.floor)
         * ÷ 50 ＋ 2 → 切り捨て(Math.floor)
         * × 乱数(0.85, 0.86, …… ,0.99, 1.00 のいずれか) → 切り捨て(Math.floor)
         * × タイプ一致わざ (1.5) → 五捨五超入(Math.roundで代用)
         * × 急所 (1.5) → 五捨五超入(Math.roundで代用)
         * × 防御側のポケモンとわざの相性  → 切り捨て(Math.floor)
         * × 命中したかしないか(0か1) (いわゆる論理積?)
		 */

		// 計算用の変数を準備
		double tempDamage1;
		double tempDamage2;
		double tempDamage3;
		double tempDamage4;
		double tempDamage5;
		double tempDamage6;
		double tempDamage7;
		double cm;


		// 仕様通りに計算式を記述
		tempDamage1 = Math.floor(Status.LEVEL * 2 / 5 + 2);

		// 物理技か特殊技か?
		if (w.getPhysics()) {
			tempDamage2 = Math.floor(tempDamage1 * w.getPower() * at.getAttack() / df.getDefence());
		}else {
			tempDamage2 = Math.floor(tempDamage1 * w.getPower() * at.getSpecialAttack() / df.getSpecialDefence());
		}
			
		

		// ---
		tempDamage3 = Math.floor(tempDamage2 / 50 + 2);

		// ---
		tempDamage4 = Math.floor(tempDamage3 * this.rand());

		// ---
		tempDamage5 = Math.round(tempDamage4 * this.sameTypeAttackBonus(at, w));

		// ---
		tempDamage6 = Math.round(tempDamage5 * this.criticalMultiplier());

		// ---
		// 変数cmに相性倍率を代入
		
		// 技のタイプが複数あるかないか
		if (df.getType2() == null) {
			cm = this.chemistryMultiplier(w, df.getType());
		}else {
			cm = this.chemistryMultiplier(w, df.getType()) * this.chemistryMultiplier(w, df.getType2());
		}

		// cmの値によってメッセージを出す
		if (cm >= 2) {
			System.out.println("効果は抜群だ");
		}
        if (cm > 0 && cm <= 0.5) {
			System.out.println("効果はいまひとつのようだ");
		}
		if (cm == 0.0d) {
			System.out.println("効果はないようだ");
		}

		// 最終的にダメージ計算
		tempDamage7 = Math.floor(tempDamage6 * cm);

		// 最終的なダメージをフィールドに代入
		this.damage = (int)(tempDamage7 * this.AccuracyRate(w));
		
	}


	/* --- メソッド --- */

	// 乱数生成メソッド
	public double rand() {

		//  0 ～ 15 の16個の数を生成
		Random r = new Random();
		double num = (double)r.nextInt(16);

		// 生成されたnumを100で割りそれに0.85を加えたものを返す
		return num / 100 + 0.85;
	}

	// タイプ一致ボーナス判定
	public double sameTypeAttackBonus(Pokemon at, Waza w) {

		// タイプ一致ボーナス(既定1.0)
		double stab = 1.0d;

		// 繰り出そうとしているわざのタイプとポケモンのタイプが一致している場合ボーナスとして1.5倍をかける
		// タイプ1
		if (at.getType().equals(w.getType()))
			stab = 1.5d;

		// タイプ2がある場合の処理
		// ぬるぽが出ないようにtype2がnullじゃないことを確認してからequalsで比較
		if (at.getType2() != null) {
			if( at.getType2().equals(w.getType()))
				stab = 1.5d;
		}

		// ボーナスの倍率を返す
		return stab;
	}

	// 命中判定
	public double AccuracyRate(Waza w) {

		//わざが当たれば倍率として1.0dを返し,当たらなければ0.0dを返せばよい
		// 命中率を命中したかしないかのboolに変換する方法としてここでは乱数を使う
		Random r = new Random();
		if (r.nextInt(100) + 1 > w.getAccuracy()) {
			System.out.println(this.attackerName + "の攻撃は外れた");
			return 0.0d;  // 外れた判定
		}
		else {
			return 1.0d;  // 当たった判定
		}

	}

	// タイプ相性倍率計算メソッド
	public double chemistryMultiplier(Waza w, String dfType) {

		// 既定の倍率
		double multiplier = 1.0d;  // 相性が普通の時

		// ノーマル
		if (w.getType().equals("ノーマル")) {
			if (dfType.equals("いわ") || dfType.equals("はがね"))
				multiplier = 0.5d;
			if (dfType.equals("ゴースト"))
				multiplier = 0.0d;
		}

		// ほのお
		if (w.getType().equals("ほのお")) {
			if (dfType.equals("くさ") || dfType.equals("こおり") || dfType.equals("むし") || dfType.equals("はがね"))
				multiplier = 2.0d;
			if (dfType.equals("ほのお") || dfType.equals("みず") || dfType.equals("いわ") || dfType.equals("ドラゴン"))
				multiplier = 0.5d;
		}

		// みず
		if (w.getType().equals("みず")) {
			if (dfType.equals("ほのお") || dfType.equals("じめん") || dfType.equals("いわ"))
				multiplier = 2.0d;
			if (dfType.equals("みず") || dfType.equals("くさ") || dfType.equals("ドラゴン"))
				multiplier = 0.5d;
		}

		// でんき
		if (w.getType().equals("でんき")) {
			if (dfType.equals("みず") || dfType.equals("ひこう"))
				multiplier = 2.0d;
			if (dfType.equals("でんき") || dfType.equals("くさ") || dfType.equals("ドラゴン"))
				multiplier = 0.5d;
			if (dfType.equals("じめん"))
				multiplier = 0.0d;
		}

		// くさ
		if (w.getType().equals("くさ")) {
			if (dfType.equals("みず") || dfType.equals("じめん") || dfType.equals("いわ"))
				multiplier = 2.0d;
			if (dfType.equals("ほのお") || dfType.equals("くさ") || dfType.equals("どく") || dfType.equals("ひこう") || dfType.equals("むし") || dfType.equals("ドラゴン") || dfType.equals("はがね"))
				multiplier = 0.5d;
		}

		// こおり
		if (w.getType().equals("こおり")) {
			if (dfType.equals("くさ") || dfType.equals("じめん") || dfType.equals("ひこう") || dfType.equals("ドラゴン"))
				multiplier = 2.0d;
			if (dfType.equals("ほのお") || dfType.equals("みず") || dfType.equals("こおり") || dfType.equals("はがね"))
				multiplier = 0.5d;
		}

		// かくとう
		if (w.getType().equals("かくとう")) {
			if (dfType.equals("ノーマル") || dfType.equals("こおり") || dfType.equals("いわ") || dfType.equals("あく") || dfType.equals("	はがね"))
				multiplier = 2.0d;
			if (dfType.equals("どく") || dfType.equals("ひこう") || dfType.equals("エスパー") || dfType.equals("むし") || dfType.equals("フェアリー"))
				multiplier = 0.5d;
			if (dfType.equals("ゴースト")) {
				multiplier = 0.0d;
			}
		}

		// どく
		if (w.getType().equals("どく")) {
			if (dfType.equals("くさ") || dfType.equals("フェアリー"))
				multiplier = 2.0d;
			if (dfType.equals("どく") || dfType.equals("じめん") || dfType.equals("いわ") || dfType.equals("ゴースト"))
				multiplier = 0.5d;
			if (dfType.equals("はがね"))
				multiplier = 0.0d;
		}

		// じめん
		if (w.getType().equals("じめん")) {
			if (dfType.equals("ほのお") || dfType.equals("でんき") || dfType.equals("どく") || dfType.equals("いわ") || dfType.equals("はがね"))
				multiplier = 2.0d;
			if (dfType.equals("くさ") || dfType.equals("むし"))
				multiplier = 0.5d;
			if (dfType.equals("ひこう"))
				multiplier = 0.0d;
		}

		// ひこう
		if (w.getType().equals("ひこう")) {
			if (dfType.equals("くさ") || dfType.equals("かくとう") || dfType.equals("むし"))
				multiplier = 2.0d;
			if (dfType.equals("でんき") || dfType.equals("いわ") || dfType.equals("はがね"))
				multiplier = 0.5d;
		}

		// エスパー
		if (w.getType().equals("エスパー")) {
		if (dfType.equals("かくとう") || dfType.equals("どく"))
			multiplier = 2.0d;
		if (dfType.equals("エスパー") || dfType.equals("はがね"))
			multiplier = 0.5d;
		if (dfType.equals("あく"))
			multiplier = 0.0d;
		}

		// むし
		if (w.getType().equals("むし")) {
			if (dfType.equals("くさ") || dfType.equals("エスパー") || dfType.equals("あく"))
				multiplier = 2.0d;
			if (dfType.equals("ほのお") || dfType.equals("かくとう") || dfType.equals("どく") || dfType.equals("ひこう") || dfType.equals("ゴースト") || dfType.equals("はがね") || dfType.equals("フェアリー"))
				multiplier = 0.5d;
		}

		// いわ
		if (w.getType().equals("いわ")) {
			if (dfType.equals("ほのお") || dfType.equals("こおり") || dfType.equals("ひこう") || dfType.equals("むし"))
				multiplier = 2.0d;
			if (dfType.equals("かくとう") || dfType.equals("じめん") || dfType.equals("はがね"))
				multiplier = 0.5d;
		}

		// ゴースト
		if (w.getType().equals("ゴースト")) {
			if (dfType.equals("エスパー") || dfType.equals("ゴースト"))
				multiplier = 2.0d;
			if (dfType.equals("あく"))
				multiplier = 0.5d;
			if (dfType.equals("ノーマル"))
				multiplier = 0.0d;
		}

		// ドラゴン
		if (w.getType().equals("ドラゴン")) {
			if (dfType.equals("ドラゴン"))
				multiplier = 2.0d;
			if(dfType.equals("はがね"))
				multiplier = 0.5d;
			if(dfType.equals("フェアリー"))
				multiplier = 0.0d;
		}

		// あく
		if (w.getType().equals("あく")) {
			if (dfType.equals("エスパー") || dfType.equals("ゴースト"))
				multiplier = 2.0d;
			if (dfType.equals("かくとう") || dfType.equals("あく") || dfType.equals("フェアリー"))
				multiplier = 0.0d;
		}

		// はがね
		if (w.getType().equals("はがね")) {
			if (dfType.equals("こおり") || dfType.equals("いわ") || dfType.equals("フェアリー"))
				multiplier = 2.0d;
			if (dfType.equals("ほのお") || dfType.equals("みず") || dfType.equals("でんき") || dfType.equals("はがね"))
				multiplier = 0.5d;
		}

		// フェアリー
		if (w.getType().equals("フェアリー")) {
			if (dfType.equals("かくとう") || dfType.equals("ドラゴン") || dfType.equals("あく"))
				multiplier = 2.0d;
			if (dfType.equals("ほのお") || dfType.equals("どく") || dfType.equals("はがね"))
				multiplier = 0.5d;
		}

		return multiplier;
	}

	// 急所判定メソッド
	public double criticalMultiplier() {

		// 1/24の確率で急所にあたリダメージが1.5倍になる
		Random r = new Random();
		int rand = r.nextInt(24);  // 0～23までの24個の整数
		if (rand == 23) {
			System.out.println("急所にあたった");
			return 1.5d;
		}
		else {
			return 1.0d;
		}

	}


	/* ゲッタ */

	// ダメージのゲッタ
	public int getDamage() {
		return this.damage;
	}
}
