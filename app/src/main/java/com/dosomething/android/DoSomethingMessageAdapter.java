package com.dosomething.android;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * AwesomeAdapter is a Custom class to implement custom row in ListView
 *
 * @author Adil Soomro
 */
public class DoSomethingMessageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Message> mMessages;


    public DoSomethingMessageAdapter(Context context, ArrayList<Message> messages) {
        super();
        this.mContext = context;
        this.mMessages = messages;
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessages.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = (Message) this.getItem(position);

        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_chatlist, parent, false);
            holder.dosomething_fragment_chat_message = (TextView) convertView.findViewById(R.id.dosomething_fragment_chat_message);
            holder.dosomething_fragment_chat_message_layout = (LinearLayout) convertView.findViewById(R.id.dosomething_fragment_chat_message_layout);
            holder.dosomething_fragment_chat_message_time = (TextView) convertView.findViewById(R.id.dosomething_fragment_chat_message_time);
            convertView.setTag(holder);
        } else
            holder = (Holder) convertView.getTag();

        holder.dosomething_fragment_chat_message.setText(message.getMessage());

        LayoutParams lp = (LayoutParams) holder.dosomething_fragment_chat_message_layout.getLayoutParams();
        //check if it is a status message then remove background, and change text color.

        //Check whether message is mine to show green background and align to right
        if (message.isMine()) {
            holder.dosomething_fragment_chat_message.setBackgroundResource(R.drawable.round_edges_white);
            holder.dosomething_fragment_chat_message.setTextColor(R.color.white);
            lp.gravity = Gravity.RIGHT;
        }
        //If not mine then it is from sender to show orange background and align to left
        else {
            holder.dosomething_fragment_chat_message.setBackgroundResource(R.drawable.round_edges);
            holder.dosomething_fragment_chat_message.setTextColor(R.color.white);
            lp.gravity = Gravity.LEFT;
        }
        holder.dosomething_fragment_chat_message_layout.setLayoutParams(lp);

        return convertView;
    }

    private static class Holder {
        LinearLayout dosomething_fragment_chat_message_layout;
        TextView dosomething_fragment_chat_message;
        TextView dosomething_fragment_chat_message_time;
    }

    @Override
    public long getItemId(int position) {
        //Unimplemented, because we aren't using Sqlite.
        return position;
    }

}
