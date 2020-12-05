package com.ekr.smartlaundry.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.utils.EmailValidator;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ResetPasswordActivity extends AppCompatActivity {

    private Button buttonReset;
    private TextInputEditText editText_Email;
    private SpinKitView spinKitView;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        auth = FirebaseAuth.getInstance();
        buttonReset = findViewById(R.id.btn_submit_reset);
        editText_Email = findViewById(R.id.email_reset);
        spinKitView = findViewById(R.id.spin_kit_reset);

        resetPassword();
    }

    private void resetPassword() {
        buttonReset.setOnClickListener(v -> {
            String email = Objects.requireNonNull(editText_Email.getText()).toString();
            if (email.equals("")) {
                editText_Email.setError("Kolom Tidak Boleh Kosong !");
            } else if (!EmailValidator.validate(email)) {
                editText_Email.setError("Format Email Tidak Valid !");
            } else {
                spinKitView.setVisibility(View.VISIBLE);
                buttonReset.setVisibility(View.GONE);
                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        spinKitView.setVisibility(View.GONE);
                        buttonReset.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),
                                "Berhasil Reset Password, Silahkan Periksa Email Anda !",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).addOnFailureListener(e -> {
                    spinKitView.setVisibility(View.GONE);
                    buttonReset.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                });
            }
        });
    }
}