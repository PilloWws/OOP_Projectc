package com.example.oop_project;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.Locale;

public class FilterCategory extends Filter {
    ArrayList<Category> filterList;
    AdapterCategory adapterCategory;

    public FilterCategory(ArrayList<Category> filterList, AdapterCategory adapterCategory) {
        this.filterList = filterList;
        this.adapterCategory = adapterCategory;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length() > 0){
            // change to uppercase, or lower case
            constraint = constraint.toString().toUpperCase();
            ArrayList<Category> filterCategories = new ArrayList<>();
            for(int i = 0; i < filterList.size(); i++){
                if(filterList.get(i).getTitle().toUpperCase().contains(constraint)){
                    filterCategories.add(filterList.get(i));
                }
            }
            results.count = filterCategories.size();
            results.values = filterCategories;
        }else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapterCategory.categoryArrayList = (ArrayList<Category>)results.values;

        adapterCategory.notifyDataSetChanged();
    }
}
