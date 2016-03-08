package com.praxisgs.emergencysms.adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.model.ContactModel;

import java.util.HashMap;

/**
 * Created on 02/03/2016.
 */
public class ContactsAdapter extends BaseAdapter {
    public static final String MOBILE_NUMBER_KEY = "mobile_number_key";
    public static final String DISPLAY_NAME_KEY = "display_name_key";

//        implements Filterable {

    private final Context mContext;
    private final Cursor mCursor;

    public ContactsAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;

    }


    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mCursor.getCount();
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
        return null;
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
        mCursor.moveToPosition(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.contact_list_view_item, null);
            viewHolder.displayName = (TextView) convertView.findViewById(R.id.firstName_textView);
            viewHolder.mobileNumber = (TextView) convertView.findViewById(R.id.mobile_textView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        HashMap<String, String> contactDetails = getContactDetails(mCursor);
//        viewHolder.displayName.setText(contactDetails.get(DISPLAY_NAME_KEY));
//        viewHolder.mobileNumber.setText(contactDetails.get(MOBILE_NUMBER_KEY));

        String contactId = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts._ID));
        String displayName = mCursor.getString(mCursor.getColumnIndex(Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                ContactsContract.Contacts.DISPLAY_NAME));
        String mobileNumber = "No Mobile number available";
        viewHolder.displayName.setText(displayName);

        Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
        if (phones != null) {
            while (phones.moveToNext()) {
                int phoneType = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                    mobileNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                    mobileNumber = mobileNumber.replaceAll("\\s", "");
                    phones.close();
                    break;
                } else {
                    phones.close();
                    break;
                }
            }
            phones.close();
        }

        viewHolder.mobileNumber.setText(mobileNumber);

        return convertView;
    }

//    public HashMap<String, String> getContactDetails(Cursor cursor) {
//        HashMap<String,String> contactDetails = new HashMap<>();
//        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//        String displayName = cursor.getString(cursor.getColumnIndex(Build.VERSION.SDK_INT
//                >= Build.VERSION_CODES.HONEYCOMB ?
//                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
//                ContactsContract.Contacts.DISPLAY_NAME));
//        String mobileNumber = "No Mobile number available";
//        contactDetails.put(DISPLAY_NAME_KEY,displayName);
//
//        Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
//        if (phones != null) {
//            while (phones.moveToNext()) {
//                int phoneType = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
//                if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
//                    mobileNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
//                    mobileNumber = mobileNumber.replaceAll("\\s", "");
//                    phones.close();
//                    break;
//                } else {
//                    phones.close();
//                    break;
//                }
//            }
//            phones.close();
//        }
//        contactDetails.put(MOBILE_NUMBER_KEY,mobileNumber);
//
//        return contactDetails;
//    }

    class ViewHolder {
        TextView displayName;
        TextView mobileNumber;
    }


}
