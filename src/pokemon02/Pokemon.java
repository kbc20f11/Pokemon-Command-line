package pokemon02;

/**
 *
 * ポケモンの抽象クラスです
 *
 */

public abstract class Pokemon {

	/* --- フィールド --- */

	// ID
	private int id;

	// 名前
	private String name;

	// タイプ
	private String type;

	// タイプ2
	private String type2;
	// HP
	private int hp;

	// 攻撃力
	private int attack;

	// 防御力
	private int defence;

	// とくこう
	private int sAttack;

	// とくぼう
	private int sDefence;

	// すばやさ
	private int speed;

	// わざ1
	private Waza w1;
	private Waza w2;
	private Waza w3;
	private Waza w4;

	// 攻撃
	public abstract void attack(int wazaId, Pokemon df);


	// toStringのオーバーライド
	@Override
	public String toString() {

		// リターンする文字列を格納する変数を準備
		String retString;

		// 代入
		if (this.getType2() == null) {
			retString = "図鑑No:" + this.id + ", 名前:" +  this.name + ", タイプ:" + this.type + ", HP:" + this.hp + ", 攻撃:" + this.attack + ", 防御:" + this.defence
				 + ", とくこう:" + this.sAttack + ", とくぼう" + this.sDefence + ", すばやさ:" + this.speed;
		}else {
			retString = "図鑑No:" + this.id + ", 名前:" +  this.name + ", タイプ:" + this.type + ", " + this.type2 + ", HP:" + this.hp + ", 攻撃:" + this.attack + ", 防御:" + this.defence
					 + ", とくこう:" + this.sAttack + ", とくぼう" + this.sDefence + ", すばやさ:" + this.speed;
		}

		// 文字列を返す
		return retString;

	}

	// equalsのオーバーライド
	public boolean equals(Object o) {

		// 等値なら間違いなく等価
		if (this == o) {
			return true;
		}

		// idが等しければ等価
		if (o instanceof Pokemon) {
			Pokemon p = (Pokemon)o;
			if (this.id == p.id) {
				return true;
			}
		}

		// 上の条件に当てはまらないときはfalseを返す
		return false;
	}


	/* --- コンストラクタ --- */

	public Pokemon(int id) {

		// 引数をフィールドに代入
		this.id = id;

		// DownloadInfoを使ってポケモン情報をダウンロード
		DownloadInfo di = new DownloadInfo(id);

		// ステータスをフィールドに代入
		// 名前
		this.name = di.getName();

		// タイプ
		if (di.getType2() == null)
			this.type = di.getType();
		else {
			this.type = di.getType();
			this.type2 = di.getType2();
		}


		// HP
		this.hp = hpResolver(di.getHp());

		// 攻撃力
		this.attack = attackResolver(di.getAttack());

		// 防御力
		this.defence = defenceResolver(di.getDefence());

		// とくこう
		this.sAttack = sAttackResolver(di.getSAttack());

		// とくぼう
		this.sDefence = sDefenceResolver(di.getSDefence());

		// すばやさ
		this.speed = speedResolver(di.getSpeed());

		//わざ1～4
		// 自分の持っているわざをすべてインスタンス化
		this.w1 = new Waza(di.getWaza1());
		this.w2 = new Waza(di.getWaza2());
		this.w3 = new Waza(di.getWaza3());
		this.w4 = new Waza(di.getWaza4());

		// 名前をフィールドに代入
	}


	/* --- メソッド --- */

	//ゲッタ
	// idのゲッタ
	public int getId() {
		return this.id;
	}

	// 名前のゲッタ
	public String getName() {
		return this.name;
	}

	// typeのゲッタ
	public String getType() {
		return this.type;
	}

	// type2のゲッタ
	public String getType2() {
		return this.type2;
	}

	// hpのゲッタ
	public int getHp() {
		return this.hp;
	}

	// 攻撃力のゲッタ
	public int getAttack() {
		return this.attack;
	}

	// 防御力のゲッタ
	public int getDefence() {
		return this.defence;
	}

	// とくこうのゲッタ
	public int getSpecialAttack(){
		return this.sAttack;
	}

	// とくぼうのゲッタ
	public int getSpecialDefence() {
		return this.sDefence;
	}

	// スピードのゲッタ
	public int getSpeed() {
		return this.speed;
	}

	// わざ1のゲッタ
	public Waza getWaza1(){
		return this.w1;
	}

	// わざ2のゲッタ
	public Waza getWaza2(){
		return this.w2;
	}

	// わざ3のゲッタ
	public Waza getWaza3(){
		return this.w3;
	}

	// わざ4のゲッタ
	public Waza getWaza4(){
		return this.w4;
	}

	// セッタ
	// HPのセッタ
	public void setHp(int hp) {
		// 最小値を0とする(マイナスの体力はありえないので)
		this.hp = Math.max(hp, 0);
	}

	// ステータス計算メソッド
	// HP専用のresolver
	public int hpResolver(int value) {
		double resolvedHp = ((((value * 2) + Status.IV_HP + (Status.EV_HP / 4)) * Status.LEVEL) / 100) + (10 + Status.LEVEL);
		return (int)resolvedHp;
	}

	// 攻撃のresolver
	public int attackResolver(int value) {
		double resolvedAttack = ((((value * 2) + Status.IV_ATTACK + (Status.EV_ATTACK / 4)) * Status.LEVEL) / 100) + 5;
		return (int)resolvedAttack;
	}

	// 防御のresolver
	public int defenceResolver(int value) {
		double resolvedDefence = ((((value * 2) + Status.IV_DEFENCE + (Status.EV_DEFENCE / 4)) * Status.LEVEL) / 100) + 5;
		return (int)resolvedDefence;
	}

	// とくこうのresolver
	public int sAttackResolver(int value) {
		double resolvedSAttack = ((((value * 2) + Status.IV_SATTACK + (Status.EV_SATTACK / 4)) * Status.LEVEL) / 100) + 5;
		return (int)resolvedSAttack;
	}

	// とくぼうのresolver
	public int sDefenceResolver(int value) {
		double resolvedDefence = ((((value * 2) + Status.IV_SDEFENCE + (Status.EV_SDEFENCE / 4)) * Status.LEVEL) / 100) + 5;
		return (int)resolvedDefence;
	}

	// すばやさのresolver
	public int speedResolver(int value) {
		double resolvedSpeed = ((((value * 2) + Status.IV_SPEED + (Status.EV_SPEED / 4)) * Status.LEVEL) / 100) + 5;
		return (int)resolvedSpeed;
	}
}
