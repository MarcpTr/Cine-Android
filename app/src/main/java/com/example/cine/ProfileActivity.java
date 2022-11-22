package com.example.cine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cine.utils.DownLoadImageTask;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    FirebaseUser user;
    ImageView profileImage;
    TextView textViewName;
    TextView textViewEmail;
    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (isLogged()) {
            profileImage = findViewById(R.id.profileImage);
            textViewName = findViewById(R.id.textViewName);
            textViewEmail = findViewById(R.id.textViewEmail);

            textViewName.setText(user.getDisplayName());
            textViewEmail.setText(user.getEmail());
            new DownLoadImageTask(profileImage).execute(user.getPhotoUrl().toString());

            bottom_navigation = findViewById(R.id.bottom_navigation);

            bottom_navigation.setOnNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.principal:
                        Intent mainIntent = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        break;
                    case R.id.busqueda:
                        Intent searchIntent = new Intent(ProfileActivity.this, SearchActivity.class);
                        startActivity(searchIntent);
                        break;
                    case R.id.listas:
                        Intent listsIntent = new Intent(ProfileActivity.this, ListActivity.class);
                        startActivity(listsIntent);
                        break;
                    case R.id.perfil:
                        Intent profileIntent = new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivity(profileIntent);
                        break;
                    default:
                        throw new IllegalArgumentException("item not implemented : " + item.getItemId());
                }
                return true;
            });
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isLogged();
    }

    public void signOutClick(View v) {
        signOut();
    }

    public void deleteAccountClick(View v) {
        confirmDeleteAccount();
    }

    public boolean isLogged() {
        if (user == null) {
            setContentView(R.layout.activity_must_login);
            return false;
        } else
            setContentView(R.layout.activity_profile);
        return true;
    }

    public void confirmDeleteAccount() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

        builder.setMessage("Estas seguro quieres borrar la cuenta, esta accion no podra deshacerse.")
                .setTitle("Borrar Cuenta");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteAccount();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(),
                        "La cuenta no se borrara",
                        Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void deleteAccount() {
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),
                                "La cuenta ha sido borrada",
                                Toast.LENGTH_LONG).show();
                        finish();
                        Intent mainIntent = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(mainIntent);
                    }
                });
    }

    public void signOut() {
        AuthUI.getInstance()
                .signOut(ProfileActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent mainIntent = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                });
    }
    public void loginClick(View v) {
        Intent profileIntent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(profileIntent);
    }
}