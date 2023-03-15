package chessgame.chess.manager;

public class m {

	public static void main(String[] args) {
		First f1 = new First("Bruh");
		First f2 = (First) f1.clone();
		//f2.setAName(0, "broseph");
		
		System.out.println(f1.getNames());
		System.out.println(f2.getNames());
		
		for(int i = 0; i < f1.getNames().length; i++) {
			System.out.println(f1.getNames()[i]);
		}
		for(int i = 0; i < f2.getNames().length; i++) {
			System.out.println(f2.getNames()[i]);
		}
	}

	public static void changeStuff(String[] arr) {
		
		
	}
}