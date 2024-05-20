package data.repositoryImpl

import com.db.SearchHistory
import com.db.Search_historyQueries

class BpRepositoryImpl(private val searchHistoryqueries: Search_historyQueries) {

    fun insertSearchHistory(city: String, temp: Double, event_time: String) {
        searchHistoryqueries.InsertSearchHistoryByValues(null, city, temp, event_time)
    }

    fun updateSearchHistory(id:Int, city: String, temp: Double, event_time: String) {
        searchHistoryqueries.InsertSearchHistoryByValues(id.toLong(), city, temp, event_time)
    }

    fun deleteSearchHistory(id: Int) {
        searchHistoryqueries.DeleteSearchHistory(id.toLong())
    }

    fun selectSearchHistory(id: Int) : SearchHistory? {
        return searchHistoryqueries.SelectSearchHistoryById(id.toLong()).executeAsOneOrNull()
    }

    fun deleteAll() {
        searchHistoryqueries.DeleteAllSearchHistory()
    }

    fun selectAll() : List<SearchHistory>? {
        val bpList = searchHistoryqueries.SelectAll().executeAsList()
        return bpList.ifEmpty { null }
    }

    fun dropSearchHistoryTable() {
        searchHistoryqueries.DropBpTable()
    }
}