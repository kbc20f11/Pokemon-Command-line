package pokemon02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Waza {

	/* --- フィールド --- */

	// 技の名前
	private String name;

	// 技のタイプ
	private String type;

	// 物理か特殊か?
	private boolean isPhysics;

	// 技の威力
	private int power;

	// 技の命中率
	private int accuracy;

	// 技の説明文
	private String description;

	// 名前のパターン
	private final String regexName = "名前:(.*?),";

	// タイプのパターン
	private final String regexType = "タイプ:(.*?),";

	// 物理のパターン
	private final String regexPhysics = "物/特:(.*?),";

	// 威力のパターン
	private final String regexPower = "威力:(.*?),";

	// 命中率のパターン
	private final String regexAccuracy = "命中率:(.*?),";

	// 説明文のパターン
	private final String regexDescription = "説明:(.*+)";

	/* --- コンストラクタ --- */

	public Waza(String wazaData) {

		// 技の名前を抽出
		Pattern pName = Pattern.compile(regexName);
		Matcher mName = pName.matcher(wazaData);
		if (mName.find())
			this.name = mName.group(1);

		// 技のタイプを抽出
		Pattern pType = Pattern.compile(regexType);
		Matcher mType = pType.matcher(wazaData);
		if (mType.find())
			this.type = mType.group(1);

		// 物理か特殊か?
		Pattern pPhysics = Pattern.compile(regexPhysics);
		Matcher mPhysics = pPhysics.matcher(wazaData);
		if (mPhysics.find()) {
			String temp = mPhysics.group(1);
			if (temp.equals("物理"))
				this.isPhysics = true;
			else
				this.isPhysics = false;
		}

		// 技の威力を抽出
		Pattern pPower = Pattern.compile(regexPower);
		Matcher mPower = pPower.matcher(wazaData);
		if (mPower.find())
			this.power = Integer.parseInt(mPower.group(1));

		// 命中率を抽出
		Pattern pAccuracy = Pattern.compile(regexAccuracy);
		Matcher mAccuracy = pAccuracy.matcher(wazaData);
		if (mAccuracy.find())
			this.accuracy = Integer.parseInt(mAccuracy.group(1));

		// 説明文を抽出
		Pattern pDescription = Pattern.compile(regexDescription);
		Matcher mDescription = pDescription.matcher(wazaData);

		if (mDescription.find())
			this.description = mDescription.group(1);

	}

	/* --- メソッド --- */

	// 名前のゲッタ
	public String getName() {
		return this.name;
	}

	// タイプのゲッタ
	public String getType() {
		return this.type;
	}

	// 物理か特殊かのゲッタ
	public boolean getPhysics() {
		return this.isPhysics;
	}

	// 威力のゲッタ
	public int getPower() {
		return this.power;
	}

	// 命中率のゲッタ
	public int getAccuracy() {
		return this.accuracy;
	}

	// 説明文のゲッタ
	public String getDescription() {
		return this.description;
	}

	// toStringをオーバーライドする形で実装する
	@Override
	public String toString() {
		String physics;
		if (this.isPhysics) {
			physics = "物理";
		}else {
			physics = "特殊";
		}

		return this.name + ", " + "タイプ:" + this.type + ", " + physics + " ," + "威力:" + this.power + ", " + "命中:" + this.accuracy;
	}

}
