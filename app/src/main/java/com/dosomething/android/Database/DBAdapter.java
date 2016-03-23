package com.dosomething.android.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dosomething.android.Beanclasses.DosomethingHobbies;
import com.dosomething.android.Beanclasses.ImageBan;

import java.util.ArrayList;

/**
 * Created by hi on 10/31/2015.
 */
public class DBAdapter {
    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "DoSomethingDatabase";
    private static final String DATABASE_TABLE = "DoSomethingStatus";
    private static final String DATABASE_TABLE_HOBBIES = "DoSomethingHobbies";
    private static final String DATABASE_TABLE_NEARUSERS = "DoSomethingNearusers";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_IMAGEID = "statusimage_id";
    private static final String KEY_HOBBIESIMAGEID = "hobbies_id";
    private static final String KEY_HOBBIESCATEGORYID = "category_id";
    private static final String KEY_IMAGENAME = "statusimage_name";
    private static final String KEY_HOBBIESIMAGENAME = "name";
    private static final String KEY_IMAGEACTIVEURL = "statusimage_activeimage";
    private static final String KEY_HOBBIESIMAGEINACTIVEURL = "image";
    private static final String KEY_IMAGEINACTIVEURL = "statusimage_inactiveimage";


    private static final String KEY_HOBBIESIMAGEACTIVEURL = "image_active";
    private static final String DATABASE_CREATE_STATUS = "CREATE TABLE DoSomethingStatus(statusimage_id INTEGER,statusimage_name TEXT,statusimage_activeimage TEXT,statusimage_inactiveimage TEXT)";
    private static final String DATABASE_CREATE_HOBBIES = "CREATE TABLE DoSomethingHobbies(hobbies_id INTEGER UNIQUE,category_id INTEGER,name TEXT UNIQUE,image TEXT UNIQUE,image_active TEXT UNIQUE)";
    private static final String DATABASE_CREATE_NEARUSERS = "CREATE TABLE DoSomethingNearusers(user_id INTEGER UNIQUE,first_name TEXT,last_name TEXT,about TEXT,gender TEXT,date_of_birth VARCHAR,hobbies VARCHAR,age INTEGER,available_now VARCHAR,online_status VARCHAR,image2 VARCHAR,image1 VARCHAR,image3 VARCHAR,dosomepoints INTEGER,onlinepoints INTEGER,)";
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        DBHelper = new DatabaseHelper(ctx);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE_STATUS);
                db.execSQL(DATABASE_CREATE_HOBBIES);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS DoSomethingStatus");
            onCreate(db);
        }

    }

    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    public int getStatusCount() {
        String countQuery = "SELECT  * FROM " + DATABASE_TABLE;
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DoSomethingStatus", null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public int getHobbiesCount() {
        String countQuery = "SELECT  * FROM " + DATABASE_TABLE_HOBBIES;
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DoSomethingHobbies", null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public void delete() {
        db.execSQL("delete from "+ DATABASE_TABLE);;

    }


    public void addStatus(ImageBan bean) {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_IMAGEID, bean.getImage_id()); // Contact Name
        values.put(KEY_IMAGENAME, bean.getImage_name());
        values.put(KEY_IMAGEACTIVEURL, bean.getImag_Active());
        values.put(KEY_IMAGEINACTIVEURL, bean.getImag_inActive());
//        String sqlQuery = "SELECT * FROM " + DATABASE_TABLE;
//        Log.d("sqlQuery", "__________________" + sqlQuery);
        db.insert(DATABASE_TABLE, null, values);
//        Cursor c = db.rawQuery(sqlQuery, null);
//        if (c != null && c.getCount() != 0)
//        {
//
//        }
//        else
//        {
//            db.insert(DATABASE_TABLE, null, values);
//        }
        db.close(); // Closing database connection
    }


    public void addHobbies(DosomethingHobbies dosomethingHobbies) {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_HOBBIESIMAGEID, dosomethingHobbies.getImage_id()); // Contact Name
        values.put(KEY_HOBBIESCATEGORYID, dosomethingHobbies.getCategory_id());
        values.put(KEY_HOBBIESIMAGENAME, dosomethingHobbies.getImage_name());
        values.put(KEY_HOBBIESIMAGEACTIVEURL, dosomethingHobbies.getImag_Active());
        values.put(KEY_HOBBIESIMAGEINACTIVEURL, dosomethingHobbies.getImag_inActive());
