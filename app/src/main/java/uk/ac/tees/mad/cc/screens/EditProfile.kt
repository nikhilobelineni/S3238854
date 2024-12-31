package uk.ac.tees.mad.cc.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.cc.AppViewModel
import uk.ac.tees.mad.cc.R
import uk.ac.tees.mad.cc.ui.theme.poppins
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(navController: NavHostController, vm: AppViewModel) {
    val userData = vm.userData
    var name by remember {
        mutableStateOf(userData.value.name)
    }
    var email by remember {
        mutableStateOf(userData.value.email)
    }
    var number by remember {
        mutableStateOf(userData.value.number)
    }

    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val imageFile = remember {
        File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "profile_image.jpg")
    }
    val imageUri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        imageFile
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            profileImageUri = imageUri
            vm.uploadProfile(context,imageUri)
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(imageUri)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "back",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .size(30.dp)
                    )
                    Text(
                        text = "Edit Profile",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                if (userData.value.profile.isNotEmpty() || profileImageUri != null) {
                    AsyncImage(
                        model = profileImageUri ?: userData.value.profile,
                        contentDescription = "profile_photo",
                        modifier = Modifier
                            .height(200.dp)
                            .clip(CircleShape)
                            .clickable {
                                handlePermissions(context, permissionLauncher)
                            },
                        contentScale = ContentScale.FillBounds
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.contact),
                        contentDescription = "null",
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                handlePermissions(context, permissionLauncher)
                            },
                        contentScale = ContentScale.FillBounds
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Column(Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Name", fontFamily = poppins, fontWeight = FontWeight.SemiBold)
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                        focusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                        cursorColor = androidx.compose.ui.graphics.Color.Red
                    )
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Email", fontFamily = poppins, fontWeight = FontWeight.SemiBold)
                OutlinedTextField(
                    value = email, onValueChange = { email = it },
                    colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                        focusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                        cursorColor = androidx.compose.ui.graphics.Color.Red
                    )
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Phone Number", fontFamily = poppins, fontWeight = FontWeight.SemiBold)
                OutlinedTextField(
                    value = number, onValueChange = { number = it },
                    colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                        focusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                        cursorColor = androidx.compose.ui.graphics.Color.Red
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        androidx.compose.ui.graphics.Color(0xFF066666)
                    )
                ){
                        Text(text = "Save changes")
                    }
            }
        }
    }
}

private fun handlePermissions(context: Context, permissionLauncher: ActivityResultLauncher<String>) {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) -> {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
        else -> {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}