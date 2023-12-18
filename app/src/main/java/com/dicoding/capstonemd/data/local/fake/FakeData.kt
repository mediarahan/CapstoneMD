package com.dicoding.capstonemd.data.local.fake

import com.dicoding.capstonemd.R

object FakeData {
    val data = listOf(
        Fake(
            1,
            "Nasi Goreng",
            "Nasi digoreng dengan bumbu rempah khas Indonesia, disajikan dengan telur, daging, dan sayuran.",
            R.drawable.reward_1,
            FakeNutritionData.nutritionData.filter {it.id == 1}),
        Fake(
            2,
            "Sate Ayam",
            "Potongan ayam yang ditusuk dan dipanggang dengan bumbu kacang, menjadi hidangan sate yang lezat.",
            R.drawable.reward_2,
            FakeNutritionData.nutritionData.filter {it.id == 2}),
        Fake(
            3,
            "Rendang",
            "Daging sapi dimasak dalam santan dan rempah-rempah, menghasilkan masakan khas Minang yang gurih dan lezat.",
            R.drawable.reward_3,
            FakeNutritionData.nutritionData.filter {it.id == 3}),
        Fake(
            4,
            "Gado-gado",
            "Sayuran segar disajikan dengan bumbu kacang dan kerupuk, menciptakan salad Indonesia yang lezat.",
            R.drawable.reward_4,
            FakeNutritionData.nutritionData.filter {it.id == 4}),
        Fake(
            5,
            "Nasi Padang",
            "Hidangan nasi beragam dengan berbagai lauk khas Padang, seperti rendang dan sambal lado.",
            R.drawable.reward_5,
            FakeNutritionData.nutritionData.filter {it.id == 5}),
        Fake(
            6,
            "Bakso",
            "Bakso adalah bola daging kenyal yang disajikan dalam kuah kaldu dengan mie dan sayuran.",
            R.drawable.reward_6,
            FakeNutritionData.nutritionData.filter {it.id == 6}),
        Fake(
            7,
            "Soto Ayam",
            "Sup ayam dengan bumbu rempah, disajikan dengan mie, telur, dan bawang goreng.",
            R.drawable.reward_7,
            FakeNutritionData.nutritionData.filter {it.id == 7}),
        Fake(
            8,
            "Martabak",
            "Adonan tebal berisi daging atau cokelat, digoreng hingga renyah, menciptakan camilan lezat.",
            R.drawable.reward_8,
            FakeNutritionData.nutritionData.filter {it.id == 8}),
        Fake(
            9,
            "Pecel Lele",
            "Lele goreng renyah disajikan dengan sambal, nasi, dan lalapan, menciptakan hidangan lele yang nikmat.",
            R.drawable.reward_9,
            FakeNutritionData.nutritionData.filter {it.id == 9}),
        Fake(
            10,
            "Lontong Sayur",
            "Nasi lontong disajikan dengan kuah sayur kacang dan potongan ketupat, menjadi hidangan lezat khas Indonesia.",
            R.drawable.reward_10,
            FakeNutritionData.nutritionData.filter {it.id == 10}),
        Fake(
            11,
            "Soto Betawi",
            "Sup daging sapi khas Betawi dengan santan, kentang, dan tomat, disajikan dengan sambal dan bawang goreng.",
            R.drawable.reward_11,
            FakeNutritionData.nutritionData.filter {it.id == 11}),
        Fake(
            12,
            "Pisang Goreng",
            "Pisang yang digoreng dengan adonan tepung krispi, menciptakan camilan manis yang populer di seluruh Indonesia.",
            R.drawable.reward_12,
            FakeNutritionData.nutritionData.filter {it.id == 12}),
    )

    fun getFakeData(): List<Fake> {
        return data
    }

}