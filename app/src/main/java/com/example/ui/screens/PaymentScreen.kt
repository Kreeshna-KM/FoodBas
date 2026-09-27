package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PaymentMethod
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
  selectedMethod: PaymentMethod,
  onSelectMethod: (PaymentMethod) -> Unit,
  onPlaceOrder: () -> Unit,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val paymentMethods = listOf(
    PaymentMethod.ESEWA,
    PaymentMethod.KHALTI,
    PaymentMethod.CARD,
    PaymentMethod.COD,
    PaymentMethod.BANK_TRANSFER
  )

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Payment",
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
      Surface(
        tonalElevation = 8.dp,
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Button(
            onClick = onPlaceOrder,
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = FoodBasRed)
          ) {
            Text(
              text = "Place Order",
              color = Color.White,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Filled.Lock,
              contentDescription = null,
              tint = FoodBasTextSecondary,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Your payment information is secure",
              fontSize = 12.sp,
              color = FoodBasTextSecondary
            )
          }
        }
      }
    },
    modifier = modifier.fillMaxSize()
  ) { padding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .background(FoodBasScreenBg),
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Text(
          text = "Select Payment Method",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = FoodBasTextPrimary,
          modifier = Modifier.padding(bottom = 6.dp)
        )
      }

      items(paymentMethods) { method ->
        val isSelected = method == selectedMethod
        PaymentOptionCard(
          method = method,
          isSelected = isSelected,
          onSelect = { onSelectMethod(method) }
        )
      }
    }
  }
}

@Composable
fun PaymentOptionCard(
  method: PaymentMethod,
  isSelected: Boolean,
  onSelect: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onSelect() }
      .then(
        if (isSelected) Modifier.border(1.5.dp, FoodBasRed, RoundedCornerShape(14.dp))
        else Modifier.border(1.dp, FoodBasBorder, RoundedCornerShape(14.dp))
      ),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isSelected) FoodBasRed.copy(alpha = 0.03f) else Color.White
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Radio circle
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(20.dp)
          .clip(CircleShape)
          .border(
            width = 2.dp,
            color = if (isSelected) FoodBasRed else FoodBasTextMuted,
            shape = CircleShape
          )
      ) {
        if (isSelected) {
          Box(
            modifier = Modifier
              .size(10.dp)
              .clip(CircleShape)
              .background(FoodBasRed)
          )
        }
      }

      Spacer(modifier = Modifier.width(14.dp))

      // Brand Icon
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(42.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(
            when (method) {
              PaymentMethod.ESEWA -> EsewaGreen.copy(alpha = 0.15f)
              PaymentMethod.KHALTI -> KhaltiPurple.copy(alpha = 0.15f)
              PaymentMethod.CARD -> Color(0xFF1E40AF).copy(alpha = 0.1f)
              PaymentMethod.COD -> FoodBasGreen.copy(alpha = 0.15f)
              PaymentMethod.BANK_TRANSFER -> Color(0xFF6B7280).copy(alpha = 0.1f)
            }
          )
      ) {
        when (method) {
          PaymentMethod.ESEWA -> Text("e", color = EsewaGreen, fontWeight = FontWeight.Bold, fontSize = 20.sp)
          PaymentMethod.KHALTI -> Text("K", color = KhaltiPurple, fontWeight = FontWeight.Bold, fontSize = 20.sp)
          PaymentMethod.CARD -> Icon(Icons.Filled.CreditCard, contentDescription = null, tint = Color(0xFF1E40AF))
          PaymentMethod.COD -> Icon(Icons.Filled.Money, contentDescription = null, tint = FoodBasGreen)
          PaymentMethod.BANK_TRANSFER -> Icon(Icons.Filled.AccountBalance, contentDescription = null, tint = FoodBasDark)
        }
      }

      Spacer(modifier = Modifier.width(12.dp))

      // Title & Subtitle
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = method.title,
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp,
          color = FoodBasTextPrimary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = method.subtitle,
          fontSize = 12.sp,
          color = FoodBasTextSecondary
        )
      }

      // Card Brands (Visa / MasterCard)
      if (method == PaymentMethod.CARD) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "VISA",
            fontWeight = FontWeight.Black,
            fontSize = 12.sp,
            color = Color(0xFF1A1F71)
          )
          Spacer(modifier = Modifier.width(6.dp))
          // Mastercard circles
          Row {
            Box(
              modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(Color(0xFFEB001B))
            )
            Box(
              modifier = Modifier
                .offset(x = (-5).dp)
                .size(14.dp)
                .clip(CircleShape)
                .background(Color(0xFFF79E1B).copy(alpha = 0.85f))
            )
          }
        }
      }
    }
  }
}
