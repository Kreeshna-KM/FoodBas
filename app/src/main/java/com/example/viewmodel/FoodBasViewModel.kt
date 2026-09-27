package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FoodBasRepository
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class Screen {
  HOME,
  RESTAURANTS_LIST,
  RESTAURANT_DETAIL,
  CART,
  CHECKOUT,
  PAYMENT,
  TRACK_ORDER,
  MY_ORDERS,
  RATE_ORDER,
  OFFERS,
  PROFILE
}

enum class BottomTab {
  HOME,
  ORDERS,
  OFFERS,
  PROFILE
}

class FoodBasViewModel(
  private val repository: FoodBasRepository = FoodBasRepository()
) : ViewModel() {

  private val screenStack = mutableListOf<Screen>(Screen.HOME)

  private val _currentScreen = MutableStateFlow(Screen.HOME)
  val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

  private val _currentTab = MutableStateFlow(BottomTab.HOME)
  val currentTab: StateFlow<BottomTab> = _currentTab.asStateFlow()

  val categories = repository.categories
  val restaurants = repository.restaurants
  val allMenuItems = repository.allMenuItems
  val cartItems = repository.cartItems
  val promoOffers = repository.promoOffers
  val appliedCoupon = repository.appliedCoupon
  val orders = repository.orders
  val activeOrder = repository.activeOrder

  // Selected restaurant for detail view
  private val _selectedRestaurant = MutableStateFlow(repository.restaurants.value.first())
  val selectedRestaurant: StateFlow<Restaurant> = _selectedRestaurant.asStateFlow()

  // Selected order for rating
  private val _selectedOrderToRate = MutableStateFlow<OrderItem?>(repository.orders.value.firstOrNull())
  val selectedOrderToRate: StateFlow<OrderItem?> = _selectedOrderToRate.asStateFlow()

  // Checkout & Payment states
  private val _selectedPaymentMethod = MutableStateFlow(PaymentMethod.COD)
  val selectedPaymentMethod: StateFlow<PaymentMethod> = _selectedPaymentMethod.asStateFlow()

  private val _orderNotes = MutableStateFlow("")
  val orderNotes: StateFlow<String> = _orderNotes.asStateFlow()

  // Rating Screen State
  private val _ratingStars = MutableStateFlow(5)
  val ratingStars: StateFlow<Int> = _ratingStars.asStateFlow()

  private val _reviewComment = MutableStateFlow("")
  val reviewComment: StateFlow<String> = _reviewComment.asStateFlow()

  private val _reviewSubmitted = MutableStateFlow(false)
  val reviewSubmitted: StateFlow<Boolean> = _reviewSubmitted.asStateFlow()

  // Search & Filter state
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _selectedRestaurantFilter = MutableStateFlow("All")
  val selectedRestaurantFilter: StateFlow<String> = _selectedRestaurantFilter.asStateFlow()

  private val _selectedCategory = MutableStateFlow<String?>(null)
  val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

  private val _selectedOffersFilter = MutableStateFlow("All Offers")
  val selectedOffersFilter: StateFlow<String> = _selectedOffersFilter.asStateFlow()

  private val _selectedOrdersFilter = MutableStateFlow("All")
  val selectedOrdersFilter: StateFlow<String> = _selectedOrdersFilter.asStateFlow()

  // Delivery & Contact
  val deliveryAddress = MutableStateFlow(DeliveryAddress())
  val userContact = MutableStateFlow(UserContact())

  fun navigateTo(screen: Screen) {
    if (_currentScreen.value != screen) {
      screenStack.add(screen)
      _currentScreen.value = screen
    }
  }

  fun navigateBack(): Boolean {
    if (screenStack.size > 1) {
      screenStack.removeAt(screenStack.lastIndex)
      val previous = screenStack.last()
      _currentScreen.value = previous
      // Keep bottom tab in sync
      when (previous) {
        Screen.HOME -> _currentTab.value = BottomTab.HOME
        Screen.MY_ORDERS -> _currentTab.value = BottomTab.ORDERS
        Screen.OFFERS -> _currentTab.value = BottomTab.OFFERS
        Screen.PROFILE -> _currentTab.value = BottomTab.PROFILE
        else -> {}
      }
      return true
    }
    return false
  }

  fun switchTab(tab: BottomTab) {
    _currentTab.value = tab
    when (tab) {
      BottomTab.HOME -> navigateTo(Screen.HOME)
      BottomTab.ORDERS -> navigateTo(Screen.MY_ORDERS)
      BottomTab.OFFERS -> navigateTo(Screen.OFFERS)
      BottomTab.PROFILE -> navigateTo(Screen.PROFILE)
    }
  }

  fun openRestaurant(restaurant: Restaurant) {
    _selectedRestaurant.value = restaurant
    navigateTo(Screen.RESTAURANT_DETAIL)
  }

  fun openRateOrder(order: OrderItem) {
    _selectedOrderToRate.value = order
    _ratingStars.value = 5
    _reviewComment.value = ""
    _reviewSubmitted.value = false
    navigateTo(Screen.RATE_ORDER)
  }

  fun setRatingStars(stars: Int) {
    _ratingStars.value = stars
  }

  fun setReviewComment(comment: String) {
    _reviewComment.value = comment
  }

  fun submitReview() {
    _reviewSubmitted.value = true
  }

  fun setRestaurantFilter(filter: String) {
    _selectedRestaurantFilter.value = filter
  }

  fun setCategoryFilter(categoryId: String?) {
    _selectedCategory.value = categoryId
    navigateTo(Screen.RESTAURANTS_LIST)
  }

  fun setOffersFilter(filter: String) {
    _selectedOffersFilter.value = filter
  }

  fun setOrdersFilter(filter: String) {
    _selectedOrdersFilter.value = filter
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun toggleFavorite(restaurantId: String) {
    repository.toggleFavorite(restaurantId)
    if (_selectedRestaurant.value.id == restaurantId) {
      _selectedRestaurant.value = _selectedRestaurant.value.copy(
        isFavorite = !_selectedRestaurant.value.isFavorite
      )
    }
  }

  fun addToCart(item: MenuItem) {
    repository.addToCart(item)
  }

  fun decrementCart(menuItemId: String) {
    repository.decrementCart(menuItemId)
  }

  fun removeFromCart(menuItemId: String) {
    repository.removeFromCart(menuItemId)
  }

  fun applyCoupon(code: String): Boolean {
    return repository.applyCoupon(code)
  }

  fun setPaymentMethod(method: PaymentMethod) {
    _selectedPaymentMethod.value = method
  }

  fun setOrderNotes(notes: String) {
    _orderNotes.value = notes
  }

  fun placeOrder(): OrderItem {
    val created = repository.placeOrder(
      restaurant = _selectedRestaurant.value,
      paymentMethod = _selectedPaymentMethod.value,
      notes = _orderNotes.value
    )
    navigateTo(Screen.TRACK_ORDER)
    return created
  }
}
