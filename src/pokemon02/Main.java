package pokemon02;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * メインメソッドです
 *
 */

public class Main {
	@SuppressWarnings("unlikely-arg-type")
	public static void main(String[] args) {

		// デバッグするときは//を外す
		// debug();

		// ポケモンのステータスの妥当性チェック
		Status.validityCheck();

		// 乱数をインスタンス化
		Random r = new Random();

		// scannerをインスタンス化
		Scanner sc = new Scanner(System.in);  // 標準入力

		// ポケモンを生成
		Pikachu p = new Pikachu(Status.PIKACHU);  // ピカチュウ
		UnknownPokemon up = new UnknownPokemon(r.nextInt(Status.MAX_POKEMON_COUNT) + 1); // ポケモンX

		// ---
		System.out.println("野生の" + up.getName() + "が現れた");

		// 選択肢
		int input;
		do {
			System.out.println("どうする？");
			System.out.print("1.たたかう, 2.自分と相手のステータスを見る 3.自分と相手がequalsであるか調べる 4.にげる -> ");
			input = sc.nextInt();
			if (input == 2) {
				System.out.println("自分:" + p.toString());
				System.out.println("相手:" + up.toString());
			}
			if(input == 3) {
				if(p.equals(up)) {
					System.out.println("どうやらequalsのようだ");
				}else {
					System.out.println("残念ながらequalsではないようだ");
				}
			}
			if(input == 4) {
				System.out.println("うまく逃げ切れた");
				System.exit(1);  // プログラムを終了
			}
		}while (input != 1);
		// 戦闘処理
		// どちらかが倒れるまでループ
		try {  // thread.sleepで発生する例外(InterruptedException)をキャッチ
			do {
				// 自分のほうが相手よりもすばやさが上の時先制させる
				if (p.getSpeed() >= up.getSpeed()) {
					System.out.println(p.getName() + "はどうする？");

					// 入力値のわざidを格納する変数を準備
					int wazaId;

					// 入力ループ
					do {
						//選択肢を表示し、入力させる
						System.out.print("1:"+ p.getWaza1().getName() + ", " + "2:"+ p.getWaza2().getName() + ", " + "3:"+ p.getWaza3().getName() + ", "+ "4:"+ p.getWaza4().getName() + ", " +  "5:" + "わざの説明を見る" + " -> " );
						wazaId = sc.nextInt();
						if (wazaId == 5) {
							System.out.println("1:"+ p.getWaza1().toString() + ", " + "2:"+ p.getWaza2().toString() + ", " + "3:"+ p.getWaza3().toString()+ ", "+ "4:"+ p.getWaza4().toString());
						}
					}while(wazaId < 1 || wazaId > 4); // 入力が1～4以外の時はループ

					// 1000ミリ秒(=1秒)待機
					Thread.sleep(1000);


					p.attack(wazaId, up);

					// ここで相手のポケモンの体力がなくなった場合は戦闘ループを抜ける
					if(up.getHp() == 0)
						break;

					// 1000ミリ秒(=1秒)待機
					Thread.sleep(1000);

					// 後攻(すなわち相手のポケモン)の攻撃ターン
					up.autoAttack(p);

					// 1000ミリ秒(=1秒)待機
					Thread.sleep(1000);

				}
				else {  // 自分よりも相手のほうがすばやさが早い場合

					// 先攻(すなわち相手のポケモン)の攻撃ターン
					up.autoAttack(p);

					// 1000ミリ秒(=1秒)待機
					Thread.sleep(1000);

					// ここで自分の体力がなくなった場合は戦闘ループを抜ける
					if (p.getHp() == 0)
						break;

					// 自分のターン
					System.out.println(p.getName() + "はどうする？");
					// 入力値のわざidを格納する変数を準備
					int wazaId;

					// 入力ループ
					do {
						//選択肢を表示し、入力させる
						System.out.print("1:"+ p.getWaza1().getName() + ", " + "2:"+ p.getWaza2().getName() + ", " + "3:"+ p.getWaza3().getName() + ", "+ "4:"+ p.getWaza4().getName() + ", " +  "5:" + "わざの説明を見る" + " -> " );
						wazaId = sc.nextInt();
						if (wazaId == 5) {
							System.out.println("1:"+ p.getWaza1().toString() + ", " + "2:"+ p.getWaza2().toString() + ", " + "3:"+ p.getWaza3().toString()+ ", "+ "4:"+ p.getWaza4().toString());
						}
					}while(wazaId < 1 || wazaId > 4);  // 入力が1～4以外の時はループ

					// 1000ミリ秒(=1秒)待機
					Thread.sleep(1000);

					// 選んだわざでアタック
					p.attack(wazaId, up);

					// 1000ミリ秒(=1秒)待機
					Thread.sleep(1000);

				}
			}while (p.getHp() != 0 && up.getHp() != 0);
		}catch(Exception e) {
			e.printStackTrace(); // thread.sleepの例外をキャッチ
		}

		// 判定
		if (p.getHp() == 0) {  // 自分の体力が0の時

			//負けメッセージ
			System.out.println("Game Over");
			System.out.println(up.getName() +"の残りHP:" + up.getHp());
		}
		else if(up.getHp() == 0) { // 相手の体力が0の時

			//勝ちメッセージ
			System.out.println("野生の" + up.getName() + "は倒れた");
			System.out.println("You win");
			System.out.println(p.getName() +"の残りHP:" + p.getHp());
		}

		// nextLineに空白文字が返るのを受け止める
		sc.nextLine();
		
		System.out.println("終了するには何か入力してください");
		
		// 何か入力するまで終了を待つ
		sc.nextLine();

		System.out.println("終了します");
		
		// scannerを閉じる
		sc.close();
	}

