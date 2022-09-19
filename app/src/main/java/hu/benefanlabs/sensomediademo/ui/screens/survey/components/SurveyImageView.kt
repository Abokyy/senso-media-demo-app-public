package hu.benefanlabs.sensomediademo.ui.screens.survey.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun SurveyImageView(
    imageUrl: String
) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(153.dp)
            .clip(
                RoundedCornerShape(
                    bottomEnd = 27.dp,
                    bottomStart = 50.dp,
                    topEnd = 10.dp,
                    topStart = 20.dp
                )
            ),
        contentScale = ContentScale.Crop,
        painter = rememberImagePainter(imageUrl),
        contentDescription = null
    )
}