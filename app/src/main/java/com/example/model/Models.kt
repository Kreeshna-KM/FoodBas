package com.example.model

import androidx.annotation.DrawableRes

data class FoodCategory(
  val id: String,
  val name: String,
  val iconEmoji: String = "",
  @DrawableRes val imageRes: Int? = null
)

data class Restaurant(
  val id: String,
  val name: String,
  @DrawableRes val imageRes: Int,
  val rating: Float,
  val reviewCount: String,
  val deliveryTime: String,
  val cuisines: String,
  val hasFreeDelivery: Boolean = true,
  val discountBadge: String? = null,
  val isFavorite: Boolean = false,
  val location: String = "Bardibas-14, Mahottari"
)

data class MenuItem(
  val id: String,
  val restaurantId: String,
  val name: String,
  val price: Int, // in Nepali Rupees (Rs.)
  val category: String,
  val description: String = "",
  @DrawableRes val imageRes: Int,
  val isVeg: Boolean = false
)

data class CartItem(
  val item: MenuItem,
  val quantity: Int = 1
)

enum class PaymentMethod(val id: String, val title: String, val subtitle: String) {
  ESEWA("esewa", "eSewa", "Pay with eSewa"),
  KHALTI("khalti", "Khalti", "Pay with Khalti"),
  CARD("card", "Card Payment", "Visa, MasterCard, etc."),
  COD("cod", "Cash on Delivery", "Pay when you receive"),
  BANK_TRANSFER("bank", "Bank Transfer", "Direct bank transfer")
}

enum class OrderProgressStage(val label: String, val time: String) {
  PLACED("Order Placed", "12:45 PM"),
  PREPARING("Preparing", "12:50 PM"),
  OUT_FOR_DELIVERY("Out for Delivery", "01:15 PM"),
  DELIVERED("Delivered", "01:35 PM")
}

data class OrderItem(
  val id: String,
  val restaurantId: String,
  val restaurantName: String,
  @DrawableRes val restaurantImageRes: Int,
  val summary: String,
  val totalAmount: Int,
  val status: String, // "Delivered", "Cancelled", "Active"
  val dateFormatted: String,
  val currentStage: OrderProgressStage = OrderProgressStage.DELIVERED,
  val deliveryPartnerName: String = "Ramesh B.",
  val deliveryPartnerRating: Float = 4.8f,
  val deliveryPartnerRatingCount: Int = 124,
  val deliveryPartnerPhone: String = "+977 9801234567",
  val etaMinutes: Int = 20,
  val address: String = "Home - Bardibas-14, Mahottari\nNear New Nagarpalika, Bardibas"
)

data class PromoOffer(
  val id: String,
  val title: String,
  val subtitle: String,
  val code: String,
  val discountPercent: Int = 0,
  val flatDiscount: Int = 0,
  val isFreeDelivery: Boolean = false,
  val minOrder: Int = 0,
  val category: String = "All Offers"
)

data class DeliveryAddress(
  val label: String = "Home",
  val line1: String = "Bardibas-14, Mahottari",
  val line2: String = "Near New Nagarpalika, Bardibas"
)

data class UserContact(
  val name: String = "Krishna Mahato",
  val phone: String = "+977 9812345678",
  val email: String = "krishnakumarmahato556@gmail.com"
)
