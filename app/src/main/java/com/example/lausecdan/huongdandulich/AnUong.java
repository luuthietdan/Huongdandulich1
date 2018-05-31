package com.example.lausecdan.huongdandulich;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lausecdan.huongdandulich.Interface.ItemClickListener;

import com.example.lausecdan.huongdandulich.ViewHolder.AnuongViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.lausecdan.huongdandulich.Model.Anuong;
import com.squareup.picasso.Picasso;

public class AnUong extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView txtFullname;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference anuongList;
    FirebaseRecyclerAdapter<Anuong,AnuongViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_an_uong);
        database=FirebaseDatabase.getInstance();
        anuongList=database.getReference("AnUong");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Anuong");
        setSupportActionBar(toolbar);

  ;
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
        recyclerView=(RecyclerView)findViewById(R.id.recycler_anuong);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadAnuong();
    }
    private void loadAnuong(){
        adapter=new FirebaseRecyclerAdapter<Anuong, AnuongViewHolder>(Anuong.class,R.layout.anuongitem,AnuongViewHolder.class,anuongList) {
            @Override
            protected void populateViewHolder(AnuongViewHolder viewHolder, Anuong model, int position) {
                viewHolder.txtanuong.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Anuong clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent anuongDetail=new Intent(AnUong.this,AnUongDetail.class);
                        anuongDetail.putExtra("AnuongId",adapter.getRef(position).getKey());
                        startActivity(anuongDetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        }
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
                Intent diadiem=new Intent(AnUong.this,Home.class);
                diadiem.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(diadiem);
                // Handle the camera action
            } else if (id == R.id.nav_eat) {

            } else if (id == R.id.nav_thuexe) {
                Intent thueXe=new Intent(AnUong.this,ThueXe.class);
                thueXe.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(thueXe);
            } else if (id == R.id.nav_khachsan) {
                Intent khachSan=new Intent(AnUong.this,KhachSan.class);
                khachSan.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(khachSan);
            }else if (id==R.id.nav_log_out){
                Intent signIn=new Intent(AnUong.this,SignIn.class);
                signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signIn);
            }



            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
}
