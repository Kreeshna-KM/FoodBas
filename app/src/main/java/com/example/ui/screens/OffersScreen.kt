package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.PromoOffer
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffersScreen(
  offers: List<PromoOffer>,
  selectedFilter: String,
  onFilterChange: (String) -> Unit,
  onUseCoupon: (String) -> Unit,
  onOrderNowClick: () -> Unit,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val filters = listOf("All Offers", "Restaurants", "Delivery", "Special")

  val filteredOffers = when (selectedFilter) {
    "Restaurants" -> offers.filter { it.category == "Restaurants" }
    "Delivery" -> offers.filter { it.category == "Delivery" }
    "Special" -> offers.filter { it.category == "Special" }
    else -> offers
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Offers & Discounts",
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
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .background(FoodBasScreenBg)
    ) {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // Hero 50% OFF Promo Banner (matches the image!)
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(20.dp))
              .background(
                Brush.horizontalGradient(
                  colors = listOf(Color(0xFFE23744), Color(0xFFC72431))
                )
              )
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1.2f)) {
                Text(
                  text = "UP TO",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = Color.White.copy(alpha = 0.9f)
                )
                Text(
                  text = "50% OFF",
                  fontSize = 26.sp,
                  fontWeight = FontWeight.Black,
                  color = Color.White
                )
                Text(
                  text = "On Your First Order",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Medium,
                  color = Color.White.copy(alpha = 0.95f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                  onClick = onOrderNowClick,
                  colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                  shape = RoundedCornerShape(10.dp),
                  contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                  Text(
                    text = "Order Now",
                    color = FoodBasRed,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                  )
                }
              }

              Spacer(modifier = Modifier.width(10.dp))

              Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(0.8f)
              ) {
                Image(
                  painter = painterResource(id = R.drawable.hero_biryani_bowl),
                  contentDescription = "Biryani",
                  contentScale = ContentScale.Crop,
                  modifier = Modifier
                    .size(105.dp)
                    .clip(CircleShape)
                )
              }
            }
          }
        }

        // Filter Pills
        item {
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(filters) { filter ->
              val isSelected = filter == selectedFilter
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(20.dp))
                  .background(if (isSelected) FoodBasRed else Color.White)
                  .border(
                    width = 1.dp,
                    color = if (isSelected) FoodBasRed else FoodBasBorder,
                    shape = RoundedCornerShape(20.dp)
                  )
                  .clickable { onFilterChange(filter) }
                  .padding(horizontal = 16.dp, vertical = 8.dp)
              ) {
                Text(
                  text = filter,
                  fontSize = 13.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) Color.White else FoodBasTextSecondary
                )
              }
            }
          }
        }

        // Offers Cards List
        items(filteredOffers) { offer ->
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
              // Icon
              Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                  .size(44.dp)
                  .clip(CircleShape)
                  .background(
                    if (offer.isFreeDelivery) Color(0xFFDCFCE7) else FoodBasRedLight
                  )
              ) {
                Icon(
                  imageVector = if (offer.isFreeDelivery) Icons.Filled.TwoWheeler else Icons.Filled.Percent,
                  contentDescription = null,
                  tint = if (offer.isFreeDelivery) Color(0xFF15803D) else FoodBasRed,
                  modifier = Modifier.size(22.dp)
                )
              }

              Spacer(modifier = Modifier.width(14.dp))

              // Title and Subtitle
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = offer.title,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = FoodBasTextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = offer.subtitle,
                  fontSize = 12.sp,
                  color = FoodBasTextSecondary
                )
              }

              Spacer(modifier = Modifier.width(8.dp))

              // Use Code Button (green text box with dashed border)
              OutlinedButton(
                onClick = { onUseCoupon(offer.code) },
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF15803D)),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                modifier = Modifier.height(34.dp)
              ) {
                Text(
                  text = "Use ${offer.code}",
                  color = Color(0xFF15803D),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }
    }
  }
}
