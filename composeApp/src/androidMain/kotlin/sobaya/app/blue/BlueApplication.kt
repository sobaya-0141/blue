package sobaya.app.blue

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module
import sobaya.app.blue.repository.di.RepositoryModule

class BlueApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                defaultModule,
                RepositoryModule().module,
            )
        }
    }
}
