package ru.aeyu.uvencotestapp.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.aeyu.uvencotestapp.R
import ru.aeyu.uvencotestapp.domain.enums.CoffeeType
import ru.aeyu.uvencotestapp.domain.models.ProductItem
import ru.aeyu.uvencotestapp.ui.composables.ErrorDialog
import ru.aeyu.uvencotestapp.ui.composables.ProgressIndicator
import ru.aeyu.uvencotestapp.ui.composables.TopAppBar
import ru.aeyu.uvencotestapp.ui.theme.UvencoTestAppTheme
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    mainViewModel: MainScreenViewModel = hiltViewModel(),
    onItemClick: (product: ProductItem) -> Unit = {},
    onUSignClick: () -> Unit = {},
    onStop: () -> Unit = {},
    onStart: () -> Unit = {},
) {
    val screenState by mainViewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)
    val currentTime: String by mainViewModel.currentTimeFlow.collectAsStateWithLifecycle(initialValue = "--:--", lifecycleOwner = lifecycleOwner)
    val temperature: String by mainViewModel.currentTemperatureFlow.collectAsStateWithLifecycle(initialValue = "99.9", lifecycleOwner = lifecycleOwner)
    
    if (screenState.error != null) {
        screenState.error?.printStackTrace()
        ErrorDialog(
            text = screenState.error?.localizedMessage ?: "Ошибка",
            onExit = {
                mainViewModel.clearError()
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

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier,
        content = { paddingValues ->
            MainContent(
                isLoading = screenState.isLoading,
                modifier = modifier.padding(paddingValues),
                mainList = screenState.products,
                onItemClick = onItemClick
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
fun MainContent(
    isLoading: Boolean,
    modifier: Modifier,
    mainList: List<ProductItem>,
    onItemClick: (product: ProductItem) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (isLoading) ProgressIndicator()
        MainList(
            mainList = mainList,
            onItemClick = onItemClick
        )
    }
}

@Composable
fun MainList(
    modifier: Modifier = Modifier,
    mainList: List<ProductItem>,
    onItemClick: (product: ProductItem) -> Unit
) {

    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 180.dp)) {
        val cardModifier = modifier
            .padding(all = 12.dp)
            .fillMaxWidth()
        items(mainList) { item ->
            ListProductItem(modifier = cardModifier, productItem = item, onItemClick = onItemClick)
        }
    }

}

@Composable
fun ListProductItem(
    modifier: Modifier = Modifier,
    productItem: ProductItem,
    onItemClick: (product: ProductItem) -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = {
            onItemClick(productItem)
        }),
        shape = ShapeDefaults.Small,

        ) {
        Column(
            modifier = Modifier
                .padding(all = 4.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceVariant),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val painterResource = if (productItem.coffeeType == CoffeeType.CAPPUCCINO)
                painterResource(id = R.drawable.cappuccino)
            else
                painterResource(id = R.drawable.espresso)
            Image(
                painter = painterResource,
                contentDescription = "Coffee image",
                contentScale = ContentScale.Fit
            )
            Text(
                text = productItem.name,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            val arrangement = if(productItem.isForFree)
                Arrangement.Center
            else
                Arrangement.SpaceBetween
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surfaceVariant,
                                MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            startX = -99.99f,
                        ),
                        shape = RoundedCornerShape(size = 4.dp)
                    )
                    .padding(vertical = 4.dp),
                horizontalArrangement = arrangement
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "0.33",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                if(!productItem.isForFree) {
                    val text = "${productItem.price} ${productItem.currencySymbol}"
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = text,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        }
    }
}

@Preview(name = "MainScreen")
@Composable
fun PreviewMainScreen(
    modifier: Modifier = Modifier
) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    val currencySymbol = numberFormat.currency?.symbol ?: ""
    val listProducts = listOf(
        ProductItem(
            id = 0,
            name = "Капучино эконом",
            price = 199,
            isForFree = false,
            coffeeType = CoffeeType.CAPPUCCINO,
            currencySymbol = currencySymbol
        ),
        ProductItem(
            id = 0,
            name = "Капучино эконом",
            price = 199,
            isForFree = true,
            coffeeType = CoffeeType.CAPPUCCINO,
            currencySymbol = currencySymbol
        ),
        ProductItem(
            id = 0,
            name = "Капучино эконом",
            price = 199,
            isForFree = false,
            coffeeType = CoffeeType.CAPPUCCINO,
            currencySymbol = currencySymbol
        ),
        ProductItem(
            id = 0,
            name = "Капучино эконом",
            price = 199,
            isForFree = false,
            coffeeType = CoffeeType.CAPPUCCINO,
            currencySymbol = currencySymbol
        ),
        ProductItem(
            id = 0,
            name = "Капучино эконом",
            price = 199,
            isForFree = true,
            coffeeType = CoffeeType.CAPPUCCINO,
            currencySymbol = currencySymbol
        ),
        ProductItem(
            id = 0,
            name = "Капучино эконом",
            price = 199,
            isForFree = false,
            coffeeType = CoffeeType.CAPPUCCINO,
            currencySymbol = currencySymbol
        ),
    )
    val calendar: Calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val curTime = dateFormat.format(calendar.time)
    UvencoTestAppTheme {


        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = modifier,
            content = { paddingValues ->
                MainContent(
                    isLoading = false,
                    modifier = modifier.padding(paddingValues),
                    mainList = listProducts,
                    onItemClick = { }
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