//        String sqlQuery = "SELECT * FROM " + DATABASE_TABLE;
//        Log.d("sqlQuery", "__________________" + sqlQuery);
        db.insert(DATABASE_TABLE_HOBBIES, null, values);
//        Cursor c = db.rawQuery(sqlQuery, null);
//        if (c != null && c.getCount() != 0)
//        {
//
//        }
//        else
//        {
//            db.insert(DATABASE_TABLE, null, values);
//        }
        db.close(); // Closing database connection
    }

    public ArrayList<ImageBan> getStatusImages() {
        ArrayList<ImageBan> status_list = new ArrayList<ImageBan>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = DBHelper.getWritableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM DoSomethingStatus", null);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            cursor.moveToFirst();
            try {
                ImageBan statusimagelist = new ImageBan();
                statusimagelist.setImage_id(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGEID))));
                statusimagelist.setImage_name(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGENAME)));
                statusimagelist.setImag_Active(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGEACTIVEURL)));
                statusimagelist.setImag_inActive(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGEINACTIVEURL)));

                // Adding contact to list
                status_list.add(statusimagelist);
            } catch (Exception e) {
                e.printStackTrace();
            }

            while (cursor.moveToNext()) {
                try {
                    ImageBan statusimagelist = new ImageBan();
                    statusimagelist.setImage_id(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGEID))));
                    statusimagelist.setImage_name(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGENAME)));
                    statusimagelist.setImag_Active(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGEACTIVEURL)));
                    statusimagelist.setImag_inActive(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGEINACTIVEURL)));

                    status_list.add(statusimagelist);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.d(DATABASE_TABLE, "null");
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        // return contact list
        return status_list;
    }

    public ArrayList<ImageBan> getStatusActiveImages() {
        ArrayList<ImageBan> status_list = new ArrayList<ImageBan>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = DBHelper.getWritableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM DoSomethingStatus", null);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            cursor.moveToFirst();
            try {
                ImageBan statusimagelist = new ImageBan();
                statusimagelist.setImage_id(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGEID))));
                statusimagelist.setImage_name(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGENAME)));
                statusimagelist.setImag_Active(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGEACTIVEURL)));
                statusimagelist.setImag_inActive(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGEINACTIVEURL)));

                // Adding contact to list
                status_list.add(statusimagelist);
            } catch (Exception e) {
                e.printStackTrace();
            }

            while (cursor.moveToNext()) {
                try {
                    ImageBan statusimagelist = new ImageBan();
                    statusimagelist.setImage_id(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGEID))));
                    statusimagelist.setImage_name(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGENAME)));
                    statusimagelist.setImag_Active(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGEACTIVEURL)));
                    statusimagelist.setImag_inActive(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGEINACTIVEURL)));

                    status_list.add(statusimagelist);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.d(DATABASE_TABLE, "null");
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        // return contact list
        return status_list;
    }


    public ArrayList<DosomethingHobbies> getHObbiesImages(int hobbies_id) {
        ArrayList<DosomethingHobbies> dosomethingHobbies = new ArrayList<DosomethingHobbies>();
        // Select All Query
        Cursor cursor1 = db.query(true, DATABASE_TABLE_HOBBIES, new String[]{KEY_HOBBIESIMAGEINACTIVEURL,KEY_HOBBIESIMAGENAME}, KEY_HOBBIESIMAGEID + "=" + hobbies_id, null, null, null, null, null, null);

        SQLiteDatabase db = DBHelper.getWritableDatabase();


        Cursor cursor = db.rawQuery(" select image,name from DoSomethingHobbies where " + KEY_HOBBIESIMAGEID + "='" + hobbies_id + "'", null);

        if (cursor1 != null && cursor1.getCount() > 0 && cursor1.moveToFirst()) {
            cursor1.moveToFirst();
            try {
                DosomethingHobbies statusimagelist = new DosomethingHobbies();
                statusimagelist.setImage_id(Integer.parseInt(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGEID))));
                statusimagelist.setCategory_id(Integer.parseInt(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESCATEGORYID))));
                statusimagelist.setImage_name(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGENAME)));
                statusimagelist.setImag_Active(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGEACTIVEURL)));
                statusimagelist.setImag_inActive(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGEINACTIVEURL)));

                // Adding contact to list
                dosomethingHobbies.add(statusimagelist);
            } catch (Exception e) {
                e.printStackTrace();
            }

            while (cursor1.moveToNext()) {
                try {
                    DosomethingHobbies statusimagelist = new DosomethingHobbies();
                    statusimagelist.setImage_id(Integer.parseInt(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGEID))));
                    statusimagelist.setCategory_id(Integer.parseInt(cursor.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESCATEGORYID))));
                    statusimagelist.setImage_name(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGENAME)));
                    statusimagelist.setImag_Active(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGEACTIVEURL)));
                    statusimagelist.setImag_inActive(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGEINACTIVEURL)));

                    dosomethingHobbies.add(statusimagelist);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.d(DATABASE_TABLE_HOBBIES, "null");
        }
        if (cursor1 != null && !cursor1.isClosed()) {
            cursor1.close();
        }

        // return contact list
        return dosomethingHobbies;
    }


    public ArrayList<DosomethingHobbies> getHobbiesActiveImages(int hobbies_id) {
        ArrayList<DosomethingHobbies> dosomethingHobbies = new ArrayList<DosomethingHobbies>();
        // Select All Query
        Cursor cursor1 = db.query(true, DATABASE_TABLE_HOBBIES, new String[]{KEY_HOBBIESIMAGEACTIVEURL,KEY_HOBBIESIMAGENAME}, KEY_HOBBIESIMAGEID + "=" + hobbies_id, null, null, null, null, null, null);

        SQLiteDatabase db = DBHelper.getWritableDatabase();


        Cursor cursor = db.rawQuery(" select image_active,name from DoSomethingHobbies where " + KEY_HOBBIESIMAGEID + "='" + hobbies_id + "'", null);

        if (cursor1 != null && cursor1.getCount() > 0 && cursor1.moveToFirst()) {
            cursor1.moveToFirst();
            try {
                DosomethingHobbies statusimagelist = new DosomethingHobbies();
                statusimagelist.setImage_id(Integer.parseInt(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGEID))));
                statusimagelist.setCategory_id(Integer.parseInt(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESCATEGORYID))));
                statusimagelist.setImage_name(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGENAME)));
                statusimagelist.setImag_Active(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGEACTIVEURL)));
                statusimagelist.setImag_inActive(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGEINACTIVEURL)));

                // Adding contact to list
                dosomethingHobbies.add(statusimagelist);
            } catch (Exception e) {
                e.printStackTrace();
            }

            while (cursor1.moveToNext()) {
                try {
                    DosomethingHobbies statusimagelist = new DosomethingHobbies();
                    statusimagelist.setImage_id(Integer.parseInt(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGEID))));
                    statusimagelist.setCategory_id(Integer.parseInt(cursor.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESCATEGORYID))));
                    statusimagelist.setImage_name(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGENAME)));
                    statusimagelist.setImag_Active(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGEACTIVEURL)));
                    statusimagelist.setImag_inActive(cursor1.getString(cursor1.getColumnIndexOrThrow(KEY_HOBBIESIMAGEINACTIVEURL)));

                    dosomethingHobbies.add(statusimagelist);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.d(DATABASE_TABLE_HOBBIES, "null");
        }
        if (cursor1 != null && !cursor1.isClosed()) {
            cursor1.close();
        }

        // return contact list
        return dosomethingHobbies;
    }

}
