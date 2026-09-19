package hu.norbisquest.nagbase.game;

public class Log {
    /**
     * Prints message to the JavaScript console.

     * @param msg
     *            The debug message to print.
     */

    private static native void log(String msg, String color) /*-{
        var str = '%c' + msg + 'color: ' + color + ';';
        $wnd.console.log(msg);
        str = null;
    }-*/;

    public static void debug(String msg) {
        log(msg, "#000000");
    }

    public static void warn(String msg) {
        log("[WARNING] " + msg, "yellow");
    }

    public static void error(String msg) {
        log("[ERROR] " + msg, "red");
    }

    public static void printNull(String name, Object obj) {
        debug("[NULLTEST] " + name + " IS " + (obj == null ? "NULL" : "OK"));
    }
}
