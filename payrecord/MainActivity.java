package com.android.garima.payrecord;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    dbhelper DBhelper;
    ArrayAdapter<String> mAdapter;
    ListView listview;
    MainActivity2 main2;
    public String record;
    int info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBhelper = new dbhelper(this);
        main2=new MainActivity2();
        listview =findViewById(R.id.listview);
        loadrecord();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String info=adapterView.getItemAtPosition(i).toString();
                //Intent intent1=new Intent(getApplicationContext(),Main3Activity.class);
                //startActivity(intent1);
                Toast.makeText(MainActivity.this,"Item "+ DBhelper.get_total(i),Toast.LENGTH_SHORT).show();
            }
        });
        //write message to the database
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myref=database.getReference("message");
        myref.setValue("hello, world");
    }

    public void loadrecord()
    {
        ArrayList<String> recordlist=DBhelper.getTaskList();
        if(mAdapter==null)
        {
            mAdapter=new ArrayAdapter<String>(this,R.layout.row,R.id.record_title,recordlist);
            listview.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.clear();
            mAdapter.addAll(recordlist);
            mAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        Drawable icon=menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_record: final EditText editText =new EditText(this);
                AlertDialog dialog=new AlertDialog.Builder(this).setTitle("Add New Record").setMessage("Enter Date:")
                        .setView(editText).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                 record=String.valueOf(editText.getText());
                                Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("stuff",record);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();

                                }
                        }).setNegativeButton("cancle",null).create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void DeleteR(View view)
    {
        View parent=(View)view.getParent();
        TextView textView= (TextView)parent.findViewById(R.id.record_title);
        String record1=String.valueOf(textView.getText());
        DBhelper.deleterecord(record1);
        loadrecord();
    }


}


