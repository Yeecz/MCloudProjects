package com.example.yee.mcloudprojects.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.activity.MyChatRoomActivity;
import com.example.yee.mcloudprojects.activity.Mycloud_more2_Activity;
import com.example.yee.mcloudprojects.activity.User_data_Activity;
import com.example.yee.mcloudprojects.adapter.SortAdapter;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.CharacterParser;
import com.example.yee.mcloudprojects.entity.Mylinked;
import com.example.yee.mcloudprojects.entity.PinyinComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Linked_f1 extends Fragment {

    private ListView sortListView;
    private List<Mylinked> my = new ArrayList<>();
    private CharacterParser characterParser = CharacterParser.getInstance();
    private SortAdapter adapter;
    private View groupView;
    private View header1;
    private View header2;
    String backbaizhu;
    int requestCode;
    String muri;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        my = add();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        groupView = inflater.inflate(R.layout.fragment_linked_f1, null);
        sortListView = (ListView) groupView.findViewById(R.id.country_lvcountry);
        header1 = View.inflate(getActivity(), R.layout.item_pinned_header, null);
       // header2 = View.inflate(getActivity(), R.layout.list_pinned_header2, null);
        //  ptrl.addHeaderView(header);
        sortListView.addHeaderView(header1);
      //  sortListView.addHeaderView(header2);
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        pinyinComparator = new PinyinComparator();
        muri = myApplication.getUrl();
        initViews();
        return groupView;
    }


    /**
     * 实例化汉字转拼音类
     */
    private void initViews() {
        //实例化汉字转拼音类
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //----------------------------点击事件
                i = i - sortListView.getHeaderViewsCount();

                if (i == -2) {
                    Toast.makeText(getActivity(), "这是头部1", Toast.LENGTH_SHORT).show();
                } else if (i == -1) {
                    Intent intent=new Intent(getActivity(),MyChatRoomActivity.class);
                    startActivity(intent);
                   // Toast.makeText(getActivity(), "这是头部2", Toast.LENGTH_SHORT).show();

                } else {
                         final Mylinked be = ((Mylinked) adapter.getItem(i));
                         final String beizhu = ((Mylinked) adapter.getItem(i)).getBeizhu();
                    //   Toast.makeText(getActivity(), ""+((Mylinked) adapter.getItem(i)).getFrident_id(), Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getActivity(), User_data_Activity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Mylink", be);
                            if (beizhu == null || beizhu.equals("")) {
                                bundle.putString("Mylinked1", "无");
                            } else {
                                bundle.putString("Mylinked1", beizhu);
                            }
                            intent.putExtras(bundle);
                            startActivity(intent);
                            // Toast.makeText(getActivity(),mUserData.toString()+"",Toast.LENGTH_LONG).show();
                        }


            }
        });
        // 给listview设置长按点击事件
        sortListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                i = i - sortListView.getHeaderViewsCount();

                if (i == -2) {
                    Toast.makeText(getActivity(), "这是头部1", Toast.LENGTH_SHORT).show();
                } else if (i == -1) {
                    //我的粉丝

                   // Toast.makeText(getActivity(), "这是头部2", Toast.LENGTH_SHORT).show();
                } else {
                    //用xuntils向数据库发送删除请求，adapter.notifyDataSetChanged();
                    final Mylinked aaa = ((Mylinked) adapter.getItem(i));
                    Dialog dialog = new AlertDialog.Builder(getActivity())
                            .setIcon(R.drawable.warning).setTitle("是否修改备注？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent1 = new Intent(getActivity(), Mycloud_more2_Activity.class);
                                    //getUsername()
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("mylinked", aaa);
                                    bundle.putString("who", "2");
                                    intent1.putExtras(bundle);
                                    startActivityForResult(intent1, 1);
                                    //请求码是1
                                    //  Toast.makeText(getActivity(), "已删除",Toast.LENGTH_LONG).show();
                                }

                            }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Toast.makeText(getActivity(), "未删除", Toast.LENGTH_LONG).show();
                                }
                            }).create();

                    dialog.show();
                }
                return true;
            }
        });
        // 根据a-z进行排序源数据
        adapter = new SortAdapter(getActivity(), my);
        Collections.sort(my, pinyinComparator);
        sortListView.setAdapter(adapter);
}

    @Override
    public void onResume() {
        Log.i("Linked_f1", "Linked_f1->onResume");
        my.clear();
        List<Mylinked> my2 = new ArrayList<>();
        my2=add();
        my.addAll(my2);
        Collections.sort(my, pinyinComparator);
        adapter.notifyDataSetChanged();
        Log.i("Linked_f1", "Linked_f1->onResume");
        super.onResume();
    }


    //汉字转换成拼音
    public String filledData(String str) {
        String pinyin = characterParser.getSelling(str);
        String sortString = pinyin.substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            return sortString.toUpperCase();
        } else {
            return "#";
        }

    }

    //startActivityForResult-》从跳转到的界面，返回之后，自动回调该方法  ---该activity已经创建adapter已经存在
    //--------------最先执行，然后才是onresum方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //得到新Activity 关闭后返回的数据
        if (requestCode == 1 && resultCode == 2) {
            //修改可用

        }
    }


    SQLiteDatabase mSQLiteDatabase;
    public List<Mylinked> add() {
        List<Mylinked> mylinkeds = new ArrayList<Mylinked>();
        mSQLiteDatabase = getActivity().openOrCreateDatabase("moyun", getActivity().MODE_PRIVATE, null);
        String CREATE_TABLE3 = "select * from friend";
        Cursor cursor2 = mSQLiteDatabase.rawQuery(CREATE_TABLE3, null);
        while (cursor2.moveToNext()) {
            Long id = cursor2.getLong(0);//id
            Integer user_id = cursor2.getInt(1);//用户id
            Integer frident_id = cursor2.getInt(2);//好友id
            String user_name = cursor2.getString(3); //名称昵称
            String user_image = cursor2.getString(4);//头像
            String beizhu = cursor2.getString(5);
            Integer ver = cursor2.getInt(6);//版本
            String userwords = cursor2.getString(7);//语句
            //开头首字母
            String sortLetters = null;
            if (beizhu == null || beizhu.equals("")) {
                sortLetters = filledData(user_name);
            } else {
                sortLetters = filledData(beizhu);
            }
            mylinkeds.add(new Mylinked(id, user_id, frident_id, user_name, user_image, beizhu, ver, sortLetters, userwords));
            //Toast.makeText(getActivity(), "" +ver,Toast.LENGTH_LONG).show();
        }
        cursor2.close();
        mSQLiteDatabase.close();
        return mylinkeds;

    }

}


