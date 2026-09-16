package ani.dantotsu.connections.crashlytics

import android.content.Context

class CrashlyticsStub : CrashlyticsInterface {
    override fun initialize(context: Context) = Unit
    override fun logException(e: Throwable) = Unit
    override fun log(message: String) = Unit
    override fun setUserId(id: String) = Unit
    override fun setCustomKey(key: String, value: String) = Unit
    override fun setCrashlyticsCollectionEnabled(enabled: Boolean) = Unit
}
