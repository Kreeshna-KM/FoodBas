package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.FoodCategory
import com.example.model.Restaurant
import com.example.ui.components.FoodBasHeader
import com.example.ui.components.RestaurantCard
import com.example.ui.theme.*

@Composable
fun HomeScreen(
  categories: List<FoodCategory>,
  restaurants: List<Restaurant>,
  cartCount: Int,
  searchQuery: String,
  onSearchChange: (String) -> Unit,
  onCategoryClick: (FoodCategory) -> Unit,
  onRestaurantClick: (Restaurant) -> Unit,
  onToggleFavorite: (String) -> Unit,
  onViewAllRestaurants: () -> Unit,
  onCartClick: () -> Unit,
  onProfileClick: () -> Unit,
  onOrderNowClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(FoodBasScreenBg)
  ) {
    // Header
    FoodBasHeader(
      location = "Bardibas, Mahottari",
      cartCount = cartCount,
      searchQuery = searchQuery,
      onSearchChange = onSearchChange,
      onCartClick = onCartClick,
      onProfileClick = onProfileClick
    )

    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      contentPadding = PaddingValues(bottom = 24.dp)
    ) {
      // Hero Banner
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
              Brush.horizontalGradient(
                colors = listOf(Color(0xFF1E222B), Color(0xFF2D1B1B))
              )
            )
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Text left column
            Column(
              modifier = Modifier.weight(1.3f)
            ) {
              Text(
                text = buildAnnotatedString {
                  append("Delicious Food,\n")
                  withStyle(style = SpanStyle(color = FoodBasRed, fontWeight = FontWeight.Black)) {
                    append("Delivered")
                  }
                  append(" to Your Door")
                },
                color = Color.White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 23.sp
              )

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = "Order from your favorite restaurants in Bardibas and get it delivered fresh and fast!",
                color = Color(0xFFD1D5DB),
                fontSize = 11.sp,
                lineHeight = 15.sp
              )

              Spacer(modifier = Modifier.height(12.dp))

              Button(
                onClick = onOrderNowClick,
                colors = ButtonDefaults.buttonColors(containerColor = FoodBasRed),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
              ) {
                Text(
                  text = "Order Now >",
                  color = Color.White,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Dish image right column
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier.weight(0.9f)
            ) {
              Image(
                painter = painterResource(id = R.drawable.hero_biryani_bowl),
                contentDescription = "Biryani Bowl",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                  .size(110.dp)
                  .clip(CircleShape)
              )
            }
          }
        }
      }

      // Feature Badges Row (Fast Delivery, Safe & Secure, Top Rated)
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          FeatureBadge(
            icon = Icons.Filled.ElectricBolt,
            title = "Fast Delivery",
            color = FoodBasOrange
          )
          FeatureBadge(
            icon = Icons.Filled.Security,
            title = "Safe & Secure",
            color = FoodBasGreen
          )
          FeatureBadge(
            icon = Icons.Filled.Star,
            title = "Top Rated",
            color = FoodBasStarYellow
          )
        }
      }

      // Popular Categories Section
      item {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
          text = "Popular Categories",
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = FoodBasTextPrimary,
          modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
          contentPadding = PaddingValues(horizontal = 16.dp),
          horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          items(categories) { category ->
            CategoryItem(
              category = category,
              onClick = { onCategoryClick(category) }
            )
          }
        }
      }

      // Top Restaurants Header
      item {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Top Restaurants",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = FoodBasTextPrimary
          )

          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onViewAllRestaurants() }
          ) {
            Text(
              text = "View All",
              fontSize = 13.sp,
              color = FoodBasRed,
              fontWeight = FontWeight.SemiBold
            )
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = null,
              tint = FoodBasRed,
              modifier = Modifier.size(14.dp)
            )
          }
        }
        Spacer(modifier = Modifier.height(10.dp))
      }

      // List of Top Restaurants
      items(restaurants) { restaurant ->
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
          RestaurantCard(
            restaurant = restaurant,
            onClick = { onRestaurantClick(restaurant) },
            onToggleFavorite = { onToggleFavorite(restaurant.id) }
          )
        }
      }
    }
  }
}

@Composable
fun FeatureBadge(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  color: Color
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .clip(RoundedCornerShape(8.dp))
      .background(Color.White)
      .padding(horizontal = 10.dp, vertical = 6.dp)
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = color,
      modifier = Modifier.size(14.dp)
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      text = title,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      color = FoodBasTextPrimary
    )
  }
}

@Composable
fun CategoryItem(
  category: FoodCategory,
  onClick: () -> Unit
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .clickable { onClick() }
      .width(62.dp)
  ) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .size(56.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)
        .padding(4.dp)
    ) {
      if (category.imageRes != null) {
        Image(
          painter = painterResource(id = category.imageRes),
          contentDescription = category.name,
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
        )
      } else {
        Text(
          text = category.iconEmoji,
          fontSize = 24.sp
        )
      }
    }
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = category.name,
      fontSize = 11.sp,
      fontWeight = FontWeight.Medium,
      color = FoodBasTextPrimary,
      maxLines = 1
    )
  }
}
