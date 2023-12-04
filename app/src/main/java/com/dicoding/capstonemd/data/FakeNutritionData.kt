package com.dicoding.capstonemd.data

object FakeNutritionData {
    val nutritionData = listOf(
        Nutrition(1,29.1,11.2,90.57,53.11,12.21),
        Nutrition(2,93.2,12.2,98.22,53.21,22.21),
        Nutrition(3,64.3,13.2,97.34,53.31,32.21),
        Nutrition(4,30.4,14.2,96.75,53.41,42.21),
        Nutrition(5,29.5,15.2,95.15,53.51,52.21),
        Nutrition(6,2.6,16.2,94.85,53.61,62.21),
        Nutrition(7,9.7,17.2,93.99,53.71,72.21),
        Nutrition(8,52.8,18.2,92.12,53.81,82.21),
        Nutrition(9,1.9,19.2,91.34,53.91,92.21),
        Nutrition(10,5.10,20.2,90.21,53.101,102.21),
        Nutrition(11,98.11,21.2,98.93,53.111,112.21),
        Nutrition(12,11.12,22.2,97.22,53.121,122.21),
    )

    fun getFakeNutritionData(): List<Nutrition> {
        return nutritionData
    }

}