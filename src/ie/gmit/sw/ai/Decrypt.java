package ie.gmit.sw.ai;

import java.awt.Point;

public class Decrypt {
	
	private String [][] table;
	private String key;
	private String cipherText;
	
	public Decrypt(String key, String ct) {
		super();
		this.key = key;
		this.cipherText = ct;
		this.table = this.cipherTable(key);
	}

	public String decode(){
	    String decoded = "";
	    for(int i = 0; i < cipherText.length() / 2; i++){
	      char a = cipherText.charAt(2*i);
	      char b = cipherText.charAt(2*i+1);
	      int r1 = (int) getPoint(a).getX();
	      int r2 = (int) getPoint(b).getX();
	      int c1 = (int) getPoint(a).getY();
	      int c2 = (int) getPoint(b).getY();
	      if(r1 == r2){
	        c1 = (c1 + 4) % 5;
	        c2 = (c2 + 4) % 5;
	      }else if(c1 == c2){
	        r1 = (r1 + 4) % 5;
	        r2 = (r2 + 4) % 5;
	      }else{
	        int temp = c1;
	        c1 = c2;
	        c2 = temp;
	      }
	      decoded = decoded + table[r1][c1] + table[r2][c2];
	    }
	    return decoded;
	  }
	  
	  private String[][] cipherTable(String key){
		    String[][] playfairTable = new String[5][5];
		    String keyString = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";
		    
		    // fill string array with empty string
		    for(int i = 0; i < 5; i++)
		      for(int j = 0; j < 5; j++)
		        playfairTable[i][j] = "";
		    
		    for(int k = 0; k < keyString.length(); k++){
		      boolean repeat = false;
		      boolean used = false;
		      for(int i = 0; i < 5; i++){
		        for(int j = 0; j < 5; j++){
		          if(playfairTable[i][j].equals("" + keyString.charAt(k))){
		            repeat = true;
		          }else if(playfairTable[i][j].equals("") && !repeat && !used){
		            playfairTable[i][j] = "" + keyString.charAt(k);
		            used = true;
		          }
		        }
		      }
		    }
		    return playfairTable;
		  }

	// returns a point containing the row and column of the letter
	  private Point getPoint(char c){
	    Point pt = new Point(0,0);
	    for(int i = 0; i < 5; i++)
	      for(int j = 0; j < 5; j++)
	        if(c == table[i][j].charAt(0))
	          pt = new Point(i,j);
	    return pt;
	  }
}
