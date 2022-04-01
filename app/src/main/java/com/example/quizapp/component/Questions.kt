package com.example.quizapp.component


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.screens.QuestionViewModel
import com.example.quizapp.util.AppColors
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizapp.model.QuestionItem

@Composable
fun Questions(viewModel: QuestionViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()

    val questionIndex = remember{
        mutableStateOf<Int>(0)
    }
    if(viewModel.data.value.loading == true){
        CircularProgressIndicator()
    }
    else{
        val question = try{
            questions?.get(questionIndex.value)
        }catch(e: Exception){
            null
        }
        if(questions != null){
            questionDisplay(question = question!!, viewModel = viewModel, questionIndex = questionIndex){
                questionIndex.value = questionIndex.value + 1
            }
        }
    }
}

//@Preview
@Composable
fun questionDisplay(
    question: QuestionItem,
    viewModel: QuestionViewModel,
    questionIndex: MutableState<Int>,
    onNextClicked: (Int) -> Unit = {}
){
    val choiceState = remember(question){
        question.choices.toMutableList()
    }

    val answerState = remember(question){
        mutableStateOf<Int?>(null)
    }

    val correctAnswer = remember(question) {
        mutableStateOf<Boolean?>(null)
    }
    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswer.value = choiceState[it] == question.answer
        }
    }
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
    color = AppColors.mDarkPurple) {
        Column(verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start) {
            if(questionIndex.value >= 3)    ShowProgress(score = questionIndex.value)
            questionTracker(counter = questionIndex.value)
            Divider(color = AppColors.mLightGray)
            Column(modifier = Modifier.padding(5.dp)) {
                Text(text = question.question,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxHeight(0.3f),
                style = TextStyle(
                    color = AppColors.mOffWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ))
             choiceState.forEachIndexed { index, answerText ->
                 Row(modifier = Modifier
                     .padding(3.dp)
                     .fillMaxWidth()
                     .height(45.dp)
                     .border(width = 4.dp, brush = Brush.linearGradient(
                         colors = listOf(AppColors.mLightBlue, AppColors.mLightBlue)
                     ),
                         shape = RoundedCornerShape(15.dp))
                     .clip(RoundedCornerShape(topStartPercent = 50,
                         topEndPercent = 50,
                         bottomStartPercent = 50,
                         bottomEndPercent = 50)
                     )
                     .background(color = Color.Transparent),
                     verticalAlignment = Alignment.CenterVertically
                 ){
                    RadioButton(selected = (answerState.value == index), onClick = {
                        updateAnswer(index)
                    },
                    modifier = Modifier.padding(16.dp),
                    colors = RadioButtonDefaults.colors(
                        if(correctAnswer.value == true && index == answerState.value){
                            Color.Green
                        }else {
                            Color.Red
                        })
                    )//ends RadioButton
                     val annotedString = buildAnnotatedString {
                         withStyle(style = SpanStyle(
                             color = if(correctAnswer.value == true && index == answerState.value){
                                 Color.Green
                             }else if(correctAnswer.value == false && index == answerState.value){
                                 Color.Red
                             }else{
                                 AppColors.mOffWhite
                             },
                             fontWeight = FontWeight.Light,
                         fontSize = 17.sp)){
                             append(answerText)
                         }
                     }
                     Text(text = annotedString)
                     }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { onNextClicked(questionIndex.value) },
                modifier = Modifier
                    .padding(5.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                shape = RoundedCornerShape(34.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppColors.mLightBlue
                )) {
                        Text("Next",
                        modifier = Modifier.padding(4.dp),
                        color = AppColors.mOffWhite,
                        fontSize = 17.sp)
                }
            }
        }
    }
}

//@Preview
@Composable
fun questionTracker(counter: Int = 10, outOf: Int = 100){
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)){
        withStyle(style = SpanStyle(color = AppColors.mLightGray,
        fontWeight = FontWeight.Bold,
        fontSize = 27.sp)){
            append("Question $counter/")
            withStyle(style = SpanStyle(color = AppColors.mLightGray,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 14.sp)){
                append("$outOf")
            }
            }
        }
    },
    modifier = Modifier.padding(20.dp))
}

@Preview
@Composable
fun ShowProgress(score: Int = 12) {

    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075),
        Color(0xFFBE6BE5)))

    val progressFactor = remember(score) {
        mutableStateOf(score*0.005f)
    }
    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(width = 4.dp,
            brush = Brush.linearGradient(colors = listOf(AppColors.mLightPurple,
                AppColors.mLightPurple)),
            shape = RoundedCornerShape(34.dp))
        .clip(RoundedCornerShape(topStartPercent = 50,
            topEndPercent = 50,
            bottomEndPercent = 50,
            bottomStartPercent = 50))
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically) {
        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = { },
            modifier = Modifier
                .fillMaxWidth(progressFactor.value)
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent
            )) {
            Text(text = (score*10).toString(),
                modifier = Modifier.clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(6.dp),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center)
        }
    }
}