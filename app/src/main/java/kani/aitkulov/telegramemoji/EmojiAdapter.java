package kani.aitkulov.telegramemoji;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kani.aitkulov.telegram_emoji.ui.EmojiTextView;

/**
 * Created by kani on 1/26/17.
 */

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.ViewHolder> {
    List<String> items;

    public EmojiAdapter() {
        this.items = new ArrayList<>();
    }

    public void addItem(String text) {
        items.add(text);
        notifyItemInserted(items.size());
    }

    public void addItems(List<String> list) {
        items.addAll(0, list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        EmojiTextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (EmojiTextView) itemView.findViewById(R.id.textView);
        }

    }
}
