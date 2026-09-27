package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CartItem
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
  cartItems: List<CartItem>,
  appliedCoupon: String?,
  onIncrement: (String) -> Unit,
  onDecrement: (String) -> Unit,
  onRemove: (String) -> Unit,
  onApplyCoupon: (String) -> Boolean,
  onProceedToCheckout: () -> Unit,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var couponInput by remember { mutableStateOf(appliedCoupon ?: "") }
  var couponMessage by remember { mutableStateOf<String?>(null) }

  val itemTotal = cartItems.sumOf { it.item.price * it.quantity }
  val deliveryFee = if (cartItems.isEmpty()) 0 else if (appliedCoupon == "FREEDEL") 0 else 50
  val discount = if (appliedCoupon != null) 50 else 0
  val finalTotal = (itemTotal + deliveryFee - discount).coerceAtLeast(0)

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Your Cart",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = FoodBasTextPrimary
          )
        },
        navigationIcon = {
          IconButton(onClick = onBackClick) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = FoodBasTextPrimary
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
      )
    },
    bottomBar = {
      if (cartItems.isNotEmpty()) {
        Surface(
          tonalElevation = 8.dp,
          color = Color.White,
          modifier = Modifier.fillMaxWidth()
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)
          ) {
            Button(
              onClick = onProceedToCheckout,
              modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(containerColor = FoodBasRed)
            ) {
              Text(
                text = "Proceed to Checkout",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    },
    modifier = modifier.fillMaxSize()
  ) { padding ->
    if (cartItems.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(padding),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = "Your cart is empty",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = FoodBasTextPrimary
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Add some delicious food from restaurants!",
            fontSize = 13.sp,
            color = FoodBasTextSecondary
          )
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(padding)
          .background(FoodBasScreenBg),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // Items List
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              cartItems.forEachIndexed { index, cartItem ->
                CartItemRow(
                  cartItem = cartItem,
                  onIncrement = { onIncrement(cartItem.item.id) },
                  onDecrement = { onDecrement(cartItem.item.id) },
                  onRemove = { onRemove(cartItem.item.id) }
                )
                if (index < cartItems.lastIndex) {
                  Divider(color = FoodBasBorder, modifier = Modifier.padding(vertical = 4.dp))
                }
              }
            }
          }
        }

        // Apply Coupon Box
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
            ) {
              Text(
                text = "Apply Coupon",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = FoodBasTextPrimary
              )

              Spacer(modifier = Modifier.height(10.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
              ) {
                OutlinedTextField(
                  value = couponInput,
                  onValueChange = { couponInput = it },
                  placeholder = { Text("Enter coupon code", fontSize = 13.sp) },
                  singleLine = true,
                  shape = RoundedCornerShape(10.dp),
                  modifier = Modifier.weight(1f),
                  colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FoodBasRed,
                    unfocusedBorderColor = FoodBasBorder
                  )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                  onClick = {
                    val success = onApplyCoupon(couponInput)
                    couponMessage = if (success) "Coupon applied successfully!" else "Invalid coupon code"
                  },
                  colors = ButtonDefaults.buttonColors(containerColor = FoodBasRed),
                  shape = RoundedCornerShape(10.dp),
                  contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp)
                ) {
                  Text(
                    text = "Apply",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                  )
                }
              }

              if (couponMessage != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = couponMessage!!,
                  color = if (appliedCoupon != null) FoodBasGreen else FoodBasRed,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Medium
                )
              }
            }
          }
        }

        // Price Summary / Breakdown
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              SummaryRow(label = "Item Total", value = "Rs. $itemTotal")
              SummaryRow(label = "Delivery Fee", value = "Rs. $deliveryFee")
              if (discount > 0) {
                SummaryRow(
                  label = "Discount",
                  value = "- Rs. $discount",
                  valueColor = FoodBasGreen
                )
              }
              Divider(color = FoodBasBorder, modifier = Modifier.padding(vertical = 4.dp))
              SummaryRow(
                label = "Total",
                value = "Rs. $finalTotal",
                isBold = true
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun CartItemRow(
  cartItem: CartItem,
  onIncrement: () -> Unit,
  onDecrement: () -> Unit,
  onRemove: () -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Image(
      painter = painterResource(id = cartItem.item.imageRes),
      contentDescription = cartItem.item.name,
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .size(54.dp)
        .clip(RoundedCornerShape(8.dp))
    )

    Spacer(modifier = Modifier.width(10.dp))

    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = cartItem.item.name,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        color = FoodBasTextPrimary
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = "Rs. ${cartItem.item.price}",
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        color = FoodBasTextPrimary
      )
    }

    // Stepper ` - 1 + `
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .border(1.dp, FoodBasBorder, RoundedCornerShape(8.dp))
        .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
      IconButton(
        onClick = onDecrement,
        modifier = Modifier.size(24.dp)
      ) {
        Icon(
          imageVector = Icons.Filled.Remove,
          contentDescription = "Decrease",
          tint = FoodBasTextSecondary,
          modifier = Modifier.size(14.dp)
        )
      }
      Text(
        text = "${cartItem.quantity}",
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        modifier = Modifier.padding(horizontal = 6.dp)
      )
      IconButton(
        onClick = onIncrement,
        modifier = Modifier.size(24.dp)
      ) {
        Icon(
          imageVector = Icons.Filled.Add,
          contentDescription = "Increase",
          tint = FoodBasTextSecondary,
          modifier = Modifier.size(14.dp)
        )
      }
    }

    Spacer(modifier = Modifier.width(8.dp))

    // Trash Delete Icon
    IconButton(
      onClick = onRemove,
      modifier = Modifier.size(28.dp)
    ) {
      Icon(
        imageVector = Icons.Filled.Delete,
        contentDescription = "Delete",
        tint = FoodBasTextMuted,
        modifier = Modifier.size(18.dp)
      )
    }
  }
}

@Composable
fun SummaryRow(
  label: String,
  value: String,
  valueColor: Color = FoodBasTextPrimary,
  isBold: Boolean = false
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      fontSize = if (isBold) 15.sp else 13.sp,
      fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
      color = if (isBold) FoodBasTextPrimary else FoodBasTextSecondary
    )
    Text(
      text = value,
      fontSize = if (isBold) 16.sp else 13.sp,
      fontWeight = if (isBold) FontWeight.Bold else FontWeight.Medium,
      color = valueColor
    )
  }
}
