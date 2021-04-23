package com.reverb.android.onsite

import IndexFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.reverb.android.onsite.R
import com.reverb.android.onsite.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding : MainActivityBinding

  override fun onCreate(savedInstanceState : Bundle?) {
    super.onCreate(savedInstanceState)
    binding = MainActivityBinding.inflate(LayoutInflater.from(this))
    setContentView(binding.root)
    if (savedInstanceState == null) {
      supportFragmentManager
        .beginTransaction()
        .add(R.id.fragment_container, IndexFragment())
        .commit()
    }
  }
}
