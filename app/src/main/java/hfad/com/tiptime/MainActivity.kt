package hfad.com.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import hfad.com.tiptime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //initialize view binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set the binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // set content view with binding
        setContentView(binding.root)

        // click the calculate button to calculate
        binding.calculateButton.setOnClickListener{ calculateTip()}

        // hide keyboard after pressing enter
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode,_ ->handleKeyEvent(view,keyCode)  }
    }

    // the logic for calculation the tip
    private fun calculateTip(){
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        if (cost == null){

            binding.tipResult.text = ""
            return
        }

        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId){
            R.id.option_tewnty_percent -> 0.20
            R.id.option_eighteen_percnt -> 0.18
            else -> 0.15
        }

        var tip = tipPercentage * cost

        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        val formattedTip = java.text.NumberFormat.getInstance().format(tip)

        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    // hide keyboard
    private fun handleKeyEvent(view: View, keyCode:Int):Boolean{

        if (keyCode == KeyEvent.KEYCODE_ENTER){
            // hide the keyboard
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}