package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.components.FoodBasBottomBar
import com.example.ui.screens.*
import com.example.ui.theme.FoodBasTheme
import com.example.viewmodel.BottomTab
import com.example.viewmodel.FoodBasViewModel
import com.example.viewmodel.Screen

class MainActivity : ComponentActivity() {

  private val viewModel: FoodBasViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      FoodBasTheme {
        FoodBasApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun FoodBasApp(viewModel: FoodBasViewModel) {
  val currentScreen by viewModel.currentScreen.collectAsState()
  val currentTab by viewModel.currentTab.collectAsState()

  val categories = viewModel.categories
  val restaurants by viewModel.restaurants.collectAsState()
  val allMenuItems = viewModel.allMenuItems
  val cartItems by viewModel.cartItems.collectAsState()
  val promoOffers = viewModel.promoOffers
  val appliedCoupon by viewModel.appliedCoupon.collectAsState()
  val orders by viewModel.orders.collectAsState()
  val activeOrder by viewModel.activeOrder.collectAsState()
  val selectedRestaurant by viewModel.selectedRestaurant.collectAsState()
  val selectedOrderToRate by viewModel.selectedOrderToRate.collectAsState()
  val selectedPaymentMethod by viewModel.selectedPaymentMethod.collectAsState()
  val orderNotes by viewModel.orderNotes.collectAsState()
  val ratingStars by viewModel.ratingStars.collectAsState()
  val reviewComment by viewModel.reviewComment.collectAsState()
  val reviewSubmitted by viewModel.reviewSubmitted.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val selectedRestaurantFilter by viewModel.selectedRestaurantFilter.collectAsState()
  val selectedOffersFilter by viewModel.selectedOffersFilter.collectAsState()
  val selectedOrdersFilter by viewModel.selectedOrdersFilter.collectAsState()
  val deliveryAddress by viewModel.deliveryAddress.collectAsState()
  val userContact by viewModel.userContact.collectAsState()

  // Handle back button behavior
  BackHandler(enabled = currentScreen != Screen.HOME) {
    viewModel.navigateBack()
  }

  // Show bottom bar on top-level tab screens
  val showBottomBar = currentScreen in listOf(
    Screen.HOME,
    Screen.MY_ORDERS,
    Screen.OFFERS,
    Screen.PROFILE
  )

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    bottomBar = {
      if (showBottomBar) {
        FoodBasBottomBar(
          currentTab = currentTab,
          onTabSelected = { tab -> viewModel.switchTab(tab) }
        )
      }
    }
  ) { innerPadding ->
    AnimatedContent(
      targetState = currentScreen,
      transitionSpec = {
        fadeIn() togetherWith fadeOut()
      },
      label = "screen_transition",
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) { screen ->
      when (screen) {
        Screen.HOME -> HomeScreen(
          categories = categories,
          restaurants = restaurants,
          cartCount = cartItems.sumOf { it.quantity },
          searchQuery = searchQuery,
          onSearchChange = { viewModel.setSearchQuery(it) },
          onCategoryClick = { cat ->
            viewModel.setCategoryFilter(cat.id)
          },
          onRestaurantClick = { r -> viewModel.openRestaurant(r) },
          onToggleFavorite = { id -> viewModel.toggleFavorite(id) },
          onViewAllRestaurants = { viewModel.navigateTo(Screen.RESTAURANTS_LIST) },
          onCartClick = { viewModel.navigateTo(Screen.CART) },
          onProfileClick = { viewModel.navigateTo(Screen.PROFILE) },
          onOrderNowClick = { viewModel.navigateTo(Screen.RESTAURANTS_LIST) }
        )

        Screen.RESTAURANTS_LIST -> RestaurantsListScreen(
          restaurants = restaurants,
          selectedFilter = selectedRestaurantFilter,
          onFilterChange = { viewModel.setRestaurantFilter(it) },
          onRestaurantClick = { r -> viewModel.openRestaurant(r) },
          onToggleFavorite = { id -> viewModel.toggleFavorite(id) },
          onBackClick = { viewModel.navigateBack() },
          onSearchClick = { /* focus search */ }
        )

        Screen.RESTAURANT_DETAIL -> RestaurantDetailScreen(
          restaurant = selectedRestaurant,
          menuItems = allMenuItems.filter { it.restaurantId == selectedRestaurant.id }
            .ifEmpty { allMenuItems.take(6) },
          cartItems = cartItems,
          onAddToCart = { item -> viewModel.addToCart(item) },
          onDecrementCart = { id -> viewModel.decrementCart(id) },
          onToggleFavorite = { viewModel.toggleFavorite(selectedRestaurant.id) },
          onBackClick = { viewModel.navigateBack() },
          onViewCartClick = { viewModel.navigateTo(Screen.CART) }
        )

        Screen.CART -> CartScreen(
          cartItems = cartItems,
          appliedCoupon = appliedCoupon,
          onIncrement = { id ->
            val found = allMenuItems.find { it.id == id }
            if (found != null) viewModel.addToCart(found)
          },
          onDecrement = { id -> viewModel.decrementCart(id) },
          onRemove = { id -> viewModel.removeFromCart(id) },
          onApplyCoupon = { code -> viewModel.applyCoupon(code) },
          onProceedToCheckout = { viewModel.navigateTo(Screen.CHECKOUT) },
          onBackClick = { viewModel.navigateBack() }
        )

        Screen.CHECKOUT -> CheckoutScreen(
          address = deliveryAddress,
          contact = userContact,
          orderNotes = orderNotes,
          onOrderNotesChange = { viewModel.setOrderNotes(it) },
          onContinueToPayment = { viewModel.navigateTo(Screen.PAYMENT) },
          onBackClick = { viewModel.navigateBack() }
        )

        Screen.PAYMENT -> PaymentScreen(
          selectedMethod = selectedPaymentMethod,
          onSelectMethod = { method -> viewModel.setPaymentMethod(method) },
          onPlaceOrder = { viewModel.placeOrder() },
          onBackClick = { viewModel.navigateBack() }
        )

        Screen.TRACK_ORDER -> TrackOrderScreen(
          order = activeOrder,
          onBackClick = {
            viewModel.switchTab(BottomTab.ORDERS)
          }
        )

        Screen.MY_ORDERS -> MyOrdersScreen(
          orders = orders,
          selectedFilter = selectedOrdersFilter,
          onFilterChange = { viewModel.setOrdersFilter(it) },
          onRateOrder = { order -> viewModel.openRateOrder(order) },
          onOrderClick = { viewModel.navigateTo(Screen.TRACK_ORDER) },
          onBackClick = { viewModel.switchTab(BottomTab.HOME) }
        )

        Screen.RATE_ORDER -> RateOrderScreen(
          order = selectedOrderToRate,
          ratingStars = ratingStars,
          reviewComment = reviewComment,
          isSubmitted = reviewSubmitted,
          onRatingChange = { stars -> viewModel.setRatingStars(stars) },
          onCommentChange = { comment -> viewModel.setReviewComment(comment) },
          onSubmitReview = { viewModel.submitReview() },
          onBackClick = { viewModel.navigateBack() }
        )

        Screen.OFFERS -> OffersScreen(
          offers = promoOffers,
          selectedFilter = selectedOffersFilter,
          onFilterChange = { viewModel.setOffersFilter(it) },
          onUseCoupon = { code ->
            viewModel.applyCoupon(code)
            viewModel.navigateTo(Screen.CART)
          },
          onOrderNowClick = { viewModel.navigateTo(Screen.RESTAURANTS_LIST) },
          onBackClick = { viewModel.switchTab(BottomTab.HOME) }
        )

        Screen.PROFILE -> ProfileScreen(
          contact = userContact,
          address = deliveryAddress,
          onOrdersClick = { viewModel.switchTab(BottomTab.ORDERS) },
          onOffersClick = { viewModel.switchTab(BottomTab.OFFERS) },
          onBackClick = { viewModel.switchTab(BottomTab.HOME) }
        )
      }
    }
  }
}
