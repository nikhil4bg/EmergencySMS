package com.praxisgs.emergencysms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.model.ContactModel;
import com.praxisgs.emergencysms.model.EmergencySMSModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 02/03/2016.
 */
public class ContactsAdapter extends BaseAdapter implements Filterable {

    private final Context mContext;
    private final List<ContactModel> mListItems;
    private List<ContactModel> mSuggestions = new ArrayList<>();
    private List<ContactModel> mResultList = new ArrayList<>();


    /**
     * Constructor
     *
     * @param context The current context.
     * @param items   The objects to represent in the ListView.
     */
    public ContactsAdapter(Context context, List<ContactModel> items) {
        this.mContext = context;
        this.mListItems = items;
    }


    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mResultList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public ContactModel getItem(int position) {
        return mResultList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.contact_list_view_item, null);
            viewHolder.displayName = (TextView) convertView.findViewById(R.id.firstName_textView);
            viewHolder.mobileNumber = (TextView) convertView.findViewById(R.id.mobile_textView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ContactModel listItem = getItem(position);
        viewHolder.displayName.setText(listItem.getDisplayName());
        viewHolder.mobileNumber.setText(listItem.getMobileNumber());

        return convertView;
    }

    /**
     * <p>Returns a filter that can be used to constrain data with a filtering
     * pattern.</p>
     * <p/>
     * <p>This method is usually implemented by {@link Adapter}
     * classes.</p>
     *
     * @return a filter used to constrain data
     */
    @Override
    public Filter getFilter() {
        return new ContactFilter();
    }

    class ViewHolder {
        TextView displayName;
        TextView mobileNumber;
    }

    class ContactFilter extends Filter {



        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((ContactModel) resultValue).getDisplayName();
            EmergencySMSModel.getInstance().setSelectedContact(((ContactModel) resultValue));
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            if (constraint != null) {
                mSuggestions.clear();
                for (ContactModel people : mListItems) {
                    if (people.getDisplayName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        mSuggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mSuggestions;
                filterResults.count = mSuggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<ContactModel> filterList = (ArrayList<ContactModel>) results.values;
            if (results != null && results.count > 0) {
                mResultList.clear();
                for (ContactModel people : filterList) {
                    mResultList.add(people);
                    notifyDataSetChanged();
                }
            }else if(results != null && results.count == 0){
                EmergencySMSModel.getInstance().setSelectedContact(null);
            }
        }
    }
}
