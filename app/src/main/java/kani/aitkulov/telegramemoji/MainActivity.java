package kani.aitkulov.telegramemoji;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import kani.aitkulov.telegram_emoji.EmojiActions;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editText;
    ImageView emojiButton;
    ImageView submitButton;
    private EmojiAdapter adapter;
    private EmojiActions emojIconActions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            list.add("\uD83D\uDE02\uD83D\uDE0D\uD83D\uDE18");

        emojiButton = (ImageView) findViewById(R.id.emoji_btn);
        submitButton = (ImageView) findViewById(R.id.submit_btn);
        editText = (EditText) findViewById(R.id.edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new EmojiAdapter();
        adapter.addItems(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        emojIconActions = new EmojiActions(this, findViewById(R.id.root_view), editText, emojiButton, 40);
        emojIconActions.showEmojIcon();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem(editText.getText().toString());
                editText.setText("");
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }
}
