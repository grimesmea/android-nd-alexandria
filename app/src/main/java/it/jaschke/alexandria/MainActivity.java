package it.jaschke.alexandria;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements ListOfBooks.Callback {

    public static boolean IS_TABLET = false;

    private CharSequence title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IS_TABLET = isTablet();
        if (IS_TABLET) {
            setContentView(R.layout.activity_main_tablet);
        } else {
            setContentView(R.layout.activity_main);
            getSupportActionBar().setElevation(0f);
        }

        title = getTitle();

        FragmentManager fragmentManager = getSupportFragmentManager();
        ListOfBooks listFragment = new ListOfBooks();

        fragmentManager.beginTransaction()
                .replace(R.id.container, listFragment)
                .addToBackStack((String) title)
                .commit();
    }

    public void setTitle(int titleId) {
        title = getString(titleId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemSelected(String ean) {
        int rightContainer = R.id.right_container;
        Bundle args = new Bundle();
        args.putString(BookDetail.EAN_KEY, ean);

        if (findViewById(rightContainer) != null) {
            BookDetail fragment = new BookDetail();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(rightContainer, fragment)
                    .addToBackStack("Book Detail")
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class)
                    .putExtras(args);
            startActivity(intent);
        }
    }

    private boolean isTablet() {
        return (getApplicationContext().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}