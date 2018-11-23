package com.ismailhakkiaydin.xmlmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.view.ContextMenu;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    static final private int ADD_NEW_TODO = Menu.FIRST;
    static final private int REMOVE_TODO = Menu.FIRST+1;

    private boolean addingNew = false;

    private ArrayList<String> arrayList;
    private ListView listView;
    private EditText editText;
    private ArrayAdapter<String> arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText =  findViewById(R.id.editText);
        listView =  findViewById(R.id.listView);

        arrayList = new ArrayList<>();
        //int resID = R.layout.todolist_item;
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.todolist_item, arrayList);
        listView.setAdapter(arrayAdapter);

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {

                if(keyEvent.getAction()== KeyEvent.ACTION_DOWN)
                    if(keyCode==KeyEvent.KEYCODE_ENTER){
                        arrayList.add(0,editText.getText().toString());
                        arrayAdapter.notifyDataSetChanged();
                        editText.setText("");
                        cancelAdd();
                        return true;
                    }

                return false;
            }
        });
        registerForContextMenu(listView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);


      /*  MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
*/
        MenuItem addItem = menu.add(0,ADD_NEW_TODO, Menu.NONE,R.string.add_new);
        MenuItem removeItem = menu.add(0,REMOVE_TODO,Menu.NONE,R.string.remove);

        addItem.setIcon(R.drawable.add);
        removeItem.setIcon(R.drawable.remove);

        addItem.setShortcut('0','a');
        removeItem.setShortcut('1','r');


        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Selected To Do Item");
        menu.add(0,REMOVE_TODO,Menu.NONE,R.string.remove);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        int idx = listView.getSelectedItemPosition();

        String removeTitle = getString(addingNew ? R.string.cancel : R.string.remove);

        MenuItem removeItem = menu.findItem(REMOVE_TODO);
        removeItem.setTitle(removeTitle);
        removeItem.setVisible(addingNew || idx>-1);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int index = listView.getSelectedItemPosition();

        switch (item.getItemId()){
            case (REMOVE_TODO):{
                if (addingNew){
                    cancelAdd();
                }else{
                    removeItem(index);
                }
                return true;
            }
            case (ADD_NEW_TODO): {
                addNewItem();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        switch (item.getItemId()){
            case(REMOVE_TODO):{
                AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                int index = menuInfo.position;
                removeItem(index);
                return true;
            }
        }

        return false;
    }

    private void cancelAdd(){
        addingNew = false;
        editText.setVisibility(View.GONE);
    }

    private void addNewItem(){
        addingNew = true;
        editText.setVisibility(View.VISIBLE);
        editText.requestFocus();
    }

    private void removeItem(int index){
        arrayList.remove(index);
        arrayAdapter.notifyDataSetChanged();
    }


}
