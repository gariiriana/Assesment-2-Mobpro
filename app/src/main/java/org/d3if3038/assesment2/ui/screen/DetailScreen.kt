package org.d3if3038.assesment2.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.d3if3038.asessment2.util.ViewModelFactory
import org.d3if3038.assesment2.R
import org.d3if3038.assesment2.database.VehicleDb

const val KEY_ID_CATATAN = "idVehicle"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null){
    val context = LocalContext.current
    val db = VehicleDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)
    var namaPembeli by remember {
        mutableStateOf("")
    }
    var namaPenjual by remember {
        mutableStateOf("")
    }
    var varianMotor by remember {
        mutableStateOf("")
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(true){
        if (id == null) return@LaunchedEffect
        val data = viewModel.getVehicle(id) ?: return@LaunchedEffect
        namaPembeli = data.namePembeli
        namaPenjual = data.namePenjual
        varianMotor = data.variant
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_kendaraan))
                    else
                        Text(text = stringResource(id = R.string.edit_kendaraan))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        if (namaPembeli == "" || namaPenjual == "" || varianMotor == ""){
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null){
                            viewModel.insert(namaPembeli, namaPenjual, varianMotor,)
                        } else {
                            viewModel.update(id, namaPembeli, namaPenjual, varianMotor)
                        }
                        navController.popBackStack()
                    }
                    ){
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan_kendaraan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null){
                        DeleteAction {
                            showDialog =true
                        }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) {padding ->
        FormCatatan(
            namaPembeli = namaPembeli,
            onPembeliChange = {namaPembeli = it},
            namaPenjual = namaPenjual,
            onPenjualChange = { namaPenjual = it},
            variant = varianMotor,
            onVariantChange = { varianMotor = it},
            modifier = Modifier.padding(padding))

    }
}


@Composable
fun FormCatatan(
    namaPembeli: String, onPembeliChange: (String) -> Unit,
    namaPenjual: String, onPenjualChange: (String) -> Unit,
    variant : String, onVariantChange: (String) -> Unit,
    modifier: Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaPembeli,
            onValueChange = { onPembeliChange(it) },
            label = { Text(text = stringResource(R.string.nama_pembeli))},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = namaPenjual,
            onValueChange = { onPenjualChange(it) },
            label = { Text(text = stringResource(R.string.nama_penjual))},
            singleLine = false,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Column {
            Text(text = "Pilih Kendaraan")
            val vehicles = listOf(
                Pair("Scopy", R.drawable.scoopy),
                Pair("Nmax", R.drawable.nmax),
                Pair("Beat", R.drawable.beat)
            )
            vehicles.forEach { (kelasOption, imageResource) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        onVariantChange(kelasOption)
                    }
                ) {
                    Image(
                        painter = painterResource(imageResource),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(180.dp)
                            .padding(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    RadioButton(
                        selected = variant == kelasOption,
                        onClick = {
                            onVariantChange(kelasOption)
                        }
                    )
                    Text(text = kelasOption)
                }
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit){
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.opsi_lain),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.hapus_kendaraan))},
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }

    }
}
