package com.hchooney.qewqs.sns_version_170801;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hchooney.qewqs.sns_version_170801.Post_Items.ListInfomationItem;
import com.hchooney.qewqs.sns_version_170801.Post_Items.PostItAdapter;
import java.util.ArrayList;
import android.view.ViewGroup.LayoutParams;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {
    private View view;
    private Context mContext;
    private static final String TAG = "PostItListFragment";
    final static int PostFragmentSignal = 2001;

    //팝업윈도우
    private RelativeLayout mRelativeLayout;

    //리스트뷰
    private ListView myPostitListview;
    private ArrayList<ListInfomationItem> TempArrayList;

    //어뎁터
    private PostItAdapter postItAdapter;
    public PostItAdapter getPostItAdapter() {
        return postItAdapter;
    }

    //EvnetListner
    private ChildEventListener childEventListener;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    //windowsPopUp
    private PopupWindow mPopupWindow;

    //이미지버튼
    private ImageButton Map;
    private ImageButton Search;
    private ImageButton Add;


    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseServerHold();
    }

    @Override
    public void onStop() {
        super.onStop();

        FirebaseServerDetech();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post, container, false);
        init(view);
        Event();
        return view;
    }

    private void init(View view){
        //팝업윈도우
        mContext = getContext();
        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.rl_custom_layout);

        //리스트뷰
        myPostitListview = (ListView) view.findViewById(R.id.PostFragment_Listview);
        Map = (ImageButton) view.findViewById(R.id.PostFragment_Map_ImageButton);
        Search = (ImageButton) view.findViewById(R.id.PostFragment_Search_ImageButton);
        Add = (ImageButton) view.findViewById(R.id.PostFragment_Add_ImageButton);
    }

    private void Event(){
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //절대 MainActivity를 종료하지 말것.
                Intent intent = new Intent(getActivity().getApplicationContext(), AddPostActivity.class);
                intent.putExtra("Who", ((MainActivity)getActivity()).getWho());
                startActivityForResult(intent, PostFragmentSignal);
            }
        });
    }

    private void FirebaseServerHold(){
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                ListInfomationItem listInfomationItem = dataSnapshot.getValue(ListInfomationItem.class);
                TempArrayList.add(0, listInfomationItem);
                postItAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                String commentKey = dataSnapshot.getKey();
                ListInfomationItem listInfomationItem = dataSnapshot.getValue(ListInfomationItem.class);
                int commentIndex = TempArrayList.indexOf(commentKey);
                if (commentIndex > -1) {
                    // Replace with the new data
                    TempArrayList.set(commentIndex, listInfomationItem);

                    postItAdapter.notifyDataSetChanged();
                } else {
                    Log.w(TAG, "onChildChanged:unknown_child:" + commentKey);
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                String commentKey = dataSnapshot.getKey();
                int commentIndex = TempArrayList.indexOf(commentKey);
                if (commentIndex > -1) {
                    // Remove data from the list
                    TempArrayList.remove(commentIndex);

                    postItAdapter.notifyDataSetChanged();
                } else {
                    Log.w(TAG, "onChildRemoved:unknown_child:" + commentKey);
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(getContext(), "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };

        //어댑터
        TempArrayList = ((MainActivity)getActivity()).getListInfomationItems();

        /*
        * 서버에서 데이터를 받아오며 이벤트로 홀드시키는 내용입니다.
        * 이 이벤트리스터는 1회성이 아닌 지속적으로 주의하고 있습니다.
         */
        databaseReference.child("PostList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                for(DataSnapshot snapshot_date : dataSnapshot.getChildren()){
                    Log.d("Server GetList", "Data : " + snapshot_date.toString());
                    ListInfomationItem listInfomationItem = snapshot_date.getValue(ListInfomationItem.class);
                    Log.d("Server GetList", "ListInformation : " + listInfomationItem.toString());

                    //최신내용 일수록 리스트 앞에 나타나 있어여 리스트뷰에서 최상위로 올라가게 됩니다.
                    TempArrayList.add(0, listInfomationItem);
                }
                postItAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //이벤트를 통해 받아온 내용을 TempArrayList를 통해서 내용을 인지하도록 합니다.
        postItAdapter = new PostItAdapter(getContext(), R.layout.postit_item, TempArrayList);
        myPostitListview.setAdapter(postItAdapter);
    }

    private void FirebaseServerDetech(){
        postItAdapter = null;
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PostFragmentSignal :
                if(resultCode == 1){
                    postItAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }*/
}
