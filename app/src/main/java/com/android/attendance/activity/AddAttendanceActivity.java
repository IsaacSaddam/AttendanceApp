package com.android.attendance.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.android.attendance.bean.AttendanceBean;
import com.android.attendance.bean.StudentBean;
import com.android.attendance.context.ApplicationContext;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

import static android.content.ContentValues.TAG;

public class AddAttendanceActivity extends Activity {

	ArrayList<StudentBean> studentBeanList;
	private ListView listView ;
	String status="A";
	private ArrayAdapter<String> listAdapter;
	int sessionId=0;

	Button attendanceSubmit;
	DBAdapter dbAdapter = new DBAdapter(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.__listview_main_checkbox);

		sessionId = getIntent().getExtras().getInt("sessionId");
		
		
		
		listView=(ListView)findViewById(R.id.listview);
		final ArrayList<String> studentList = new ArrayList<String>();

		studentBeanList=((ApplicationContext)AddAttendanceActivity.this.getApplicationContext()).getStudentBeanList();


		for(StudentBean studentBean : studentBeanList)
		{
			String users = studentBean.getStudent_firstname()+" "+studentBean.getStudent_lastname();
				
			studentList.add(users);
			Log.d("users: ", users); 

		}

		listAdapter = new ArrayAdapter<String>(this, R.layout.add_student_attendance, R.id.labelA, studentList);
		listView.setAdapter( listAdapter );

		Log.e(TAG, String.valueOf(listView.getChildCount()));


		/*listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg0.getChildAt(arg2).setBackgroundColor(Color.TRANSPARENT);
				//arg0.setBackgroundColor(234567);
				arg1.setBackgroundColor(334455);
				final StudentBean studentBean = studentBeanList.get(arg2);
//				final Dialog dialog = new Dialog(AddAttendanceActivity.this);
//				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
//				dialog.setContentView(R.layout.test_layout);
//				// set title and cancelable
//				RadioGroup radioGroup;
//				RadioButton present;
//				RadioButton absent;
//				radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
//				present=(RadioButton)dialog.findViewById(R.id.PresentradioButton);
//				absent=(RadioButton)dialog.findViewById(R.id.AbsentradioButton);
//				radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//					@Override
//					public void onCheckedChanged(RadioGroup group, int checkedId) {
//						if(checkedId == R.id.PresentradioButton) {
//
//							status = "P";
//						} else if(checkedId == R.id.AbsentradioButton) {
//
//							status = "A";
//						} else {
//						}
//					}
//				});
//
//				attendanceSubmit = (Button)dialog.findViewById(R.id.attendanceSubmitButton);
//				attendanceSubmit.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//						AttendanceBean attendanceBean = new AttendanceBean();
//
//						attendanceBean.setAttendance_session_id(sessionId);
//						attendanceBean.setAttendance_student_id(studentBean.getStudent_id());
//						attendanceBean.setAttendance_status(status);
//
//						DBAdapter dbAdapter = new DBAdapter(AddAttendanceActivity.this);
//						dbAdapter.addNewAttendance(attendanceBean);
//
//						dialog.dismiss();
//
//					}
//				});
//
//				dialog.setCancelable(true);
//				dialog.show();
			}
		});

		 */



	}

	public void submit(View v)
	{
		for(int i=0;i<listView.getChildCount();i++)
		{
			final StudentBean studentBean = studentBeanList.get(i);
			CheckBox cb=(CheckBox)listView.getChildAt(i);
            if(cb.isChecked())
			{
				status="P";
			}
            else
			{
				status="A";
			}

			AttendanceBean attendanceBean = new AttendanceBean();

						attendanceBean.setAttendance_session_id(sessionId);
						attendanceBean.setAttendance_student_id(studentBean.getStudent_id());
						attendanceBean.setAttendance_status(status);

						DBAdapter dbAdapter = new DBAdapter(AddAttendanceActivity.this);
						dbAdapter.addNewAttendance(attendanceBean);
			Toast.makeText(getApplicationContext(),"Successfull",Toast.LENGTH_LONG);
			startActivity(new Intent(AddAttendanceActivity.this,AddAttandanceSessionActivity.class));

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
