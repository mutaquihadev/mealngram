package dk.kriaactividade.mealngram.entities.domain.recipe

enum class WEEK(val id: Int, val label: String) {
    MONDAY(id = 0, label = "Seg"),
    TUESDAY(id = 1, label = "Ter"),
    WEDNESDAY(id = 2, label = "Qua"),
    THURSDAY(id = 3, label = "Qui"),
    FRIDAY(id = 4, label = "Sex"),
    SATURDAY(id = 5, "Sab"),
    SUNDAY(id = 6, label = "Dom")
}