package kani.aitkulov.telegramemoji;

import android.app.Application;

import kani.aitkulov.telegram_emoji.TelegramEmoji;

/**
 * Created by kani on 1/28/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TelegramEmoji.init(this);
    }
}
