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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DeliveryAddress
import com.example.model.UserContact
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
  contact: UserContact,
  address: DeliveryAddress,
  onOrdersClick: () -> Unit,
  onOffersClick: () -> Unit,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Profile",
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
      // User Profile Header Card
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
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(FoodBasRed)
            ) {
              Text(
                text = "KM",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
              )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
              Text(
                text = contact.name,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = FoodBasTextPrimary
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = contact.email,
                fontSize = 12.sp,
                color = FoodBasTextSecondary
              )
              Text(
                text = contact.phone,
                fontSize = 12.sp,
                color = FoodBasTextSecondary
              )
            }
          }
        }
      }

      // Saved Address Card
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
              text = "Saved Address",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = FoodBasTextPrimary
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = null,
                tint = FoodBasRed,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "${address.label} - ${address.line1}",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = FoodBasTextPrimary
                )
                Text(
                  text = address.line2,
                  fontSize = 12.sp,
                  color = FoodBasTextSecondary
                )
              }
            }
          }
        }
      }

      // Quick Links Menu
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
          Column(modifier = Modifier.fillMaxWidth()) {
            ProfileMenuItem(
              icon = Icons.Filled.ReceiptLong,
              title = "My Orders",
              onClick = onOrdersClick
            )
            Divider(color = FoodBasBorder, modifier = Modifier.padding(horizontal = 16.dp))
            ProfileMenuItem(
              icon = Icons.Filled.LocalOffer,
              title = "Offers & Discounts",
              onClick = onOffersClick
            )
            Divider(color = FoodBasBorder, modifier = Modifier.padding(horizontal = 16.dp))
            ProfileMenuItem(
              icon = Icons.Filled.HelpOutline,
              title = "Help & Support",
              onClick = { /* Help */ }
            )
            Divider(color = FoodBasBorder, modifier = Modifier.padding(horizontal = 16.dp))
            ProfileMenuItem(
              icon = Icons.Filled.Info,
              title = "About FoodBas Bardibas",
              subtitle = "Version 1.0",
              onClick = { /* About */ }
            )
          }
        }
      }
    }
  }
}

@Composable
fun ProfileMenuItem(
  icon: ImageVector,
  title: String,
  subtitle: String? = null,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(horizontal = 16.dp, vertical = 14.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = FoodBasRed,
      modifier = Modifier.size(20.dp)
    )
    Spacer(modifier = Modifier.width(12.dp))
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = FoodBasTextPrimary
      )
      if (subtitle != null) {
        Text(
          text = subtitle,
          fontSize = 11.sp,
          color = FoodBasTextSecondary
        )
      }
    }
    Icon(
      imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
      contentDescription = null,
      tint = FoodBasTextMuted,
      modifier = Modifier.size(18.dp)
    )
  }
}
