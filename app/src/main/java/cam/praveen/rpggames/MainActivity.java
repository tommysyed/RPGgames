package cam.praveen.rpggames;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public static String PREFS_NAME ="My PrefsFile" ;
    EditText Email,password;
    TextView create ;
    Button button;
    String emailpattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseUser Muser;
    FirebaseAuth Mauth;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create = findViewById(R.id.create);
        Email=findViewById(R.id.Email);
        password=findViewById(R.id.password);
        button=findViewById(R.id.button);
         Button  butt =findViewById(R.id.Google);
        Mauth=FirebaseAuth.getInstance();
        Muser=Mauth.getCurrentUser();

        googleSignInOptions =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);

        progressDialog=new ProgressDialog(this);


        final SharedPreferences sharedpre = getSharedPreferences("Data", Context.MODE_PRIVATE);
        final String type =sharedpre.getString("Email","");
        if(type.isEmpty()){
            Toast.makeText(MainActivity.this,"please login",Toast.LENGTH_SHORT).show();

        }
        else{
            Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor =sharedpre.edit();
                editor.putString("Email",Email.getText().toString());
                editor.putString("password",password.getText().toString());
                editor.commit();

                performlogin();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });



        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Google();
            }
        });
    }

    private void Google() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
        super.onActivityResult(resultcode,resultcode,data);
        if(requestcode ==1000){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                signSuccesfully();
            } catch (ApiException e) {
               Toast.makeText(getApplicationContext(),"Something went worng",Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void signSuccesfully() {
        finish();
        Intent intent =new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    public void performlogin() {
        String mail =Email.getText().toString();
        String pass=password.getText().toString();

        if(pass.matches(emailpattern)){
            Email.setError("enter the correct");
        }
        else if (pass.isEmpty() || pass.length()<6){
            password.setError("Enter the Correct Password");
        }

        else {


            progressDialog.setMessage("please wait while login");
            progressDialog.setTitle("login");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Mauth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(task -> {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    sendusertonextActivity();
                    Toast.makeText(MainActivity.this, "login is completed", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void sendusertonextActivity() {
        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}