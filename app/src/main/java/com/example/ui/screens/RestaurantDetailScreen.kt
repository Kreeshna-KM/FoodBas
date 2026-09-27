package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.TwoWheeler
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.model.CartItem
import com.example.model.MenuItem
import com.example.model.Restaurant
import com.example.ui.theme.*

@Composable
fun RestaurantDetailScreen(
  restaurant: Restaurant,
  menuItems: List<MenuItem>,
  cartItems: List<CartItem>,
  onAddToCart: (MenuItem) -> Unit,
  onDecrementCart: (String) -> Unit,
  onToggleFavorite: () -> Unit,
  onBackClick: () -> Unit,
  onViewCartClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf("Menu") }
  var selectedCategory by remember { mutableStateOf("All Items") }

  val categories = listOf("All Items", "Nepali Thali", "Momos", "Buff Momos", "Snacks", "Beverages", "Chinese", "Pizza")

  val filteredItems = if (selectedCategory == "All Items") {
    menuItems
  } else {
    menuItems.filter { it.category == selectedCategory }
  }

  val totalCartQty = cartItems.sumOf { it.quantity }
  val totalCartAmount = cartItems.sumOf { it.item.price * it.quantity }

  Scaffold(
    bottomBar = {
      if (totalCartQty > 0) {
        Surface(
          tonalElevation = 8.dp,
          color = Color.White,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(FoodBasRed)
              .clickable { onViewCartClick() }
              .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "$totalCartQty items | Rs. $totalCartAmount",
                  color = Color.White,
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp
                )
                Text(
                  text = "Extra charges may apply",
                  color = Color.White.copy(alpha = 0.8f),
                  fontSize = 10.sp
                )
              }
            }

            Text(
              text = "View Cart >",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }
        }
      }
    },
    modifier = modifier.fillMaxSize()
  ) { padding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .background(FoodBasScreenBg)
    ) {
      // Hero Photo with Navigation Buttons
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
      ) {
        Image(
          painter = painterResource(id = restaurant.imageRes),
          contentDescription = restaurant.name,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.25f))
        )

        // Back Button
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
            .size(36.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { onBackClick() }
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = FoodBasTextPrimary,
            modifier = Modifier.size(20.dp)
          )
        }

        // Favorite Button
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(end = 16.dp, top = 16.dp)
            .size(36.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { onToggleFavorite() }
        ) {
          Icon(
            imageVector = if (restaurant.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (restaurant.isFavorite) FoodBasRed else FoodBasTextSecondary,
            modifier = Modifier.size(20.dp)
          )
        }
      }

      // Restaurant Info Header Card
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color.White)
          .padding(16.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Top
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = restaurant.name,
              fontSize = 20.sp,
              fontWeight = FontWeight.Bold,
              color = FoodBasTextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Rating & Time Row
            Row(verticalAlignment = Alignment.CenterVertically) {
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
              Spacer(modifier = Modifier.width(10.dp))
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

            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = restaurant.cuisines,
              color = FoodBasTextSecondary,
              fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Free Delivery & 30% OFF badges
            Row(verticalAlignment = Alignment.CenterVertically) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(FoodBasGreenLight)
                  .padding(horizontal = 8.dp, vertical = 3.dp)
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
                  fontWeight = FontWeight.SemiBold
                )
              }

              if (restaurant.discountBadge != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFDCFCE7))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                  Text(
                    text = restaurant.discountBadge,
                    color = Color(0xFF15803D),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Main Tabs: Menu | Reviews | Info
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceAround
        ) {
          listOf("Menu", "Reviews", "Info").forEach { tab ->
            val isSelected = tab == selectedTab
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              modifier = Modifier
                .clickable { selectedTab = tab }
                .padding(vertical = 4.dp)
            ) {
              Text(
                text = tab,
                color = if (isSelected) FoodBasRed else FoodBasTextSecondary,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 14.sp
              )
              Spacer(modifier = Modifier.height(4.dp))
              Box(
                modifier = Modifier
                  .width(40.dp)
                  .height(2.dp)
                  .background(if (isSelected) FoodBasRed else Color.Transparent)
              )
            }
          }
        }
      }

      // Content area
      if (selectedTab == "Menu") {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
          // Left Sidebar Categories (matches the image layout!)
          Column(
            modifier = Modifier
              .width(96.dp)
              .fillMaxHeight()
              .background(Color(0xFFF9FAFB))
              .padding(vertical = 8.dp)
          ) {
            categories.forEach { cat ->
              val isSelected = cat == selectedCategory
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .background(if (isSelected) FoodBasRed else Color.Transparent)
                  .clickable { selectedCategory = cat }
                  .padding(horizontal = 10.dp, vertical = 12.dp)
              ) {
                Text(
                  text = cat,
                  fontSize = 11.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) Color.White else FoodBasTextSecondary,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
              }
            }
          }

          // Right Column: Food Menu Items
          LazyColumn(
            modifier = Modifier
              .weight(1f)
              .fillMaxHeight()
              .background(Color.White)
              .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            item {
              Text(
                text = "Popular Items",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = FoodBasTextPrimary
              )
            }

            items(filteredItems) { item ->
              val cartEntry = cartItems.find { it.item.id == item.id }
              MenuItemRow(
                item = item,
                cartQuantity = cartEntry?.quantity ?: 0,
                onAdd = { onAddToCart(item) },
                onDecrement = { onDecrementCart(item.id) }
              )
            }
          }
        }
      } else if (selectedTab == "Reviews") {
        // Reviews tab
        Column(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
        ) {
          Text(
            text = "Customer Reviews",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = FoodBasTextPrimary
          )
          Spacer(modifier = Modifier.height(12.dp))
          ReviewItem(
            name = "Aman Shrestha",
            rating = 5,
            comment = "Best Chicken Thali in Bardibas! Super fast delivery.",
            date = "Yesterday"
          )
          Divider(color = FoodBasBorder, modifier = Modifier.padding(vertical = 10.dp))
          ReviewItem(
            name = "Pooja Chaudhary",
            rating = 5,
            comment = "Steam momos are delicious and piping hot.",
            date = "3 days ago"
          )
        }
      } else {
        // Info tab
        Column(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
        ) {
          Text(
            text = "About ${restaurant.name}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = FoodBasTextPrimary
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Located in the heart of Bardibas, Mahottari. Serving authentic Himalayan delights, continental cuisine, and traditional Nepali khana thali prepared fresh with local ingredients.",
            fontSize = 13.sp,
            color = FoodBasTextSecondary,
            lineHeight = 18.sp
          )
          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = "📍 Address: Bardibas Main Chowk, Mahottari",
            fontSize = 13.sp,
            color = FoodBasTextPrimary
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "🕒 Hours: 09:00 AM - 10:30 PM",
            fontSize = 13.sp,
            color = FoodBasTextPrimary
          )
        }
      }
    }
  }
}

