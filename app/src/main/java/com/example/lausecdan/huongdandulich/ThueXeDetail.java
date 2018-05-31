package com.example.lausecdan.huongdandulich;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lausecdan.huongdandulich.Common.Common;
import com.example.lausecdan.huongdandulich.Model.ThuexeDetail;
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

public class ThueXeDetail extends AppCompatActivity implements RatingDialogListener{


    TextView thuexe_name,thuexe_description;
    ImageView thuexe_image;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String thuexeId="";
    FirebaseDatabase database;
    DatabaseReference thuexedetail;
    DatabaseReference ratingTbl;
    ImageView imgRating;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thue_xe_detail);
        database=FirebaseDatabase.getInstance();
        thuexedetail=database.getReference("ThueXeDetail");
        ratingTbl=database.getReference("Rating");

        thuexe_name=(TextView) findViewById(R.id.thuexe_name);
        thuexe_description=(TextView) findViewById(R.id.thuexe_description);
        thuexe_image=(ImageView) findViewById(R.id.img_thuexe);
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
            thuexeId=getIntent().getStringExtra("ThuexeId");
        if (!thuexeId.isEmpty())
        {
            getDetailThuexe(thuexeId);
            getRatingThuexe(thuexeId);
        }
    }

    private void getRatingThuexe(String thuexeId) {
        Query thuexeRating=ratingTbl.orderByChild("thuexeId").equalTo(thuexeId);

        thuexeRating.addValueEventListener(new ValueEventListener() {
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
                .setTitle("Đánh Giá Nơi Thuê Xe")
                .setDescription("Xin Vui Lòng Chọn Số Sao Và Gửi Phản Hồi")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Xin vui lòng ghi bình luận tại đây...")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimary)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(ThueXeDetail.this)
                .show();
    }

    private void getDetailThuexe(String thuexeId) {
        thuexedetail.child(thuexeId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ThuexeDetail thuexeDetail=dataSnapshot.getValue(ThuexeDetail.class);

                Picasso.with(getBaseContext()).load(thuexeDetail.getImage())
                        .into(thuexe_image);
                //collapsingToolbarLayout.getTitle(diadiemDetail.getName());

                thuexe_name.setText(thuexeDetail.getName());

                thuexe_description.setText(thuexeDetail.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPositiveButtonClicked(int value, String comments) {
        final Rating rating=new Rating(Common.currentUser.getName(),
                thuexeId,
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
