package com.example.cine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cine.fragments.CastFragment;
import com.example.cine.fragments.InfoFragment;
import com.example.cine.fragments.SimilarFragment;
import com.example.cine.pojo.Cast;
import com.example.cine.pojo.Details;
import com.example.cine.pojo.Movie;
import com.example.cine.retrofit.APIClient;
import com.example.cine.retrofit.APIInterface;
import com.example.cine.utils.DownLoadImageTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {
    APIInterface apiInterface;
    Details resource;
    TextView titleTV, overviewTV, multiInfoTV;
    ImageView posterIV;
    boolean logged;
    FirebaseUser user;
    private FirebaseFirestore db;
    String movieID, title, posterPath, multiInfo, overview;
    List<Cast> cast;
    List<Movie> movies;
    private static final int NUM_PAGES = 3;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        logged = isLogged();
        db = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_info);
        titleTV = findViewById(R.id.titleTV);
        overviewTV = findViewById(R.id.overviewTV);
        posterIV = findViewById(R.id.posterIV);
        multiInfoTV = findViewById(R.id.multiInfoTv);
        movieID = String.valueOf(getIntent().getIntExtra("id", 0));

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Details> call = apiInterface.getInfoMovie(movieID);
        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Call<Details> call, Response<Details> response) {
                resource = response.body();
                title = resource.getTitle();
                titleTV.setText(title);
                posterPath = resource.getPosterPath();
                if (posterPath == null || posterPath.equals("")) {
                    posterIV.setImageResource(R.drawable.film);
                } else {
                    new DownLoadImageTask(posterIV).execute("https://image.tmdb.org/t/p/w154" + resource.getPosterPath());
                }
                overview = resource.getOverview();
                multiInfo = resource.getRuntime() + " min | " + String.format("%.2f", resource.getVoteAverage()*10) +"% | " + resource.getBudget() + "$";
                cast=  resource.getCredits().getCast();
                movies=  resource.getSimilar().getMovies();
                viewPager.setAdapter(pagerAdapter);
            }
            @Override
            public void onFailure(Call<Details> call, Throwable t) {
                call.cancel();
            }
        });
        bottom_navigation = findViewById(R.id.bottom_navigation);

        bottom_navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.principal:
                    Intent mainIntent = new Intent(InfoActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    break;
                case R.id.busqueda:
                    Intent searchIntent = new Intent(InfoActivity.this, SearchActivity.class);
                    startActivity(searchIntent);
                    break;
                case R.id.listas:
                    Intent listsIntent = new Intent(InfoActivity.this, ListActivity.class);
                    startActivity(listsIntent);
                    break;
                case R.id.perfil:
                    Intent profileIntent = new Intent(InfoActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                    break;
                default:
                    throw new IllegalArgumentException("item not implemented : " + item.getItemId());
            }
            return true;
        });

    }

    public void addClick(View v) {
        String type = String.valueOf(v.getTag());
        if (logged) {
            Map<String, Object> lists = new HashMap<>();
            Map<String, String> movie = new HashMap<>();
            movie.put("id", movieID);
            movie.put("title", title);
            movie.put("posterPath", posterPath);
            lists.put(type, FieldValue.arrayUnion(movie));
            db.collection("Users").document(user.getUid()).set(lists, SetOptions.merge())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast hola = Toast.makeText(getApplicationContext(), "Añadida a " + type, Toast.LENGTH_LONG);
                            hola.show();
                        }
                    });
        } else {
            Toast toast = Toast.makeText(this, "Debes estar logueado para dar realizar esta accion", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public boolean isLogged() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return false;
        }
        return true;
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return InfoFragment.newInstance(multiInfo, overview);
                case 1:
                    return CastFragment.newInstance(cast);
                case 2:
                return SimilarFragment.newInstance(movies);

                default:
                    return new CastFragment();
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}