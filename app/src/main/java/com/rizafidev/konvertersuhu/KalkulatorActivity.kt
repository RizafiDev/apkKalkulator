package com.rizafidev.konvertersuhu

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView

class KalkulatorActivity : AppCompatActivity() {
    private lateinit var tvHasil: TextView
    private var currentInput = StringBuilder()
    private var result: String = ""
    private var lastOperator: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kalkulator)

        tvHasil = findViewById(R.id.tvHasil)
        //deklarasi variabel dengan elemen
        findViewById<Button>(R.id.Btn0).setOnClickListener { appendToInput("0") }
        findViewById<Button>(R.id.Btn1).setOnClickListener { appendToInput("1") }
        findViewById<Button>(R.id.Btn2).setOnClickListener { appendToInput("2") }
        findViewById<Button>(R.id.Btn3).setOnClickListener { appendToInput("3") }
        findViewById<Button>(R.id.Btn4).setOnClickListener { appendToInput("4") }
        findViewById<Button>(R.id.Btn5).setOnClickListener { appendToInput("5") }
        findViewById<Button>(R.id.Btn6).setOnClickListener { appendToInput("6") }
        findViewById<Button>(R.id.Btn7).setOnClickListener { appendToInput("7") }
        findViewById<Button>(R.id.Btn8).setOnClickListener { appendToInput("8") }
        findViewById<Button>(R.id.Btn9).setOnClickListener { appendToInput("9") }
        findViewById<Button>(R.id.dotBtn).setOnClickListener { appendToInput(".") }
        findViewById<Button>(R.id.plusBtn).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.lessBtn).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.multipleBtn).setOnClickListener { setOperator("X") }
        findViewById<Button>(R.id.devideBtn).setOnClickListener { setOperator("/") }
        findViewById<Button>(R.id.clearBtn).setOnClickListener { clearInput() }
        findViewById<Button>(R.id.percentBtn).setOnClickListener { setOperator("%") }
        findViewById<Button>(R.id.backspaceBtn).setOnClickListener { backspace() }
        findViewById<Button>(R.id.hasilBtn).setOnClickListener { calculateResult() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

//memasukkan input ke tvHasil
    private fun appendToInput(value: String) {
        currentInput.append(value)
        tvHasil.text = currentInput.toString()
    }

    private fun setOperator(operator: String) {
        if (currentInput.isNotEmpty()) {
            if (lastOperator != null) {
                calculateResult()
            }
            result = currentInput.toString()
            currentInput.clear()
            currentInput.append(result).append(" $operator ")
            tvHasil.text = currentInput.toString()
            lastOperator = operator
        }
    }

//    hitung
    private fun calculateResult() {
        if (currentInput.isNotEmpty() && result.isNotEmpty() && lastOperator != null) {
            val input = currentInput.toString().split(" $lastOperator ").last().toDouble()
            var calculationResult: Double = 0.0

            when (lastOperator) {
                "+" -> calculationResult = result.toDouble() + input
                "-" -> calculationResult = result.toDouble() - input
                "X" -> calculationResult = result.toDouble() * input
                "/" -> if (input != 0.0) calculationResult = result.toDouble() / input else {
                    tvHasil.text = "Error"
                    return
                }
                "%" -> calculationResult = result.toDouble() * (input / 100)
            }

            // Menyimpan hasil sebagai string, hanya tampilkan desimal jika perlu
            result = if (calculationResult % 1.0 == 0.0) {
                calculationResult.toInt().toString()  // Jika hasil bulat, konversi ke integer
            } else {
                calculationResult.toString()  // Jika ada desimal, tampilkan desimal
            }

            currentInput.clear()
            tvHasil.text = result
            lastOperator = null
        }
    }

//    clear tvHasil
    private fun clearInput() {
        currentInput.clear()
        result = ""
        lastOperator = null
        tvHasil.text = ""
    }

//hapus perkarakter/backspace
    private fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput.deleteCharAt(currentInput.length - 1)
            tvHasil.text = currentInput.toString()
        }
    }

}