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

import com.example.lausecdan.huongdandulich.Model.Thuexe;
import com.example.lausecdan.huongdandulich.ViewHolder.ThuexeViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ThueXe extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView txtFullname;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference thuexeList;
    FirebaseRecyclerAdapter<Thuexe,ThuexeViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thue_xe);
        database=FirebaseDatabase.getInstance();
        thuexeList=database.getReference("ThueXe");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Thuexe");
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
        recyclerView=(RecyclerView)findViewById(R.id.recycler_thuexe);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadThuexe();
    }
    private void loadThuexe(){
        adapter=new FirebaseRecyclerAdapter<Thuexe, ThuexeViewHolder>(Thuexe.class,R.layout.thuexeitem,ThuexeViewHolder.class,thuexeList) {
            @Override
            protected void populateViewHolder(ThuexeViewHolder viewHolder, Thuexe model, int position) {
                viewHolder.txtthuexe.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Thuexe clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent thuexeDetail=new Intent(ThueXe.this,ThueXeDetail.class);
                        thuexeDetail.putExtra("ThuexeId",adapter.getRef(position).getKey());
                        startActivity(thuexeDetail);
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
            Intent diadiem=new Intent(ThueXe.this,Home.class);
            diadiem.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(diadiem);
            // Handle the camera action
        } else if (id == R.id.nav_eat) {
            Intent eat=new Intent(ThueXe.this,AnUong.class);
            eat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(eat);
        } else if (id == R.id.nav_thuexe) {

        } else if (id == R.id.nav_khachsan) {
            Intent thueXe=new Intent(ThueXe.this,KhachSan.class);
            thueXe.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(thueXe);
        }else if (id==R.id.nav_log_out){
            Intent signIn=new Intent(ThueXe.this,SignIn.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
