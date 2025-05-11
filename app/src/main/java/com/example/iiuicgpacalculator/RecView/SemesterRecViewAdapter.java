package com.example.iiuicgpacalculator.RecView;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iiuicgpacalculator.ModelClass.Semester;
import com.example.iiuicgpacalculator.R;

import java.util.ArrayList;

public class SemesterRecViewAdapter extends RecyclerView.Adapter<SemesterRecViewAdapter.ViewHolder> {
    private ArrayList<Semester> semesters;
    private Context context;

    public SemesterRecViewAdapter(Context context,ArrayList<Semester> semesters){
        this.context=context;
        this.semesters=semesters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cgpa_adapter_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos=position;
        holder.semesterNo.setText("Semester #"+(pos+1));
        holder.gpaSemester.setText(semesters.get(pos).getCGPA());
        holder.gpaSemester.setHint(semesters.get(pos).getHint());
        holder.gpaSemester.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!holder.gpaSemester.getText().toString().equals("")) {
                    semesters.get(pos).setCGPA(holder.gpaSemester.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                semesters.remove(semesters.get(pos));
                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        return semesters.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView semesterNo;
        private ImageView deleteImg;
        private EditText gpaSemester;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gpaSemester=itemView.findViewById(R.id.gpa_sem_edt);
            semesterNo=itemView.findViewById(R.id.semName);
            deleteImg=itemView.findViewById(R.id.delete_semester);

        }
    }


    //Calculate Grades
    public float calculateGrade() {
        int count=0;
        float sum=0;
        float res=0;
        if(semesters.size()!=0) {
            for (Semester sem : semesters) {
                if(sem.getCGPA().isEmpty()){

                }else {
                    count++;
                    sum = sum + Float.parseFloat(sem.getCGPA());
                }
            }
            res=sum/count;
        }else {
            Toast.makeText(context, "Please Add Semesters", Toast.LENGTH_SHORT).show();
        }
        return res;
    }

}
