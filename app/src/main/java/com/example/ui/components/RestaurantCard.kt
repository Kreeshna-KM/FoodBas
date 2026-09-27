package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.TwoWheeler
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Restaurant
import com.example.ui.theme.*

@Composable
fun RestaurantCard(
  restaurant: Restaurant,
  onClick: () -> Unit,
  onToggleFavorite: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clickable { onClick() },
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column {
      // Image Banner with overlay badges & favorite button
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(150.dp)
      ) {
        Image(
          painter = painterResource(id = restaurant.imageRes),
          contentDescription = restaurant.name,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        // Discount Tag (if any)
        if (restaurant.discountBadge != null) {
          Box(
            modifier = Modifier
              .align(Alignment.TopStart)
              .padding(10.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(FoodBasRed)
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = restaurant.discountBadge,
              color = Color.White,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        // Favorite Toggle Icon Button
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(10.dp)
            .size(34.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.9f))
            .clickable { onToggleFavorite() }
        ) {
          Icon(
            imageVector = if (restaurant.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (restaurant.isFavorite) FoodBasRed else FoodBasTextSecondary,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      // Details section
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp)
      ) {
        // Restaurant Name
        Text(
          text = restaurant.name,
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = FoodBasTextPrimary,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Rating and Delivery Time Row
        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Green rating pill
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(FoodBasGreen)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(12.dp)
              )
              Spacer(modifier = Modifier.width(3.dp))
              Text(
                text = "${restaurant.rating}",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Spacer(modifier = Modifier.width(6.dp))

          Text(
            text = "(${restaurant.reviewCount})",
            color = FoodBasTextSecondary,
            fontSize = 11.sp
          )

          Spacer(modifier = Modifier.width(12.dp))

          // Time icon and delivery duration
          Icon(
            imageVector = Icons.Filled.Schedule,
            contentDescription = null,
            tint = FoodBasTextSecondary,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = restaurant.deliveryTime,
            color = FoodBasTextSecondary,
            fontSize = 11.sp
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Cuisines / Category tags
        Text(
          text = restaurant.cuisines,
          color = FoodBasTextSecondary,
          fontSize = 12.sp,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        // Free Delivery badge
        if (restaurant.hasFreeDelivery) {
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(FoodBasGreenLight)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Icon(
              imageVector = Icons.Outlined.TwoWheeler,
              contentDescription = null,
              tint = Color(0xFF15803D),
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Free Delivery",
              color = Color(0xFF15803D),
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }
      }
    }
  }
}
