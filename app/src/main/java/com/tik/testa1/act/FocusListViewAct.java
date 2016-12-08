package com.tik.testa1.act;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tik.testa1.R;
import com.tik.testa1.adapter.FocusListViewAdapter;
import com.tik.testa1.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @auth tik
 */
public class FocusListViewAct extends BaseActivity {

    @BindView(R.id.listview)
    ListView listView;
    @Override
    protected void beforeBindViews() {

    }

    @Override
    protected void afterBindViews() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 20; i++){
            data.add("I am item "+(i+1));
        }
        final FocusListViewAdapter adapter = new FocusListViewAdapter(this, data);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                adapter.setCurrentItem(position);
//                adapter.notifyDataSetChanged();
//            }
//        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_focuslistview;
    }
}
