package hu.norbisquest.nagbase.core;

public class StringUtil {
    /**
     * @param delimiter
     *            delimiter
     * @param objects
     *            objects to be joined
     * @return joined string
     */
    public static String join(String delimiter, Object[] objects) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
            if (i != 0) {
                sb.append(delimiter);
            }
            sb.append(objects[i]);
        }
        return sb.toString();
    }

    /**
     * @param delimiter
     *            delimiter
     * @param objects
     *            objects to be joined
     * @return joined string
     */
    public static String join(String delimiter, Iterable<?> objects) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Object o : objects) {
            if (i != 0) {
                sb.append(delimiter);
            }
            sb.append(o);
            i++;
        }
        return sb.toString();
    }

}
