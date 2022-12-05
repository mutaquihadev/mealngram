package dk.kriaactividade.mealngram.helpers

sealed class DataState<out T> {
    data class Data<T>(
        val data: T
    ): DataState<T>()

    data class Error(
        val error: Throwable
    ): DataState<Nothing>()

    data class Loading(
        val loadingState: LoadingState = LoadingState.Idle
    ): DataState<Nothing>()
}

sealed class LoadingState{
    object Loading: LoadingState()
    object Idle: LoadingState()
}

interface HandleGetState<T>{
    fun handleGetState(state: DataState<T>)
}