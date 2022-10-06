package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private ImageView fotoprofil;
    private TextInputEditText inputnama;
    private TextInputEditText inputemail;
    private TextInputEditText inputpassword;
    private TextInputEditText confirm_inputpassword;
    private TextInputEditText inputhomepage;
    private TextInputEditText inputabout;
    private Button btnInput;
    private CircleImageView avatar, change_Avatar;
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private Bitmap bitmap;
    private Uri imgUri = null;
    private boolean change_img = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fotoprofil = (ImageView) findViewById(R.id.imageView);
        inputnama = (TextInputEditText) findViewById(R.id.text_fullname);
        inputemail = (TextInputEditText) findViewById(R.id.text_email);
        inputpassword = (TextInputEditText) findViewById(R.id.text_password);
        confirm_inputpassword = (TextInputEditText) findViewById(R.id.text_confirm_password);
        inputhomepage = (TextInputEditText) findViewById(R.id.text_homepage);
        inputabout = (TextInputEditText) findViewById(R.id.text_about);
        btnInput = findViewById(R.id.button_ok);
        avatar = findViewById(R.id.image_profile);


        fotoprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(RegisterActivity.this, ProfileActivity.class);
                String fullname = inputnama.getText().toString();
                String email = inputemail.getText().toString();
                String password = inputpassword.getText().toString();
                String con_password = confirm_inputpassword.getText().toString();
                String homepage = inputhomepage.getText().toString();
                String about = inputabout.getText().toString();

                if (!change_img) {
                    Toast.makeText(RegisterActivity.this, "Image must be change", Toast.LENGTH_SHORT).show();
                } else if (fullname.isEmpty()) {
                    inputnama.setError("Fullname required");
                } else if (email.isEmpty()) {
                    inputemail.setError("Email required");
                } else if (password.isEmpty()) {
                    inputpassword.setError("Password required");
                } else if (con_password.isEmpty()) {
                    confirm_inputpassword.setError("Confirm Password required");
                } else if (homepage.isEmpty()) {
                    inputhomepage.setError("Homepage required");
                } else if (about.isEmpty()) {
                    inputabout.setError("About required");
                } else if (!password.equals(con_password)) {
                    Toast.makeText(RegisterActivity.this, "Confirm password is not correct", Toast.LENGTH_SHORT).show();
                } else {
                    String image = imgUri.toString();
                    pindah.putExtra("image", image);
                    pindah.putExtra("fullname", fullname);
                    pindah.putExtra("email", email);
                    pindah.putExtra("password", password);
                    pindah.putExtra("con_password", con_password);
                    pindah.putExtra("homepage", homepage);
                    pindah.putExtra("about", about);
                    startActivity(pindah);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Batal mengunggah foto", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (requestCode == GALLERY_REQUEST_CODE) {
                if (data != null) {
                    try {
                        change_img = true;
                        imgUri = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        avatar.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Toast.makeText(this, "Tidak dapat memuat foto", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
    }
}