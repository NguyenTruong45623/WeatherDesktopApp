package presentation.vm

import com.db.SearchHistory
import data.local.SearchHistoryDatabase
import data.repositoryImpl.BpRepositoryImpl
import kotlinx.coroutines.*


class CustomViewModel {
    private val bpAccess = BpRepositoryImpl(SearchHistoryDatabase.getDatabaseDao())

    @OptIn(DelicateCoroutinesApi::class)
    fun insertSearchHistory(city: String, temp: Double, event_time: String) = GlobalScope.launch(Dispatchers.IO) {
        bpAccess.insertSearchHistory(city , temp, event_time)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun updateSearchHistory(id:Int, city: String, temp: Double, event_time: String) = GlobalScope.launch(Dispatchers.IO) {
        bpAccess.updateSearchHistory(id, city, temp, event_time)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteSearchHistory(id: Int) = GlobalScope.launch(Dispatchers.IO) {
        bpAccess.deleteSearchHistory(id)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun selectSearchHistory(id: Int): Deferred<SearchHistory?> = GlobalScope.async(Dispatchers.IO) {
        bpAccess.selectSearchHistory(id)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteAll() = GlobalScope.launch(Dispatchers.IO) {
        bpAccess.deleteAll()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun selectAll(): Deferred<List<SearchHistory>?> = GlobalScope.async(Dispatchers.IO) {
        bpAccess.selectAll()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun dropBpTable() = GlobalScope.launch(Dispatchers.IO) {
        bpAccess.dropSearchHistoryTable()
    }
}