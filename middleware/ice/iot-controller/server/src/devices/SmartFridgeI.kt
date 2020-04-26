package devices

import IotController.ArgumentException
import IotController.FridgeInfo
import IotController.Item
import IotController.SmartFridge
import com.zeroc.Ice.Current

class SmartFridgeI(
    fridgeInfo: FridgeInfo,
    private val initialItems: List<Item>
) : FridgeI(fridgeInfo), SmartFridge {
    private val recipes = mutableMapOf<String, List<Item>>()

    override fun whatINeedFor(name: String?, current: Current?): Array<Item> {
        if(!recipes.containsKey(name))
            throw ArgumentException("No such recipe")
        val ingredients = recipes[name]
        return ingredients
            ?.map { ing ->
                val item = initialItems.find { it.name == ing.name }
                var q = ing.quantity
                if(item != null) {
                    q -= item.quantity
                }
                Item(ing.name, q)
            }
            ?.filter { it.quantity >= 0 }
            ?.toTypedArray() ?: throw ArgumentException()
    }

    override fun addRecipe(name: String?, ingredients: Array<out Item>?, current: Current?) {
        println("$name, $ingredients")
        if(name != null && ingredients != null)
            recipes[name] = ingredients.toList()
    }

    override fun getCurrentItems(current: Current?): Array<Item> {
        return initialItems.toTypedArray()
    }
}