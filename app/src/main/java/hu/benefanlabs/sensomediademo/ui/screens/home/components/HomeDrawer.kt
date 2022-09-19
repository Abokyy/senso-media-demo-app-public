package hu.benefanlabs.sensomediademo.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.benefanlabs.sensomediademo.R

enum class DrawerItem {
    SURVEY, LOGOUT
}

@ReadOnlyComposable
@Composable
fun DrawerItem.getDrawerItemTitle(): String {
    return when (this) {
        DrawerItem.SURVEY -> stringResource(R.string.survey)
        DrawerItem.LOGOUT -> stringResource(R.string.logout)
    }
}

@Composable
fun HomeDrawer(
    onItemClicked: (DrawerItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.default_horizontal_padding))
    ) {
        items(DrawerItem.values()) { item ->
            HomeDrawerItem(title = item.getDrawerItemTitle()) {
                onItemClicked(item)
            }
        }
    }
}

@Composable
fun HomeDrawerItem(
    title: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        ),
        onClick = onClick
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.secondary
        )
    }
}

@Preview
@Composable
private fun ItemPreview() {
    HomeDrawerItem(title = "Title") {

    }
}

@Preview
@Composable
private fun DrawerPreview() {
    HomeDrawer(onItemClicked = {})
}