package com.jarvis.chiefcodetest.feature.transaction.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarvis.chiefcodetest.data.transaction.model.TransactionResponse

@Composable
fun TransactionUIItem(
    transaction: TransactionResponse,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.padding(20.dp)) {
        Column(modifier = modifier.fillMaxWidth().padding(20.dp)) {
            Text(
                transaction.description,
                fontSize = 24.sp
            )
            Text(
                transaction.category,
                fontSize = 20.sp
            )
            KeyValueListItem("Credit:", transaction.credit)
            KeyValueListItem("Debit:", transaction.debit)
            KeyValueListItem("Transaction Date:", transaction.transactionDate)
        }
    }
}

@Composable
private fun KeyValueListItem(keyTitle: String, value: Any?) {
    if (value == null) return

    Row {
        Text(
            keyTitle,
            fontSize = 18.sp
        )
        Spacer(Modifier.width(20.dp))
        Text(
            value.toString(),
            fontSize = 18.sp
        )
    }
}