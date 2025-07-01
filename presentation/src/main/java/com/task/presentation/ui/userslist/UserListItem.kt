package com.task.presentation.ui.userslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.task.presentation.R
import com.task.presentation.ui.theme.DarkGray
import com.task.presentation.ui.theme.Gray

@Composable
fun UserListItem(
    user: UserUiModel,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_medium)
            )
    ) {
        val (avatarRef, nameRef, scoreRef, arrowRef) = createRefs()
        val context = LocalContext.current
        val imageSize = dimensionResource(R.dimen.profile_image_small_size)
        val marginStart = dimensionResource(R.dimen.padding_medium)
        val marginEnd = dimensionResource(R.dimen.padding_small)

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(user.avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
                .constrainAs(avatarRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(nameRef.start)
                }
        )

        Column(
            modifier = Modifier.constrainAs(nameRef) {
                start.linkTo(avatarRef.end, margin = marginStart)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(scoreRef.start, margin = marginEnd)
                width = Dimension.fillToConstraints
            }
        ) {
            Text(
                text = user.login,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    .copy(color = DarkGray)
            )
            Text(
                text = stringResource(R.string.user_item_id_label, user.id),
                modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_extra_small)),
                style = MaterialTheme.typography.bodySmall.copy(color = Gray)
            )
        }

        Text(
            text = stringResource(R.string.user_item_score_label, user.score),
            modifier = Modifier.constrainAs(scoreRef) {
                centerHorizontallyTo(parent)
                bottom.linkTo(nameRef.bottom)
            },
            style = MaterialTheme.typography.bodySmall.copy(color = Gray)
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Details",
            modifier = Modifier.constrainAs(arrowRef) {
                end.linkTo(parent.end)
                centerVerticallyTo(parent)
            },
            tint = Gray,
        )
    }
}


@Composable
@Preview
fun UserListItemPreview() {
    UserListItem(
        user = UserUiModel(
            id = 12345L,
            login = "john_doe",
            score = "1.0",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4"
        ),
        onClick = {}
    )
}