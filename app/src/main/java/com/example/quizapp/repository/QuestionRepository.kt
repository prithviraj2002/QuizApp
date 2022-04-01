package com.example.quizapp.repository

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import com.example.quizapp.data.DataOrException
import com.example.quizapp.model.QuestionItem
import com.example.quizapp.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception>{
        try{
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if(dataOrException.data.toString().isNotEmpty())    dataOrException.loading = false

        }catch (exception: Exception){
            dataOrException.e = exception
            Log.d(TAG,"getAllQuestions: ${dataOrException.e!!.localizedMessage}" )
        }
        return dataOrException
    }
}