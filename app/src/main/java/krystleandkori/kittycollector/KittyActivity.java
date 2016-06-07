package krystleandkori.kittycollector;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class KittyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Kitty currentKitty;
    TextView clickCounterView;
    Integer count = 0;
    Button upgrade1;
    User currentUser;
    Button upgrade2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setup user
        currentUser = new User();

        final ImageButton kittyButton = (ImageButton) findViewById(R.id.kittyButton);
        clickCounterView = (TextView) findViewById(R.id.click_number_text);
        currentKitty = new Kitty(Kitty.CatType.CAT,"First Cat");
        final TextView kittyName = (TextView) findViewById(R.id.kitty_name);
        kittyName.setText(currentKitty.getmName());
//        kittyButton.setBackground(R.drawable.shittykitty);
        final ProgressBar loveBar = (ProgressBar) findViewById(R.id.love_bar);
        //get the max from the cat's love value
        loveBar.setMax(currentKitty.mCat.getLove());

        if (kittyButton != null) {
            kittyButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    count++;
                    clickCounterView.setText(count.toString());
                    loveBar.setProgress(count);
                    if(count == 100){
                        loveBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
//                        loveBar.setProgressBackgroundTintList(@android:color/colorPrimary);
                        Toast.makeText(KittyActivity.this,"You caught a cat!",Toast.LENGTH_SHORT).show();
                        saveStatsAndAddnewCat();

                        //TODO make a user that has stored info
                    }
                }
            });
        }

        upgrade1 = (Button) findViewById(R.id.upgrade_button_1);
        upgrade2 = (Button) findViewById(R.id.upgrade_button_2);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private boolean gotCollar(){
         final double LUCKCHANCE = currentKitty.mCat.getLuck();
        Random r = new Random(10);
        double val = r.nextInt();
        return true;

    }
    public void saveStatsAndAddnewCat(){
        //I feel like this is a really weird way to do this. Should I have generate Kitty in another class?
        if (gotCollar()){

        }


        currentKitty = currentKitty.generateKitty();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kitty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            Toast.makeText(KittyActivity.this,"Clicked Profile",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            // do a thing
            Toast.makeText(KittyActivity.this,"Clicked Share",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(KittyActivity.this,"Clicked Send",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
