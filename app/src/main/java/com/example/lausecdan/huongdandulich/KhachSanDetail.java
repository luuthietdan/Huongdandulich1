package com.example.lausecdan.huongdandulich;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lausecdan.huongdandulich.Common.Common;
import com.example.lausecdan.huongdandulich.Model.KhacsanDetail;
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

import java.util.Arrays;

public class KhachSanDetail extends AppCompatActivity implements RatingDialogListener{


    TextView khachsan_name,khachsan_description;
    ImageView khachsan_image;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String khachsanId="";

    FirebaseDatabase database;
    DatabaseReference khachsandetail;
    DatabaseReference ratingTbl;
    ImageView imgRating;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_san_detail);
        database=FirebaseDatabase.getInstance();
        khachsandetail=database.getReference("KhachSanDetail");
        ratingTbl=database.getReference("Rating");

        khachsan_name=(TextView) findViewById(R.id.khachsan_name);
        khachsan_description=(TextView) findViewById(R.id.khachsan_description);
        khachsan_image=(ImageView) findViewById(R.id.img_khachsan);
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
            khachsanId = getIntent().getStringExtra("KhachsanId");

        if(!khachsanId.isEmpty())
        {
            getDetailKhachsan(khachsanId);
            getRatingKhachsan(khachsanId);
        }
    }

    private void getDetailKhachsan(String khachsanId) {
        khachsandetail.child(khachsanId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                KhacsanDetail khacsanDetail=dataSnapshot.getValue(KhacsanDetail.class);
                Picasso.with(getBaseContext()).load(khacsanDetail.getImage())
                        .into(khachsan_image);
                khachsan_name.setText(khacsanDetail.getName());
                khachsan_description.setText(khacsanDetail.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getRatingKhachsan(String khachsanId) {
        Query khachsanRating=ratingTbl.orderByChild("KhachsanId").equalTo(khachsanId);

        khachsanRating.addValueEventListener(new ValueEventListener() {
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
                .setTitle("Đánh Giá Khách San")
                .setDescription("Xin Vui Lòng Chọn Số Sao Và Gửi Phản Hồi")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Xin vui lòng ghi bình luận tại đây...")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimary)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(KhachSanDetail.this)
                .show();
    }



    @Override
    public void onPositiveButtonClicked(int value, String comments) {
        final Rating rating=new Rating(Common.currentUser.getName(),
                khachsanId,
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
