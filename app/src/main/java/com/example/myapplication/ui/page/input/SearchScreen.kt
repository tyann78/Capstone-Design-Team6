package com.example.myapplication.ui.page.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.ui.backendset.data.Poi_

@Composable
fun SearchScreen(
    type: String,
    navController: NavController,
    viewModel: SearchViewModel = viewModel(),
    onItemSelected: (Poi_) -> Unit
) {
    val query by viewModel.query.collectAsState()
    val filteredList by viewModel.filteredList.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = if (type == "departure") "출발지 검색" else "도착지 검색",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 検索欄には名前のみ表示（緯度・経度は含めない）
        TextField(
            value = query,
            onValueChange = { viewModel.updateQuery(it) },
            label = { Text("검색 키워드 입력") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search // Return キーを検索ボタンに設定
            ),
            keyboardActions = KeyboardActions(
                onSearch = { // 検索アクションを処理
                    viewModel.performSearch(query) // 検索処理を実行
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 検索結果リスト
        LazyColumn {
            items(filteredList) { poi ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.saveSelectedPoi(poi, type)
                            onItemSelected(poi)
                        }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = poi.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
