package com.example.hw2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;

import com.bumptech.glide.Glide;
import com.example.hw2.user.UserData;
import com.example.hw2.user.UserResponse;
import com.example.hw2.user.UserService;
import com.example.hw2.user.UserAPIClient;
import com.example.hw2.usersDB.User;
import com.example.hw2.usersDB.UserDatabase;

public class MainActivity extends AppCompatActivity {
    ImageView image;

    TextView firstname;
    TextView lastname;
    TextView age;
    TextView email;
    TextView city;
    TextView country;

    Button nextUser;
    Button addUser;
    Button viewCollection;

    User user;

    UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userDatabase = UserDatabase.getInstance(this);

        image = findViewById(R.id.image);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);

        nextUser = findViewById(R.id.nextUser);
        addUser = findViewById(R.id.addUser);
        viewCollection = findViewById(R.id.viewCollection);

        nextUser.setOnClickListener(v -> getUserData());
        addUser.setOnClickListener(v -> {
            if (user == null) {
                Toast.makeText(this, "Cannot add user right now", Toast.LENGTH_SHORT).show();
                return;
            }

            ChangeButtonsState(false);
            try{
                userDatabase.userDao().insertUser(user);
                Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
            }
            finally {
                ChangeButtonsState(true);
            }
        });

        getUserData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserData();
    }

    private void getUserData() {
        Retrofit retrofit = UserAPIClient.getClient();
        UserService service = retrofit.create(UserService.class);
        Call<UserResponse> callAsync = service.getUser(null, null, null);

        ChangeButtonsState(false);

        callAsync.enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                UserResponse userResponse = response.body();

                if (userResponse == null) {
                    ChangeButtonsState(true);
                    SetErrorUserData();
                    return;
                }

                UserData userData = userResponse.results.get(0);
                SetUserData(userData);
                ChangeButtonsState(true);
            }
            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable throwable) {
                SetErrorUserData();
                ChangeButtonsState(true);
            }
        });

        viewCollection.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UsersActivity.class);
            startActivity(intent);
        });
    }

    private void SetUserData(UserData user) {
        firstname.setText(user.getFirstName());
        lastname.setText(user.getLastName());
        age.setText(String.valueOf(user.getAge()));
        email.setText(user.getEmail());
        city.setText(user.getCity());
        country.setText(user.getCountry());

        Glide.with(this).load(user.getImage()).into(image);

        this.user = new User(user);
    }

    private void SetErrorUserData() {
        firstname.setText(R.string.error);
        lastname.setText(R.string.error);
        age.setText(R.string.error);
        email.setText(R.string.error);
        city.setText(R.string.error);
        country.setText(R.string.error);

        image.setImageResource(R.drawable.id_card);

        this.user = null;
    }

    private void ChangeButtonsState(boolean state) {
        nextUser.setEnabled(state);
        addUser.setEnabled(state);
        viewCollection.setEnabled(state);
    }
}