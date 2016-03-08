package com.praxisgs.emergencysms.adapters;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.praxisgs.emergencysms.R;

import java.util.HashMap;

/**
 * Created on 08/03/2016.
 */
public class ContactCursorAdapter extends CursorAdapter {
    private final LayoutInflater mCursorInflater;
    public static final String MOBILE_NUMBER_KEY = "mobile_number_key";
    public static final String DISPLAY_NAME_KEY = "display_name_key";

    public ContactCursorAdapter(Context context, Cursor cursor, int flag) {
        super(context, cursor,flag);
        mCursorInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Makes a new view to hold the data pointed to by cursor.
     *
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mCursorInflater.inflate(R.layout.contact_list_view_item, parent,false);
    }

    /**
     * Bind an existing view to the data pointed to by cursor
     *
     * @param view    Existing view, returned earlier by newView
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView displayNameTxtView = (TextView) view.findViewById(R.id.firstName_textView);
        TextView mobileNumberTxtView = (TextView) view.findViewById(R.id.mobile_textView);


        HashMap<String, String> contactDetails = getContactDetails(cursor);
        displayNameTxtView.setText(contactDetails.get(DISPLAY_NAME_KEY));
        mobileNumberTxtView.setText(contactDetails.get(MOBILE_NUMBER_KEY));


    }

    public HashMap<String,String> getContactDetails(Cursor cursor){
        HashMap<String,String> contactDetails = new HashMap<>();


        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        String displayName = cursor.getString(cursor.getColumnIndex(Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                ContactsContract.Contacts.DISPLAY_NAME));
        String mobileNumber = "No Mobile number available";
        contactDetails.put(DISPLAY_NAME_KEY,displayName);

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

        contactDetails.put(MOBILE_NUMBER_KEY,mobileNumber);

        return contactDetails;
    }


}
