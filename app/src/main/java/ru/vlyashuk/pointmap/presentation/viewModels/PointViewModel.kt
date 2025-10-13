package ru.vlyashuk.pointmap.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.vlyashuk.pointmap.domain.model.Point
import ru.vlyashuk.pointmap.domain.usecase.AddPointUseCase
import ru.vlyashuk.pointmap.domain.usecase.DeletePointUseCase
import ru.vlyashuk.pointmap.domain.usecase.GetPointByIdUseCase
import ru.vlyashuk.pointmap.domain.usecase.GetPointsUseCase
import ru.vlyashuk.pointmap.domain.usecase.UpdatePointUseCase
import javax.inject.Inject

@HiltViewModel
class PointViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase,
    private val addPointUseCase: AddPointUseCase,
    private val deletePointUseCase: DeletePointUseCase,
    private val updatePointUseCase: UpdatePointUseCase,
    private val getPointByIdUseCase: GetPointByIdUseCase
) : ViewModel() {

    private val _points = MutableStateFlow<List<Point>>(emptyList())
    val points: StateFlow<List<Point>> = _points

    private val _selectedPoint = MutableStateFlow<Point?>(null)
    val selectedPoint: StateFlow<Point?> = _selectedPoint

    init {
        loadPoints()
    }

    fun loadPoints() {
        viewModelScope.launch {
            getPointsUseCase()
                .catch { e -> e.printStackTrace() }
                .collect { list -> _points.value = list }
        }
    }

    fun addPoint(title: String, coordinates: String, description: String?) {
        viewModelScope.launch {
            val point = Point(title = title, coordinates = coordinates, description = description)
            addPointUseCase(point)
        }
    }

    fun loadPointById(id: Long) {
        viewModelScope.launch {
            val point = getPointByIdUseCase(id)
            _selectedPoint.value = point
        }
    }

    fun updatePoint(point: Point) {
        viewModelScope.launch {
            updatePointUseCase(point)
        }
    }

    fun deletePoint(point: Point) {
        viewModelScope.launch {
            deletePointUseCase(point)
        }
    }
}