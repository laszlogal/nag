package hu.norbisquest.nagbase.core;

import com.google.gwt.dom.client.Element;

/**
 * Class to query information about the browser.
 */
public class Browser {
    /**
     *
     * @return true if Safari browser
     */
    public static native boolean isSafari() /*-{
        $wnd.console.log('USERAGENT: ' + $wnd.navigator.userAgent)
		return /safari/.test($wnd.navigator.userAgent.toLowerCase() );
	}-*/;

    public static native boolean isCordova() /*-{
        var res = typeof($wnd.window._cordovaNative) != 'undefined';
        $wnd.console.log("cordova: " + res);
        return res;
	}-*/;

    public static native void exitApp() /*-{
            $wnd.navigator.app.exitApp();
	}-*/;

    public static native void requestFullscreen() /*-{
		var element = $doc.documentElement;
		if (element.requestFullscreen) {
			element.requestFullscreen();
		} else if (element.mozRequestFullScreen) {
			element.mozRequestFullScreen();
		} else if (element.webkitRequestFullscreen) {
			element.webkitRequestFullscreen();
		} else if (element.msRequestFullscreen) {
			element.msRequestFullscreen();
		}
	}-*/;

    public static native boolean isFullscreen() /*-{
		var element = $doc.documentElement;
		return $doc.fullscreenElement || $doc.mozFullScreenElement
				|| $doc.webkitFullscreenElement || document.msFullscreenElement;
	}-*/;


    public static native void toggleFullscreen() /*-{
		var element = $doc.documentElement;
		if (!$doc.fullscreenElement && // alternative standard method
		!$doc.mozFullScreenElement && !$doc.webkitFullscreenElement
				&& !document.msFullscreenElement) { // current working methods
			if (element.requestFullscreen) {
				element.requestFullscreen();
			} else if (element.mozRequestFullScreen) {
				element.mozRequestFullScreen();
			} else if (element.webkitRequestFullscreen) {
				element.webkitRequestFullscreen();
			} else if (element.msRequestFullscreen) {
				element.msRequestFullscreen();
			}
		} else {

			if ($doc.exitFullscreen) {
				$doc.exitFullscreen();
			} else if ($doc.msExitFullscreen) {
				$doc.msExitFullscreen();
			} else if ($doc.mozCancelFullScreen) {
				$doc.mozCancelFullScreen();
			} else if ($doc.webkitExitFullscreen) {
				$doc.webkitExitFullscreen();
			}
		}
	}-*/;


    public static native void addFullscreenChangeListener() /*-{
		var listener = function() {
			@hu.norbisquest.nagbase.core.Browser::onFullscreenChange()();
		};

		$doc.addEventListener('fullscreenchange', listener);
		$doc.addEventListener('mozfullscreenchange', listener);
		$doc.addEventListener('webkitfullscreenchange', listener);
		$doc.addEventListener('msullscreenchange', listener);
	}-*/;

    public static void onFullscreenChange() {
        // implement me
   }
    private final static String[] engines = new String[] { "", "webkit", "ms", "Moz", "O", "khtml" };

    public static void scale(Element elem, double ratio) {
        scale(elem, 0, 0, ratio);
    }

    public static void scale(Element elem, int x, int y, double ratio) {
        String name = "Transform";
        String origin = "TransformOrigin";
        for (String prefix : engines) {
            elem.getStyle().setProperty(prefix + name, "scale(" + ratio + ")");
            elem.getStyle().setProperty(prefix + origin, x + "px " + y + "px 0px");
        }
    }

    public static native boolean isAndroid() /*-{
        var navString = navigator.userAgent.toLowerCase();
        if (navString.indexOf("android") < 0) {
            return true;
        }
        return true;
    }-*/;
}
