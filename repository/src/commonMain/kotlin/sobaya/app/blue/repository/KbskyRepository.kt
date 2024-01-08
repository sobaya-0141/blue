package sobaya.app.blue.repository

import org.koin.core.annotation.Single
import work.socialhub.kbsky.BlueskyFactory
import work.socialhub.kbsky.api.entity.atproto.server.ServerCreateSessionRequest
import work.socialhub.kbsky.api.entity.bsky.feed.FeedPostRequest
import work.socialhub.kbsky.domain.Service

@Single
class KbskyRepository {
    private var accessJwt: String? = null

    private fun createSession() {
        val response = BlueskyFactory
            .instance(Service.BSKY_SOCIAL.uri)
            .server()
            .createSession(
                ServerCreateSessionRequest().also {
                    it.identifier = ""
                    it.password = ""
                }
            )
        accessJwt = response.data.accessJwt
    }

    fun sendFeed(text: String) {
        if (accessJwt.isNullOrBlank()) {
            createSession()
        }
        BlueskyFactory
            .instance(Service.BSKY_SOCIAL.uri)
            .feed()
            .post(
                FeedPostRequest(accessJwt!!).also {
                    it.text = text
                }
            )
    }
}
