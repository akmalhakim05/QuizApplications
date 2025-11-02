package com.project.quizapplications;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity; // Import AppCompatActivity

public class ResultActivity extends AppCompatActivity { // Change to extend AppCompatActivity

    TextView scoreText, timeText;
    Button replayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize views from the layout file
        scoreText = findViewById(R.id.txtScore);
        timeText = findViewById(R.id.txtTime);
        replayButton = findViewById(R.id.btnReplay);

        // Get the data (score and time) passed from MainActivity
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int totalTime = intent.getIntExtra("time", 0);
        int totalQuestions = 10; // Assuming there are 10 questions as per your MainActivity

        // Set the text for score and time
        scoreText.setText("Your Score: " + score + "/" + totalQuestions);
        timeText.setText("Total Time: " + totalTime + " seconds");

        // Set an OnClickListener for the replay button
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to go back to MainActivity
                Intent replayIntent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(replayIntent);
                finish(); // Finish this activity so the user can't go back to it
            }
        });
    }
}

