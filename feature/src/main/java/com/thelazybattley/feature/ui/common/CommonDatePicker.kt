package com.thelazybattley.feature.ui.common

import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.thelazybattley.feature.R
import com.thelazybattley.feature.util.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonDatePicker(
    modifier: Modifier = Modifier,
    yearRange: IntRange,
    initialSelectedStartDateMillis: Long,
    initialSelectedEndDateMillis: Long,
    initialDisplayMode: DisplayMode,
    dateValidator: (Long) -> Boolean,
    onDismiss: () -> Unit,
    onSelectDate: (Long, Long) -> Unit
) {
    val state = rememberDateRangePickerState(
        initialDisplayMode = initialDisplayMode,
        initialSelectedStartDateMillis = initialSelectedStartDateMillis,
        initialSelectedEndDateMillis = initialSelectedEndDateMillis,
        yearRange = yearRange
    )

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
                }
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
