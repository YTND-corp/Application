package uz.mod.templatex.model.remote.network

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class AppExecutors  constructor(
    diskIO: Executor = Executors.newSingleThreadExecutor(),
    networkIO: Executor = Executors.newFixedThreadPool(3),
    mainThread: Executor = MainThreadExecutor()
) {
    private val mDiskIO: Executor
    private val mNetworkIO: Executor
    private val mMainThread: Executor
    fun diskIO(): Executor {
        return mDiskIO
    }

    fun networkIO(): Executor {
        return mNetworkIO
    }

    fun mainThread(): Executor {
        return mMainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
        override fun execute( command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        @Volatile
        private var mInstance: AppExecutors? = null
        val instance: AppExecutors?
            get() {
                if (mInstance == null) {
                    synchronized(
                        AppExecutors::class.java
                    ) { mInstance = AppExecutors() }
                }
                return mInstance
            }

        fun disk(command: Runnable) {
            instance!!.diskIO().execute(command)
        }

        fun net(command: Runnable) {
            instance!!.networkIO().execute(command)
        }

        fun main(command: Runnable) {
            instance!!.mainThread().execute(command)
        }
    }

    init {
        mDiskIO = diskIO
        mNetworkIO = networkIO
        mMainThread = mainThread
    }
}