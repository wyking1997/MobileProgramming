package com.cluj.cinema.marius.cinemacluj.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by marius on 1/13/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG="MyFirebaseInsIDService";

    @Override
    public void onTokenRefresh() {
        //Get the updated token
        String refreshedTocken= FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"New Token: "+refreshedTocken);
    }
}
