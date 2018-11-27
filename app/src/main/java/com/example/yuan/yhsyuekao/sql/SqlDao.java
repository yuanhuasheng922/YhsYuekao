package com.example.yuan.yhsyuekao.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yuan.yhsyuekao.Bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class SqlDao {
    private Context context;
    private final SQLiteDatabase database;

    public SqlDao(Context context) {
        Sql sql=new Sql(context);
        database = sql.getReadableDatabase();
    }

    public void insert(UserBean.DataBean dataBean)
    {
        ContentValues values=new ContentValues();
        values.put("title",dataBean.getTitle());
        values.put("name",dataBean.getAuthor_name());
        database.insert("tabless",null,values);
    }

    //全部添加
    public void insertAll(List<UserBean.DataBean> list)
    {
        for (UserBean.DataBean data : list)
        {
            insert(data);
        }
    }

    public void delete()
    {
        database.delete("tabless",null,null);
    }

    //查询
    public List<UserBean.DataBean> select()
    {
        List<UserBean.DataBean> list=new ArrayList<>();
        Cursor query = database.query("tabless", null, null, null, null, null, null);
        while (query.moveToNext())
        {
            String name = query.getString(query.getColumnIndex("name"));
            String title = query.getString(query.getColumnIndex("title"));
            UserBean.DataBean datas=new UserBean.DataBean(name,title);
            list.add(datas);
        }
        return list;
    }

}
