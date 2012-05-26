package Beatbox;

public class Message {
	String message;
	int[][] pattern;
	
	public Message(String message, int[][] pattern) {
		this.message = message;
		this.pattern = pattern;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int[][] getPattern() {
		return pattern;
	}
	
	public String toString() {
		String out = "";
		message.replace(';', ',');
		
		out += message + ";";
		for (int x = 0; x <= 15; x++) {
			for (int y = 0; y <= 15; y++) {
				if (pattern[x][y] == 1)
					out += "1&";
				else
					out += "0&";
			}
			out = out.substring(0, out.length()-1);
			out+="/";
		}
		out = out.substring(0, out.length()-1);
		
		return out;
	}
	
	public static Message toMessage(String messageString) {
		String[] split1 = messageString.split(";");
		String message = split1[0];
		
		String split2 = split1[1];
		String[] split3 = split2.split("/");
		String[][] split4 = new String[16][16];
		for(int x = 0; x <= 15; x++) {
			String[] split5 = split3[x].split("&");
			for(int y = 0; y <= 15; y++) {
				split4[x][y] = split5[y];
			}
		}
		
		int[][] pattern = new int[16][16];
		for(int x = 0; x <= 15; x++) {
			for(int y = 0; y <= 15; y++) {
				if(split4[x][y].contains("1"))
					pattern[x][y] = 1;
				else
					pattern[x][y] = 0;
			}
		}
		
		return new Message(message, pattern);
	}
}
