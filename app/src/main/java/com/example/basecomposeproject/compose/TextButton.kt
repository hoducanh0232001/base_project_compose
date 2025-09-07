package com.example.basecomposeproject.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basecomposeproject.R
import com.example.basecomposeproject.ui.theme.Black2
import com.example.basecomposeproject.ui.theme.Gray12
import com.example.basecomposeproject.ui.theme.Gray7
import com.example.basecomposeproject.ui.theme.Gray9

import com.example.core.compose.safeClickable

@Composable
fun TextButton(
    text: String,
    enabled: Boolean = true,
    color: Color = Black,
    backgroundColor: Color = White,
    icon: Painter? = null,
    shape: Shape = RoundedCornerShape(16.dp),
    contentPadding: PaddingValues = PaddingValues(
        start = 0.dp,
        top = 14.dp,
        end = 0.dp,
        bottom = 14.dp,
    ),
    fontSize: TextUnit = 24.sp,
    letterSpacing: TextUnit =TextUnit.Unspecified,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape)
            .safeClickable(enabled = enabled) { onClick() }
            .background(backgroundColor)
            .padding(contentPadding),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Image(
                    painter = icon,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                modifier = Modifier,
                text = text,
                color = color,
                fontSize = fontSize,
                letterSpacing = letterSpacing,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun TextButtonConfirmAndCancel(
    modifier: Modifier = Modifier,
    isSingleButton: Boolean = true,
    textCancel: String = stringResource(R.string.cancel),
    textConfirm: String = stringResource(R.string.ok),
    onConfirmClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isSingleButton) {
            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 50.dp),
                text = textConfirm,
                shape = RoundedCornerShape(16.dp),
                fontSize = 24.sp,
                color = Black2,
                backgroundColor = White,
                contentPadding = PaddingValues(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 0.dp,
                ),
                onClick = onConfirmClick,
            )
        } else {
            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 50.dp),
                text = textCancel,
                shape = RoundedCornerShape(16.dp),
                fontSize = 24.sp,
                color = White,
                backgroundColor = Gray9,
                contentPadding = PaddingValues(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 0.dp,
                ),
                onClick = onCancelClick,
            )

            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 50.dp),
                text = textConfirm,
                shape = RoundedCornerShape(16.dp),
                fontSize = 24.sp,
                color = Black2,
                backgroundColor = White,
                contentPadding = PaddingValues(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 0.dp,
                ),
                onClick = onConfirmClick,
            )
        }
    }
}

@Composable
fun TextTitleIcon(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter,
    fontSize: TextUnit = 24.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    spaceWidth: Dp = 12.dp,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(40.dp),
            contentScale = ContentScale.Fit,
            painter = icon,
            contentDescription = "icon",
        )
        Spacer(modifier = Modifier.width(spaceWidth))
        Text(
            text = text,
            color = White,
            fontWeight = fontWeight,
            letterSpacing = letterSpacing,
            fontSize = fontSize,
        )
    }
}

@Preview
@Composable
private fun TextTitleIconPreview() {
    TextTitleIcon(
        text = "Title",
        icon = painterResource(id = R.drawable.ic_launcher_foreground),
    )
}

@Preview
@Composable
private fun TextButtonPreview() {
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        text = stringResource(R.string.no),
        color = Gray12,
        backgroundColor = Gray7,
    )
}

@Preview
@Composable
private fun TextButtonConfirmAndCancelPreview() {
    TextButtonConfirmAndCancel(
        isSingleButton = false,
    )
}
