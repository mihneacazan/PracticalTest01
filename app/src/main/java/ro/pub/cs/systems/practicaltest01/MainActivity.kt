package ro.pub.cs.systems.practicaltest01

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ro.pub.cs.systems.eim.practicaltest01.PracticalTest01SecondaryActivity
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    var leftCount: Int = 0
    var rightCount: Int = 0

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            Toast.makeText(this, "Result from secondary activity: OK", Toast.LENGTH_LONG).show()
        } else if (result.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Result from secondary activity: CANCELED", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val leftButton = findViewById<Button>(R.id.left_button)
        val rightButton = findViewById<Button>(R.id.right_button)
        val leftText = findViewById<EditText>(R.id.left_text)
        val rightText = findViewById<EditText>(R.id.right_text)
        val navigateButton: Button = findViewById(R.id.navigate_button)

        navigateButton.setOnClickListener {
            val intent = Intent(this, PracticalTest01SecondaryActivity::class.java)
            val totalClicks = leftCount + rightCount
            intent.putExtra("totalClicks", totalClicks)
            activityResultLauncher.launch(intent)
        }

        if (savedInstanceState != null) {
            leftCount = savedInstanceState.getInt("leftCount")
            rightCount = savedInstanceState.getInt("rightCount")
            leftText.setText(leftCount.toString())
            rightText.setText(rightCount.toString())
        }

        leftButton.setOnClickListener {
            leftCount++
            leftText.setText(leftCount.toString())
        }

        rightButton.setOnClickListener {
            rightCount++
            rightText.setText(rightCount.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("leftCount", leftCount)
        outState.putInt("rightCount", rightCount)
    }

}