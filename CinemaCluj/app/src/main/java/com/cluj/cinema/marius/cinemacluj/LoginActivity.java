package com.cluj.cinema.marius.cinemacluj;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cluj.cinema.marius.cinemacluj.model.Association;
import com.cluj.cinema.marius.cinemacluj.model.Cinema;
import com.cluj.cinema.marius.cinemacluj.model.Movie;
import com.cluj.cinema.marius.cinemacluj.model.User;
import com.cluj.cinema.marius.cinemacluj.repository.AppDatabase;
import com.cluj.cinema.marius.cinemacluj.repository.AssociationRepository;
import com.cluj.cinema.marius.cinemacluj.repository.CinemaRepository;
import com.cluj.cinema.marius.cinemacluj.repository.MovieRepository;
import com.cluj.cinema.marius.cinemacluj.repository.UserRepository;
import com.cluj.cinema.marius.cinemacluj.util.Globals;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private SignInButton mGoogleButton;
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private GoogleApiClient mGoogleApiClient;

    private static final String TAG = "LoginActivity";
    private static boolean userListLisenerSetedUp = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Globals.initializeAppGlobals();

        if (Globals.cinemaRepository == null) {
            AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
            Globals.cinemaRepository = new CinemaRepository(appDatabase);
            Globals.movieRepository = new MovieRepository(appDatabase);
            Globals.associationRepository = new AssociationRepository(appDatabase);
            Globals.userRepository = new UserRepository(appDatabase);
        }
        setUpAssocListener();

//        Globals.addCinema(new Cinema("Cinema Marasti","marasti","0752125080"));
//        Globals.addCinema(new Cinema("Cinema Victoria","victoria","0752125080"));
//        Globals.addCinema(new Cinema("Cinema Florin Piersic","viteazu","0752125080"));
//        Globals.addCinema(new Cinema("Cinema Magia","iulia",""));
//        Globals.addMovie(new Movie("1995","Minionii",100,"galbeni si multi",""));
//        Globals.addMovie(new Movie("2011","Minionii 2",100,"galbeni si multi 2",""));
//        Globals.addMovie(new Movie("2018","Minionii 3",100,"galbeni si multi 3",""));
//        Globals.addMovie(new Movie("2011","Star wars",100,"arme galaxii babe",""));
//        Globals.addMovie(new Movie("2000","Thor",100,"verde si gras",""));
//        Globals.addMovie(new Movie("1997","Cei 4 fantastici",100,"multi si magici",""));
//        Globals.addMovie(new Movie("2013","Rapunzel",100,"copii frumosi",""));
//        Globals.addMovie(new Movie("2015","Mama",100,"groaza",""));

//        List<String> cinemas = Arrays.asList(
//                "-L2knRCBKbEGRuufx3BX",
//                "-L2knRCpiKl8tZbcL7Di",
//                "-L2knRCqjRl3TckF5Nih",
//                "-L2knRCsvM4aKYPMT9R2"
//        );
//        List<String> movies = Arrays.asList(
//                "-L2k1xgV4jcdCjAhYrhB",
//                "-L2k224O4Xd9FxWem-q-",
//                "-L2knRCtR6b8dmU40B5n",
//                "-L2knRD2agrrNH9bCudd",
//                "-L2knRD4uj7k9NPeiAtQ",
//                "-L2knRD5lL4ief16E_ja",
//                "-L2knRD7z-y67qJ3yfDK",
//                "-L2knRD8Mpl586nthIv1",
//                "-L2knRDAQ4v8-PoblbPW",
//                "-L2knRDCHV9y_Wpo3ODN"
//        );
//        List<String> dates = Arrays.asList(
//                "2018-01-08",
//                "2018-01-09",
//                "2018-01-10",
//                "2018-01-11",
//                "2018-01-12",
//                "2018-01-13"
//        );
//        for (int i =0; i < 100; i++){
//            Random random = new Random();
//            int cinema = random.nextInt(cinemas.size());
//            int movie = random.nextInt(movies.size());
//            int date = random.nextInt(dates.size());
//            Globals.addAssocation(new Association(cinemas.get(cinema), movies.get(movie), dates.get(date)));
//        }


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    String userEmail = mAuth.getCurrentUser().getEmail();
                    if (Globals.getUserByUsername(userEmail) == null){
                        Globals.addUser(new User(null, userEmail, "normalUser"));
                        startActivity(new Intent(LoginActivity.this, CinemaListActivityForSimpleUser.class));
                        finish();
                    } else if (Globals.getUserByUsername(userEmail).getRole().equals("admin")) {
                        startActivity(new Intent(LoginActivity.this, CinemaListActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(LoginActivity.this, CinemaListActivityForSimpleUser.class));
                        finish();
                    }
                }
            }
        };

        mGoogleButton = (SignInButton) findViewById(R.id.googleButton);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "You got an error", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else{

            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    private static void setUpAssocListener(){
        if (userListLisenerSetedUp)
            return;

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("server");
        DatabaseReference userRef=ref.child("users");

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User a=dataSnapshot.getValue(User.class);

                if(Globals.getUserByKey(a.getFirebaseKey()) == null)
                    Globals.userRepository.add(a);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                User newUser = dataSnapshot.getValue(User.class);
                Globals.userRepository.update(newUser);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for(int i=0;i<Globals.userRepository.getAll().size();i++)
                    if(Globals.userRepository.getUser(i).getFirebaseKey().equals(dataSnapshot.getKey()))
                        Globals.userRepository.delete(Globals.userRepository.getUser(i).getFirebaseKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userListLisenerSetedUp = true;
    }
}