package com.example.happyplaces

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.example.happyplaces.R.*
import java.util.Locale


class AddHappyPlace : AppCompatActivity(), View.OnClickListener {
    private var etDate: EditText? = null
    private var cal = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private var toolbarAddPlace: Toolbar? = null
    private var tvAddImage: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_add_happy_place)

        toolbarAddPlace = findViewById(id.toolbar_add_place)
        etDate = findViewById(id.et_date)
        tvAddImage = findViewById(id.tv_add_image)


        dateSetListener()
        setToolBar()
        etDate?.setOnClickListener(this)
        tvAddImage?.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            id.et_date -> {
                DatePickerDialog(
                    this@AddHappyPlace,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            id.tv_add_image -> {
                val pictureDialog = AlertDialog.Builder(this)
                pictureDialog.setTitle("Select Action")
                val pictureDialogItems =
                    arrayOf("Select photo from gallery", "Capture photo from camera")
                pictureDialog.setItems(
                    pictureDialogItems
                ) { _, which ->
                    when (which) {
                        // Here we have create the methods for image selection from GALLERY
                        0 -> choosePhotoFromGallery()
                        1 -> Toast.makeText(
                            this@AddHappyPlace,
                            "Camera selection coming soon...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                pictureDialog.show()
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Activity.RESULT_OK) {
            if (resultCode == Gallary) {
                if (data != null) {

                }
            }

        } else {

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            Log.d("", "")
        }
    }

    private fun dateSetListener() {
        dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                updateDateInView()
            }
    }

    private fun setToolBar() {
        setSupportActionBar(toolbarAddPlace)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarAddPlace?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault()) // A date format
        etDate?.setText(sdf.format(cal.time).toString())
    }

    private fun checkPermission(): Boolean {
        val resultW = ActivityCompat.checkSelfPermission(
            this,
            WRITE_EXTERNAL_STORAGE
        )
        val resultR = ActivityCompat.checkSelfPermission(
            this,
            READ_EXTERNAL_STORAGE
        )
        val resultC = ActivityCompat.checkSelfPermission(
            this,
            CAMERA
        )


        return resultW == PackageManager.PERMISSION_GRANTED && resultR == PackageManager.PERMISSION_GRANTED && resultC == PackageManager.PERMISSION_GRANTED
    }

    private fun takePermission() {

        if (checkPermission()) {
            Toast.makeText(this, "Permission given already", Toast.LENGTH_SHORT).show()
        } else {

            var arrData = mutableListOf<String>()
            if (ActivityCompat.checkSelfPermission(

                    this,
                    WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                arrData.add("android.permission.READ_EXTERNAL_STORAGE   ")
            }
            if (ActivityCompat.checkSelfPermission(
                    this,
                    READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                arrData.add("android.permission.WRITE_EXTERNAL_STORAGE")
            }
            if (ActivityCompat.checkSelfPermission(
                    this,
                    CAMERA
                ) == PackageManager.PERMISSION_DENIED
            ) {
                arrData.add("android.permission.CAMERA")
            }

            if (arrData.isNotEmpty()) {
                ActivityCompat.requestPermissions(this, arrData.toTypedArray(), 0)
            }
        }
    }

    private fun choosePhotoFromGallery() {
        takePermission()
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, Gallary)

    }

    companion object {
        private const val Gallary = 1
        private const val Camera = 2

    }

}
