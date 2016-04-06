package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.follow.plan.CheckSycle;
import com.hxqydyl.app.ys.utils.DialogUtils;

import java.util.List;

/**
 * Created by wangchao36 on 16/3/23.
 */
public class PlanCheckSycleAdapter extends BaseAdapter {

    private Context context;
    private List<CheckSycle> list;

    public PlanCheckSycleAdapter(Context context, List<CheckSycle> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_plan_check_sycle, parent, false);
        }
        TextView tvName = BaseViewHolder.get(convertView, R.id.tvName);
        TextView tvLiverCycle = BaseViewHolder.get(convertView, R.id.tvLiverCycle);
        final CheckSycle cs = list.get(position);
        tvName.setText(cs.getName());
        tvLiverCycle.setText(cs.getSycle());

        tvLiverCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.cycleDialog(context, (TextView) v, CheckSycle.cycleItem2);
            }
        });
        return convertView;
    }
}
