public enum Seasons {
    WINTER("this is winter"),

    SPRING("This is spring"),

    SUMMER("This is summer"),

    FALL("This is fall");

    private final String errorMsg;

    private Seasons(String optMsg) {
        errorMsg = "Error" + optMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
