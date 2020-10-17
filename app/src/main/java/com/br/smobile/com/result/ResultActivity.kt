package com.br.smobile.com.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.smobile.com.R
import com.br.smobile.com.result.adapter.ResultAdapter
import com.br.smobile.com.result.model.ResultModel
import com.br.smobile.com.result.viewmodel.ResultViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat
import java.util.Date


class ResultActivity : AppCompatActivity() {
    lateinit var textViewLink: TextView
    lateinit var shareButton: ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var resultAdapter: ResultAdapter

    private val viewModel: ResultViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val text = intent.getStringExtra(TEXT_BUNDLE)

        saveItemIntoDB(text)
        configureLinkTextView(text)
        configureShareButton(text)
        configureRecyclerView()

        bindItems()
    }

    private fun configureRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_itens)
        resultAdapter = ResultAdapter()
        resultAdapter.clickListener = ::handleItemClicked
        resultAdapter.deleteClick = ::handleDeleteClick

        val infoLayoutManager = LinearLayoutManager(this)

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            infoLayoutManager.orientation
        )

        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.apply {
            adapter = resultAdapter
            layoutManager = infoLayoutManager
        }
    }

    private fun handleDeleteClick(model: ResultModel) {
        viewModel.deleteResult(model)
    }

    private fun handleItemClicked(url: String) {
        openUrlInWeb(url)
    }

    private fun saveItemIntoDB(text: String?) {
        val currentDateTimeString =
            DateFormat.getDateTimeInstance().format(Date())
        val model = ResultModel(data = currentDateTimeString, text = text.orEmpty())
        viewModel.insertResult(model)
    }

    private fun bindItems() {
        viewModel.observeResults().observe(this, Observer { result ->
            if (result == ResultViewModel.ResultState.Error) {
                Toast.makeText(this, getString(R.string.scanner_error_link), Toast.LENGTH_SHORT)
                    .show()
            } else {
                val list = (result as ResultViewModel.ResultState.Success).list
                resultAdapter.updateItems(list)
            }
        })
    }

    private fun configureShareButton(text: String?) {
        shareButton = findViewById(R.id.share_button)
        shareButton.setOnClickListener {
            shareLink(text.orEmpty())
        }
    }

    private fun configureLinkTextView(text: String?) {
        textViewLink = findViewById(R.id.text_view_link_update)
        textViewLink.text = text.orEmpty()
        textViewLink.setOnClickListener {
            openUrlInWeb(text)
        }

    }

    private fun openUrlInWeb(text: String?) {
        try {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(text))
            startActivity(browserIntent)
        } catch (ex: Exception) {
            Toast.makeText(this, getString(R.string.scanner_error_link), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun shareLink(text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    companion object {
        const val TEXT_BUNDLE = "TEXT_BUNDLE"
    }
}