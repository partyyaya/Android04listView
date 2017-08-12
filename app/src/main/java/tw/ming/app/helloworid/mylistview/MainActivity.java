package tw.ming.app.helloworid.mylistview;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private List<Map<String,String>> data ;
    private String[] from = {"title","content"};
    private SimpleAdapter adapter;
    private int removeIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        initList();
    }

    private void initList(){

        int[] to  = {R.id.item_title,R.id.item_content};
        data = new LinkedList<>();
        Map<String,String> d0 = new HashMap<>();
        d0.put(from[0],"Android");
        d0.put(from[1],"Android...");
        data.add(d0);

        Map<String,String> d1 = new HashMap<>();
        d1.put(from[0],"Ios");
        d1.put(from[1],"Ios...");
        data.add(d1);

       adapter = new SimpleAdapter(this,data,R.layout.layout_item,from ,to);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("ming","Click:"+i);

            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("ming","LongClick:"+i);
                removeIndex=i;
                showDeleteDialog(i);
                return false;//false:當放開會觸發OnItemClickListener事件;true:只觸發LongClick
            }
        });
    }

    private void showDeleteDialog(int i){
        AlertDialog dialog= null;
        AlertDialog.Builder builter = new AlertDialog.Builder(this);
        builter.setTitle("Warn");
        builter.setMessage("Delete"+data.get(i).get(from[0])+"?");
        builter.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem();
                removeIndex = -1;
            }
        });
        builter.setNegativeButton("Cancel",null);
        builter.setCancelable(false);
        dialog = builter.create();
        dialog.show();
    }

    public void removeItem(){
        data.remove(removeIndex);
        adapter.notifyDataSetChanged();
    }

    public void clearItem(View view){
        data.clear();
        adapter.notifyDataSetChanged();
    }
    public void addItem(View view){
        Map<String,String> d1 = new HashMap<>();
        d1.put(from[0],"New Item");
        d1.put(from[1],"Content...."+(int)(Math.random()*49+1));
        data.add(d1);

        adapter.notifyDataSetChanged();//隨時更新改變
    }
}
