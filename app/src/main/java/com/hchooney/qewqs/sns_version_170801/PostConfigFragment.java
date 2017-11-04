package com.hchooney.qewqs.sns_version_170801;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hchooney.qewqs.sns_version_170801.Post_Items.ListInfomationItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostConfigFragment extends DialogFragment {
    private View view;

    //사용자
    private ImageView UserImageview;
    private TextView UserName;
    //등록일
    private TextView When;
    //평점
    private TextView Rating;
    //작성
    private TextView Title;
    private TextView Context;
    private TextView HashTag;
    //주소
    private Button DetailLocationBTN;
    //배경
    private LinearLayout background_postit;
    //베스트이미지
    private ImageView BestPhoto;
    //확인 취소 버튼
    private Button OkBTN;
    private Button NoBTN;

    //메타데이터
    private ListInfomationItem previewItem;
    private Bitmap BestImage;

    //서브쓰레드
    private Thread SaveDateToServer = new Thread(new Runnable() {
        @Override
        public void run() {
            DatabaseReference mref = FirebaseDatabase.getInstance().getReference();
            String[] DateTemp = previewItem.getWhen().split(" ");

            mref.child("PostList").child(DateTemp[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Integer child = (int) dataSnapshot.getChildrenCount();
                    if(child == null || child == 0){
                        child = 0;
                    }
                    //   00 00 00 00 총 8자리
                    String PostName = "";
                    if(child >= 10000000){
                        PostName += "";
                    }else if(child >= 1000000){
                        PostName += "0";
                    }else if(child >= 100000){
                        PostName += "00";
                    }else if(child >= 10000){
                        PostName += "000";
                    }else if(child >= 1000){
                        PostName += "0000";
                    }else if(child >= 100){
                        PostName += "00000";
                    }else if(child >= 10){
                        PostName += "000000";
                    }else{
                        PostName += "0000000";
                    }
                    PostName+=(child+"");

                    DatabaseReference sendRef = dataSnapshot.getRef();

                    sendRef.child(PostName).setValue(previewItem);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            Log.d("Save Date", "Save is Complete!");
        }
    });

    public PostConfigFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);                    //중간 에러가 생기지 않도록 이벤트 홀드
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_post_config, null);
        dialog.setContentView(view);

        if(getArguments() == null){
            Toast.makeText(getActivity(), "죄송합니다. 시도해주십시오.", Toast.LENGTH_SHORT).show();
            dismiss();
        }else{
            previewItem = (ListInfomationItem) getArguments().getSerializable("Preview");
        }

        init(view);

        setPreView();

        Event();

        return dialog;
    }

    private void init(View view){
        //사용자
        UserImageview = (ImageView) view.findViewById(R.id.PostConfig_UserImage);
        UserName = (TextView) view.findViewById(R.id.PostConfig_UserName);
        //등록일
        When = (TextView) view.findViewById(R.id.PostConfig_WriteDate);
        //평점
        Rating = (TextView) view.findViewById(R.id.PostConfig_LikeRate);
        //작성
        Title = (TextView) view.findViewById(R.id.PostConfig_Title);
        Context = (TextView) view.findViewById(R.id.PostConfig_Context);
        HashTag = (TextView) view.findViewById(R.id.PostConfig_HashTag);
        //주소
        DetailLocationBTN = (Button) view.findViewById(R.id.PostConfig_LocationBTN);
        //확인 취소 버튼
        OkBTN = (Button) view.findViewById(R.id.PostConfig_CompleteBTN);
        NoBTN = (Button) view.findViewById(R.id.PostConfig_NoBTN);
        //배경
        background_postit = (LinearLayout) view.findViewById(R.id.PostConfig_Background);
        //갤러리
        BestPhoto = (ImageView) view.findViewById(R.id.PostConfig_ImageView);
        //메타데이터
        BestImage = ((AddPostActivity)getActivity()).getImage_bitmap();
    }

    private void setPreView(){
        UserName.setText("ID : " + this.previewItem.getWho());
        When.setText("등록일 : " + this.previewItem.getWhen());
        double temp_Rate = this.previewItem.getLikeRate();
        if(temp_Rate > 4.5 && temp_Rate <= 5.0){
            Rating.setText("Perfect");
            Rating.setTextColor(Color.parseColor("#D500F9")); //밝은 보라색
        }else if(temp_Rate > 4.0 && temp_Rate <= 4.5){
            Rating.setText("Excellent");
            Rating.setTextColor(Color.parseColor("#00C853")); //짙은 녹색
        }else if(temp_Rate > 3.5 && temp_Rate <= 4.0){
            Rating.setText("Good");
            Rating.setTextColor(Color.parseColor("#0D47A1")); //짙은 파란색
        }else if(temp_Rate > 3.0 && temp_Rate <= 3.5){
            Rating.setText("Nice");
            Rating.setTextColor(Color.parseColor("#F57F17")); //주황색
        }else if(temp_Rate > 2.5 && temp_Rate <= 3.0){
            Rating.setText("Not Bad");
            Rating.setTextColor(Color.parseColor("#EC407A")); //핑크색
        }else if(temp_Rate > 1.5 && temp_Rate <= 2.5){
            Rating.setText("Bad");
            Rating.setTextColor(Color.parseColor("#ff0000")); //빨강색
        }else{
            Rating.setText("Oh..no..");
            Rating.setTextColor(Color.parseColor("#000000")); //검은색
        }
        Title.setText(this.previewItem.getWhere());

        String temp = "";
        int count = this.previewItem.getHashTag().size();
        for(int num = 0 ; num < count ; num ++){
            temp = temp + this.previewItem.getHashTag().get(num);
            if(num < count - 1 ){
                temp += ", ";
            }
        }
        HashTag.setText(temp);

        Context.setText(this.previewItem.getContext());
        String tempPostitName =  this.previewItem.getPostItDesign();
        Log.d("PostAdater", "getPostDesign : " + tempPostitName);
        if(tempPostitName.equals("postit_grade_one_img01")) {
            //backgroundIMG.setBackgroundResource(R.drawable.postit_grade_one_img01);
            background_postit.setBackgroundResource(R.drawable.postit_grade_one_img01);
        }else if(tempPostitName.equals("postit_grade_one_img02")){
            //backgroundIMG.setBackgroundResource(R.drawable.postit_grade_one_img02);
            background_postit.setBackgroundResource(R.drawable.postit_grade_one_img02   );
        }

        //이미지파일은 웹 스토리지의 URL을 받지 못함으로 이미지는 Bitmap으로 따로 처리한다.
        //이후 완료시 처리 고로 어텝터와 다른 코드여야함.

        BestPhoto.setImageBitmap(BestImage);
        /*try {
            BestImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), ((AddPostActivity)getActivity()).getPhotoURL());
            BestPhoto.setImageBitmap(BestImage);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        DetailLocationBTN.setText(this.previewItem.getDetailCity());
    }
    private void Event(){
        OkBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //서버 업로드
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://startupproject-5b5ca.appspot.com");
                StorageReference imagesRef = storageRef.child(previewItem.getWho() + "__" + previewItem.getLat() + "__" + previewItem.getLon());

                Bitmap tempBitmap = BestImage;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = imagesRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        previewItem.setPhotoURL(downloadUrl.toString());

                        SaveDateToServer.start();


                        dismiss();
                        ((AddPostActivity)getActivity()).NotificationAdator();

                    }
                });


            }
        });
        NoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //창 닫기
                dismiss();
            }
        });
    }

}
