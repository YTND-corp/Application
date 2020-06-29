package uz.mod.templatex.model.remote.network

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class AppExecutors  constructor(
    val diskIO: Executor = Executors.newSingleThreadExecutor(),
    val networkIO: Executor = Executors.newFixedThreadPool(3),
    val mainThread: Executor = MainThreadExecutor()
) {

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
            instance!!.diskIO.execute(command)
        }

        fun net(command: Runnable) {
            instance!!.networkIO.execute(command)
        }

        fun main(command: Runnable) {
            instance!!.mainThread.execute(command)
        }
    }
}