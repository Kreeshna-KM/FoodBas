package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.OrderItem
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrdersScreen(
  orders: List<OrderItem>,
  selectedFilter: String,
  onFilterChange: (String) -> Unit,
  onRateOrder: (OrderItem) -> Unit,
  onOrderClick: (OrderItem) -> Unit,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val filters = listOf("All", "Delivered", "Cancelled")

  val filteredOrders = when (selectedFilter) {
    "Delivered" -> orders.filter { it.status == "Delivered" }
    "Cancelled" -> orders.filter { it.status == "Cancelled" }
    else -> orders
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "My Orders",
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
      // Filter Chips Row
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color.White)
          .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(filters) { filter ->
          val isSelected = filter == selectedFilter
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(if (isSelected) FoodBasRed else FoodBasLightGray)
              .clickable { onFilterChange(filter) }
              .padding(horizontal = 18.dp, vertical = 8.dp)
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

      // Orders List
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        items(filteredOrders) { order ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onOrderClick(order) },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
              ) {
                // Dish thumbnail
                Image(
                  painter = painterResource(id = order.restaurantImageRes),
                  contentDescription = order.restaurantName,
                  contentScale = ContentScale.Crop,
                  modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(10.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = order.restaurantName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = FoodBasTextPrimary
                  )
                  Spacer(modifier = Modifier.height(2.dp))
                  Text(
                    text = order.summary,
                    fontSize = 12.sp,
                    color = FoodBasTextSecondary
                  )
                  Spacer(modifier = Modifier.height(2.dp))
                  Text(
                    text = "Rs. ${order.totalAmount}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = FoodBasTextPrimary
                  )
                }

                // Status Badge
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFDCFCE7))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                  Text(
                    text = order.status,
                    color = Color(0xFF15803D),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                  )
                }
              }

              Spacer(modifier = Modifier.height(12.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = order.dateFormatted,
                  fontSize = 11.sp,
                  color = FoodBasTextSecondary
                )

                // Rate Order Button
                OutlinedButton(
                  onClick = { onRateOrder(order) },
                  shape = RoundedCornerShape(8.dp),
                  border = androidx.compose.foundation.BorderStroke(1.dp, FoodBasRed),
                  contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                  modifier = Modifier.height(32.dp)
                ) {
                  Text(
                    text = "Rate Order",
                    color = FoodBasRed,
                    fontSize = 12.sp,
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
}
