package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.MUserData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by zn on 2016/8/24.
 */
public class AddFridentLVAdapter extends BaseAdapter {
    Context context;
    List<MUserData> list;
    String muri;

    String a ;
    public AddFridentLVAdapter(Context context, List<MUserData> list, String muri, String a){
        this.context=context;
        this.list=list;
       this.muri=muri;
       this.a=a;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i,  View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.addfridents,null);
            viewHolder=new ViewHolder();
            viewHolder.imv= (ImageView) view.findViewById(R.id.imageCircle);
            viewHolder.txv1= (TextView) view.findViewById(R.id.title);
            viewHolder.txv2= (TextView) view.findViewById(R.id.title2);
            viewHolder.button= (Button) view.findViewById(R.id.button);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setLoadingDrawableId(R.drawable.img_loading_bg)
                .setCircular(true)
                .build();
        //viewHolder.imageView.setImageResource(this.list.get(i).getUser_image());
        x.image().bind(viewHolder.imv,"http://oc1souo4f.bkt.clouddn.com/"+this.list.get(i).getHeadportrait(),imageOptions);
        viewHolder.txv1.setText(list.get(i).getName());
        viewHolder.txv2.setText(list.get(i).getSign());
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams(muri);
                params.addQueryStringParameter("flag","88");
                params.addQueryStringParameter("res","3");
                params.addQueryStringParameter("user_id",""+list.get(i).getId());
                params.addQueryStringParameter("text1",""+a);
                x.http().get(params, new Callback.CommonCallback<String>(){
                    @Override
                    public void onSuccess(String result) {
                        if (result.equals("ok")) {
                            Toast.makeText(context, "请耐心等待对方同意", Toast.LENGTH_LONG).show();
                        } else if (result.equals("no")) {
                            Toast.makeText(context, "请求已发送！请不要在重复操作！", Toast.LENGTH_LONG).show();
                        }

                    }
                        @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
        }
    });
        return view;
    }
    class ViewHolder{
        ImageView imv;
        TextView txv1;
        TextView txv2;
        Button button;
    }
}
