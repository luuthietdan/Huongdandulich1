package com.example.lausecdan.huongdandulich;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lausecdan.huongdandulich.Common.Common;
import com.example.lausecdan.huongdandulich.Model.AnuongDetail;
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

public class AnUongDetail extends AppCompatActivity implements RatingDialogListener{

    TextView anuong_name,anuong_description;
    ImageView anuong_image;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String anuongId="";

    FirebaseDatabase database;
    DatabaseReference anuongdetail;
    DatabaseReference ratingTbl;
    ImageView imgRating;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_an_uong_detail);
        database=FirebaseDatabase.getInstance();
        anuongdetail=database.getReference("AnUongDetail");
        ratingTbl=database.getReference("Rating");

        anuong_name=(TextView) findViewById(R.id.anuong_name);
        anuong_description=(TextView) findViewById(R.id.anuong_description);
        anuong_image=(ImageView) findViewById(R.id.img_anuong);
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
            anuongId = getIntent().getStringExtra("AnuongId");

        if(!anuongId.isEmpty())
        {
            getDetailAnuong(anuongId);
            //getRatingAnuong(anuongId);
        }
    }

    private void getDetailAnuong(final String anuongId) {
        anuongdetail.child(anuongId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AnuongDetail anuongDetail=dataSnapshot.getValue(AnuongDetail.class);
                Picasso.with(getBaseContext()).load(anuongDetail.getImage())
                        .into(anuong_image);
                anuong_name.setText(anuongDetail.getName());
                anuong_description.setText(anuongDetail.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getRatingAnuong(String anuongid) {
        Query anuongRating=ratingTbl.orderByChild("AnuongId").equalTo(anuongid);

        anuongRating.addValueEventListener(new ValueEventListener() {
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
                .setTitle("Đánh Giá Nơi Ăn Uống")
                .setDescription("Xin Vui Lòng Chọn Số Sao Và Gửi Phản Hồi")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Xin vui lòng ghi bình luận tại đây...")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimary)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(AnUongDetail.this)
                .show();
    }



    @Override
    public void onPositiveButtonClicked(int value, String comments) {
        final Rating rating=new Rating(Common.currentUser.getName(),
                anuongId,
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
