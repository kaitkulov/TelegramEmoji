/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kani.aitkulov.telegram_emoji.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import kani.aitkulov.telegram_emoji.Emoji;
import kani.aitkulov.telegram_emoji.R;


/**
 * @author Kanimet Aitkulov (kani.aitkulov@gmail.com)
 */
public class EmojiTextView extends TextView {
    private int mEmojiSize;
    private int mEmojiTextSize;
    private TextPaint textPaintEmoji;


    public EmojiTextView(Context context) {
        super(context);
        init(null);
    }

    public EmojiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EmojiTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mEmojiTextSize = (int) getTextSize();
        if (attrs == null) {
            mEmojiSize = (int) getTextSize();
        } else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Emoji);
            mEmojiSize = (int) a.getDimension(R.styleable.Emoji_emojiSize, getTextSize());
            a.recycle();
        }
        textPaintEmoji = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaintEmoji.setTextSize(mEmojiSize);
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!TextUtils.isEmpty(text)) {
            text = Emoji.replaceEmoji(text, textPaintEmoji.getFontMetricsInt(), mEmojiTextSize, false);
        }
        super.setText(text, type);
    }

    public void setEmojiSize(int pixels) {
        mEmojiSize = pixels;
        textPaintEmoji.setTextSize(mEmojiSize);
        super.setText(getText());
    }
}
