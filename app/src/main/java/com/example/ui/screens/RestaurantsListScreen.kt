package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Restaurant
import com.example.ui.components.RestaurantCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantsListScreen(
  restaurants: List<Restaurant>,
  selectedFilter: String,
  onFilterChange: (String) -> Unit,
  onRestaurantClick: (Restaurant) -> Unit,
  onToggleFavorite: (String) -> Unit,
  onBackClick: () -> Unit,
  onSearchClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val filters = listOf("All", "Top Rated", "Nearby", "Offers")

  val filteredRestaurants = when (selectedFilter) {
    "Top Rated" -> restaurants.filter { it.rating >= 4.5f }
    "Offers" -> restaurants.filter { it.discountBadge != null }
    else -> restaurants
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Restaurants",
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
        actions = {
          IconButton(onClick = onSearchClick) {
            Icon(
              imageVector = Icons.Filled.Search,
              contentDescription = "Search",
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
      // Location Indicator row
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .background(Color.White)
          .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        Icon(
          imageVector = Icons.Filled.LocationOn,
          contentDescription = "Location",
          tint = FoodBasRed,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Bardibas, Mahottari",
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold,
          color = FoodBasTextPrimary
        )
      }

      // Filter Chips Row
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color.White)
          .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(filters) { filter ->
          val isSelected = filter == selectedFilter
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(if (isSelected) FoodBasRed else FoodBasLightGray)
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

      Spacer(modifier = Modifier.height(8.dp))

      // Restaurant List
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        items(filteredRestaurants) { restaurant ->
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
