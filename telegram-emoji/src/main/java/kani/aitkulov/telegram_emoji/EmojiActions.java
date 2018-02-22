/*
 * Copyright 2016 Hani Al Momani
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
package kani.aitkulov.telegram_emoji;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;


/**
 * @author Kanimet Aitkulov (kani.aitkulov@gmail.com)
 */
public class EmojiActions {
    TextPaint textPaintEmoji;
    int emojiSize;
    Context context;
    EmojiPopup popup;
    View rootView;
    ImageView emojiButton;
    EditText editText;
    int KeyBoardIcon = R.drawable.ic_keyboard_w;
    int SmileyIcons = R.drawable.ic_smile_w;
    KeyboardListener keyboardListener;
    SoftKeyboardListener softKeyboardListener;
    private int innerTextChange;

    public EmojiActions(Context ctx, View rootView, EditText editText, ImageView emojiButton, int emojiSize) {
        this.editText = editText;
        this.emojiButton = emojiButton;
        this.context = ctx;
        this.rootView = rootView;
        this.emojiSize = emojiSize;
        popup = new EmojiPopup(rootView, ctx);
        textPaintEmoji = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaintEmoji.setTextSize(emojiSize);
    }

    public void setIconsIds(int keyboardIcon, int smileyIcon) {
        this.KeyBoardIcon = keyboardIcon;
        this.SmileyIcons = smileyIcon;
    }

    public void showEmojIcon() {

        //Will automatically set size according to the soft keyboard size
        popup.setSizeForSoftKeyboard();

        //If the emoji popup is dismissed, change emojiButton to smiley icon
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                changeEmojiKeyboardIcon(emojiButton, SmileyIcons);
            }
        });

        //If the text keyboard closes, also dismiss the emoji popup
        popup.setOnSoftKeyboardOpenCloseListener(new EmojiPopup.OnSoftKeyboardOpenCloseListener() {

            @Override
            public void onKeyboardOpen(int keyBoardHeight) {
                if (keyboardListener != null)
                    keyboardListener.onKeyboardOpen();
            }

            @Override
            public void onKeyboardClose() {
                if (keyboardListener != null)
                    keyboardListener.onKeyboardClose();
                if (popup.isShowing())
                    popup.dismiss();
            }
        });

        //On emoji clicked, add it to edittext
        popup.setOnEmojiClickedListener(new EmojiPopup.OnEmojiClickedListener() {
            @Override
            public void onEmojiClicked(String emoji) {
                int i = editText.getSelectionEnd();
                if (i < 0) {
                    i = 0;
                }
                try {
                    innerTextChange = 2;
                    CharSequence localCharSequence = Emoji.replaceEmoji(emoji, editText.getPaint().getFontMetricsInt(), emojiSize, false);
                    editText.setText(editText.getText().insert(i, localCharSequence));
                    int j = i + localCharSequence.length();
                    editText.setSelection(j, j);
                } catch (Exception e) {
                    Log.e("tmessages", e.toString());
                } finally {
                    innerTextChange = 0;
                }
            }
        });
        //On backspace clicked, emulate the KEYCODE_DEL key event
        popup.setOnEmojiBackspaceClickedListener(new EmojiPopup.OnEmojiBackspaceClickedListener() {

            @Override
            public void onEmojiBackspaceClicked() {
                KeyEvent event = new KeyEvent(
                        0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                editText.dispatchKeyEvent(event);
            }

        });

        // To toggle between text keyboard and emoji keyboard keyboard(Popup)
        emojiButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //If popup is not showing => emoji keyboard is not visible, we need to show it
                if (!popup.isShowing()) {

                    //If keyboard is visible, simply show the emoji popup
                    if (popup.isKeyBoardOpen()) {
                        popup.showAtBottom();
                        changeEmojiKeyboardIcon(emojiButton, KeyBoardIcon);
                    }

                    //else, open the text keyboard first and immediately after that show the emoji popup
                    else {
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();
                        popup.showAtBottomPending();
                        final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                        changeEmojiKeyboardIcon(emojiButton, KeyBoardIcon);
                    }
                    if(softKeyboardListener != null) {
                        softKeyboardListener.onShownEmoji();
                    }
                }

                //If popup is showing, simply dismiss it to show the undelying text keyboard
                else {
                    if(softKeyboardListener != null) {
                        softKeyboardListener.onShownKeyboard();
                    }
                    popup.dismiss();
                }
            }
        });

    }

    public void closeEmojIcon() {
        if (popup != null && popup.isShowing())
            popup.dismiss();

    }

    private void changeEmojiKeyboardIcon(ImageView iconToBeChanged, int drawableResourceId) {
        iconToBeChanged.setImageResource(drawableResourceId);
    }


    public interface KeyboardListener {
        void onKeyboardOpen();

        void onKeyboardClose();
    }
    
    public interface SoftKeyboardListener {
        void onShownEmoji();
        
        void onShownKeyboard();
    }

    public void setKeyboardListener(KeyboardListener listener) {
        this.keyboardListener = listener;
    }
    
    public void setSoftKeyboardListener(SoftKeyboardListener listener) {
        this.softKeyboardListener = listener;
    }

}
