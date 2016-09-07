package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yee.mcloudprojects.CircleImageView;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.ChatRoom;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yee on 2016/8/29.
 */
public class ChatRoomsAdapter extends BaseAdapter {
    Context context;
    List<ChatRoom> chatRoomList = new ArrayList<>();
    LayoutInflater inflater;

    public ChatRoomsAdapter(Context context, List<ChatRoom> chatRoomList) {
        this.context = context;
        this.chatRoomList = chatRoomList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (chatRoomList==null)?0:chatRoomList.size();
    }

    @Override
    public Object getItem(int i) {
        return (chatRoomList==null)?null:chatRoomList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.chatroom_item,null);
            viewHolder = new ViewHolder();
            viewHolder.chatroom_name = (TextView) view.findViewById(R.id.chatroom_name);
            viewHolder.chatroom_id = (TextView) view.findViewById(R.id.chatroom_id);
            viewHolder.chatroom_icon = (CircleImageView) view.findViewById(R.id.chatroom_icon);
            viewHolder.chatroom_creator = (TextView) view.findViewById(R.id.chatroom_creator);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ChatRoom chatRoom = chatRoomList.get(i);
        viewHolder.chatroom_name.setText(chatRoom.getName());
        viewHolder.chatroom_id.setText("(" + chatRoom.getNumber() + ")");
        Picasso.with(context).load(R.drawable.logo).placeholder(R.drawable.logo).into(viewHolder.chatroom_icon);
        viewHolder.chatroom_creator.setText(chatRoom.getUser_id() + "");
        return view;
    }

    class ViewHolder{
        TextView chatroom_name,chatroom_id;
        CircleImageView chatroom_icon;
        TextView chatroom_creator;
    }
}
