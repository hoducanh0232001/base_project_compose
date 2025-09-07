package com.example.basecomposeproject.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.basecomposeproject.R
import com.example.basecomposeproject.ui.theme.BackgroundPopup
import com.example.basecomposeproject.ui.theme.White
import com.example.basecomposeproject.ui.theme.White200
import com.example.basecomposeproject.ui.theme.White400


@Composable
fun ConfirmNoTitleDialog(
    modifier: Modifier = Modifier,
    content: String,
    textCancel: String = stringResource(R.string.cancel),
    isSingleButton: Boolean = true,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
        ),
        content = {
            Box(
                modifier = modifier
                    .padding(20.dp)
                    .background(
                        color = BackgroundPopup,
                        shape = RoundedCornerShape(8.dp),
                    ),
            ) {
                Column(modifier = Modifier.padding(vertical = 36.dp, horizontal = 16.dp)) {
                    Text(
                        text = content,
                        fontSize = 20.sp,
                        color = White,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButtonConfirmAndCancel(
                        modifier = Modifier
                            .fillMaxWidth(),
                        isSingleButton = isSingleButton,
                        onConfirmClick = {
                            onConfirmClick()
                        },
                        textCancel = textCancel,
                        onCancelClick = {
                            onCancelClick()
                        },
                    )
                }
            }
        },
    )
}

@Composable
fun EnterPasswordDialog(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onConfirmClick: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val password = remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
        ),
        content = {
            Box(
                modifier = modifier
                    .padding(20.dp)
                    .background(
                        color = BackgroundPopup,
                        shape = RoundedCornerShape(8.dp),
                    ),
            ) {
                Column(modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 20.sp,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = {
                            password.value = it
                        },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.cancel),
                                color = White400,
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = White,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .border(
                                color = White200,
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                            ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Transparent,
                            unfocusedBorderColor = Transparent,
                            errorBorderColor = Transparent,
                            errorContainerColor = Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            cursorColor = Color.Black,
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButtonConfirmAndCancel(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textCancel = stringResource(R.string.cancel),
                        isSingleButton = false,
                        onConfirmClick = {
                            onConfirmClick(password.value)
                        },
                        onCancelClick = {
                            onCancelClick()
                        },
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun ConfirmNoTitleDialogPreview() {
    EnterPasswordDialog(
        onCancelClick = { },
        onConfirmClick = { },
        onDismissRequest = { },
    )
}

@Preview
@Composable
private fun NoTitleDialogPreview() {
    ConfirmNoTitleDialog(
        content = stringResource(R.string.ok),
        onCancelClick = {},
        onConfirmClick = {},
        onDismissRequest = {},
    )
}
