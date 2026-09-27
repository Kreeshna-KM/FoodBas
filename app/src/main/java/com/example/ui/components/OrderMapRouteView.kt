package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.FoodBasRed

@Composable
fun OrderMapRouteView(
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 0.95f,
    targetValue = 1.05f,
    animationSpec = infiniteRepeatable(
      animation = tween(900, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "scale"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(260.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(Color(0xFFE8F1F5))
  ) {
    // Stylized Street Map Canvas
    Canvas(modifier = Modifier.fillMaxSize()) {
      val w = size.width
      val h = size.height

      // City blocks background tint
      drawRect(Color(0xFFE4EFF2))

      // Water body / canal accent
      val waterPath = Path().apply {
        moveTo(0f, h * 0.15f)
        cubicTo(w * 0.3f, h * 0.05f, w * 0.7f, h * 0.25f, w, h * 0.18f)
        lineTo(w, 0f)
        lineTo(0f, 0f)
        close()
      }
      drawPath(waterPath, Color(0xFFC7E2F0))

      // City streets network (white roads)
      val roadColor = Color.White
      val roadStroke = 18f

      // Street lines
      drawLine(roadColor, Offset(0f, h * 0.45f), Offset(w, h * 0.45f), strokeWidth = roadStroke)
      drawLine(roadColor, Offset(0f, h * 0.75f), Offset(w, h * 0.75f), strokeWidth = roadStroke)
      drawLine(roadColor, Offset(w * 0.25f, 0f), Offset(w * 0.25f, h), strokeWidth = roadStroke)
      drawLine(roadColor, Offset(w * 0.65f, 0f), Offset(w * 0.65f, h), strokeWidth = roadStroke)
      drawLine(roadColor, Offset(w * 0.85f, 0f), Offset(w * 0.85f, h), strokeWidth = roadStroke)

      // Diagonal arterial roads
      drawLine(roadColor, Offset(0f, h * 0.9f), Offset(w, h * 0.3f), strokeWidth = 24f)
      drawLine(roadColor, Offset(w * 0.1f, 0f), Offset(w * 0.9f, h), strokeWidth = 14f)

      // Active Delivery Route (Bold Red Line)
      val routePath = Path().apply {
        moveTo(w * 0.12f, h * 0.68f) // Bardibas origin
        lineTo(w * 0.38f, h * 0.62f)
        lineTo(w * 0.58f, h * 0.48f)
        lineTo(w * 0.72f, h * 0.48f)
        lineTo(w * 0.82f, h * 0.65f) // Your location
      }

      // Outer glow/casing for route
      drawPath(
        routePath,
        color = Color(0x33E23744),
        style = Stroke(width = 16f, cap = StrokeCap.Round, join = StrokeJoin.Round)
      )

      // Red main route
      drawPath(
        routePath,
        color = FoodBasRed,
        style = Stroke(width = 8f, cap = StrokeCap.Round, join = StrokeJoin.Round)
      )

      // Waypoint dots
      drawCircle(Color.White, radius = 7f, center = Offset(w * 0.38f, h * 0.62f))
      drawCircle(FoodBasRed, radius = 4f, center = Offset(w * 0.38f, h * 0.62f))

      drawCircle(Color.White, radius = 7f, center = Offset(w * 0.58f, h * 0.48f))
      drawCircle(FoodBasRed, radius = 4f, center = Offset(w * 0.58f, h * 0.48f))
    }

    // Origin Pin: "Bardibas"
    Box(
      modifier = Modifier
        .align(Alignment.BottomStart)
        .padding(start = 16.dp, bottom = 48.dp)
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = "Bardibas",
            color = Color(0xFF111827),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
        Icon(
          imageVector = Icons.Filled.LocationOn,
          contentDescription = "Origin",
          tint = Color(0xFF16A34A),
          modifier = Modifier.size(24.dp)
        )
      }
    }

    // Destination Pin: "Your Location"
    Box(
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(end = 24.dp, bottom = 38.dp)
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
          imageVector = Icons.Filled.LocationOn,
          contentDescription = "Destination",
          tint = FoodBasRed,
          modifier = Modifier.size(28.dp)
        )
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = "Your Location",
            color = FoodBasRed,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }

    // Live Courier Bike marker (near center of route)
    Box(
      modifier = Modifier
        .align(Alignment.Center)
        .offset(x = 40.dp, y = (-10).dp)
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // "Live Location" badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(FoodBasRed)
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = "Live Location",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Bike icon bubble with shadow & pulse
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(2.dp, FoodBasRed, CircleShape)
        ) {
          Icon(
            imageVector = Icons.Filled.TwoWheeler,
            contentDescription = "Driver Location",
            tint = FoodBasRed,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }
  }
}
