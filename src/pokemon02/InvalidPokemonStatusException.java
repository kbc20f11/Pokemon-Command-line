package pokemon02;

/**
 *
 * オリジナル例外クラスです
 *
 */

public class InvalidPokemonStatusException extends Exception{
	public InvalidPokemonStatusException(String msg) {
		super(msg);
	}
}
