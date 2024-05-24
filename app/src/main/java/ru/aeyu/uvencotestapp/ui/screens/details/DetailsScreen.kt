package ru.aeyu.uvencotestapp.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ru.aeyu.uvencotestapp.R
import ru.aeyu.uvencotestapp.domain.enums.CoffeeType
import ru.aeyu.uvencotestapp.domain.models.ProductItem
import ru.aeyu.uvencotestapp.ui.composables.ErrorDialog
import ru.aeyu.uvencotestapp.ui.composables.OutlinedTextFieldWithoutBorders
import ru.aeyu.uvencotestapp.ui.composables.TopAppBar
import ru.aeyu.uvencotestapp.ui.theme.UvencoTestAppTheme
import ru.aeyu.uvencotestapp.utils.getCurrencySymbol
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    detailsViewModel: DetailsScreenViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    snackBarHostState: SnackbarHostState,
    onUSignClick: () -> Unit = {},
    onSaveClick: suspend (productItem: ProductItem?) -> Boolean,
    onStop: () -> Unit = {},
    onStart: () -> Unit = {},
) {
    val screenState by detailsViewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)
    val currentTime: String by detailsViewModel.currentTimeFlow.collectAsStateWithLifecycle(initialValue = "--:--", lifecycleOwner = lifecycleOwner)
    val temperature: String by detailsViewModel.currentTemperatureFlow.collectAsStateWithLifecycle(initialValue = "99.9", lifecycleOwner = lifecycleOwner)

    if (screenState.error != null) {
        screenState.error?.printStackTrace()
        ErrorDialog(
            text = screenState.error?.localizedMessage ?: "Ошибка",
            onExit = {
                detailsViewModel.clearError()
            }
        )
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                onStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                onStop()
            }
        }
        println("!!!###!!! ------------------------TEST: DisposableEffect")
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier,
        content = { paddingValues ->
            DetailsScreenContent(
                modifier = modifier.padding(paddingValues),
                snackBarHostState = snackBarHostState,
                isLoading = screenState.isLoading,
                productItem = screenState.newProductItem,
                hasChanges = screenState.hasChanges,
                onSaveClick = onSaveClick,
                onNameChanged = {
                    detailsViewModel.onNameChanged(it)
                },
                hasNameError = screenState.hasNameError,
                onPriceChanged = {
                    detailsViewModel.onPriceChanged(it)
                },
                hasPriceError = screenState.hasPriceError,
                onForFreeChecked = {
                    detailsViewModel.onForFreeChecked(it)
                },
                onCoffeeTypeChanged = {
                    detailsViewModel.onCoffeeTypeChanged(it)
                }
            )
        },
        topBar = {
            TopAppBar(
                time = currentTime,
                temperature = temperature,
                onUSignClick = onUSignClick
            )
        },
    )
}

@Composable
fun DetailsScreenContent(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    isLoading: Boolean = false,
    productItem: ProductItem?,
    hasChanges: Boolean = false,
    onSaveClick: suspend (productItem: ProductItem?) -> Boolean,
    onNameChanged: (String) -> Unit = {},
    hasNameError: Boolean = false,
    onPriceChanged: (String) -> Unit = {},
    hasPriceError: Boolean = false,
    onForFreeChecked: (Boolean) -> Unit = {},
    onCoffeeTypeChanged: (CoffeeType) -> Unit = {},
) {
    val isCappuccinoChecked = productItem?.coffeeType == CoffeeType.CAPPUCCINO
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextFieldWithoutBorders(
            isLoading = isLoading,
            startingValue = productItem?.name ?: "",
            onValueChange = onNameChanged,
            labelText = stringResource(R.string.item_name),
            isError = hasNameError,
        )
        OutlinedTextFieldWithoutBorders(
            isLoading = isLoading,
            startingValue = "${productItem?.price ?: 0}",
            onValueChange = onPriceChanged,
            labelText = stringResource(R.string.item_price),
            suffix = {
                Text(
                    text = getCurrencySymbol(),
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            isError = hasPriceError,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.item_sale_for_free),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = productItem?.isForFree ?: false,
                onCheckedChange = onForFreeChecked,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.onSurface,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    checkedTrackColor = MaterialTheme.colorScheme.onTertiary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.tertiary,
                    checkedBorderColor = Color.Transparent,
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Box(
                modifier = Modifier.width(125.dp),
                contentAlignment = Alignment.BottomCenter
            ) {

                Image(
                    modifier = Modifier.clickable {
                        onCoffeeTypeChanged(CoffeeType.CAPPUCCINO)
                    },
                    painter = painterResource(id = R.drawable.cappuccino),
                    contentDescription = "Cappuccino image",
                    contentScale = ContentScale.Fit
                )
                IconToggleButton(
                    modifier = Modifier.size(28.dp, 28.dp),
                    checked = isCappuccinoChecked, onCheckedChange = {},
                    colors = IconButtonDefaults.iconToggleButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        checkedContainerColor = MaterialTheme.colorScheme.onTertiary,
                        checkedContentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = "check")
                }
            }
            Box(
                modifier = Modifier.width(125.dp),
                contentAlignment = Alignment.BottomCenter
            ) {

                Image(
                    modifier = Modifier.clickable {
                        onCoffeeTypeChanged(CoffeeType.ESPRESSO)
                    },
                    painter = painterResource(id = R.drawable.espresso),
                    contentDescription = "Cappuccino image",
                    contentScale = ContentScale.Fit
                )
                IconToggleButton(
                    modifier = Modifier.size(28.dp, 28.dp),
                    checked = !isCappuccinoChecked, onCheckedChange = {},
                    colors = IconButtonDefaults.iconToggleButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        checkedContainerColor = MaterialTheme.colorScheme.onTertiary,
                        checkedContentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = "check")
                }
            }
        }
        OutlinedButton(
            enabled = hasChanges,
            onClick = {
                coroutineScope.launch {
                    val result = onSaveClick(productItem)
                    if(result) {
                        println("!!!###!!! ---------------------- Изменения сохранены")
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.changes_saved),
                            duration = SnackbarDuration.Short
                        )
                    } else
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.changes_not_saved),
                            duration = SnackbarDuration.Short
                        )
                }
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .width(128.dp),
            shape = RoundedCornerShape(8.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onTertiary,
                disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            if(isLoading)
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    trackColor = MaterialTheme.colorScheme.onSurface,
                    strokeWidth = 3.dp
                )
            else
                Text(text = "Сохранить", color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Preview(name = "DetailsScreen", device = "spec:width=1080px,height=2340px,dpi=480")
@Composable
fun PreviewMainScreen(
    modifier: Modifier = Modifier
) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    val currencySymbol = numberFormat.currency?.symbol ?: ""
    val productItem = ProductItem(
        id = 0,
        name = "Капучино эконом",
        price = 199,
        isForFree = true,
        coffeeType = CoffeeType.CAPPUCCINO,
        currencySymbol = currencySymbol
    )
    val calendar: Calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val curTime = dateFormat.format(calendar.time)
    UvencoTestAppTheme {

        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = modifier,
            content = { paddingValues ->
                DetailsScreenContent(
                    modifier = modifier.padding(paddingValues),
                    isLoading = false,
                    productItem = productItem,
                    snackBarHostState = SnackbarHostState(),
                    onSaveClick = { false }
                )
            },
            topBar = {
                TopAppBar(
                    time = curTime,
                    temperature = "85.7",
                    onUSignClick = { }
                )
            },
        )
    }
}