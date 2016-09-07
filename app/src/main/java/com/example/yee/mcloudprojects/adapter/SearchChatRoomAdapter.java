package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.yee.mcloudprojects.CircleImageView;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.ChatRoom;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by Yee on 2016/8/29.
 */
public class SearchChatRoomAdapter extends BaseAdapter {
    Context context;
    List<ChatRoom> chatRooms = new ArrayList<>();
    LayoutInflater inflater;

    public SearchChatRoomAdapter(Context context, List<ChatRoom> chatRooms) {
        this.context = context;
        this.chatRooms = chatRooms;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (chatRooms==null)?0:chatRooms.size();
    }

    @Override
    public Object getItem(int i) {
        return (chatRooms==null)?null:chatRooms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.searchchatroom_item,null);
            viewHolder = new ViewHolder();
            viewHolder.chatroom_name = (TextView) view.findViewById(R.id.searchchatroom_name);
            viewHolder.chatroom_icon = (CircleImageView) view.findViewById(R.id.searchchatroom_icon);
            viewHolder.chatroom_creator = (TextView) view.findViewById(R.id.searchchatroom_creator);
            viewHolder.chatroom_join = (Button) view.findViewById(R.id.searchchatroom_join);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ChatRoom chatRoom = chatRooms.get(i);
        viewHolder.chatroom_name.setText(chatRoom.getName());
        Picasso.with(context).load(R.drawable.logo).placeholder(R.drawable.logo).into(viewHolder.chatroom_icon);
        viewHolder.chatroom_creator.setText(chatRoom.getUser_id() + "");
        viewHolder.chatroom_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinChatRoom(i);
            }
        });

        return view;
    }

    private void joinChatRoom(int i) {
        if (RongIM.getInstance()!=null){
            RongIM.getInstance().startConversation(context, Conversation.ConversationType.CHATROOM, chatRooms.get(i).getNumber(), chatRooms.get(i).getName());
        }
    }

    class ViewHolder{
        TextView chatroom_name;
        CircleImageView chatroom_icon;
        TextView chatroom_creator;
        Button chatroom_join;
    }
}
