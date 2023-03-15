package chessgame.chess.manager;

public class First implements Cloneable {

	private String name;
	private String[] names;
	
	public First(String name) {
		setName(name);
		names = new String[] {"0", "1", "2"};
	}
	
	public Object clone(){  
	    try{  
	        return super.clone();  
	    }catch(Exception e){ 
	        return null; 
	    }
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setAName(int index, String element) {
		names[index] = element;
	}
	
	public String[] getNames() {
		return names;
	}
	
	
}
