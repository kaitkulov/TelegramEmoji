package kani.aitkulov.telegram_emoji;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by kani on 1/28/17.
 */

public class TelegramEmoji {
    private static volatile Application application;
    public static volatile Handler applicationHandler;

    public static void init(Application application) {
        TelegramEmoji.application = application;
        applicationHandler = new Handler(application.getApplicationContext().getMainLooper());
    }

    public static Context getContext() {
        return application;
    }
}
