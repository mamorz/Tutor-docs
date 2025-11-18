enum Color {
	RED("FF0000", "255, 0, 0"),
	WHITE("FFFFFF", "255, 255, 255"), 
	BLACK("000000", "0, 0, 0"), 
	YELLOW("FFFF00", "255, 255, 0"), 
	BLUE("0000FF", "0, 0, 255");
	
	Color(String hexCode, String rgbCode) {
		this.hexCode = hexCode;
		this.rgbCode = rgbCode;
	}
	
	public String getHexadecimalCode() {
		return this.hexCode;
	}
	
	public String getRGBCode() {
		return this.rgbCode;
	}
}

class Main {
	public static void main(String[] args) {
		System.out.println(Color.RED.getHexadecimalCode());
		System.out.println(Color.YELLOW.getRGBCode());
	}
}