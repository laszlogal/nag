package hu.norbisquest.nagbase.core;

/** Interface to provide objects that can be loaded and unloaded runtime.
 * 
 * 
 * @author lac
 *
 */
public interface Loadable {
	boolean load();
	boolean unload();
}
