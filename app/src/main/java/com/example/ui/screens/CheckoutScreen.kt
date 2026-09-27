package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DeliveryAddress
import com.example.model.UserContact
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
  address: DeliveryAddress,
  contact: UserContact,
  orderNotes: String,
  onOrderNotesChange: (String) -> Unit,
  onContinueToPayment: () -> Unit,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Checkout",
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
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Button(
            onClick = onContinueToPayment,
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = FoodBasRed)
          ) {
            Text(
              text = "Continue to Payment",
              color = Color.White,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
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
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Step Indicator (1 Address, 2 Payment, 3 Confirm)
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
          ) {
            StepItem(stepNumber = "1", label = "Address", isActive = true)
            Box(
              modifier = Modifier
                .width(40.dp)
                .height(2.dp)
                .background(FoodBasBorder)
            )
            StepItem(stepNumber = "2", label = "Payment", isActive = false)
            Box(
              modifier = Modifier
                .width(40.dp)
                .height(2.dp)
                .background(FoodBasBorder)
            )
            StepItem(stepNumber = "3", label = "Confirm", isActive = false)
          }
        }
      }

      // Delivery Address Card
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
              .padding(16.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Delivery Address",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = FoodBasTextPrimary
              )
              Text(
                text = "Change",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = FoodBasRed,
                modifier = Modifier.clickable { /* Change address */ }
              )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                  .size(40.dp)
                  .clip(CircleShape)
                  .background(FoodBasRed.copy(alpha = 0.1f))
              ) {
                Icon(
                  imageVector = Icons.Filled.Home,
                  contentDescription = null,
                  tint = FoodBasRed,
                  modifier = Modifier.size(20.dp)
                )
              }

              Spacer(modifier = Modifier.width(12.dp))

              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = address.label,
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp,
                  color = FoodBasTextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = address.line1,
                  fontSize = 12.sp,
                  color = FoodBasTextSecondary
                )
                Text(
                  text = address.line2,
                  fontSize = 12.sp,
                  color = FoodBasTextSecondary
                )
              }

              Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = FoodBasTextMuted
              )
            }
          }
        }
      }

      // Contact Details Card
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
              .padding(16.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Contact Details",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = FoodBasTextPrimary
              )
              Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit Contact",
                tint = FoodBasTextSecondary,
                modifier = Modifier.size(18.dp)
              )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                  .size(40.dp)
                  .clip(CircleShape)
                  .background(FoodBasLightGray)
              ) {
                Icon(
                  imageVector = Icons.Filled.Person,
                  contentDescription = null,
                  tint = FoodBasTextPrimary,
                  modifier = Modifier.size(20.dp)
                )
              }

              Spacer(modifier = Modifier.width(12.dp))

              Column {
                Text(
                  text = contact.name,
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp,
                  color = FoodBasTextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = contact.phone,
                  fontSize = 12.sp,
                  color = FoodBasTextSecondary
                )
              }
            }
          }
        }
      }

      // Order Notes (Optional)
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
              .padding(16.dp)
          ) {
            Text(
              text = "Order Notes (Optional)",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = FoodBasTextPrimary
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
              value = orderNotes,
              onValueChange = onOrderNotesChange,
              placeholder = { Text("e.g. Ring the bell, no onions, etc.", fontSize = 13.sp) },
              modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
              shape = RoundedCornerShape(10.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FoodBasRed,
                unfocusedBorderColor = FoodBasBorder
              )
            )
          }
        }
      }
    }
  }
}

@Composable
fun StepItem(
  stepNumber: String,
  label: String,
  isActive: Boolean
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .size(28.dp)
        .clip(CircleShape)
        .background(if (isActive) FoodBasRed else FoodBasLightGray)
    ) {
      Text(
        text = stepNumber,
        color = if (isActive) Color.White else FoodBasTextSecondary,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
      )
    }
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = label,
      color = if (isActive) FoodBasRed else FoodBasTextSecondary,
      fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
      fontSize = 11.sp
    )
  }
}
