package sobaya.app.blue.repository.di

import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.Single

val ktorfit = Ktorfit.Builder().baseUrl("https://api.blueskyapi.io").build()
// @Single
// fun getBlueApi() = ktorfit.create<BlueApi>()
