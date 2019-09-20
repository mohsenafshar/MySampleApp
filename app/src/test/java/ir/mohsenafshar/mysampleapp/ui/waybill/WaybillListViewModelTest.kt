package ir.mohsenafshar.mysampleapp.ui.waybill

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Flowable
import io.reactivex.Single
import ir.mohsenafshar.mysampleapp.data.Repository
import ir.mohsenafshar.mysampleapp.data.remote.RemoteDataProvider
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.disposables.Disposable
import io.reactivex.Scheduler
import io.reactivex.annotations.NonNull
import ir.mohsenafshar.mysampleapp.data.model.Waybill
import org.junit.*
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


class WaybillListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: WaybillListViewModel

    @Mock
    private lateinit var repository: Repository

    @Before
    fun setupRepository() {
        MockitoAnnotations.initMocks(this)
        viewModel = WaybillListViewModel(repository)
    }

    @Before
    fun setUpRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(@NonNull run: Runnable, delay: Long, @NonNull unit: TimeUnit): Disposable {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Scheduler.Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }

    @Test
    fun successLoadData() {
        `when`(repository.getConsignmentList(0)).thenReturn(Flowable.just(RemoteDataProvider.list))

        Assert.assertEquals(viewModel.items.value, emptyList<Waybill>())

        viewModel.getWaybills(0)
        verify(repository).getConsignmentList(0)

        Assert.assertEquals(viewModel.items.value, RemoteDataProvider.list)
    }

    @Test
    fun failedLoadData() {
        val exception = Exception()
        `when`(repository.getConsignmentList(0)).thenReturn(Flowable.error<List<Waybill>>(exception))

        Assert.assertEquals(viewModel.items.value, emptyList<Waybill>())

        viewModel.getWaybills(0)
        verify(repository).getConsignmentList(0)

        Assert.assertEquals(viewModel.items.value, emptyList<Waybill>())
        Assert.assertEquals(viewModel.error.value, exception)
    }
}