package com.example.attractions.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.attractions.data.model.Attraction
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class MapViewModel @Inject constructor() : ViewModel() {

    // Пришлось обойтись "ХардКодом" в этом месте, так как катастрофически
    // не хватает времени на реализацию репозитория.

    private val vologdaKremlin = Attraction(
        "Вологодский Кремль",
        "Часть государственного архитектурного заповедника, " +
                "признанного памятником культуры федерального значения. ",
        Point(59.223614, 39.882158)
    )

    private val stSophiaCathedral = Attraction(
        "Софийский собор",
        "Собор Святой Софии — древнейшее из уцелевших до нашего времени " +
                "каменных сооружений в городе. Он был заложен в 1568 году по приказу " +
                "Ивана Грозного.",
        Point(59.224402, 39.882413)
    )

    private val laceMuseum = Attraction(
        "Музей Кружева",
        "Значительная часть выставки посвящена зарождению и развитию кружевного " +
                "искусства с XIX по XXI вв. Основа коллекции — уникальные предметы из вологодских " +
                "музеев и заповедников.",
        Point(59.223885, 39.884772)
    )

    private val listAttraction = mutableListOf<Attraction>(
        vologdaKremlin,
        stSophiaCathedral,
        laceMuseum
    )

    val listAttractions = listOf(listAttraction).asFlow()

}