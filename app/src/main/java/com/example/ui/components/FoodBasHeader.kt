package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun FoodBasHeader(
  location: String = "Bardibas, Mahottari",
  cartCount: Int,
  searchQuery: String,
  onSearchChange: (String) -> Unit,
  onCartClick: () -> Unit,
  onProfileClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(Color.White)
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    // Top row: Brand Logo + Location + Cart & Profile
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      // FoodBas Brand Logo
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(FoodBasRed)
        ) {
          Text(
            text = "FB",
            color = Color.White,
            fontWeight = FontWeight.Black,
            fontSize = 15.sp
          )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
          Text(
            text = "FoodBas",
            color = FoodBasRed,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 22.sp
          )
          Text(
            text = "Good Food • Fast Delivery • Happy You",
            color = FoodBasTextSecondary,
            fontSize = 9.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }

      // Actions: Cart with Badge & Profile
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
          onClick = onCartClick,
          modifier = Modifier.size(40.dp)
        ) {
          BadgedBox(
            badge = {
              if (cartCount > 0) {
                Badge(
                  containerColor = FoodBasRed,
                  contentColor = Color.White
                ) {
                  Text(text = cartCount.toString(), fontSize = 10.sp)
                }
              }
            }
          ) {
            Icon(
              imageVector = Icons.Filled.ShoppingCart,
              contentDescription = "Cart",
              tint = FoodBasTextPrimary
            )
          }
        }

        Spacer(modifier = Modifier.width(4.dp))

        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(FoodBasLightGray)
            .clickable { onProfileClick() }
        ) {
          Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "Profile",
            tint = FoodBasDark,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Location Pill Dropdown
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .clip(RoundedCornerShape(8.dp))
        .clickable { /* Select location */ }
        .padding(vertical = 4.dp)
    ) {
      Icon(
        imageVector = Icons.Filled.LocationOn,
        contentDescription = "Location",
        tint = FoodBasRed,
        modifier = Modifier.size(16.dp)
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = location,
        color = FoodBasTextPrimary,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold
      )
      Icon(
        imageVector = Icons.Filled.KeyboardArrowDown,
        contentDescription = "Change Location",
        tint = FoodBasTextSecondary,
        modifier = Modifier.size(18.dp)
      )
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Search Bar
    Surface(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      color = FoodBasLightGray,
      tonalElevation = 0.dp
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Filled.Search,
          contentDescription = "Search",
          tint = FoodBasTextMuted,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = if (searchQuery.isEmpty()) "Search for restaurants, food, cuisines..." else searchQuery,
          color = if (searchQuery.isEmpty()) FoodBasTextMuted else FoodBasTextPrimary,
          fontSize = 13.sp,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }
    }
  }
}
