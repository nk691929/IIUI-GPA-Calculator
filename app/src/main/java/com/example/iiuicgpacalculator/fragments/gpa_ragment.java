package com.example.iiuicgpacalculator.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iiuicgpacalculator.ModelClass.Subject;
import com.example.iiuicgpacalculator.R;
import com.example.iiuicgpacalculator.RecView.SubjectsRecView;
import com.example.iiuicgpacalculator.databinding.FragmentGpaRagmentBinding;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;

public class gpa_ragment extends Fragment {
    int check=3;
    int num_clicks=0;
    private InterstitialAd mInterstitialAd;

    private FragmentGpaRagmentBinding binding;
    private SubjectsRecView adapter;
    private ArrayList<Subject> subjects;
    private AdView adView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentGpaRagmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            showSubjectDialog();
            bannerAd();
            intAd();
            subjects = new ArrayList<>();
            binding.subjectRecView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new SubjectsRecView(getContext(), subjects);
            binding.subjectRecView.setAdapter(adapter);
            showResult();
            clearAll();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Show Dialog for Subjects
    private void showSubjectDialog(){
       binding.addSub.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               num_clicks++;
               if (mInterstitialAd != null&&num_clicks%check==0) {
                   mInterstitialAd.show(getActivity());

                   mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                       @Override
                       public void onAdClicked() {
                           // Called when a click is recorded for an ad.
                       }

                       @Override
                       public void onAdDismissedFullScreenContent() {
                           Dialog dialog=new Dialog(getContext());
                           dialog.setContentView(R.layout.subjects_dialog);
                           Button addBtn=dialog.findViewById(R.id.add_sub);
                           TextView subNameTv=dialog.findViewById(R.id.subject_name);


                           addBtn.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                   binding.gpaSuggestion.setVisibility(View.GONE);
                                   //new added
                                   int num= Integer.parseInt(subNameTv.getText().toString());
                                   //new added
                                   for (int i=0;i<num;i++){
                                       Subject subject=new Subject();
                                       subject.setName("Subject #"+(subjects.size()+1));
                                       subject.setGradeIndex("0");
                                       subject.setHrs("1");
                                       subject.setGrade("A");
                                       subject.setHrsIndex("0");
                                       subjects.add(subject);
                                       //new added
                                   }
                                   adapter.notifyDataSetChanged();
                                   dialog.dismiss();
                               }
                           });
                           dialog.show();
                           intAd();
                       }

                       @Override
                       public void onAdFailedToShowFullScreenContent(AdError adError) {
                           // Called when ad fails to show.
                           mInterstitialAd = null;
                       }

                       @Override
                       public void onAdImpression() {
                           // Called when an impression is recorded for an ad.
                       }

                       @Override
                       public void onAdShowedFullScreenContent() {
                           // Called when ad is shown.
                       }
                   });
               } else {
                   Dialog dialog=new Dialog(getContext());
                   dialog.setContentView(R.layout.subjects_dialog);
                   Button addBtn=dialog.findViewById(R.id.add_sub);
                   TextView subNameTv=dialog.findViewById(R.id.subject_name);


                   addBtn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           binding.gpaSuggestion.setVisibility(View.GONE);
                           //new added
                           int num= Integer.parseInt(subNameTv.getText().toString());
                           //new added
                           for (int i=0;i<num;i++){
                               Subject subject=new Subject();
                               subject.setName("Subject #"+(subjects.size()+1));
                               subject.setGradeIndex("0");
                               subject.setHrs("1");
                               subject.setGrade("A");
                               subject.setHrsIndex("0");
                               subjects.add(subject);
                               //new added
                           }
                           adapter.notifyDataSetChanged();
                           dialog.dismiss();
                       }
                   });
                   dialog.show();
               }
           }
       });
    }


    //Show Result
    private void showResult(){
        binding.calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num_clicks++;
                if (mInterstitialAd != null&&num_clicks%check==0) {
                    mInterstitialAd.show(getActivity());

                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                        @Override
                        public void onAdClicked() {
                            // Called when a click is recorded for an ad.
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            float res= (float) adapter.calculateGrade();
                            if(res!=0) {
                                binding.calculate.setText("GPA = " + res);
                            }
                            intAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // Called when ad fails to show.
                            mInterstitialAd = null;
                        }

                        @Override
                        public void onAdImpression() {
                            // Called when an impression is recorded for an ad.
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            // Called when ad is shown.
                        }
                    });
                } else {
                    float res= (float) adapter.calculateGrade();
                    if(res!=0) {
                        binding.calculate.setText("GPA = " + res);
                    }
                }
            }
        });

    }


    //clearing all
    private void clearAll(){
        binding.clrAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subjects.size()!=0) {
                    num_clicks++;
                    if (mInterstitialAd != null&&num_clicks%check==0) {
                        mInterstitialAd.show(getActivity());

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                AlertDialog dialog = new AlertDialog.Builder(getContext())
                                        .setTitle("Clear All")
                                        .setMessage("Do you want to Clear All?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                subjects.removeAll(subjects);
                                                adapter.notifyDataSetChanged();
                                                binding.gpaSuggestion.setVisibility(View.VISIBLE);
                                            }
                                        }).setNegativeButton("No", null).show();
                                intAd();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                            }
                        });
                    } else {
                        AlertDialog dialog = new AlertDialog.Builder(getContext())
                                .setTitle("Clear All")
                                .setMessage("Do you want to Clear All?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        subjects.removeAll(subjects);
                                        adapter.notifyDataSetChanged();
                                        binding.gpaSuggestion.setVisibility(View.VISIBLE);
                                    }
                                }).setNegativeButton("No", null).show();
                    }
                }else {
                    Toast.makeText(getContext(), "No Records ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //create banner ad
    private void bannerAd(){
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = new AdView(getContext());

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-3791280932715299/9795370367");
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
    }


    //create int ad
    private void intAd(){
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(getContext(),"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }
}