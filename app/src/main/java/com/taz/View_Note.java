package com.taz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.net.Uri;

import static android.content.Intent.ACTION_VIEW;


public class View_Note extends AppCompatActivity {

    SQLiteDatabase db;
    DbHelper dbHelper;
    Spinner mSpinner1;
    Button link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        final long id = getIntent().getExtras().getLong(getString(R.string.row_id));

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + dbHelper.TABLE_NAME + " where " + dbHelper.C_ID + "=" + id, null);
        TextView title = (TextView) findViewById(R.id.title);
        TextView detail = (TextView) findViewById(R.id.detail);
        TextView notetype = (TextView) findViewById(R.id.note_type_ans);
        TextView eventtype = (TextView) findViewById(R.id.event_type_ans);
        TextView time = (TextView) findViewById(R.id.alertvalue);
        TextView date = (TextView) findViewById(R.id.datevalue);
        mSpinner1 = (Spinner) findViewById(R.id.spinnereventType);
        link = (Button) findViewById(R.id.link);
        link.setVisibility(View.INVISIBLE);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                title.setText(cursor.getString(cursor.getColumnIndex(dbHelper.TITLE)));
                detail.setText(cursor.getString(cursor.getColumnIndex(dbHelper.DETAIL)));
                notetype.setText(cursor.getString(cursor.getColumnIndex(dbHelper.TYPE)));
                eventtype.setText(cursor.getString(cursor.getColumnIndex(dbHelper.ETYPE)));
                time.setText(cursor.getString(cursor.getColumnIndex(dbHelper.TIME)));
                date.setText(cursor.getString(cursor.getColumnIndex(dbHelper.DATE)));

            }
            cursor.close();
        }
        String a = eventtype.getText().toString().trim();
        if (a.equals("Hospital and Medicals")) {
            link.setVisibility(View.INVISIBLE);
        } else if (a.equals("Electric Bill Payments")) {
            link.setVisibility(View.VISIBLE);
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  Intent url = new Intent(Intent.ACTION_VIEW,Uri.parse("https://bescom.org/pay-bill/"));
                  startActivity(url);
                }
            });
        }
        else if (a.equals("Card Bill Payments")) {
            link.setVisibility(View.VISIBLE);
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent url = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/search?client=ms-android-xiaomi&sxsrf=ACYBGNRVvPmZPQTvubz73-e9o8RdDwUckA%3A1572796212098&ei=NPe-Xd7OBea6vgSE8Igo&q=online+credit+card+bill+payment&oq=online+credit+card+bill+payment&gs_l=mobile-gws-wiz-serp.3..0l8.9217.10636..10915...0.1..0.138.1115.0j9......0....1.........0i71j35i304i39j0i13j0i7i30.N0y5P-wPbhA"));
                    startActivity(url);
                }
            });
        } else if (a.equals("Postpaid and Prepaid Payments")) {
            link.setVisibility(View.VISIBLE);
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent I = getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
                    startActivity(I);
                }
            });
        } else if (a.equals("Meetings")) {
            link.setVisibility(View.VISIBLE);
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(View_Note.this,Timer.class));
                }
            });
        } else if (a.equals("Normal Events")) {
            link.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final long id = getIntent().getExtras().getLong(getString(R.string.rowID));

        switch (item.getItemId()) {
            case R.id.action_back:
                Intent openMainActivity = new Intent(this, MainActivity.class);
                startActivity(openMainActivity);
                return true;
            case R.id.action_edit:

                Intent openEditNote = new Intent(View_Note.this, Edit_Note.class);
                openEditNote.putExtra(getString(R.string.intent_row_id), id);
                startActivity(openEditNote);
                return true;

            case R.id.action_discard:
                AlertDialog.Builder builder = new AlertDialog.Builder(View_Note.this);
                builder
                        .setTitle(getString(R.string.delete_title))
                        .setMessage(getString(R.string.delete_message))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Long id = getIntent().getExtras().getLong(getString(R.string.rodID));
                                db.delete(DbHelper.TABLE_NAME, DbHelper.C_ID + "=" + id, null);
                                db.close();
                                Intent openMainActivity = new Intent(View_Note.this, MainActivity.class);
                                startActivity(openMainActivity);

                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)                        //Do nothing on no
                        .show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
