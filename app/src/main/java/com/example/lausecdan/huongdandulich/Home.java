package com.example.lausecdan.huongdandulich;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lausecdan.huongdandulich.Common.Common;
import com.example.lausecdan.huongdandulich.Interface.ItemClickListener;
import com.example.lausecdan.huongdandulich.Model.Category;
import com.example.lausecdan.huongdandulich.ViewHolder.DiadiemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import io.paperdb.Paper;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtFullname;
    FirebaseDatabase database;
    DatabaseReference category;
    RecyclerView recyler_diadiem;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,DiadiemViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Diadiem");
        setSupportActionBar(toolbar);

        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");

        Paper.init(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView=navigationView.getHeaderView(0);
        txtFullname=(TextView)headerView.findViewById(R.id.txtFullname);
        //txtFullname.setText(Common.currentUser.getName());

        recyler_diadiem=(RecyclerView) findViewById(R.id.recycler_diadiem);
        recyler_diadiem.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyler_diadiem.setLayoutManager(layoutManager);

        loadDiadiem();
    }

    private void loadDiadiem() {
         adapter=new FirebaseRecyclerAdapter<Category, DiadiemViewHolder>(Category.class,R.layout.diadiemitem,DiadiemViewHolder.class,category) {
            @Override
            protected void populateViewHolder(DiadiemViewHolder viewHolder, Category model, int position) {
                viewHolder.txtTenDiaDiem.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Category clickItem=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent diadiemDetail=new Intent(Home.this,HomeDetail.class);
                        diadiemDetail.putExtra("DiadiemId",adapter.getRef(position).getKey());
                        startActivity(diadiemDetail);
                    }
                });
            }
        };
        recyler_diadiem.setAdapter(adapter);
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_diadiem) {

            // Handle the camera action
        } else if (id == R.id.nav_eat) {
            Intent diadiem=new Intent(Home.this,AnUong.class);
            diadiem.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(diadiem);
        } else if (id == R.id.nav_thuexe) {
            Intent thuexe=new Intent(Home.this,ThueXe.class);
            thuexe.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(thuexe);
        } else if (id == R.id.nav_khachsan) {
            Intent khachsan=new Intent(Home.this,KhachSan.class);
            khachsan.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(khachsan);
        }else if (id==R.id.nav_log_out){

            Paper.book().destroy();
            Intent singIn=new Intent(Home.this,SignIn.class);
            singIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(singIn);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
