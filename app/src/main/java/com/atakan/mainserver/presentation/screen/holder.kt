package com.atakan.mainserver.presentation.screen

/*
@Composable
fun MainScreen(context: Context) {
    var finalData by remember { mutableStateOf(UserWallet("null", "null", 0.0, 0.0)) }
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        // Failed permission show user the text
        Toast.makeText(context, "Permission request failed.", Toast.LENGTH_SHORT).show()
    } else {
        // Permission successful

        val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val jsonFile = File(downloadsDirectory, "wallet.json")

        if (jsonFile.exists()) {
            // File exists
            val inputStream = FileInputStream(jsonFile)
            val reader = InputStreamReader(inputStream)
            val jsonString = reader.readText()
            try {
                finalData = Gson().fromJson(jsonString, UserWallet::class.java)
            }catch (e : Exception){
                Toast.makeText(context, "Failed to get file, please check", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
            Column(modifier = Modifier
                .padding(30.dp)
                .fillMaxSize(), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
                Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = finalData.username, style = TextStyle(fontSize = 35.sp, color = Color.DarkGray), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(50.dp))
                Text(text = "Balance:   ${finalData.balanceBTC} BTC")
                Button(onClick = {
                    val written = UserWallet("Ali Atakan Akın", "null",4545.0, 0.0)
                    val json = Gson().toJson(written)
                    FileWriter(jsonFile).use { writer ->
                        writer.write(json)
                    }
                    // Read the updated data from the file again
                    val updatedInputStream = FileInputStream(jsonFile)
                    val updatedReader = InputStreamReader(updatedInputStream)
                    val updatedJsonString = updatedReader.readText()
                    finalData = Gson().fromJson(updatedJsonString, UserWallet::class.java)
                }) {
                    Text("Click")
                }
            }
        }
        else {
            Toast.makeText(context, "File does not exists", Toast.LENGTH_SHORT).show()
            // Handle the case where the JSON file does not exist
        }
    }
}

 */