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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iiuicgpacalculator.ModelClass.Semester;
import com.example.iiuicgpacalculator.ModelClass.Subject;
import com.example.iiuicgpacalculator.R;
import com.example.iiuicgpacalculator.RecView.SemesterRecViewAdapter;
import com.example.iiuicgpacalculator.RecView.SubjectsRecView;
import com.example.iiuicgpacalculator.databinding.FragmentCgpaRagmentBinding;
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

public class cgpa_ragment extends Fragment {
    int check=3;
    int num_clicks=0;
    private InterstitialAd mInterstitialAd;
    private SemesterRecViewAdapter adapter;
    private ArrayList<Semester> semesters;
    private FragmentCgpaRagmentBinding binding;
    private AdView adView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentCgpaRagmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            addSemester();
            bannerAd();
            intAd();
            semesters = new ArrayList<>();
            binding.semesterRecView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new SemesterRecViewAdapter(getContext(), semesters);
            binding.semesterRecView.setAdapter(adapter);
            showResult();
            clearAll();
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    //Show Dialog for Subjects
    private void addSemester(){
        binding.addSem.setOnClickListener(new View.OnClickListener() {
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
                            binding.cgpaSuggestion.setVisibility(View.GONE);
                            Semester semester=new Semester();
                            semester.setHint("0.00");
                            semester.setCGPA("");
                            semesters.add(semester);
                            adapter.notifyItemInserted(semesters.size());
//                            AlertDialog dialog=new AlertDialog.Builder(getContext())
//                                    .setTitle("Semester")
//                                    .setMessage("Do you want to add GPA")
//                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            binding.cgpaSuggestion.setVisibility(View.GONE);
//                                            Semester semester=new Semester();
//                                            semester.setHint("0.00");
//                                            semester.setCGPA("");
//                                            semesters.add(semester);
//                                            adapter.notifyItemInserted(semesters.size());
//                                        }
//                                    }).setNegativeButton("No",null).show();
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
                    binding.cgpaSuggestion.setVisibility(View.GONE);
                    Semester semester=new Semester();
                    semester.setHint("0.00");
                    semester.setCGPA("");
                    semesters.add(semester);
                    adapter.notifyItemInserted(semesters.size());
                }
            }
        });
    }


    //Show Result
    private void showResult(){
        binding.calculateCGPA.setOnClickListener(new View.OnClickListener() {
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
                            float res=adapter.calculateGrade();
                            if(res!=0){
                                binding.calculateCGPA.setText("CGPA = "+res);
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
                    float res=adapter.calculateGrade();
                    if(res!=0){
                        binding.calculateCGPA.setText("CGPA = "+res);
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
                if(semesters.size()!=0) {
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
                                                semesters.removeAll(semesters);
                                                adapter.notifyDataSetChanged();
                                                binding.cgpaSuggestion.setVisibility(View.VISIBLE);
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
                        new AlertDialog.Builder(getContext())
                                .setTitle("Clear All")
                                .setMessage("Do you want to Clear All?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        semesters.removeAll(semesters);
                                        adapter.notifyDataSetChanged();
                                        binding.cgpaSuggestion.setVisibility(View.VISIBLE);
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

        adView.setAdUnitId("ca-app-pub-3791280932715299/5472981973");
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adViewCGPA.loadAd(adRequest);
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