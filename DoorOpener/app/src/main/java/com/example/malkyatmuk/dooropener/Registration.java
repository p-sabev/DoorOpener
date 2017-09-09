package com.example.malkyatmuk.dooropener;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.*;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Objects;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import static android.R.attr.editable;
import static com.example.malkyatmuk.dooropener.R.id.btnsignup;
import static com.example.malkyatmuk.dooropener.R.id.pass1;
import static com.example.malkyatmuk.dooropener.R.id.pass2;

public class Registration extends AppCompatActivity {
    EditText pass;
    EditText pass2;
    EditText username;
    EditText email;
    Button btns;
    String txt;
    String txt2;
    View v;
    private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\]) ";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        pass= (EditText) findViewById(R.id.pass1);
        pass2 = (EditText) findViewById(R.id.pass2);
        username=(EditText) findViewById(R.id.username);
        email=(EditText)findViewById(R.id.email);
        btns=(Button) findViewById(R.id.btnsignup);
        pass2.addTextChangedListener(textWatcher);
        pass.addTextChangedListener(textWatcher2);
        email.addTextChangedListener(textWatcheremail);
        //pass2.addTextChangedListener(textWatcher);

        btns.setOnClickListener(btn);

    }


    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    TextWatcher textWatcher2=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            txt = pass.getText().toString();
            txt2 = pass2.getText().toString();

            if (txt2.equals(txt)) {
                pass.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
                pass2.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            }

            if (pass.length() < 5)
                Toast.makeText(getBaseContext(), "Your password is too short!", Toast.LENGTH_SHORT).show();
            pass.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
    };
    TextWatcher textWatcher=new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
             txt = pass.getText().toString();
             txt2 = pass2.getText().toString();


            if (!txt2.equals(txt)) {
                pass.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                pass2.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

            }

        }
        @Override
        public void afterTextChanged(Editable arg0) {
            txt = pass.getText().toString();
            txt2 = pass2.getText().toString();
            if (txt2.equals(txt)) {
                pass.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
                pass2.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            }


            if (!txt2.equals(txt)) {Toast.makeText(getBaseContext(), "The two passwords don't match!", Toast.LENGTH_SHORT).show();
                pass2.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            }
        }



        };
    TextWatcher textWatcheremail=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!validateEmail(email.getText().toString())){Toast.makeText(getBaseContext(), "The email is not valid!", Toast.LENGTH_SHORT).show();
                email.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);}
        }

        @Override
        public void afterTextChanged(Editable editable) {

            if(validateEmail(email.getText().toString())) email.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        }
    };
    public String Hash(EditText password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Random random = new Random();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.getText().toString().toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA2");
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();

        return enc.encodeToString(hash);
    }
    public String Hash2(EditText password) {
    final String hashed = Hashing.sha256()
                .hashString(password.getText().toString(), StandardCharsets.UTF_8)
                .toString();
        return hashed;
    }
    View.OnClickListener btn=new View.OnClickListener() {

        public void onClick(View view) {


          Intent intent = new Intent(view.getContext(), ID_hardware_divice.class).putExtra("passhesh", Hash2(pass));
            startActivity(intent);
        }
    };
}