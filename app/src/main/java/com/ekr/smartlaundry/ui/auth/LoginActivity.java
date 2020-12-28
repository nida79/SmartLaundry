package com.ekr.smartlaundry.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.ui.main.MainActivity;
import com.ekr.smartlaundry.utils.AlertHelper;
import com.ekr.smartlaundry.utils.EmailValidator;
import com.ekr.smartlaundry.utils.Session;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shashank.platform.fancyflashbarlib.Flashbar;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private Session session;
    private TextView textView_Reset,textView_belum;
    private Button buttonLogin, buttonRegister;
    private TextInputEditText inputEditTextEmail, inputEditTextPassword;
    private SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initIdentity();
        loadingLogin(false);
        cekLogin();
        doLogin();

    }

    private void initIdentity() {
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        session = new Session(this);
        textView_Reset = findViewById(R.id.tv_reset);
        textView_belum = findViewById(R.id.tv_belum);
        buttonLogin = findViewById(R.id.btn_submit_login);
        buttonRegister = findViewById(R.id.btn_register);
        inputEditTextEmail = findViewById(R.id.email_login);
        inputEditTextPassword = findViewById(R.id.passowrd_login);
        spinKitView = findViewById(R.id.spin_kit_login);
        textView_Reset.setOnClickListener(view ->
                startActivity(new Intent(this, ResetPasswordActivity.class)));
        buttonRegister.setOnClickListener(view ->
                startActivity(new Intent(this,RegisterActivity.class)));
    }

    private void cekLogin() {
        currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            goToMainMenu(currentUser);
        }
    }

    private void doLogin() {
        buttonLogin.setOnClickListener(v -> {
            String email, password;
            email = Objects.requireNonNull(inputEditTextEmail.getText()).toString();
            password = Objects.requireNonNull(inputEditTextPassword.getText()).toString();
            if (email.equals("") || password.equals("")) {
               Toast.makeText(getApplicationContext(),"Lengkapi Kolom Pengisian",Toast.LENGTH_LONG).show();
            } else if (!EmailValidator.validate(email)) {
                inputEditTextEmail.setError("Format Email Salah");
            } else if (password.length() < 6) {
                inputEditTextPassword.setError("Minimum Password 6 Karakter");
            } else {
                loadingLogin(true);
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, task -> {
                            if (task.isSuccessful()) {
                                loadingLogin(false);
                                Toast.makeText(getApplicationContext(),"Login Berhasil",Toast.LENGTH_LONG).show();
                                FirebaseUser user = auth.getCurrentUser();
                                goToMainMenu(user);
                            } else { //jika tidak berhasil
                                loadingLogin(false);
                                Toast.makeText(LoginActivity.this,
                                        "Login Gagal, Data Tidak Sesuai", Toast.LENGTH_SHORT).show();

                            }
                        });
            }

        });
    }

    private void loadingLogin(Boolean loading) {
        if (loading) {
            spinKitView.setVisibility(View.VISIBLE);
            buttonLogin.setVisibility(View.GONE);
            buttonRegister.setVisibility(View.GONE);
            textView_belum.setVisibility(View.GONE);
        } else {
            spinKitView.setVisibility(View.GONE);
            buttonLogin.setVisibility(View.VISIBLE);
            textView_belum.setVisibility(View.VISIBLE);
            buttonRegister.setVisibility(View.VISIBLE);
        }
    }

    private void goToMainMenu(FirebaseUser currentUser) {
        if (currentUser!=null){
            String a = currentUser.getUid();
            session.saveSPString(Session.SP_UID,a);
            startActivity(new Intent(this,MainActivity.class));
            finishAffinity();
            finish();
        }else {
            Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_LONG).show();
        }

    }


}