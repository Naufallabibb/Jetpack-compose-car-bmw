package com.dicoding.jetpackcomposebmw.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.jetpackcomposebmw.R


@Composable
fun ItemDetail(
    modifier: Modifier = Modifier,
    id: String,
    name: String,
    description: String,
    color: String,
    type: String,
    image: Int,
    highlight: Boolean,
    onBackClick: () -> Unit,
    onUpdateFavorite: (String) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(345.dp)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(40.dp))
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(40.dp))
            )
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = modifier
                    .padding(start = 16.dp, top = 24.dp)
                    .clickable { onBackClick() }
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .drawWithContent {
                        drawContent()
                    }
            )
            FloatingActionButton(
                onClick = {
                    onUpdateFavorite(id)
                },
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp),
                contentColor = if (highlight) Color.Red else MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = if (highlight) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                    contentDescription = stringResource(R.string.favorite),
                    tint = if (highlight) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Card(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 48.sp
                )
                Text(
                    text = description,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = modifier
                        .padding(vertical = 8.dp)
                )
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.color),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = color,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.type),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = type,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}