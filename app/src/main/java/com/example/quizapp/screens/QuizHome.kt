package com.example.quizapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.component.Questions

@Composable
fun QuizHome(viewModel: QuestionViewModel = hiltViewModel()){
    Questions(viewModel)
}

@Preview(showBackground = true)
@Composable
fun QuizHomePreview(){
    QuizHome()
}