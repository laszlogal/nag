package hu.norbisquest.nagbase.core.howler;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/** Only the Howler API used by the channel adapter. */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Howl")
public class Howl {
    public Howl(Options options) {}

    public native Howl load();
    public native double play();
    public native double play(double soundId);
    public native Howl pause(double soundId);
    public native Howl stop();
    public native boolean playing(double soundId);
    public native Howl loop(boolean loop);
    public native Howl volume(double volume);
    public native Howl unload();
    public native Howl once(String event, SoundCallback callback);

    @JsFunction
    public interface Callback {
        void call();
    }

    @JsFunction
    public interface SoundCallback {
        void call(double soundId);
    }

    @JsFunction
    public interface ErrorCallback {
        void call(Object soundId, Object error);
    }

    @JsFunction
    public interface PlaybackErrorCallback {
        void call(double soundId, Object error);
    }

    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
    public static class Options {
        public String[] src;
        public boolean preload;
        public boolean loop;
        public double volume;
        public Callback onload;
        public SoundCallback onplay;
        public SoundCallback onpause;
        public SoundCallback onend;
        public ErrorCallback onloaderror;
        public PlaybackErrorCallback onplayerror;
    }
}
