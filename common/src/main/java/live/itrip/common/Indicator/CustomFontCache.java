package live.itrip.common.Indicator;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

import live.itrip.common.util.AppLog;

/**
 * Created by ameykshirsagar on 23/07/16.
 * Custom Font Cache Implementation
 * Prevent(s) memory leaks due to Typeface objects
 */
public class CustomFontCache {
    private static final Hashtable<String, Typeface> fCache = new Hashtable<>();

    public static Typeface get(String tfn, Context ctx) {
        Typeface tf = fCache.get(tfn);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(ctx.getAssets(), tfn);
                if (tf != null) {
                    fCache.put(tfn, tf);
                }

                return tf;
            } catch (Exception e) {
                AppLog.w(e.toString());
                return null;
            }
        } else {
            return tf;
        }

    }
}
