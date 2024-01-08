package com.thelazybattley.common.ui

import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.thelazybattley.common.util.Colors
import com.thelazybattley.common.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonDatePicker(
    state: DateRangePickerState,
    modifier: Modifier = Modifier,
    dateValidator: (Long) -> Boolean,
    onDismiss: () -> Unit,
    onSelectDate: (Long, Long) -> Unit,
) {
    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    onSelectDate(
                        state.selectedStartDateMillis!!,
                        state.selectedEndDateMillis!!
                    )
                    onDismiss()
                },
                enabled = state.selectedStartDateMillis != null && state.selectedEndDateMillis != null
            ) {
                Text(
                    text = stringResource(R.string.select_date),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Colors.Black
                    )
                )
            }
        },
        colors = DatePickerDefaults.colors(),
    ) {
        DateRangePicker(
            state = state,
            showModeToggle = false,
            dateValidator = {
                dateValidator(it)
            }
        )
    }
}
