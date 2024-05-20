package data.usecases

import com.db.SearchHistory
import data.local.SearchHistoryDatabase
import data.repositoryImpl.BpRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Class responsible for fetching all blood pressure records from the database.
 */
class GetAll {
    // Instance of BpRepositoryImpl for accessing the database
    private val bpAccess = BpRepositoryImpl(SearchHistoryDatabase.getDatabaseDao())
    /**
     * Invokes the retrieval of all blood pressure records from the database.
     * Returns a Flow containing the list of records.
     */
    operator fun invoke(): Flow<List<SearchHistory>?> = flow {
        val records = bpAccess.selectAll()
        emit (records)
    }
}