@Composable
fun MenuItemRow(
  item: MenuItem,
  cartQuantity: Int,
  onAdd: () -> Unit,
  onDecrement: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(Color.White)
      .padding(vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Thumbnail Image
    Image(
      painter = painterResource(id = item.imageRes),
      contentDescription = item.name,
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .size(64.dp)
        .clip(RoundedCornerShape(8.dp))
    )

    Spacer(modifier = Modifier.width(10.dp))

    // Title and Price
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = item.name,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        color = FoodBasTextPrimary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = "Rs. ${item.price}",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = FoodBasTextPrimary
      )
    }

    Spacer(modifier = Modifier.width(6.dp))

    // Add / Stepper Button (Red +Add button from image)
    if (cartQuantity == 0) {
      Button(
        onClick = onAdd,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = FoodBasRed),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
        modifier = Modifier.height(30.dp)
      ) {
        Text(
          text = "+ Add",
          color = Color.White,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold
        )
      }
    } else {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .background(FoodBasRed)
          .padding(horizontal = 4.dp, vertical = 2.dp)
      ) {
        IconButton(
          onClick = onDecrement,
          modifier = Modifier.size(24.dp)
        ) {
          Icon(
            imageVector = Icons.Filled.Remove,
            contentDescription = "Remove",
            tint = Color.White,
            modifier = Modifier.size(14.dp)
          )
        }
        Text(
          text = "$cartQuantity",
          color = Color.White,
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp,
          modifier = Modifier.padding(horizontal = 4.dp)
        )
        IconButton(
          onClick = onAdd,
          modifier = Modifier.size(24.dp)
        ) {
          Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add",
            tint = Color.White,
            modifier = Modifier.size(14.dp)
          )
        }
      }
    }
  }
}

@Composable
fun ReviewItem(
  name: String,
  rating: Int,
  comment: String,
  date: String
) {
  Column {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
      Text(text = date, color = FoodBasTextSecondary, fontSize = 11.sp)
    }
    Spacer(modifier = Modifier.height(2.dp))
    Row {
      repeat(rating) {
        Icon(
          imageVector = Icons.Filled.Star,
          contentDescription = null,
          tint = FoodBasStarYellow,
          modifier = Modifier.size(14.dp)
        )
      }
    }
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = comment, fontSize = 12.sp, color = FoodBasTextSecondary)
  }
}
