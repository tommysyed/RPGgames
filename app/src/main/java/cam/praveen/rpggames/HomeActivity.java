package cam.praveen.rpggames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeActivity extends AppCompatActivity {

    Button guess ,swipeme ,Signout, truth;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        swipeme=findViewById(R.id.swipeme);
        guess=findViewById(R.id.guess_the_number);
truth=findViewById(R.id.stonepaperscissor);
        Signout=findViewById(R.id.signOut);
        googleSignInOptions =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);
truth.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(HomeActivity.this,BottleSpin.class);
        startActivity(intent);
    }
});

        guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,GuessTheNumber.class);
                startActivity(intent);
            }
        });
        swipeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,SwipeMeActivity.class);
                startActivity(intent);
            }
        });
        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();

            }
        });

        }

    private void signout() {
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Toast.makeText(HomeActivity.this,"signout successfully",Toast.LENGTH_SHORT).show();

            }
        });
    }
}