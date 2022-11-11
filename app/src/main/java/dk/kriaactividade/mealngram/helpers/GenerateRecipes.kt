package dk.kriaactividade.mealngram.helpers

private val images = listOf(
    "https://firebasestorage.googleapis.com/v0/b/mealngram.appspot.com/o/recipes-assets%2Fphoto-1.jpg?alt=media&token=f1f8481a-99a6-404c-9880-f76043db2863",
    "https://firebasestorage.googleapis.com/v0/b/mealngram.appspot.com/o/recipes-assets%2Fphoto-2.jpg?alt=media&token=d08717a5-1185-47d3-8018-c5a2543caaf0",
    "https://firebasestorage.googleapis.com/v0/b/mealngram.appspot.com/o/recipes-assets%2Fphoto-10.jpg?alt=media&token=101ef770-c91e-455e-95bd-90524e46552f",
    "https://firebasestorage.googleapis.com/v0/b/mealngram.appspot.com/o/recipes-assets%2Fphoto-11.jpg?alt=media&token=9dac0f13-7062-4bc0-86c0-5f278c7978fb",
    "https://firebasestorage.googleapis.com/v0/b/mealngram.appspot.com/o/recipes-assets%2Fphoto-12.jpg?alt=media&token=fc3cd951-a865-42a7-a5eb-89700699f728",
    "https://firebasestorage.googleapis.com/v0/b/mealngram.appspot.com/o/recipes-assets%2Fphoto-21.jpg?alt=media&token=de88ef0f-0b1b-45de-8f05-d1266479cb75",
    "https://firebasestorage.googleapis.com/v0/b/mealngram.appspot.com/o/recipes-assets%2Fphoto-27.jpg?alt=media&token=067ebb44-96a5-4a67-970d-dda43cbb784e",
    "https://firebasestorage.googleapis.com/v0/b/mealngram.appspot.com/o/recipes-assets%2Fphoto-29.png?alt=media&token=56de64fa-5b10-4369-be86-bf566370fa69",
    "https://firebasestorage.googleapis.com/v0/b/mealngram.appspot.com/o/recipes-assets%2Fphoto-34.png?alt=media&token=6c2ceec1-d8c9-4b36-9d5b-75d17014986f"
)

private fun generateRecipes(): List<Map<String, Any>> {

    val recipes = listOf(
        mapOf(
            "id" to 1,
            "name" to "Romanian",
            "description" to "Breaded fried chicken with waffles, and a side of maple syrup.",
            "image" to images[0],
            "ingredients" to listOf("Strawberries", "Cabbage", "Parrotfish", "Sour Dough Bread")
        ),
        mapOf(
            "id" to 2,
            "name" to "Korean",
            "description" to "Granny Smith apples mixed with brown sugar and butter filling, in a flaky all-butter crust, with ice cream.",
            "image" to images[1],
            "ingredients" to listOf("Rice Syrup", "Radicchio", "Cornichons", "Ricemilk")
        ),
        mapOf(
            "id" to 3,
            "description" to "Thick slices of French toast bread, brown sugar, half-and-half and vanilla, topped with powdered sugar. With two eggs served any style, and your choice of smoked bacon or smoked ham.",
            "image" to images[2],
            "ingredients" to listOf("Carob Carrot", "Sweet Chilli Sauce", "Asparagus", "Incaberries")
        ),
        mapOf(
            "id" to 4,
            "name" to "Punjabi",
            "description" to "Three eggs with cilantro, tomatoes, onions, avocados and melted Emmental cheese. With a side of roasted potatoes, and your choice of toast or croissant.",
            "image" to images[3],
            "ingredients" to listOf("Crabs", "Chives", "Sunflower Seeds", "Harissa")
        ),
        mapOf(
            "id" to 5,
            "name" to "Chinese Islamic",
            "description" to "Three egg whites with spinach, mushrooms, caramelized onions, tomatoes and low-fat feta cheese. With herbed quinoa, and your choice of rye or whole-grain toast.",
            "image" to images[4],
            "ingredients" to listOf("Bacon", "Zucchini", "Pear", "Butternut Lettuce")
        ),
        mapOf(
            "id" to 6,
            "name" to "Ricotta Stuffed Ravioli",
            "description" to "28-day aged 300g USDA Certified Prime Ribeye, rosemary-thyme garlic butter, with choice of two sides.",
            "ingredients" to listOf("Broccoli", "Blue Cheese", "Broccolini", "Scallops"),
            "image" to images[5]
        ),
        mapOf(
            "id" to 7,
            "name" to "Pashtun",
            "description" to "Fresh Norwegian salmon, lightly brushed with our herbed Dijon mustard sauce, with choice of two sides.",
            "ingredients" to listOf("Coriander Seed", "Red Pepper", "Pear", "Ricemilk"),
            "image" to images[6]
        )
    )

    return recipes
}
