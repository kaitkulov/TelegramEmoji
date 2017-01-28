/*
 * This is the source code of Telegram for Android v. 3.x.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2016.
 */

package kani.aitkulov.telegram_emoji;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

    public static Pattern pattern = Pattern.compile("[\\-0-9]+");

    public static volatile DispatchQueue globalQueue = new DispatchQueue("globalQueue");

    public static Integer parseInt(String value) {
        if (value == null) {
            return 0;
        }
        Integer val = 0;
        try {
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                String num = matcher.group(0);
                val = Integer.parseInt(num);
            }
        } catch (Exception e) {
            Log.e("tmessages", e.toString());
        }
        return val;
    }

    public static Long parseLong(String value) {
        if (value == null) {
            return 0L;
        }
        Long val = 0L;
        try {
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                String num = matcher.group(0);
                val = Long.parseLong(num);
            }
        } catch (Exception e) {
            Log.e("tmessages", e.toString());
        }
        return val;
    }
}
