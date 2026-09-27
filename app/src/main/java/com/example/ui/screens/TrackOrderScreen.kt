package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.OrderItem
import com.example.model.OrderProgressStage
import com.example.ui.components.OrderMapRouteView
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackOrderScreen(
  order: OrderItem?,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Track Your Order",
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
        .verticalScroll(scrollState)
    ) {
      // Horizontal Progress Stepper Card
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Top
        ) {
          TrackStepNode(
            icon = Icons.Filled.ShoppingCart,
            title = "Order Placed",
            time = "12:45 PM",
            isDone = true
          )
          TrackStepNode(
            icon = Icons.Filled.ShoppingBag,
            title = "Preparing",
            time = "12:50 PM",
            isDone = true
          )
          TrackStepNode(
            icon = Icons.Filled.TwoWheeler,
            title = "Out for Delivery",
            time = "01:15 PM",
            isDone = true,
            isActive = true
          )
          TrackStepNode(
            icon = Icons.Filled.Check,
            title = "Delivered",
            time = "01:35 PM",
            isDone = false
          )
        }
      }

      // Live Map View Card
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
      ) {
        OrderMapRouteView()
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Delivery Partner Card
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Driver Avatar
            Image(
              painter = painterResource(id = R.drawable.driver_ramesh),
              contentDescription = "Delivery Partner",
              contentScale = ContentScale.Crop,
              modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Partner details
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = order?.deliveryPartnerName ?: "Ramesh B.",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = FoodBasTextPrimary
              )
              Text(
                text = "Delivery Partner",
                fontSize = 12.sp,
                color = FoodBasTextSecondary
              )
              Spacer(modifier = Modifier.height(2.dp))
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Filled.Star,
                  contentDescription = null,
                  tint = FoodBasStarYellow,
                  modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                  text = "4.8 (124)",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium,
                  color = FoodBasTextSecondary
                )
              }
            }

            // Call Button
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFDCFCE7))
                .clickable {
                  val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+9779812345678"))
                  context.startActivity(intent)
                }
            ) {
              Icon(
                imageVector = Icons.Filled.Call,
                contentDescription = "Call Driver",
                tint = Color(0xFF15803D),
                modifier = Modifier.size(20.dp)
              )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Chat Button
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFDCFCE7))
                .clickable {
                  val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:+9779812345678"))
                  context.startActivity(intent)
                }
            ) {
              Icon(
                imageVector = Icons.Filled.Chat,
                contentDescription = "Chat with Driver",
                tint = Color(0xFF15803D),
                modifier = Modifier.size(20.dp)
              )
            }
          }

          Divider(color = FoodBasBorder, modifier = Modifier.padding(vertical = 12.dp))

          // ETA Row
          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Filled.TwoWheeler,
              contentDescription = null,
              tint = Color(0xFF15803D),
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Arriving in ",
              fontSize = 13.sp,
              color = FoodBasTextSecondary
            )
            Text(
              text = "20 mins",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF15803D)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
fun TrackStepNode(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  time: String,
  isDone: Boolean,
  isActive: Boolean = false
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.width(72.dp)
  ) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .size(34.dp)
        .clip(CircleShape)
        .background(
          if (isDone || isActive) Color(0xFF16A34A) else FoodBasLightGray
        )
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = if (isDone || isActive) Color.White else FoodBasTextMuted,
        modifier = Modifier.size(17.dp)
      )
    }

    Spacer(modifier = Modifier.height(6.dp))

    Text(
      text = title,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      color = FoodBasTextPrimary,
      maxLines = 1
    )

    Text(
      text = time,
      fontSize = 9.sp,
      color = FoodBasTextSecondary
    )
  }
}
