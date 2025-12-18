package ru.vlyashuk.pointmap.presentation.viewModels

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.vlyashuk.pointmap.R
import ru.vlyashuk.pointmap.ui.ui_item.PointFormState
import ru.vlyashuk.pointmap.utils.Const
import javax.inject.Inject

@HiltViewModel
class PointFormViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(PointFormState())
    val state: StateFlow<PointFormState> = _state

    fun onTitleChanged(value: String) {
        _state.update { it.copy(title = value, titleError = null) }
        validate()
    }

    fun onCoordinatesChanged(value: String) {

        val masked = value
            .replace("\\s+".toRegex(), " ")
            .replace(" ,".toRegex(), ",")
            .replace(",(?=\\S)".toRegex(), ", ") // "55.0,37.0" → "55.0, 37.0"

        _state.update { it.copy(coordinates = masked, coordinatesError = null) }
        validate()
    }

    fun onDescriptionChanged(value: String) {
        _state.update { it.copy(description = value, descriptionError = null) }
    }

    fun onStatusChanged(value: String) {
        _state.update { it.copy(status = value, statusError = null) }
    }

    private fun validate() {
        val st = _state.value
        var isValid = true

        val titleError = if (st.title.isBlank()) {
            isValid = false
            getString(context, R.string.name_required)
        } else null

        val coordinatesError = validateCoordinates(st.coordinates).also {
            if (it != null) isValid = false
        }

        _state.update {
            it.copy(
                titleError = titleError,
                coordinatesError = coordinatesError,
                isValid = isValid
            )
        }
    }

    private fun validateCoordinates(s: String): String? {
        if (s.isBlank()) return getString(context, R.string.coordinates_required)

        val regex = Regex("""^(-?\d+(\.\d+)?),\s*(-?\d+(\.\d+)?)$""")
        val match = regex.matchEntire(s) ?: return getString(
            context,
            R.string.enter_coordinates_in_format_lat_lon
        )

        val lat = match.groupValues[1].toDoubleOrNull() ?: return getString(
            context,
            R.string.invalid_latitude
        )
        val lon = match.groupValues[3].toDoubleOrNull() ?: return getString(
            context,
            R.string.invalid_longitude
        )

        if (lat !in Const.LAT_MIN..Const.LAT_MAX) return getString(
            context,
            R.string.width_should_be_in_the_range_of_90_90
        )
        if (lon !in Const.LON_MIN..Const.LON_MAX) return getString(
            context,
            R.string.longitude_should_be_in_the_range_of_180_180
        )

        return null
    }
}