package factory;

public enum Browsers {

    CHROME("CHROME");

    private final String browserName;

    Browsers(String browserName) {
        this.browserName = browserName;
    }

    public static Browsers getBrowserByStringName(String browserName) {
        for (Browsers br : Browsers.values()) {
            if (br.browserName.equals(browserName.toUpperCase())) {
                return br;
            }
        }
        throw new IllegalArgumentException("There is no realization for the browser " + browserName);
    }
}
