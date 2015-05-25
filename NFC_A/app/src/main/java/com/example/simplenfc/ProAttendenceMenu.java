package com.example.simplenfc;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ProAttendenceMenu extends Activity implements AdapterView.OnItemClickListener {

    Button btn1, btn2;
    ArrayAdapter<String> adapter;
    ListView listView;
    String select, select2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proattendencemenu);
        // �����δ� ���Ǹ���Ʈ�� Login ��Ƽ��Ƽ���� ���� �޾Ƽ� ǥ�� �� ��� ��
        ArrayList<String> courselist = new ArrayList<String>();
        courselist.add("�˰���");
        courselist.add("����Ʈ�������");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,courselist);
        listView = (ListView) findViewById(R.id.courselist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // ���� ����Ʈ �� �ϳ��� ��ġ�Ǹ� �� ���ǰ� �־��� ��¥�� ���� ��Ƽ��Ƽ�� �����Ѵ�.
        Intent intent = new Intent(ProAttendenceMenu.this, ProOneAttend.class);
        intent.putExtra("Date","5��24��");
        startActivity(intent);

    }
}