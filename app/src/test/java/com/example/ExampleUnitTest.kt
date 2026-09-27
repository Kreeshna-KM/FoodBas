package com.example

import com.example.data.FoodBasRepository
import com.example.model.PaymentMethod
import com.example.viewmodel.FoodBasViewModel
import com.example.viewmodel.Screen
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun testCartAndCouponCalculation() {
    val repo = FoodBasRepository()
    val initialItems = repo.cartItems.value
    assertEquals(3, initialItems.size) // Chicken Thali (250) + Momos (150) + Coke (60) = 460

    val subtotal = initialItems.sumOf { it.item.price * it.quantity }
    assertEquals(460, subtotal)

    // Test coupon application
    val valid = repo.applyCoupon("FOOD20")
    assertTrue(valid)
    assertEquals("FOOD20", repo.appliedCoupon.value)

    // Test order placement
    val restaurant = repo.restaurants.value.first()
    val placedOrder = repo.placeOrder(restaurant, PaymentMethod.COD, "No onions")
    assertNotNull(placedOrder)
    assertEquals("Out for Delivery", placedOrder.status)
    assertEquals("Ramesh B.", placedOrder.deliveryPartnerName)
    assertTrue(repo.cartItems.value.isEmpty())
  }

  @Test
  fun testViewModelNavigation() {
    val vm = FoodBasViewModel()
    assertEquals(Screen.HOME, vm.currentScreen.value)

    vm.navigateTo(Screen.RESTAURANTS_LIST)
    assertEquals(Screen.RESTAURANTS_LIST, vm.currentScreen.value)

    val didGoBack = vm.navigateBack()
    assertTrue(didGoBack)
    assertEquals(Screen.HOME, vm.currentScreen.value)
  }
}
