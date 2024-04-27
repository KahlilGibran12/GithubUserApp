package com.example.githubuserapp.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubuserapp.R;
import com.example.githubuserapp.data.response.ItemsItem;
import com.example.githubuserapp.data.response.UsernewResponse;
import com.example.githubuserapp.data.retrofit.ApiConfig;
import com.example.githubuserapp.data.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvUsers;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvUsers = findViewById(R.id.rv_users);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        // Set an empty adapter initially


        ApiService apiService = ApiConfig.getApiService();
        Call<UsernewResponse> call = apiService.getUsers("Gibran"); // replace with the desired username

        call.enqueue(new Callback<UsernewResponse>() {
            @Override
            public void onResponse(@NonNull Call<UsernewResponse> call, @NonNull Response<UsernewResponse> response) {
                if (response.isSuccessful()) {
                    List<ItemsItem> users = response.body().getItems();
                    //List<User> users = Objects.requireNonNull(response.body()).getItems();
                    //Log.d("data",users.toString());
                    if (users != null) {
                        Toast.makeText(MainActivity.this, users.get(0).getLogin(), Toast.LENGTH_SHORT).show();
                        adapter = new ListAdapter(new ArrayList<ItemsItem>());
                        adapter.setUsers(users);
                        rvUsers.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this, "Data is empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Gagal mendapatkan data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UsernewResponse> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Koneksi gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}