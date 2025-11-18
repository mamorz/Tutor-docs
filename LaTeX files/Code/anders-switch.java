enum Color {
    RED, WHITE, BLACK, YELLOW, BLUE
}

class Main {
    Color mainColor = Color.BLACK;

    
    public int foo(){
        String str = switch (mainColor) {
            case RED -> "yellow";
            case BLUE -> "white";
            case BLACK -> "black";
            case WHITE -> "blue";
            case YELLOW -> "red";
        };
		
		
        return switch (mainColor) {
            case RED -> 0;
            case BLUE -> 1;
            case BLACK -> 2;
            case WHITE -> 3;
            case YELLOW -> 4;
        };
    }
}