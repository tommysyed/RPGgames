package cam.praveen.rpggames;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GuessTheNumber extends AppCompatActivity {
    int result;
    Button back;

    static int getRandomNumber(int max, int min)
    {
        return (int)((Math.random()
                * (max - min)) + min);
    }

    public void makeToast(String str)
    {
        Toast.makeText(GuessTheNumber.this,
                        str,
                        Toast.LENGTH_SHORT)
                .show();
    }
    public void clickFunction(View view)
    {
        int userGuessing;
        EditText variable
                = (EditText)findViewById(
                R.id.editId);
        userGuessing
                = Integer.parseInt(
                variable
                        .getText()
                        .toString());
        if (userGuessing < result) {

            makeToast("Think of Higher Number,Try Again");
        }
        else if (userGuessing > result) {
            makeToast("Think of Lower Number,Try Again");
        }
        else {
            makeToast(
                    "Congratulations,"
                            +" You Got the Number");
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_number);
        int min = 1;
        int max = 100;
        result = getRandomNumber(min, max);
        back=findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GuessTheNumber.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}