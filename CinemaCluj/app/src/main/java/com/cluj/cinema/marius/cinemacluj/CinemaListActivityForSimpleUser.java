package com.cluj.cinema.marius.cinemacluj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cluj.cinema.marius.cinemacluj.model.Association;
import com.cluj.cinema.marius.cinemacluj.model.Cinema;
import com.cluj.cinema.marius.cinemacluj.repository.AppDatabase;
import com.cluj.cinema.marius.cinemacluj.repository.AssociationRepository;
import com.cluj.cinema.marius.cinemacluj.repository.CinemaRepository;
import com.cluj.cinema.marius.cinemacluj.repository.MovieRepository;
import com.cluj.cinema.marius.cinemacluj.util.Globals;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CinemaListActivityForSimpleUser extends AppCompatActivity {

    public static final String EXTRA_CINEMA_POSITION_IN_LIST = "com.cluj.cinema.marius.cinemacluj.CinemaListActivity.cinemaPositionInList";
    public static final int CINEMA_DETAIL_REQUEST = 1;
    public static final int CREATE_CINEMA_REQUEST = 2;
    public static final String ACTION_UPDATE = "UPDATE";
    public static final String ACTION_DELETE = "DELETE";

    public static ArrayAdapter<String> adapter;
    public static List<String> titles;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean cinemaListLisenerSetedUp = false;
    private boolean assocListLisenerSetedUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_list_for_simple_user);

        mAuth= FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Button mLogOutButton=(Button)findViewById(R.id.signOutButton);
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                mGoogleSignInClient.signOut();
                startActivity(new Intent(CinemaListActivityForSimpleUser.this, LoginActivity.class));
                finish();
            }
        });

        if (Globals.cinemaRepository == null) {
            AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
            Globals.cinemaRepository = new CinemaRepository(appDatabase);
            Globals.movieRepository = new MovieRepository(appDatabase);
            Globals.associationRepository = new AssociationRepository(appDatabase);
        }

        titles = new ArrayList<>();
        for (Cinema c : Globals.cinemaRepository.getAll())
            titles.add(c.getListItemRepresentation());

        ListView list = (ListView) findViewById(R.id.cinema_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CinemaListActivityForSimpleUser.this, CinemaDetailActivity.class);
                intent.putExtra(CinemaListActivity.EXTRA_CINEMA_POSITION_IN_LIST, position);
                startActivity(intent);
            }
        });

        adapter = new ArrayAdapter<String>(list.getContext(),
                android.R.layout.simple_list_item_1, titles);
        list.setAdapter(adapter);

        setUpCinemaListener();
        setUpMovieListener();
        setUpAssocListener();
    }

    public void goToMovieList(View view){
        Intent intent = new Intent(this, MovieListActivity.class);
        startActivity(intent);
    }

    private void setUpCinemaListener(){
        if (cinemaListLisenerSetedUp)
            return;
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("server");
        DatabaseReference bookRef=ref.child("cinemas");

        bookRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cinema c=dataSnapshot.getValue(Cinema.class);

                if(Globals.getCinemaByKey(c.getFirebaseKey()) == null){
                    Globals.cinemaRepository.add(c);
                    titles.add(c.getListItemRepresentation());
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Cinema newCinema = dataSnapshot.getValue(Cinema.class);
                titles.remove(Globals.getCinemaByKey(newCinema.getFirebaseKey()).getListItemRepresentation());
                Globals.cinemaRepository.update(newCinema);
                titles.add(newCinema.getListItemRepresentation());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for(int i=0;i<Globals.cinemaRepository.getAll().size();i++){
                    if(Globals.cinemaRepository.getCinema(i).getFirebaseKey().equals(dataSnapshot.getKey())){
                        Globals.cinemaRepository.delete(Globals.cinemaRepository.getCinema(i).getFirebaseKey());
                        titles.remove(i);
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cinemaListLisenerSetedUp = true;
    }

    private void setUpMovieListener(){
        MovieListActivity.setUpMovieListener();
    }

    private void setUpAssocListener(){
        if (assocListLisenerSetedUp)
            return;

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("server");
        DatabaseReference bookRef=ref.child("associations");

        bookRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Association a=dataSnapshot.getValue(Association.class);

                if(Globals.getAssociationByKey(a.getFirebaseKey()) == null)
                    Globals.associationRepository.add(a);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Association newAssociation = dataSnapshot.getValue(Association.class);
                Globals.associationRepository.update(newAssociation);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for(int i=0;i<Globals.associationRepository.getAll().size();i++)
                    if(Globals.associationRepository.getAssociation(i).getFirebaseKey().equals(dataSnapshot.getKey()))
                        Globals.associationRepository.delete(Globals.associationRepository.getAssociation(i).getFirebaseKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        assocListLisenerSetedUp = true;
    }
}
