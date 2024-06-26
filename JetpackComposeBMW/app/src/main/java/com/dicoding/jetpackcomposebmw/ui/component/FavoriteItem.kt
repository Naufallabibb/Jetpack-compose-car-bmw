package com.dicoding.jetpackcomposebmw.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FavoriteItem(
    modifier: Modifier = Modifier,
    image: Int,
    name: String
) {
    Column(
        modifier = modifier,
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(),
        ) {
            Box(
                modifier = modifier
                    .height(240.dp)
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(240.dp)
                )
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ),
                                startY = 240f,
                            )
                        ),
                )
                Box(
                    modifier = modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomStart,
                ) {
                    Column(
                        modifier = modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = name,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}