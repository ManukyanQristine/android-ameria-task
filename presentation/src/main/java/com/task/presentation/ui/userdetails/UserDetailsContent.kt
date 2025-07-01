package com.task.presentation.ui.userdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.task.presentation.R
import com.task.presentation.ui.theme.Black
import com.task.presentation.ui.theme.DarkGray
import com.task.presentation.ui.theme.Gray
import com.task.presentation.ui.theme.LightGray

@Composable
fun UserDetailsContent(user: UserDetailsUiModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.card_levation_6)),
            shape = RectangleShape
        ) {
            Column(
                modifier = Modifier
                    .background(LightGray)
                    .fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.padding_medium))
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.profile_image_size))
                        .align(Alignment.CenterHorizontally)
                        .clip(CircleShape),
                    model = user.avatarUrl,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height_12)))

                Text(
                    text = user.username,
                    style = MaterialTheme.typography.titleLarge,
                    color = DarkGray,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )

                user.location?.let {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.padding_medium))
                                .padding(end = dimensionResource(R.dimen.padding_extra_small)),
                            tint = Gray
                        )
                        Text(text = it, color = Gray)
                    }
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height_24)))

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(
                            R.string.user_details_followers_label,
                            user.followers
                        ),
                        color = DarkGray,
                        fontWeight = FontWeight.Medium,

                        )
                    VerticalDivider(
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.spacer_height_12))
                            .height(dimensionResource(R.dimen.padding_medium))
                            .width(dimensionResource(R.dimen.divider_thickness))
                    )
                    Text(
                        text = stringResource(
                            R.string.user_details_following_label,
                            user.following
                        ),
                        color = DarkGray,
                        fontWeight = FontWeight.Medium,
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    user.bio?.let {
                        ProfileInfoItem(
                            label = stringResource(R.string.user_details_bio_label),
                            value = it
                        )
                    }
                    user.publicRepos?.let {
                        ProfileInfoItem(
                            label = stringResource(R.string.user_details_public_repo_label),
                            value = it.toString()
                        )
                    }
                    user.publicGists?.let {
                        ProfileInfoItem(
                            label = stringResource(R.string.user_details_public_gists_label),
                            value = it.toString()
                        )
                    }
                    user.updatedAt?.let {
                        ProfileInfoItem(
                            label = stringResource(R.string.user_details_updated_at_label),
                            value = it
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileInfoItem(
    label: String,
    value: String
) {
    Column {
        Text(text = label, color = Black)
        Text(text = value, color = Gray)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
    }
}
