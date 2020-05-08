package com.swufe.firstapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TextListActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener {

    private String TAG = "mylist2";

    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;//存放文字，图片信息
    private SimpleAdapter listItemAdapter;//适配器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        Intent intent = getIntent();
        String keywords = intent.getStringExtra("keyWords");

        this.setListAdapter(listItemAdapter);

        Thread t = new Thread(this);
        t.start();//开启线程

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    List<HashMap<String,String>> list2 = (List<HashMap<String,String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(TextListActivity.this, list2,// listItems数据源
                            R.layout.list_item, //ListItem的XML 布局实现
                            new String[] { "ItemTitle","ItemDetail" },
                            new int[] { R.id.itemTitle,R.id.itemDetail }
                    );
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };

        getListView().setOnItemClickListener(this);




    }
    private void initListView(){
        listItems = new ArrayList<HashMap<String,String>>();
        for(int i = 0;i < 10;i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle","Rate:"+ i);//标题文字
            map.put("ItemDetail", "detail" + i); //详情描述
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this, listItems,// listItems数据源
                R.layout.list_item, //ListItem的XML 布局实现
                new String[] { "ItemTitle","ItemDetail" },
                new int[] { R.id.itemTitle,R.id.itemDetail }
        );
    }

    public void run() {
        //获取网络数据，放入list带回主线程
        List<HashMap<String,String>>retList = new ArrayList<HashMap<String,String>>();

        Document doc = null;
        try {
            Thread.sleep(1000);
            doc = Jsoup.connect("https://it.swufe.edu.cn/index/tzgg.htm").get();
            Log.i(TAG, "run: " + doc.title());
            Elements tables = doc.getElementsByTag("span");

            int result1 = tables.toString().indexOf("keywords");

            for (int i = 7; i < tables.size(); i=i+2) {
                Element table2 = tables.get(i);
                if(result1 != -1) {
                    String str1 = table2.text();

                SharedPreferences sharedPreferences = getSharedPreferences("mytitle", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("title",str1);
                editor.commit();
                Log.i(TAG,"onActivityResult: 数据已保存到sharePreferences");//数据保存


                    Log.i(TAG, "run: " + str1);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ItemTitle", str1);
                    retList.add(map);
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String,String>map = (HashMap<String,String>)getListView().getItemAtPosition(position);

        final Uri uri=Uri.parse("https://it.swufe.edu.cn/index/tzgg.htm");
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }

    }

