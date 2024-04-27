package com.example.githubuserapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.githubuserapp.data.response.DetailResponse;
import com.example.githubuserapp.data.response.ItemsItem;
import com.example.githubuserapp.data.retrofit.ApiConfig;
import com.example.githubuserapp.data.retrofit.ApiService;
import com.example.githubuserapp.databinding.ActivityDetailBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_USERNAME = "username"; // Kirim username saja

    private ActivityDetailBinding binding;
    private DetailResponse user;
    private boolean isLoading;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Dapatkan username dari intent
        String username = getIntent().getStringExtra(EXTRA_USERNAME);
        System.out.println("ini "+ username);

        if (username != null) {
            ApiService apiService = ApiConfig.getApiService();
            Call<DetailResponse> call = apiService.getUserDetail(username);

            call.enqueue(new Callback<DetailResponse>() {
                @Override
                public void onResponse(@NonNull Call<DetailResponse> call, @NonNull Response<DetailResponse> response) {
                    if (response.isSuccessful()) {
//                        showLoading(false);
                        DetailResponse user = response.body();
                        if (user != null) {
                            binding.tvDetailName.setText(user.getLogin());
                            binding.tvBio.setText(user.getBio()); // Set the bio text
                            Glide.with(DetailActivity.this)
                                    .load(user.getAvatarUrl())
                                    .into(binding.ivDetailFotoItem);
                        } else {
                            // Handle user data not found
                            Toast.makeText(DetailActivity.this, "Failed to get user data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DetailResponse> call, @NonNull Throwable t) {
                    Toast.makeText(DetailActivity.this, "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void showLoading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }
}