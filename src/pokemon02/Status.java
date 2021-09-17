package pokemon02;

/**
 *
 * ステータスを記入するクラスです
 *
 */

public class Status {

	/* --- 全般 --- */

	public static final int PIKACHU = 25; // 主人公となるポケモンのID

	public static final int MAX_POKEMON_COUNT = 893; // 敵として登場させるポケモンIDの上限

	public static final int LEVEL = 50; // 対戦させる両ポケモンのレベル

	/* --- 努力値(振れる努力値はMAX:252かつすべての要素の合計値が510以下) --- */

	// HP
	public static final int EV_HP = 0;

	// 攻撃
	public static final int EV_ATTACK = 0;

	// 防御
	public static final int EV_DEFENCE = 0;

	// とくこう
	public static final int EV_SATTACK = 0;

	// とくぼう
	public static final int EV_SDEFENCE = 0;

	// すばやさ
	public static final int EV_SPEED = 0;

	/* --- 個体値(各々の最大値:31) --- */

	// HP
	public static final int IV_HP = 31;

	// 攻撃
	public static final int IV_ATTACK = 31;

	// 防御
	public static final int IV_DEFENCE = 31;

	// とくこう
	public static final int IV_SATTACK = 31;

	// とくぼう
	public static final int IV_SDEFENCE = 31;

	// すばやさ
	public static final int IV_SPEED = 31;

	/*  --- 妥当性チェック --- */

	@SuppressWarnings("unused")
	public static void validityCheck() {
		try {

			// 各々の個体値の上限は31
			if (IV_HP > 31 || IV_ATTACK > 31 || IV_DEFENCE > 31 || IV_SATTACK > 31 || IV_SDEFENCE > 31 || IV_SPEED > 31)
				throw new InvalidPokemonStatusException("ポケモンのステータスが異常です");

			//  各々の努力値はMAX:252かつすべての要素の合計値が510以下
			if (EV_HP > 252 || EV_ATTACK > 252 || EV_DEFENCE > 252 || EV_SATTACK > 252 || EV_SDEFENCE > 252 || EV_SPEED > 252 || (EV_HP + EV_ATTACK + EV_DEFENCE + EV_SATTACK + EV_SDEFENCE + EV_SPEED > 510))
				throw new InvalidPokemonStatusException("ポケモンのステータスが異常です");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}


}
