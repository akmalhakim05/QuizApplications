package com.project.quizapplications;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView questionText;
    ImageView resultImage;
    Button btnA, btnB, btnC, btnD;
    int currentQuestion = 0;
    int score = 0;
    MediaPlayer bgMusic;

    String[] questions = {
            "1. What is the capital of France?",
            "2. Which planet is known as the Red Planet?",
            "3. Who is the 10th Prime Minister of Malaysia?",
            "4. What is the largest ocean on Earth?",
            "5. Which element has the symbol 'O'?",
            "6. What is the national flower of Malaysia?",
            "7. What gas do plants absorb from the atmosphere?",
            "8. Which country is famous for the Great Wall?",
            "9. What is 6 + 7?",
            "10. Who discovered gravity?"
    };

    String[][] choices = {
            {"Paris", "London", "Berlin", "Rome"},
            {"Earth", "Mars", "Jupiter", "Venus"},
            {"Ismail Sabri Yaakob", "Tun Dr. Mahathir Mohamd", "Anwar Ibrahim", "Muhyiddin Yassin"},
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        questionText = findViewById(R.id.questionText);
        resultImage = findViewById(R.id.resultImage);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);

        // Music in loop
        bgMusic = MediaPlayer.create(this, R.raw.bgmusic);
        bgMusic.setLooping(true);
        bgMusic.start();

        loadQuestion();

        View.OnClickListener listener = v -> {
            Button clicked = (Button) v;
            checkAnswer(clicked.getText().toString(), v);
        };

        btnA.setOnClickListener(listener);
        btnB.setOnClickListener(listener);
        btnC.setOnClickListener(listener);
        btnD.setOnClickListener(listener);
    }

    void loadQuestion() {
        if (currentQuestion < questions.length) {
            questionText.setText(questions[currentQuestion]);
            btnA.setText(choices[currentQuestion][0]);
            btnB.setText(choices[currentQuestion][1]);
            btnC.setText(choices[currentQuestion][2]);
            btnD.setText(choices[currentQuestion][3]);
            resultImage.setImageResource(R.drawable.questionmark);
        } else {
            questionText.setText("🎉 Quiz Finished! Your score: " + score + "/10 🎉");
            btnA.setVisibility(View.GONE);
            btnB.setVisibility(View.GONE);
            btnC.setVisibility(View.GONE);
            btnD.setVisibility(View.GONE);
            resultImage.setImageResource(R.drawable.trophy);
        }
    }

    void checkAnswer(String selected, View view) {
        if (selected.equals(correctAnswers[currentQuestion])) {
            score++;
            resultImage.setImageResource(R.drawable.correct);
            Snackbar.make(view, "✅ Correct Answer!", Snackbar.LENGTH_SHORT).show();
        } else {
            resultImage.setImageResource(R.drawable.incorrect);
            Snackbar.make(view, "❌ Wrong Answer!", Snackbar.LENGTH_SHORT).show();
        }
        currentQuestion++;
        loadQuestion();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bgMusic != null) {
            bgMusic.stop();
            bgMusic.release();
        }
    }
}