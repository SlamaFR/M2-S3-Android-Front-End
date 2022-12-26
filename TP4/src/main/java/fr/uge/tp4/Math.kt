package fr.uge.tp4

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.StrictMath.random
import kotlin.math.exp

/* swap two elements in a list */
fun <T> MutableList<T>.swap(a: Int, b: Int) {
    val tmp = this[a]
    this[a] = this[b]
    this[b] = tmp
}

data class SimulatedAnnealingParams(
    val startTemperature: Double = 1.0,
    val temperatureDecreaseRatio: Double
)

fun computeWithSimulatedAnnealing(
    initialCircuit: List<Town>,
    iterations: Long,
    params: SimulatedAnnealingParams
): Flow<List<Town>> = flow {
    val currentCircuit = mutableListOf<Town>().apply { addAll(initialCircuit) }
    var currentDistance = currentCircuit.pathLength
    var currentTemperature = params.startTemperature

    fun acceptChange(deltaDistance: Double): Boolean {
        if (deltaDistance < 0) return true
        val v = exp(-deltaDistance / currentDistance / currentTemperature)
        return random() < v
    }

    for (i in 0 until iterations) {
        // pick two random points in the circuit and swap them
        val a = currentCircuit.indices.random()
        val b = currentCircuit.indices.random()
        if (a != b) {
            currentCircuit.swap(a, b)
            val newDistance = currentCircuit.pathLength // could be optimized
            if (acceptChange(newDistance - currentDistance)) {
                currentDistance = newDistance
            } else {
                // we cancel the swap
                currentCircuit.swap(a, b)
            }
            // we decrement the temperature
            currentTemperature *= (1.0 - params.temperatureDecreaseRatio)
        }
        emit(currentCircuit)
    }

    emit(currentCircuit)
}
fun getLongFlow() = flow<Long> {
    // we are in a coroutine
    var i = 0L
    while (true) {
        emit(i)
        i++
    }
}
