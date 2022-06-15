package com.hit.pauzzz.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hit.pauzzz.screen.composeview.OutlineTextFieldWithErrorView
import com.hit.pauzzz.ui.theme.HitPauzzzTheme
import com.hit.pauzzz.ui.theme.Teal200
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun LoginPreview() {
    HitPauzzzTheme {
        LoginScreen(rememberNavController()) {}
    }
}

const val LOGIN_WITH_GOOGLE = "Login With Google"
const val LOGIN_WITH_FACEBOOK = "Login With Facebook"
const val LOADING = "Loading..."

@Composable
fun LoginScreen(
    navController: NavController,
    loggedIn: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "Sign In",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h3,
            )
            Spacer(modifier = Modifier.height(50.dp))

            var emailError by remember { mutableStateOf(false) }
            var email by remember { mutableStateOf("") }
            val emailUpdate = { data: String ->
                email = data
                if (email.validEmail()) emailError = false
            }

            var passwordError by remember { mutableStateOf(false) }
            var password by remember { mutableStateOf("") }
            val passwordTextUpdate = { data: String ->
                password = data
                if (password.validPass()) passwordError = false
            }

            val errorText: String? by remember { mutableStateOf(null) }


            var submitButtonText by remember { mutableStateOf("Submit") }
            var googleLoginButtonText by remember { mutableStateOf(LOGIN_WITH_GOOGLE) }
            var facebookLoginButtonText by remember { mutableStateOf(LOGIN_WITH_FACEBOOK) }

            val scope = rememberCoroutineScope()

            /**
             *  Email Field
             */
            OutlineTextFieldWithErrorView(
                value = email, onValueChange = emailUpdate,
                isError = emailError,
                label = { Text("email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                errorMsg = "Enter A Valid Email"
            )

            var passwordVisibility: Boolean by remember { mutableStateOf(false) }

            /**
             *  Password Field
             */
            OutlineTextFieldWithErrorView(
                value = password, onValueChange = passwordTextUpdate,
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
                        if (passwordVisibility) Icons.Default.Email
                        else Icons.Default.Menu

                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(imageVector = image, "")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Submit Button
             */
            Button(
                enabled = submitButtonText == "Submit",
                onClick = {

                    emailError = !email.validEmail()
                    passwordError = !password.validPass()

                    if (!(emailError || passwordError)) {
                        submitButtonText = LOADING
                        scope.launch {
                            delay(3000)
                            submitButtonText = "Successful"
                            delay(200)
                            navController.navigate(Screen.HomeScreen.route)
                        }
                    }
                },
                shape = RoundedCornerShape(size = 22.5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
            ) {
                if (submitButtonText == LOADING) {
                    CircularProgressIndicator(modifier = Modifier.size(30.dp))
                }
                Text(
                    text = submitButtonText,
                    fontSize = 16.sp
                )
            }

            /**
             * Error Text Under Submit Button
             */
            errorText?.let {
                Row {
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(it, fontSize = 14.sp, color = Color.Red)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Divider(Modifier.weight(1f).align(CenterVertically))
                Text(text = "Or", fontSize = 18.sp, modifier = Modifier.padding(horizontal = 16.dp))
                Divider(Modifier.weight(1f).align(CenterVertically))
            }

            Spacer(modifier = Modifier.height(30.dp))

            /**
             * Google Login Button
             */

            Button(
                enabled = googleLoginButtonText == LOGIN_WITH_GOOGLE,
                onClick = {

                },
                shape = RoundedCornerShape(size = 22.5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                if (googleLoginButtonText == LOADING) {
                    CircularProgressIndicator(modifier = Modifier.size(30.dp))
                }
                Text(
                    text = googleLoginButtonText,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            /**
             * Google Login Button
             */

            Button(
                enabled = facebookLoginButtonText == LOGIN_WITH_FACEBOOK,
                onClick = {

                },
                shape = RoundedCornerShape(size = 22.5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
            ) {
                if (facebookLoginButtonText == LOADING) {
                    CircularProgressIndicator(modifier = Modifier.size(30.dp))
                }
                Text(
                    text = facebookLoginButtonText,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

        }
    }
}

private const val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
fun String.validEmail() = matches(emailPattern.toRegex())

fun String.validPass() = length >= 6
