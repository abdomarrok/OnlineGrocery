package com.marrok.onlinegrocery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.marrok.onlinegrocery.Models.GroceryItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class FragmentMain extends Fragment {
    private static final String TAG = "FragmentMain";
    private RecyclerView newItemsRecView, popularItemsRecView, suggestedItemsRecView;
    private BottomNavigationView bottom_nav_view;
    private GroceryItemAdapter newItemsAdapter, popularItemsAdapter, suggestedItemsAdapter;
    Utils utils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_main,container,false);
        initWidget(view);
        initBottomNavView();
         utils=new Utils(getActivity());
         utils.initDatabase();
         initRecViews();
        return view;

    }

    private void initRecViews() {
        Log.d(TAG, "initRecViews: started");
        newItemsAdapter = new GroceryItemAdapter(getActivity());
        popularItemsAdapter = new GroceryItemAdapter(getActivity());
        suggestedItemsAdapter = new GroceryItemAdapter(getActivity());

        newItemsRecView.setAdapter(newItemsAdapter);
        popularItemsRecView.setAdapter(popularItemsAdapter);
        suggestedItemsRecView.setAdapter(suggestedItemsAdapter);

        newItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        suggestedItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        updateRecViews();
    }


    @Override
    public void onResume() {
        updateRecViews();
        super.onResume();
    }

    private void updateRecViews() {
        ArrayList<GroceryItem> newItems= utils.getAllItems();

        Comparator<GroceryItem> newItemsComparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem o1, GroceryItem o2) {
                return o1.getId() - o2.getId();
            }
        };
        Comparator<GroceryItem> reversedNewItemsComparator = Collections.reverseOrder(newItemsComparator);
        Collections.sort(newItems, reversedNewItemsComparator);

        if (null != newItems) {
            newItemsAdapter.setItems(newItems);
        }

        ArrayList<GroceryItem> popularItems = utils.getAllItems();

        Comparator<GroceryItem> popularityComparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem o1, GroceryItem o2) {
                return compareByPopularity(o1, o2);
            }
        };

        Comparator<GroceryItem> reversePopularityComparator = Collections.reverseOrder(popularityComparator);
        Collections.sort(popularItems, reversePopularityComparator);

        popularItemsAdapter.setItems(popularItems);

        ArrayList<GroceryItem> suggestedItems = utils.getAllItems();
        Comparator<GroceryItem> suggestedItemsComparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem o1, GroceryItem o2) {
                return o1.getUserPoint() - o2.getUserPoint();
            }
        };

        Comparator<GroceryItem> reveredSuggestedItemsComparator = Collections.reverseOrder(suggestedItemsComparator);
        Collections.sort(suggestedItems, reveredSuggestedItemsComparator);

        suggestedItemsAdapter.setItems(suggestedItems);
    }

    private int compareByPopularity (GroceryItem item1, GroceryItem item2) {
        Log.d(TAG, "compareByPopularity: started");
        if (item1.getPopularityPoint()>item2.getPopularityPoint()) {
            return 1;
        }else if (item1.getPopularityPoint()<item2.getPopularityPoint()) {
            return -1;
        }else {
            return 0;
        }
    }



    private void initWidget(View view) {
        bottom_nav_view =(BottomNavigationView) view.findViewById(R.id.bottomNavigationView);
        newItemsRecView = (RecyclerView) view.findViewById(R.id.newItemsRecView);
        popularItemsRecView = (RecyclerView) view.findViewById(R.id.popularItems);
        suggestedItemsRecView = (RecyclerView) view.findViewById(R.id.suggestedItems);
    }

    private void initBottomNavView() {
        bottom_nav_view.setSelectedItemId(R.id.home1);
        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.cart:
                        Intent c_intent = new Intent(getActivity(), CartActivity.class);
                        c_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(c_intent);
                        break;
                    case R.id.home1:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }


}
