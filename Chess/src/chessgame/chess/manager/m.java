package chessgame.chess.manager;

public class m {

	public static void main(String[] args) {
		String[] arr = new String[] {"One", "Two", "Three"};
		changeStuff(arr);
		for(int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}

	}

	public static void changeStuff(String[] arr) {
		arr[0] = "*()*)(";
	}
}
