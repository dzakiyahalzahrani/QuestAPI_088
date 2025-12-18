package com.example.questapi_088.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.questapi_088.modeldata.DetailSiswa
import com.example.questapi_088.modeldata.UIStateSiswa
import com.example.questapi_088.modeldata.toDataSiswa
import com.example.questapi_088.repositori.RepositoriDataSiswa

import retrofit2.Response

class EntryViewModel (private val repositoryDataSiswa: RepositoriDataSiswa) : ViewModel(){
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState){
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa){
        uiStateSiswa = UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    suspend fun addSiswa(){
        if (validasiInput()){
            val sip: Response<Void> = repositoryDataSiswa
                .postDataSiswa(uiStateSiswa.detailSiswa.toDataSiswa())
            if (sip.isSuccessful){
                println("Sukses Tambah Data : ${sip.message()}")
            }else {
                println("Gagal Tambah Data : ${sip.message()}")
            }
        }
    }
}
