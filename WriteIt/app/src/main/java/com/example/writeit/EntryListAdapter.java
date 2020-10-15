
package com.example.writeit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class EntryListAdapter extends ListAdapter<Entry, EntryListAdapter.EntryHolder> {
    private OnItemClickListener listener;

    public EntryListAdapter() {
        super(DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public EntryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_entry, parent, false);

        return new EntryHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull EntryHolder holder, int position) {
        holder.bind(getItem(position));
    }


    public Entry getEntryAt(int position) {
        return getItem(position);
    }



    public static final DiffUtil.ItemCallback<Entry> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Entry>() {
                @Override
                public boolean areItemsTheSame(@NonNull Entry oldItem, @NonNull Entry newItem) {
                    return oldItem.getEntry_id() == newItem.getEntry_id();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Entry oldItem, @NonNull Entry newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle()) &&
                            oldItem.getEntry().equals(newItem.getEntry());
                }
            };


    class EntryHolder extends RecyclerView.ViewHolder {
        private Entry mEntry;
        private TextView titleTextView;


        public EntryHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.entry_title);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }


        public void bind(Entry entry) {
            mEntry = entry;
            titleTextView.setText(mEntry.getTitle());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Entry entry);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}


