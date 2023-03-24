package cam.praveen.rpggames;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SwipeMeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    // Creating a ArrayList of type Modelclass
    private List<Modelclass> barsColor;

    // Alert dialog
    AlertDialog.Builder alertDialog;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_me);
        barsColor=new ArrayList<>();
        Random random = new Random();
        // Add 15 bars to the RecyclerView
        for(int i=0;i<15;i++)
        {
            // Generate a random number
            int n= random.nextInt(2);
            // Giving the color for the
            // bar based on the random number
            if(n==0)
            {
                barsColor.add(new Modelclass("Yellow"));
            }
            else
            {
                barsColor.add(new Modelclass("Red"));
            }
        }

        // Finding the RecyclerView by it's ID
        recyclerView = findViewById(R.id.recyclerview);

        // Creating an Adapter Object
        adapter=new Adapter(this,barsColor);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add ItemTouchHelper to the recyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.notifyDataSetChanged();
    }

    ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder, @NotNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NotNull RecyclerView.ViewHolder viewHolder, int direction) {
            // get the position of the swiped bar
            int position = viewHolder.getPosition();
            switch (direction) {
                // Right side is for Yellow
                case ItemTouchHelper.LEFT: {

                        if ((barsColor.get(position).getColor()).equals("Red")) {
                            barsColor.remove(position);
                            adapter.notifyDataSetChanged();
                        } else {
                            endthegame();
                            adapter.notifyDataSetChanged();
                            alertDialog.show();
                        }

                    break;
                }
                // Left side is for Red
                case ItemTouchHelper.RIGHT: {
                    if ((barsColor.get(position).getColor()).equals("Yellow")) {
                        barsColor.remove(position);
                        adapter.notifyDataSetChanged();
                    } else {
                        endthegame();
                        adapter.notifyDataSetChanged();
                        alertDialog.show();
                    }
                    break;
                }
            }
        }
    };

    // Shows game ended dialog
    private void endthegame()
    {
        alertDialog=new AlertDialog.Builder(this);
        alertDialog.setMessage("Oopa! Wrong side! Try Again! ").setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SwipeMeActivity.this, "Try again", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SwipeMeActivity.this, "Later!", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.create();
    }
}