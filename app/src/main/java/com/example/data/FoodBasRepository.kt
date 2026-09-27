package com.example.data

import com.example.R
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FoodBasRepository {

  val categories = listOf(
    FoodCategory("nepali", "Nepali", "🍛", R.drawable.nepali_thali),
    FoodCategory("indian", "Indian", "🍲", R.drawable.hero_biryani_bowl),
    FoodCategory("chinese", "Chinese", "🍜", R.drawable.fried_rice),
    FoodCategory("fast_food", "Fast Food", "🍔", null),
    FoodCategory("pizza", "Pizza", "🍕", R.drawable.pizza_dish),
    FoodCategory("momos", "Momos", "🥟", R.drawable.steamed_momos),
    FoodCategory("drinks", "Drinks", "🥤", R.drawable.coke_bottle),
    FoodCategory("desserts", "Desserts", "🍰", null),
    FoodCategory("more", "More", "•••", null)
  )

  private val _restaurants = MutableStateFlow(
    listOf(
      Restaurant(
        id = "himalayan_kitchen",
        name = "The Himalayan Kitchen",
        imageRes = R.drawable.restaurant_ambiance,
        rating = 4.6f,
        reviewCount = "320+",
        deliveryTime = "30-40 mins",
        cuisines = "Nepali • Continental",
        hasFreeDelivery = true,
        discountBadge = "30% OFF",
        isFavorite = false
      ),
      Restaurant(
        id = "spicy_bite",
        name = "Spicy Bite Restaurant",
        imageRes = R.drawable.fried_rice,
        rating = 4.6f,
        reviewCount = "210+",
        deliveryTime = "25-35 mins",
        cuisines = "Indian • Chinese",
        hasFreeDelivery = true,
        discountBadge = null,
        isFavorite = true
      ),
      Restaurant(
        id = "momo_hub",
        name = "Momo Hub",
        imageRes = R.drawable.steamed_momos,
        rating = 4.5f,
        reviewCount = "180+",
        deliveryTime = "20-30 mins",
        cuisines = "Momos • Nepali",
        hasFreeDelivery = true,
        discountBadge = null,
        isFavorite = false
      ),
      Restaurant(
        id = "pizza_world",
        name = "Pizza World",
        imageRes = R.drawable.pizza_dish,
        rating = 4.3f,
        reviewCount = "150+",
        deliveryTime = "30-45 mins",
        cuisines = "Pizza • Fast Food",
        hasFreeDelivery = true,
        discountBadge = null,
        isFavorite = false
      )
    )
  )
  val restaurants: StateFlow<List<Restaurant>> = _restaurants.asStateFlow()

  val allMenuItems = listOf(
    MenuItem("m1", "himalayan_kitchen", "Chicken Thali", 250, "Nepali Thali", "Traditional Nepali chicken curry with aromatic rice, dal & seasonal saag", R.drawable.nepali_thali, false),
    MenuItem("m2", "himalayan_kitchen", "Veg Thali", 180, "Nepali Thali", "Fresh garden vegetables with lentils, basmati rice, papad and fresh chutney", R.drawable.nepali_thali, true),
    MenuItem("m3", "himalayan_kitchen", "Chicken Momos", 150, "Momos", "Steamed juicy chicken dumplings with spicy roasted tomato sesame chutney", R.drawable.steamed_momos, false),
    MenuItem("m4", "himalayan_kitchen", "Buff Momo", 170, "Buff Momos", "Authentic Himalayan seasoned buff dumplings served piping hot", R.drawable.steamed_momos, false),
    MenuItem("m5", "himalayan_kitchen", "Veg Fried Rice", 120, "Snacks", "Wok tossed fragrant rice with crunchy seasonal vegetables and spring onions", R.drawable.fried_rice, true),
    MenuItem("m6", "himalayan_kitchen", "Coke (500ml)", 60, "Beverages", "Chilled refreshing Coca-Cola pet bottle", R.drawable.coke_bottle, true),

    MenuItem("m7", "spicy_bite", "Veg Fried Rice", 180, "Chinese", "Chef special Schezwan spiced vegetable fried rice", R.drawable.fried_rice, true),
    MenuItem("m8", "spicy_bite", "Chicken Biryani", 320, "Indian", "Slow cooked dum chicken biryani in sealed clay pot with raita", R.drawable.hero_biryani_bowl, false),

    MenuItem("m9", "momo_hub", "Chicken Momos", 150, "Momos", "Famous Bardibas recipe steamed chicken momos with fiery chutney", R.drawable.steamed_momos, false),
    MenuItem("m10", "momo_hub", "Veg Momos", 120, "Momos", "Finely chopped cabbage, paneer, and onion steamed momos", R.drawable.steamed_momos, true),

    MenuItem("m11", "pizza_world", "Pizza Margherita", 350, "Pizza", "Classic mozzarella cheese with fresh tomato sauce and basil", R.drawable.pizza_dish, true),
    MenuItem("m12", "pizza_world", "Chicken Pepperoni", 450, "Pizza", "Loaded with spicy chicken sausage, bell peppers and melted cheese", R.drawable.pizza_dish, false)
  )

  // Seeded cart matching Screen 4 in the image!
  private val _cartItems = MutableStateFlow<List<CartItem>>(
    listOf(
      CartItem(allMenuItems[0], 1), // Chicken Thali (Rs. 250)
      CartItem(allMenuItems[2], 1), // Chicken Momos (Rs. 150)
      CartItem(allMenuItems[5], 1)  // Coke 500ml (Rs. 60)
    )
  )
  val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

  private val _appliedCoupon = MutableStateFlow<String?>("FOOD20")
  val appliedCoupon: StateFlow<String?> = _appliedCoupon.asStateFlow()

  val promoOffers = listOf(
    PromoOffer("p1", "Flat 20% OFF", "On orders above Rs. 500", "FOOD20", discountPercent = 20, minOrder = 500, category = "All Offers"),
    PromoOffer("p2", "Free Delivery", "On orders above Rs. 300", "FREEDEL", isFreeDelivery = true, minOrder = 300, category = "Delivery"),
    PromoOffer("p3", "Buy 1 Get 1 Free", "On selected Momos", "MOMOBOGO", flatDiscount = 50, minOrder = 200, category = "Restaurants")
  )

  // Past Orders matching Screen 8 in image
  private val _orders = MutableStateFlow(
    listOf(
      OrderItem(
        id = "ORD-9841",
        restaurantId = "himalayan_kitchen",
        restaurantName = "The Himalayan Kitchen",
        restaurantImageRes = R.drawable.nepali_thali,
        summary = "Chicken Thali • Coke",
        totalAmount = 310,
        status = "Delivered",
        dateFormatted = "Sep 23, 2025 • 01:35 PM",
        currentStage = OrderProgressStage.DELIVERED
      ),
      OrderItem(
        id = "ORD-9820",
        restaurantId = "spicy_bite",
        restaurantName = "Spicy Bite Restaurant",
        restaurantImageRes = R.drawable.fried_rice,
        summary = "Veg Fried Rice",
        totalAmount = 180,
        status = "Delivered",
        dateFormatted = "Sep 20, 2025 • 12:20 PM",
        currentStage = OrderProgressStage.DELIVERED
      ),
      OrderItem(
        id = "ORD-9755",
        restaurantId = "momo_hub",
        restaurantName = "Momo Hub",
        restaurantImageRes = R.drawable.steamed_momos,
        summary = "Chicken Momos",
        totalAmount = 150,
        status = "Delivered",
        dateFormatted = "Sep 18, 2025 • 07:45 PM",
        currentStage = OrderProgressStage.DELIVERED
      )
    )
  )
  val orders: StateFlow<List<OrderItem>> = _orders.asStateFlow()

  // Active tracking order (matches Screen 7 in image)
  private val _activeOrder = MutableStateFlow<OrderItem?>(
    OrderItem(
      id = "ORD-9901",
      restaurantId = "himalayan_kitchen",
      restaurantName = "The Himalayan Kitchen",
      restaurantImageRes = R.drawable.nepali_thali,
      summary = "Chicken Thali • Chicken Momos • Coke",
      totalAmount = 460,
      status = "Out for Delivery",
      dateFormatted = "Today • 01:15 PM",
      currentStage = OrderProgressStage.OUT_FOR_DELIVERY,
      deliveryPartnerName = "Ramesh B.",
      deliveryPartnerRating = 4.8f,
      deliveryPartnerRatingCount = 124,
      deliveryPartnerPhone = "+977 9812345678",
      etaMinutes = 20
    )
  )
  val activeOrder: StateFlow<OrderItem?> = _activeOrder.asStateFlow()

  fun toggleFavorite(restaurantId: String) {
    _restaurants.update { list ->
      list.map {
        if (it.id == restaurantId) it.copy(isFavorite = !it.isFavorite) else it
      }
    }
  }

  fun addToCart(menuItem: MenuItem) {
    _cartItems.update { currentList ->
      val existing = currentList.find { it.item.id == menuItem.id }
      if (existing != null) {
        currentList.map {
          if (it.item.id == menuItem.id) it.copy(quantity = it.quantity + 1) else it
        }
      } else {
        currentList + CartItem(menuItem, 1)
      }
    }
  }

  fun decrementCart(menuItemId: String) {
    _cartItems.update { currentList ->
      val existing = currentList.find { it.item.id == menuItemId }
      if (existing != null && existing.quantity > 1) {
        currentList.map {
          if (it.item.id == menuItemId) it.copy(quantity = it.quantity - 1) else it
        }
      } else {
        currentList.filterNot { it.item.id == menuItemId }
      }
    }
  }

  fun removeFromCart(menuItemId: String) {
    _cartItems.update { currentList ->
      currentList.filterNot { it.item.id == menuItemId }
    }
  }

  fun applyCoupon(code: String): Boolean {
    val found = promoOffers.find { it.code.equals(code.trim(), ignoreCase = true) }
    if (found != null) {
      _appliedCoupon.value = found.code
      return true
    }
    return false
  }

  fun removeCoupon() {
    _appliedCoupon.value = null
  }

  fun placeOrder(
    restaurant: Restaurant,
    paymentMethod: PaymentMethod,
    notes: String
  ): OrderItem {
    val items = _cartItems.value
    val subtotal = items.sumOf { it.item.price * it.quantity }
    val discount = if (_appliedCoupon.value != null) 50 else 0
    val total = (subtotal + 50 - discount).coerceAtLeast(0)

    val newOrder = OrderItem(
      id = "ORD-${System.currentTimeMillis() % 10000}",
      restaurantId = restaurant.id,
      restaurantName = restaurant.name,
      restaurantImageRes = restaurant.imageRes,
      summary = items.joinToString(" • ") { it.item.name },
      totalAmount = total,
      status = "Out for Delivery",
      dateFormatted = "Today • Just now",
      currentStage = OrderProgressStage.OUT_FOR_DELIVERY,
      deliveryPartnerName = "Ramesh B.",
      deliveryPartnerRating = 4.8f,
      deliveryPartnerRatingCount = 124,
      deliveryPartnerPhone = "+977 9812345678",
      etaMinutes = 20
    )

    _activeOrder.value = newOrder
    _orders.update { listOf(newOrder) + it }
    _cartItems.value = emptyList()
    return newOrder
  }
}
