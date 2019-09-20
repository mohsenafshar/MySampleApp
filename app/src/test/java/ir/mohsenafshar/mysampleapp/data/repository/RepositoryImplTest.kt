package ir.mohsenafshar.mysampleapp.data.repository

import io.reactivex.Flowable
import ir.mohsenafshar.mysampleapp.DataProvider
import ir.mohsenafshar.mysampleapp.data.DataSource
import ir.mohsenafshar.mysampleapp.data.Repository
import ir.mohsenafshar.mysampleapp.data.model.Waybill
import ir.mohsenafshar.mysampleapp.data.remote.RemoteDataProvider
import ir.mohsenafshar.mysampleapp.ui.waybill.WaybillListViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class RepositoryImplTest {

    private lateinit var repository: Repository

    @Mock
    lateinit var remoteDataSource: DataSource

    @Mock
    lateinit var localDataSource: DataSource

    @Before
    fun setupRepository() {
        MockitoAnnotations.initMocks(this)
        repository = RepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun getData() {
        `when`(remoteDataSource.getConsignmentList(0))
            .thenReturn(Flowable.just<List<Waybill>>(DataProvider.list))
        repository.getConsignmentList(0)
        verify(remoteDataSource).getConsignmentList(0)
    }
}