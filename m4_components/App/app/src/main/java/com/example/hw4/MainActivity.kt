package com.example.hw4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.hw4.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivityState()
    }

    private fun checkConditions(): Boolean {
        // Проверка длины имени
        val conditionNameField = binding.textInputName.length() in 1..40
        // Проверка наличия текста в поле телефона
        val conditionPhoneField = binding.textInputPhone.text.toString().isNotBlank() &&
                binding.textInputPhone.toString().isNotEmpty()
        // Проверка выбора пола
        val conditionSexIsChecked = binding.radioButtonMale.isChecked ||
                binding.radioButtonFemale.isChecked
        // Проверка состояния переключателя уведомлений
        val conditionNotificationSwitch = (binding.switchNotification.isChecked &&
                binding.checkBoxAuthorization.isChecked || binding.checkBoxNewItems.isChecked)
                || !binding.switchNotification.isChecked
        // Возвращение результата проверки всех условий
        return conditionSexIsChecked && conditionNameField
                && conditionPhoneField && conditionNotificationSwitch
    }

    // Метод для изменения состояния кнопки "Сохранить"
    private fun changeSaveButtonState() {
        // Если все условия выполнены, кнопка становится активной
        if (checkConditions()) {
            binding.buttonSave.isEnabled = true
        } else {
            // В противном случае кнопка остается неактивной
            binding.buttonSave.isEnabled = false
        }
    }

    // Метод для инициализации состояния активности
    private fun initActivityState() {
        // Генерация случайного числа очков
        val numberOfPoints = Random.nextInt(101)
        // Установка вторичного прогресса для прогресс-бара
        binding.progressBar.secondaryProgress = numberOfPoints
        // Установка текста для отображения числа очков
        binding.textViewPointsOfNumber.text =
            String.format(getString(R.string.scores_of_number, numberOfPoints))
        // Установка обработчиков событий для различных элементов пользовательского интерфейса
        binding.checkBoxNewItems.setOnCheckedChangeListener { _, _ ->
            changeSaveButtonState()
        }
        binding.checkBoxAuthorization.setOnCheckedChangeListener { _, _ ->
            changeSaveButtonState()
        }
        binding.textInputName.doAfterTextChanged {
            changeSaveButtonState()
        }
        binding.textInputPhone.doAfterTextChanged {
            changeSaveButtonState()
        }
        binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkBoxAuthorization.isEnabled = true
                binding.checkBoxNewItems.isEnabled = true
                changeSaveButtonState()
            } else {
                binding.checkBoxAuthorization.isEnabled = !binding.checkBoxAuthorization.isEnabled
                binding.checkBoxNewItems.isEnabled = !binding.checkBoxNewItems.isEnabled
                binding.checkBoxAuthorization.isChecked = false
                binding.checkBoxNewItems.isChecked = false
                changeSaveButtonState()
            }
        }
        binding.radioGroupForChoiceSex.setOnCheckedChangeListener { _, _ ->
            changeSaveButtonState()
        }
        // Установка обработчика нажатия для кнопки "Сохранить"
        binding.buttonSave.setOnClickListener {
            Toast.makeText(this, R.string.toast_text, Toast.LENGTH_SHORT).show()
        }
    }
}