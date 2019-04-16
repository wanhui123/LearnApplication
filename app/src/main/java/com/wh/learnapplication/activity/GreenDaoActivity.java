package com.wh.learnapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.wh.learnapplication.MyApplication;
import com.wh.learnapplication.R;
import com.wh.learnapplication.entity.Student;
import com.wh.learnapplication.greenDao.db.DaoSession;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GreenDaoActivity extends AppCompatActivity {

    @BindView(R.id.btn_insert)
    AppCompatButton btnInsert;
    @BindView(R.id.btn_delete)
    AppCompatButton btnDelete;
    @BindView(R.id.btn_update)
    AppCompatButton btnUpdate;
    @BindView(R.id.btn_query)
    AppCompatButton btnQuery;
    @BindView(R.id.tv_content)
    AppCompatTextView tvContent;

    DaoSession daoSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        ButterKnife.bind(this);
         daoSession = ((MyApplication) getApplication()).getDaoSession();
        List<Student> students = queryAll();
        if (students!=null&&students.size()>0){
            index=students.get(students.size()-1).getStudentNo()+1;
        }

    }



    

    int index=1;
    StringBuilder sb=new StringBuilder();

    @OnClick({R.id.btn_insert, R.id.btn_delete, R.id.btn_update, R.id.btn_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_insert:
                Toast.makeText(this,"增",Toast.LENGTH_SHORT).show();
                Student student=new Student();
                student.setAge(10+index);
                student.setAddress("地址"+index);
                student.setGrade("年级"+index);
                student.setName("小明"+index);
                student.setSex("男"+index);
                student.setSchoolName("实验小学"+index);
                student.setTelPhone("10086"+index);
                student.setStudentNo(index);
                insertData(student);
                index++;
                break;
            case R.id.btn_delete:
                Toast.makeText(this,"删",Toast.LENGTH_SHORT).show();
                deleteAll();
                break;
            case R.id.btn_update:
                Toast.makeText(this,"改",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query:
                tvContent.setText("");
                Toast.makeText(this,"查",Toast.LENGTH_SHORT).show();
                List<Student> students = queryAll();
                for (Student s:students){
                    sb.append(s.toString()+"   \n");
                }
                tvContent.setText(sb.toString());
                break;
        }
    }

    //数据存在则替换，数据不存在则插入
    private void insertOrReplace(Student student) {
    }

    //插入数据
    private void insertData(Student student) {
//        DaoSession daoSession = ((MyApplication) getApplication()).getDaoSession();
        daoSession.insert(student);
    }

    private void deleteAll() {
//        DaoSession daoSession = ((MyApplication) getApplication()).getDaoSession();
        daoSession.deleteAll(Student.class);
    }

    private void deleteData(Student student) {
//        DaoSession daoSession = ((MyApplication) getApplication()).getDaoSession();
        daoSession.delete(student);

    }

    public void updataData(Student s) {
//        DaoSession daoSession = ((MyApplication) getApplication()).getDaoSession();
        daoSession.update(s);
    }

    //loadAll()：查询所有数据。
    public List queryAll(){
//        DaoSession daoSession = ((MyApplication) getApplication()).getDaoSession();
        List<Student> students = daoSession.loadAll(Student.class);
        return students;
    }

    //queryRaw()：根据条件查询。
    public void queryData(String id) {
//        DaoSession daoSession = ((MyApplication) getApplication()).getDaoSession();
        List<Student> students = daoSession.queryRaw(Student.class, " where id = ?", id);
    }

}
