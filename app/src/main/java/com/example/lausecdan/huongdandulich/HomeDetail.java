package com.example.lausecdan.huongdandulich;

import android.media.Image;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lausecdan.huongdandulich.Common.Common;
import com.example.lausecdan.huongdandulich.Model.Category;
import com.example.lausecdan.huongdandulich.Model.DiadiemDetail;
import com.example.lausecdan.huongdandulich.Model.Rating;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.w3c.dom.Text;

import java.util.Arrays;

public class HomeDetail extends AppCompatActivity implements RatingDialogListener{

    TextView diadiem_name,diadiem_description;
    ImageView diadiem_image;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String diadiemId="";
    FirebaseDatabase database;
    DatabaseReference categorydetail;
    DatabaseReference ratingTbl;
    ImageView imgRating;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);
        database=FirebaseDatabase.getInstance();
        categorydetail=database.getReference("CategoryDetail");
        ratingTbl=database.getReference("Rating");

        diadiem_name=(TextView) findViewById(R.id.diadiem_name);
        diadiem_description=(TextView) findViewById(R.id.diadiem_description);
        diadiem_image=(ImageView) findViewById(R.id.img_diadiem);
        imgRating=(ImageView) findViewById(R.id.img_rating);
        ratingBar=(RatingBar) findViewById(R.id.ratingBar);

        imgRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });
        collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapsing);

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if (getIntent()!=null)
            diadiemId=getIntent().getStringExtra("DiadiemId");
        if (!diadiemId.isEmpty())
        {
            getDetailDiadiem(diadiemId);
            getRatingDiadiem(diadiemId);
        }
    }

    private void getRatingDiadiem(String diadiemId) {
        Query diadiemRating=ratingTbl.orderByChild("diadiemId").equalTo(diadiemId);

        diadiemRating.addValueEventListener(new ValueEventListener() {
            int count=0,sum=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Rating item=postSnapshot.getValue(Rating.class);
                    sum+=Integer.parseInt(item.getRateValue());
                    count++;
                }
                if(count!=0)
                {
                    float average=sum/count;
                    ratingBar.setRating(average);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Gửi Đi")
                .setNegativeButtonText("Hủy")
                .setNoteDescriptions(Arrays.asList("Rất Tệ","Không Tốt","Tạm Được","Tốt","Xuất Sắc"))
                .setDefaultRating(1)
                .setTitle("Đánh Giá Địa Điểm")
                .setDescription("Xin Vui Lòng Chọn Số Sao Và Gửi Phản Hồi")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Xin vui lòng ghi bình luận tại đây...")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimary)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(HomeDetail.this)
                .show();
    }

    private void getDetailDiadiem(String diadiemId) {
        categorydetail.child(diadiemId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DiadiemDetail diadiemDetail=dataSnapshot.getValue(DiadiemDetail.class);

                Picasso.with(getBaseContext()).load(diadiemDetail.getImage())
                        .into(diadiem_image);
               //collapsingToolbarLayout.getTitle(diadiemDetail.getName());

                diadiem_name.setText(diadiemDetail.getName());

                diadiem_description.setText(diadiemDetail.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPositiveButtonClicked(int value, String comments) {
        final Rating rating=new Rating(Common.currentUser.getName(),
                diadiemId,
                String.valueOf(value),
                comments);
        ratingTbl.child(Common.currentUser.getName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Common.currentUser.getName()).exists())
                {
                    ratingTbl.child(Common.currentUser.getName()).removeValue();
                    ratingTbl.child(Common.currentUser.getName()).setValue(rating);
                }
                else
                {
                    ratingTbl.child(Common.currentUser.getName()).setValue(rating);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onNegativeButtonClicked() {

    }
}
