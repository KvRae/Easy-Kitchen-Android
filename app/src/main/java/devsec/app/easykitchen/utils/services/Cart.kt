package devsec.app.easykitchen.utils.services

import devsec.app.easykitchen.data.models.Food

class Cart {
    companion object {
        var cart: ArrayList<String> = ArrayList()

        var cartRemovedItems: ArrayList<String> = ArrayList()
    }
}