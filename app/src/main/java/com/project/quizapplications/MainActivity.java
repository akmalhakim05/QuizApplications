package com.project.quizapplications;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView questionText, timerText;
    ImageView resultImage;
    Button btnA, btnB, btnC, btnD;
    int currentQuestion = 0, score = 0, totalTime = 0;
    MediaPlayer bgMusic;
    Handler timerHandler = new Handler();
    Runnable timerRunnable;

    String[] questions = {
            "1. What is the capital of France?",
            "2. Which planet is known as the Red Planet?",
            "3. Who is the 10th Prime Minister of Malaysia?",
            "4. Largest ocean?",
            "5. Symbol 'O'?",
            "6. Malaysia national flower?",
            "7. Plants absorb?",
            "8. Great Wall?",
            "9. 6 + 7?",
            "10. Discovered gravity?"
    };

    String[][] choices = {
            {"Paris", "London", "Berlin", "Rome"},
            {"Earth", "Mars", "Jupiter", "Venus"},
            {"Ismail Sabri Yaakob", "Mahathir", "Anwar Ibrahim", "Muhyiddin"},
            {"Atlantic", "Indian", "Pacific", "Arctic"},
            {"Oxygen", "Hydrogen", "Carbon", "Nitrogen"},
            {"Orchid", "Hibiscus", "Jasmine", "Rose"},
            {"Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"},
            {"India", "Japan", "China", "Thailand"},
            {"10", "11", "12", "13"},
            {"Newton", "Einstein", "Tesla", "Darwin"}
    };

    String[] correctAnswers = {
            "Paris", "Mars", "Anwar Ibrahim", "Pacific", "Oxygen",
            "Hibiscus", "Carbon Dioxide", "China", "13", "Newton"
    };

    Integer[] questionImages = {
            R.drawable.question1,
            null,
            R.drawable.question3,
            R.drawable.question4,
            null,
            R.drawable.question6,
            null,
            R.drawable.question8,
            null,
            null
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.questionText);
        resultImage = findViewById(R.id.resultImage);
        timerText = findViewById(R.id.txtTimer);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);

        bgMusic = MediaPlayer.create(this, R.raw.bgmusic);
        bgMusic.setLooping(true);
        bgMusic.start();

        startTimer();
        loadQuestion();

        View.OnClickListener o = v -> checkAnswer(((Button)v).getText().toString(), v);
        btnA.setOnClickListener(o);
        btnB.setOnClickListener(o);
        btnC.setOnClickListener(o);
        btnD.setOnClickListener(o);
    }

    void startTimer() {
        timerRunnable = () -> {
            totalTime++;
            timerText.setText("Time: " + totalTime + "s");
            timerHandler.postDelayed(timerRunnable, 1000);
        };
        timerHandler.post(timerRunnable);
    }

    void loadQuestion() {
        if (currentQuestion >= questions.length) {
            timerHandler.removeCallbacks(timerRunnable);

            Intent i = new Intent(MainActivity.this, ResultActivity.class);
            i.putExtra("score", score);
            i.putExtra("time", totalTime);
            startActivity(i);
            finish();
            return;
        }

        enableButtons(true);
        questionText.setText(questions[currentQuestion]);
        btnA.setText(choices[currentQuestion][0]);
        btnB.setText(choices[currentQuestion][1]);
        btnC.setText(choices[currentQuestion][2]);
        btnD.setText(choices[currentQuestion][3]);

        if (questionImages[currentQuestion] != null) {
            resultImage.setVisibility(View.VISIBLE);
            resultImage.setImageResource(questionImages[currentQuestion]);
        } else {
            resultImage.setVisibility(View.GONE);
        }
    }

    void checkAnswer(String selected, View view) {
        enableButtons(false);
        resultImage.setVisibility(View.VISIBLE);

        if (selected.equals(correctAnswers[currentQuestion])) {
            score++;
            Snackbar.make(view, "✅ Correct!", Snackbar.LENGTH_SHORT).show();
            resultImage.setImageResource(R.drawable.correct); // Make sure you have a 'correct.png' in your drawable folder
        } else {
            Snackbar.make(view, "❌ Wrong!", Snackbar.LENGTH_SHORT).show();
            resultImage.setImageResource(R.drawable.incorrect); // Make sure you have an 'incorrect.png' in your drawable folder
        }

        new Handler().postDelayed(() -> {
            currentQuestion++;
            loadQuestion();
        }, 1000); // 1-second delay
    }

    void enableButtons(boolean enable) {
        btnA.setEnabled(enable);
        btnB.setEnabled(enable);
        btnC.setEnabled(enable);
        btnD.setEnabled(enable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bgMusic != null) {
            bgMusic.stop();
            bgMusic.release();
        }
        timerHandler.removeCallbacks(timerRunnable);
    }
}