	public static void debug() {

		// デバッグ用に乱数を生成
		Random r = new Random();
		int rand = r.nextInt(Status.MAX_POKEMON_COUNT) + 1;

		// 乱数を引数にしてダウンロードしてきたデータの戻り値をひたすら列挙して表示
		DownloadInfo di = new DownloadInfo(rand);

		// クラスDownloadInfoの動作チェック
		System.out.println("以下、クラスDownloadInfoの各メンバを表示(ポケモンIDはランダム)");
		System.out.println(di.getPokemonId() + " --- getPokemonId()");
		System.out.println(di.getName() + " --- getName()");
		System.out.println(di.getType() + " --- getType()");
		System.out.println(di.getType2() + " --- getType2() ※タイプが2つない場合はnull");
		System.out.println(di.getHp() + " --- getHp()");
		System.out.println(di.getAttack() + " --- getAttack()");
		System.out.println(di.getDefence() + " --- getDefence()");
		System.out.println(di.getSAttack() + " --- getSAttack()");
		System.out.println(di.getSDefence() + " --- getSDefence()");
		System.out.println(di.getSpeed()  + " --- getSpeed()");
		System.out.println(di.getWaza1() + " --- getWaza1()");
		System.out.println(di.getWaza2() + " --- getWaza2()");
		System.out.println(di.getWaza3() + " --- getWaza3()");
		System.out.println(di.getWaza4() + " --- getWaza4()");

		System.out.println(""); // 改行

		// ピカチュウを生成してtoStringで表示されるステータスが正しいかチェックしてみる
		Pikachu p = new Pikachu(Status.PIKACHU);
		System.out.println("以下、ポケモンクラスののtoStringのテスト");
		System.out.println(p.toString() + " --- ピカチュウのtoString()");

		// UnknownPokemonを生成してtoStringで表示されるステータスが正しいかチェックしてみる
		UnknownPokemon up = new UnknownPokemon(rand);
		System.out.println(up.toString() + " --- PokemonXのtoString()");

		System.out.println(""); // 改行

		// わざのチェック
		Waza w1 = new Waza(di.getWaza1());
		System.out.println("以下、クラスWazaの各メンバを表示(ポケモンIDはランダム)");
		System.out.println(w1.getName() + " --- getName()");
		System.out.println(w1.getType() + " --- getType()");
		System.out.println(w1.getPower() + " --- getPower()");
		System.out.println(w1.getAccuracy()  + " --- getAccuracy()");
		System.out.println(w1.getPhysics() + " --- getPhysics() ※bool型");
		System.out.println(w1.toString() + "--- WazaクラスのtoString()");

		System.out.println(""); // 改行

		// 戦闘のチェック
		System.out.println("以下、autoAttackチェック");
		System.out.println(up.getName() + "が" + p.getName() + "にautoAttack()したと仮定する");
		up.autoAttack(p);

		// デバッグが終わったらプログラムを終了する
		System.exit(0);
	}

}
