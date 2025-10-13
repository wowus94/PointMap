package ru.vlyashuk.pointmap.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.vlyashuk.pointmap.data.local.PointEntity
import ru.vlyashuk.pointmap.data.repository.PointRepository
import javax.inject.Inject

@HiltViewModel
class PointViewModel @Inject constructor(
    private val pointRepository: PointRepository
) : ViewModel() {

    private val _points = MutableStateFlow<List<PointEntity>>(emptyList())
    val points: StateFlow<List<PointEntity>> = _points

    fun loadPoints() {
        viewModelScope.launch {
            pointRepository.getAllPoints()
                .catch { e -> e.printStackTrace() }
                .collect { list -> _points.value = list }
        }
    }

    fun addPoint(title: String, coordinates: String, description: String?) {
        viewModelScope.launch {
            val point = PointEntity(
                title = title,
                coordinates = coordinates,
                description = description
            )
            pointRepository.insert(point)
        }
    }

    fun deletePoint(point: PointEntity) {
        viewModelScope.launch { pointRepository.delete(point) }
    }

}