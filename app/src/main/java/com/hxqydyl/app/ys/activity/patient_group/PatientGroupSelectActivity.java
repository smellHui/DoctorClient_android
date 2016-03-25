package com.hxqydyl.app.ys.activity.patient_group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.PatientGroupSelectAdapter;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.utils.DialogUtils;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/20.
 */
public class PatientGroupSelectActivity extends BaseTitleActivity implements View.OnClickListener, DialogUtils.SavePatientGroupListener {
    private ListView lvPatientGroup;
    private ArrayList<PatientGroup> patientGroupArrayList = new ArrayList<PatientGroup>();
    private PatientGroupSelectAdapter patientGroupSelectAdapter;

    private Button bAddPatientGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_group_select);

        initViewOnBaseTitle(getString(R.string.select_group));
        setBackListener(this);

        lvPatientGroup = (ListView) findViewById(R.id.lvPatientGroup);
        patientGroupArrayList.add(new PatientGroup("妄想症"));
        patientGroupArrayList.add(new PatientGroup("遗忘症"));
        patientGroupArrayList.add(new PatientGroup("衰弱症"));
        patientGroupSelectAdapter = new PatientGroupSelectAdapter(this, patientGroupArrayList);
        lvPatientGroup.setAdapter(patientGroupSelectAdapter);
        lvPatientGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                patientGroupSelectAdapter.setSelect(position);
            }
        });

        bAddPatientGroup = (Button) findViewById(R.id.bAddPatientGroup);
        bAddPatientGroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                int groupSelcect = patientGroupSelectAdapter.getSelect();
                if (groupSelcect != -1) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("patient_group", patientGroupArrayList.get(groupSelcect));
                    setResult(RESULT_OK, resultIntent);
                } else {
                    setResult(RESULT_CANCELED);
                }
                this.finish();
                break;
            case R.id.bAddPatientGroup:
                showInputDialog();
                break;
        }
    }

    private void showInputDialog() {
        DialogUtils.showAddPatientGroupDialog(this, this);
    }


    @Override
    public void onSaveGroup(String groupName) {
        patientGroupArrayList.add(new PatientGroup(groupName));
        patientGroupSelectAdapter.notifyDataSetChanged();
    }
}