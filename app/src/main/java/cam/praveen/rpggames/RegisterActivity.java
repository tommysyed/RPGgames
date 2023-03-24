package cam.praveen.rpggames;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    TextView Already;
    EditText username,email,password,confirmpassword;
    Button register;
    String emailpattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseUser Muser;
    FirebaseAuth Mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Already =findViewById(R.id.Already);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmpassword=findViewById(R.id.confirmpassword);
        register =findViewById(R.id.register);
        Mauth=FirebaseAuth.getInstance();
        Muser=Mauth.getCurrentUser();
        progressDialog=new ProgressDialog(this);


       Already.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
               startActivity(intent);
           }
       });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perforAuth();
            }
        });


    }

    private void perforAuth() {
        String name =username.getText().toString();
        String mail = email.getText().toString();
        String pass =password.getText().toString();
        String confirmpass =confirmpassword.getText().toString();


        if(name.isEmpty()){
            username.setError("enter the User name");
        }

        else if(!mail.matches(emailpattern)){
            email.setError("Enter the context Email");
        }
        else if (pass.isEmpty() || pass.length()<6){
            password.setError("Enter the Correct Password");
        }
        else if (!pass.equals(confirmpass)){
            confirmpassword.setError("the password is not matched");
        }else {


            progressDialog.setMessage("please wait while registration");
            progressDialog.setTitle("registraation");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Mauth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(task -> {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "registration is completed", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }}