package com.example.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ui.theme.FoodBasRed
import com.example.ui.theme.FoodBasTextMuted
import com.example.viewmodel.BottomTab

@Composable
fun FoodBasBottomBar(
  currentTab: BottomTab,
  onTabSelected: (BottomTab) -> Unit,
  modifier: Modifier = Modifier
) {
  NavigationBar(
    modifier = modifier
      .fillMaxWidth()
      .navigationBarsPadding(),
    containerColor = Color.White,
    tonalElevation = 8.dp
  ) {
    NavigationBarItem(
      selected = currentTab == BottomTab.HOME,
      onClick = { onTabSelected(BottomTab.HOME) },
      icon = {
        Icon(
          imageVector = if (currentTab == BottomTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
          contentDescription = "Home"
        )
      },
      label = { Text("Home") },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = FoodBasRed,
        selectedTextColor = FoodBasRed,
        indicatorColor = FoodBasRed.copy(alpha = 0.12f),
        unselectedIconColor = FoodBasTextMuted,
        unselectedTextColor = FoodBasTextMuted
      )
    )

    NavigationBarItem(
      selected = currentTab == BottomTab.ORDERS,
      onClick = { onTabSelected(BottomTab.ORDERS) },
      icon = {
        Icon(
          imageVector = if (currentTab == BottomTab.ORDERS) Icons.Filled.ReceiptLong else Icons.Outlined.ReceiptLong,
          contentDescription = "Orders"
        )
      },
      label = { Text("Orders") },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = FoodBasRed,
        selectedTextColor = FoodBasRed,
        indicatorColor = FoodBasRed.copy(alpha = 0.12f),
        unselectedIconColor = FoodBasTextMuted,
        unselectedTextColor = FoodBasTextMuted
      )
    )

    NavigationBarItem(
      selected = currentTab == BottomTab.OFFERS,
      onClick = { onTabSelected(BottomTab.OFFERS) },
      icon = {
        Icon(
          imageVector = if (currentTab == BottomTab.OFFERS) Icons.Filled.LocalOffer else Icons.Outlined.LocalOffer,
          contentDescription = "Offers"
        )
      },
      label = { Text("Offers") },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = FoodBasRed,
        selectedTextColor = FoodBasRed,
        indicatorColor = FoodBasRed.copy(alpha = 0.12f),
        unselectedIconColor = FoodBasTextMuted,
        unselectedTextColor = FoodBasTextMuted
      )
    )

    NavigationBarItem(
      selected = currentTab == BottomTab.PROFILE,
      onClick = { onTabSelected(BottomTab.PROFILE) },
      icon = {
        Icon(
          imageVector = if (currentTab == BottomTab.PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
          contentDescription = "Profile"
        )
      },
      label = { Text("Profile") },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = FoodBasRed,
        selectedTextColor = FoodBasRed,
        indicatorColor = FoodBasRed.copy(alpha = 0.12f),
        unselectedIconColor = FoodBasTextMuted,
        unselectedTextColor = FoodBasTextMuted
      )
    )
  }
}
