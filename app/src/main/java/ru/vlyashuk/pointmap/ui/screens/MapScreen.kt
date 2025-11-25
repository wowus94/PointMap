package ru.vlyashuk.pointmap.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import ru.vlyashuk.pointmap.R
import ru.vlyashuk.pointmap.navigation.Routes

@Composable
fun MapScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var clickedCoordinates by remember { mutableStateOf<Point?>(null) }
    val textColor = MaterialTheme.colorScheme.onBackground

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        YandexMapView(
            modifier = Modifier.fillMaxSize(),
            onMapTap = { point ->
                clickedCoordinates = point
            }
        )

        AnimatedVisibility(
            visible = clickedCoordinates != null,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 32.dp)
        ) {
            clickedCoordinates?.let { point ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background.copy(
                            alpha = 0.8f
                        )
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.coordinates),
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            fontSize = 16.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = stringResource(R.string.latitude_6f).format(point.latitude),
                            color = textColor,
                            fontSize = 14.sp
                        )
                        Text(
                            text = stringResource(R.string.longitude_6f).format(point.longitude),
                            color = textColor,
                            fontSize = 14.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Button(
                            onClick = {
                                clickedCoordinates?.let {
                                    navController.navigate(
                                        Routes.AddPoint.withCoordinates(
                                            point.latitude,
                                            point.longitude
                                        )
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.save),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun YandexMapView(
    modifier: Modifier = Modifier,
    onMapTap: (Point) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val mapView = remember {
        MapView(context).apply {
            mapWindow.map.move(
                CameraPosition(Point(55.751244, 37.618423), 14f, 0f, 0f)
            )
        }
    }

    val onMapTapState = rememberUpdatedState(onMapTap)

    val imageProvider = remember {
        ImageProvider.fromResource(context, R.drawable.ic_pin)
    }

    DisposableEffect(mapView) {
        val inputListener = object : com.yandex.mapkit.map.InputListener {
            override fun onMapTap(map: com.yandex.mapkit.map.Map, point: Point) {
                onMapTapState.value(point)

                map.mapObjects.clear()

                map.mapObjects.addPlacemark().apply {
                    geometry = point
                    setIcon(imageProvider)
                }
            }

            override fun onMapLongTap(map: com.yandex.mapkit.map.Map, point: Point) = Unit
        }

        mapView.mapWindow.map.addInputListener(inputListener)

        onDispose {
            try {
                mapView.mapWindow.map.removeInputListener(inputListener)
            } catch (t: Throwable) {
                Log.e("MapScreen", "Map error: $t")
            }
        }
    }

    DisposableEffect(lifecycleOwner, mapView) {
        val observer = object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                MapKitFactory.getInstance().onStart()
                mapView.onStart()
            }

            override fun onStop(owner: LifecycleOwner) {
                mapView.onStop()
                MapKitFactory.getInstance().onStop()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier
    )
}