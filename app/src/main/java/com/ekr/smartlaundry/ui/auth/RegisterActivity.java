package com.ekr.smartlaundry.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.data.UserModel;
import com.ekr.smartlaundry.ui.main.MainActivity;
import com.ekr.smartlaundry.utils.EmailValidator;
import com.ekr.smartlaundry.utils.Session;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.ekr.smartlaundry.utils.GlobalPath.DB_USER;

public class RegisterActivity extends AppCompatActivity {

    private Session session;
    private FirebaseAuth auth;
    private TextInputEditText tie_nama, tie_email, tie_alamat, tie_nohp, tie_password;
    private TextView textViewLogin;
    private Button btnRegister;
    private SpinKitView spinKitViewRegister;
    private DatabaseReference database;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        identify(); //memberikan identitas pada variable
        onLoading(false); //loading ketika submit
        doRegister(); // function register

    }


    private void identify() {
        session = new Session(this);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference(DB_USER);
        linearLayout = findViewById(R.id.ll_tv_regis);
        tie_nama = findViewById(R.id.nama_register);
        tie_email = findViewById(R.id.email_register);
        tie_alamat = findViewById(R.id.alamat_register);
        tie_nohp = findViewById(R.id.nohp_register);
        tie_password = findViewById(R.id.password_register);
        textViewLogin = findViewById(R.id.tv_to_login);
        btnRegister = findViewById(R.id.btn_submit_register);
        spinKitViewRegister = findViewById(R.id.spin_kit_register);
        textViewLogin.setOnClickListener(v -> finish());

    }

    private void doRegister() {
        btnRegister.setOnClickListener(view -> {
            String nama = tie_nama.getText().toString();
            String email = tie_email.getText().toString();
            String alamat = tie_alamat.getText().toString();
            String nohp = tie_nohp.getText().toString();
            String password = tie_password.getText().toString();
            if (nama.equals("") ||
                    email.equals("") ||
                    alamat.equals("") ||
                    nohp.equals("") ||
                    password.equals("")
            ) {
                Toast.makeText(getApplicationContext(),
                        "Lengkapi Kolom Pengisian", Toast.LENGTH_LONG).show();
            } else if (!EmailValidator.validate(email)) {
                tie_email.setError("Format Email Salah !");
            } else if (password.length() < 6) {
                tie_password.setError("Input Passwrod Minimal 6 Karakter");
            } else {
                onLoading(true);
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                        task -> {
                            if (task.isSuccessful()) {
                                String uuid = task.getResult().getUser().getUid();
                                session.saveSPString(Session.SP_UID, uuid);
                                UserModel model = new UserModel();
                                model.setAlamat(alamat);
                                model.setEmail(email);
                                model.setNohp(nohp);
                                model.setNama(nama);
                                model.setRule("user");
                                model.setUuid(uuid);
                                database.push().setValue(model).addOnCompleteListener(task1 -> {
                                    onLoading(false);
                                    Toast.makeText(getApplicationContext(),
                                            "Pendaftaran Berhasil, Selamat !", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(this, MainActivity.class));
                                    finish();
                                });
                            }
                        }).addOnFailureListener(e -> {
                    onLoading(false);
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void onLoading(boolean loading) {
        if (loading) {
            spinKitViewRegister.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
        } else {
            spinKitViewRegister.setVisibility(View.GONE);
            btnRegister.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
        }
    }
}