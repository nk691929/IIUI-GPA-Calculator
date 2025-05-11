package com.example.iiuicgpacalculator.RecView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iiuicgpacalculator.ModelClass.Subject;
import com.example.iiuicgpacalculator.R;

import java.util.ArrayList;

public class SubjectsRecView extends RecyclerView.Adapter<SubjectsRecView.ViewHolder> {
    private ArrayList<Subject> subjects;
    private Context context;

    public SubjectsRecView(Context context,ArrayList<Subject> subjects){
        this.context=context;
        this.subjects=subjects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.subject_gpa_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            int pos = position;
            holder.nameTv.setText(subjects.get(position).getName());

            holder.gradesSpin.setSelection(Integer.parseInt(subjects.get(pos).getGradeIndex()));
            holder.gradesSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    subjects.get(pos).setGradeIndex(i + "");
                    subjects.get(pos).setGrade(adapterView.getItemAtPosition(i).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            holder.hrsSpin.setSelection(Integer.parseInt(subjects.get(pos).getHrsIndex()));
            holder.hrsSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    subjects.get(pos).setHrsIndex(i + "");
                    subjects.get(pos).setHrs(adapterView.getItemAtPosition(i).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            holder.deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subjects.remove(pos);
                    notifyDataSetChanged();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         public TextView nameTv;
         public Spinner hrsSpin,gradesSpin;
         private ImageView deleteImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv=itemView.findViewById(R.id.subName);
            hrsSpin=itemView.findViewById(R.id.credit_spinner);
            gradesSpin=itemView.findViewById(R.id.grade_spinner);
            deleteImg=itemView.findViewById(R.id.delete_subject);
        }
    }


    //Calculate Grades
    public float calculateGrade(){
        int totalHrs=0;
        double sum=0;
        float avg=0;

        if(subjects.size()!=0) {
            for (Subject sub : subjects) {
                if (sub.getGrade().equals("A")) {
                    sub.setResMultiply(4 * Integer.parseInt(sub.getHrs()));
                } else if (sub.getGrade().equals("B+")) {
                    sub.setResMultiply(3.5 * Integer.parseInt(sub.getHrs()));
                } else if (sub.getGrade().equals("B")) {
                    sub.setResMultiply(3 * Integer.parseInt(sub.getHrs()));
                } else if (sub.getGrade().equals("C+")) {
                    sub.setResMultiply(2.5 * Integer.parseInt(sub.getHrs()));
                } else if (sub.getGrade().equals("C")) {
                    sub.setResMultiply(2 * Integer.parseInt(sub.getHrs()));
                } else if (sub.getGrade().equals("D+")) {
                    sub.setResMultiply(1.5 * Integer.parseInt(sub.getHrs()));
                } else if (sub.getGrade().equals("F")) {
                    sub.setResMultiply(1 * Integer.parseInt(sub.getHrs()));
                }
                totalHrs=totalHrs+Integer.parseInt(sub.getHrs());
                sum = sum+sub.getResMultiply();
            }
            avg= (float) (sum/totalHrs);
        }else {
            Toast.makeText(context, "Please Add Subjects", Toast.LENGTH_SHORT).show();
        }
        return avg;
    }

}
