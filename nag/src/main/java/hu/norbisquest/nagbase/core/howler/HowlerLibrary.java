package hu.norbisquest.nagbase.core.howler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/** Loads the vendored core once in the host window, where native JsInterop looks. */
public final class HowlerLibrary {
    private static boolean loaded;

    public interface Resources extends ClientBundle {
        @Source("howler.core.min.js")
        TextResource script();

        @Source("LICENSE.howler.txt")
        TextResource license();
    }

    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Howler")
    private static class Global {
        public static native boolean codecs(String format);
    }

    private HowlerLibrary() {}

    public static Howl create(Howl.Options options) {
        ensureLoaded();
        return new Howl(options);
    }

    public static boolean canPlayMp3() {
        ensureLoaded();
        return Global.codecs("mp3");
    }

    private static void ensureLoaded() {
        if (!loaded) {
            Resources resources = GWT.create(Resources.class);
            String source = "/*\n" + resources.license().getText() + "\n*/\n"
                    + resources.script().getText();
            ScriptInjector.fromString(source)
                    .setWindow(ScriptInjector.TOP_WINDOW).inject();
            loaded = true;
        }
    }
}
