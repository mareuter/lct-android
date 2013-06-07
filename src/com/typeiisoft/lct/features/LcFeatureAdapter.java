package com.typeiisoft.lct.features;

import java.util.ArrayList;

import com.typeiisoft.lct.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class LcFeatureAdapter extends BaseExpandableListAdapter {
	/** Holder for current activity context. */
	private final Context context;
	/** Holder for group headers. */
	private final ArrayList<String> headers;
	/** Holder for features list. */
	private final ArrayList<ArrayList<LunarFeature>> features;
	/** Logging identifier. */
	private static final String TAG = "LcFeatureAdapter";
	
	/**
	 * This function is the class constructor.
	 * @param context : The current activity context.
	 * @param hvals : The list of group headers.
	 * @param values : The list of lists of features.
	 */
	public LcFeatureAdapter(Context context, ArrayList<String> hvals,
			ArrayList<ArrayList<LunarFeature>> values) {
		//super(context, R.layout.lcfeatureitem, values);
		this.context = context;
		this.headers = hvals;
		this.features = values;
	}
	
    // Return a child view. You can load your custom layout here.
    @Override
    public View getChildView(int groupPosition, int childPosition, 
    		boolean isLastChild, View convertView, ViewGroup parent) {
        LunarFeature feature = (LunarFeature) this.getChild(groupPosition, 
        		childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.lcfeatures_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.tv_item);
        tv.setText(feature.getName());
        return convertView;
    }

    // Return a group view. You can load your custom layout here.
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, 
    		View convertView, ViewGroup parent) {
        String header = (String) this.getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.lcfeatures_group, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.tv_group);
        tv.setText(header);
        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.features.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.features.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.headers.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.headers.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }
}
