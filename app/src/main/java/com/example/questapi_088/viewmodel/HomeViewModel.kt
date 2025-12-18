package com.example.questapi_088.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questapi_088.modeldata.DataSiswa
import com.example.questapi_088.repositori.RepositoriDataSiswa
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException


sealed interface StatusUiSiswa {
    data class Success(val siswa: List<DataSiswa> = listOf()) : StatusUiSiswa
    object Error : StatusUiSiswa
    object Loading : StatusUiSiswa
}

class HomeViewModel (private val repositoryDataSiswa: RepositoriDataSiswa):
    ViewModel() {
    var listSiswa: StatusUiSiswa by mutableStateOf(StatusUiSiswa.Loading)
        private set

    init {
        loadSiswa()
    }

    fun loadSiswa() {
        viewModelScope.launch {
            listSiswa = StatusUiSiswa.Loading
            listSiswa = try {
                StatusUiSiswa.Success(repositoryDataSiswa
                    .getDataSiswa())
            }catch (e: IOException){
                StatusUiSiswa.Error
            }
            catch (e: HttpException){
                StatusUiSiswa.Error
            }
        }
    }
}