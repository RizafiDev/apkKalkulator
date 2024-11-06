package com.rizafidev.konvertersuhu


import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    //    inisialisasi variabel
    lateinit var etSuhuAwal: EditText
    lateinit var tvHasilSuhuAkhir: TextView
    lateinit var spSuhuAwal: Spinner
    lateinit var spSuhuAkhir: Spinner
    lateinit var btKonversi: Button
    lateinit var btBersihkan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()

        btKonversi.setOnClickListener {
            konversiSuhu()
        }

        btBersihkan.setOnClickListener {
            bersihkanForm()
        }
    }

    fun init() {
        // mencocokkan variabel dengan komponen di layout
        etSuhuAwal = findViewById(R.id.etSuhuAwal)
        tvHasilSuhuAkhir = findViewById(R.id.tvHasilSuhuAkhir)
        spSuhuAwal = findViewById(R.id.spSuhuAwal)
        spSuhuAkhir = findViewById(R.id.spSuhuAkhir)
        btKonversi = findViewById(R.id.btKonversi)
        btBersihkan = findViewById(R.id.btBersihkan)
    }

    fun konversiSuhu() {
        val decimalFormat = DecimalFormat("#.####")
        val suhuAwal = etSuhuAwal.text.toString().toDoubleOrNull() ?: return
        val suhuAwalString = spSuhuAwal.selectedItem.toString()
        val suhuAkhirString = spSuhuAkhir.selectedItem.toString()

        var hasil: Double = 0.0
        when (suhuAwalString) {
            "Celcius" -> {
                hasil = when (suhuAkhirString) {
                    "Fahrenheit" -> (suhuAwal * 9 / 5) + 32
                    "Kelvin" -> suhuAwal + 273.15
                    else -> suhuAwal
                }
            }
            "Fahrenheit" -> {
                hasil = when (suhuAkhirString) {
                    "Celcius" -> (suhuAwal - 32) * 5 / 9
                    "Kelvin" -> (suhuAwal + 459.67) * 5 / 9
                    else -> suhuAwal
                }
            }
            "Kelvin" -> {
                hasil = when (suhuAkhirString) {
                    "Celcius" -> suhuAwal - 273.15
                    "Fahrenheit" -> (suhuAwal * 9 / 5) - 459.67
                    else -> suhuAwal
                }
            }
        }

        val hasilTerformat = decimalFormat.format(hasil)
        tvHasilSuhuAkhir.text = hasilTerformat
    }

    fun bersihkanForm() {
        etSuhuAwal.text.clear()  // Menghapus input suhu awal
        spSuhuAwal.setSelection(0)  // Reset spinner suhu awal ke pilihan pertama
        spSuhuAkhir.setSelection(0)  // Reset spinner suhu akhir ke pilihan pertama
        tvHasilSuhuAkhir.text = "-"  // Reset hasil suhu akhir
    }
}