package com.example.yuan.yhsyuekao.fragemnt;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yuan.yhsyuekao.Bean.UserBean;
import com.example.yuan.yhsyuekao.NetUtils.NetUtil;
import com.example.yuan.yhsyuekao.R;
import com.example.yuan.yhsyuekao.adapter.HomeAdapter;
import com.example.yuan.yhsyuekao.sql.SqlDao;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

public class HomeFragment extends Fragment {

    private PullToRefreshListView pull;
    private int mPage;
    private String str="http://www.xieast.com/api/news/news.php?page=%d";
    private HomeAdapter homeAdapter;
    private SqlDao dao;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.homefragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //找控件
        pull = view.findViewById(R.id.pull);
        //获取dao
        dao = new SqlDao(getActivity());
        //适配器
        homeAdapter = new HomeAdapter(getActivity());
        pull.setAdapter(homeAdapter);
        //允许刷新加载
        pull.setMode(PullToRefreshListView.Mode.BOTH);
        //监听
        pull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
              //刷新
              mPage=1;
              infoData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载
                infoData();
            }
        });

        infoData();
    }
    //加载数据
    private void infoData() {
        NetUtil.getData(String.format(str, mPage), UserBean.class, new NetUtil.CallBack<UserBean>() {
            @Override
            public void onSuccess(UserBean o) {
                //判断网络
                if (!NetUtil.isPanduan(getActivity()))
                {
                    Toast.makeText(getActivity(),"没有网络",Toast.LENGTH_LONG).show();
                    //无网状态查询数据库数据展示出来
                    List<UserBean.DataBean> select = dao.select();
                    homeAdapter.setmDatas(select);

                    return;
                }

                if (mPage==1)
                {
                    dao.delete();
                }
                dao.insertAll(o.getData());
                if (mPage==1)
                {
                    homeAdapter.setmDatas(o.getData());

                }
                else
                {
                    homeAdapter.addmDatas(o.getData());
                }
                //加载
                mPage++;
                //停止刷新
                pull.onRefreshComplete();
            }
        });
    }
}
