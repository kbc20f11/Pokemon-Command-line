package pokemon02;

import java.util.Random;

/**
 *
 * ポケモンXのクラスです
 *
 */

public class UnknownPokemon extends Pokemon {

	/* --- コンストラクタ --- */

	public UnknownPokemon(int id){
		super(id);
	}

	/* --- メソッド --- */

	// 攻撃メソッド
	public void attack(int wazaId, Pokemon df) {

		//ダメージを格納する変数を準備
		int damage = 0;

		// わざによって処理を場合分け
		switch (wazaId) {
		case 1:
			System.out.println(this.getName() + "の" + this.getWaza1().getName());
			DamageCalc dc1 = new DamageCalc(this.getWaza1(), this, df);
			damage = dc1.getDamage();
			break;
		case 2:
			System.out.println(this.getName() + "の" + this.getWaza2().getName());
			DamageCalc dc2 = new DamageCalc(this.getWaza2(), this, df);
			damage = dc2.getDamage();
			break;
		case 3:
			System.out.println(this.getName() + "の" + this.getWaza3().getName());
			DamageCalc dc3 = new DamageCalc(this.getWaza3(), this, df);
			damage = dc3.getDamage();
			break;
		case 4:
			System.out.println(this.getName() + "の" + this.getWaza4().getName());
			DamageCalc dc4 = new DamageCalc(this.getWaza4(), this, df);
			damage = dc4.getDamage();
			break;
		}

		// ダメージ処理
		df.setHp(df.getHp() - damage);

		// ダメージメッセージ
		System.out.println(df.getName() +"に" + damage +"ダメージを与えた");

	}


	/* 自動攻撃メソッド */
	public void autoAttack(Pokemon df) {

		// 1～4までの乱数で技を選ぶ
		Random r = new Random();
		int wazaId = r.nextInt(4) + 1;

		// wazaIdを引数に加えてattackを呼び出す
		this.attack(wazaId, df);
	}

}
