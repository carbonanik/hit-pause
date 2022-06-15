package com.hit.pauzzz.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hit.pauzzz.screen.composeview.OutlineTextFieldWithErrorView

@Preview
@Composable
fun SignupPrev() {
    SignUpScreen {

    }
}

@Composable
fun SignUpScreen(
//    navController: NavController,
//    viewModel: AuthViewModel,
    signuped: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "Signup",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h3,
            )
            Spacer(modifier = Modifier.height(50.dp))

            var nameError by remember { mutableStateOf(false) }
            var name by remember { mutableStateOf("") }
            val nameUpdate = { data: String ->
                if (data.length <= 100) name = data
                if (name.isNotEmpty()) nameError = false
            }

            var phoneError by remember { mutableStateOf(false) }
            var phone by remember { mutableStateOf("") }
            val phoneUpdate = { data: String ->
                if (data.length <= 20) phone = data
                if (phone.isNotEmpty()) phoneError = false
            }

            var passwordError by remember { mutableStateOf(false) }
            var password by remember { mutableStateOf("") }
            val passwordTextUpdate = { data: String ->
                password = data
                if (password.isNotEmpty()) passwordError = false
            }

            var submitButtonText by remember { mutableStateOf("Submit") }
            var errorText: String? by remember { mutableStateOf(null) }

//            val signupResponse by viewModel.signupResponse.collectAsState(initial = null)

//            when (signupResponse) {
//                is Resource.Loading -> {
//                    submitButtonText = "Loading..."
//                    errorText = null
//                }
//                is Resource.Success -> {
//                    submitButtonText = "Successful"
//                    errorText = null//"Name: ${signupResponse?.data?.name}"
//                    signuped()
//                }
//                is Resource.Error -> {
//                    submitButtonText = "Submit"
//                    errorText = "Error: ${signupResponse?.error?.message}"
//                }
//                null -> {
//                    submitButtonText = "Submit"
//                    errorText = null
//                }
//            }
//
            SignUpForm(
                name = name,
                nameUpdate = nameUpdate,
                nameError = nameError,

                phone = phone,
                phoneUpdate = phoneUpdate,
                phoneError = phoneError,

                password = password,
                passwordUpdate = passwordTextUpdate,
                passwordError = passwordError,

//                navController = navController,
//                viewModel = viewModel,
                errorText = errorText,
                submitButtonText = submitButtonText
            ) {
                nameError = name.isEmpty()
                phoneError = phone.isEmpty()
                passwordError = password.isEmpty()

                if (!(nameError || phoneError || passwordError)) {
//                    viewModel.signupRequest(
//                        AuthRequest(
//                            name = name,
//                            phone = "+880$phone",
//                            password = password
//                        )
//                    )
                }
            }
        }
    }
}


@Composable
fun SignUpForm(
    name: String,
    nameUpdate: (String) -> Unit,
    nameError: Boolean,

    phone: String,
    phoneUpdate: (String) -> Unit,
    phoneError: Boolean,

    password: String,
    passwordUpdate: (String) -> Unit,
    passwordError: Boolean,

    submitButtonText: String,
    errorText: String?,
//    navController: NavController,
//    viewModel: AuthViewModel,
    validate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlineTextFieldWithErrorView(
            value = phone, onValueChange = phoneUpdate,
            isError = phoneError,
            label = { Text("phone") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone
            ),
//            visualTransformation = PrefixTransformation("(+880)"),
            errorMsg = "Enter A Valid Number"
        )

        OutlineTextFieldWithErrorView(
            value = name, onValueChange = nameUpdate,
            isError = nameError,
            label = { Text("name") },
            modifier = Modifier.fillMaxWidth(),
            errorMsg = "Name Is Empty"
        )

        var passwordVisibility: Boolean by remember { mutableStateOf(false) }

        /**
         * Password Field
         */
        OutlineTextFieldWithErrorView(
            value = password, onValueChange = passwordUpdate,
            isError = passwordError,
            label = { Text("password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            errorMsg = "Password Too Short",
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (passwordVisibility) Icons.Default.Refresh
                    else Icons.Default.Menu

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(imageVector = image, "")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        /** Submit Button */
        Button(
            enabled = submitButtonText == "Submit",
            onClick = { validate() },
            shape = RoundedCornerShape(size = 22.5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
        ) {
            if (submitButtonText == "Loading..."){
                CircularProgressIndicator(modifier = Modifier.size(30.dp))
            }
            Text(text = submitButtonText, fontSize = 16.sp)
        }

        errorText?.let {
            Row {
                Spacer(modifier = Modifier.width(16.dp))
                Text(it, fontSize = 14.sp, color = Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text("Already Have An Account?", fontSize = 8.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                modifier = Modifier
                    .height(30.dp)
                    .width(73.dp),
                onClick = {
//                    viewModel.signupRequest(null)
//                    navController.popBackStack()
//                    navController.navigate("login")
                }) {
                Text("Log In?", fontSize = 8.sp)
            }
        }
    }
}
