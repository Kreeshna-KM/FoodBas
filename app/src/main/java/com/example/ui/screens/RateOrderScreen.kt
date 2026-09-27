package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
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
import com.example.R
import com.example.model.OrderItem
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateOrderScreen(
  order: OrderItem?,
  ratingStars: Int,
  reviewComment: String,
  isSubmitted: Boolean,
  onRatingChange: (Int) -> Unit,
  onCommentChange: (String) -> Unit,
  onSubmitReview: () -> Unit,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val ratingText = when (ratingStars) {
    5 -> "Excellent!"
    4 -> "Very Good!"
    3 -> "Good!"
    2 -> "Average!"
    1 -> "Poor!"
    else -> "Rate Experience"
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Rate Your Order",
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
      if (!isSubmitted) {
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
              onClick = onSubmitReview,
              modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(containerColor = FoodBasRed)
            ) {
              Text(
                text = "Submit Review",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
            }
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
        .padding(16.dp)
    ) {
      if (isSubmitted) {
        // Success state
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Filled.CheckCircle,
              contentDescription = null,
              tint = FoodBasGreen,
              modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
              text = "Thank You for Your Feedback!",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = FoodBasTextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Your rating helps us improve our service in Bardibas.",
              fontSize = 13.sp,
              color = FoodBasTextSecondary
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
              onClick = onBackClick,
              colors = ButtonDefaults.buttonColors(containerColor = FoodBasRed),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text("Back to Orders", color = Color.White)
            }
          }
        }
      } else {
        // Order Summary Card
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
            Image(
              painter = painterResource(id = order?.restaurantImageRes ?: R.drawable.nepali_thali),
              contentDescription = null,
              contentScale = ContentScale.Crop,
              modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column {
              Text(
                text = order?.restaurantName ?: "The Himalayan Kitchen",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = FoodBasTextPrimary
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = order?.summary ?: "Chicken Thali",
                fontSize = 13.sp,
                color = FoodBasTextSecondary
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Star Rating Card
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            // Stars Row
            Row(
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              for (star in 1..5) {
                IconButton(
                  onClick = { onRatingChange(star) },
                  modifier = Modifier.size(44.dp)
                ) {
                  Icon(
                    imageVector = if (star <= ratingStars) Icons.Filled.Star else Icons.Outlined.StarOutline,
                    contentDescription = "$star Stars",
                    tint = if (star <= ratingStars) FoodBasStarYellow else FoodBasBorder,
                    modifier = Modifier.size(36.dp)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = ratingText,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = FoodBasTextPrimary
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Review Text Area Card
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
              text = "Add a review (optional)",
              fontSize = 14.sp,
              fontWeight = FontWeight.SemiBold,
              color = FoodBasTextPrimary
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
              value = reviewComment,
              onValueChange = onCommentChange,
              placeholder = { Text("Tell us about your experience...", fontSize = 13.sp) },
              modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
              shape = RoundedCornerShape(12.dp),
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
