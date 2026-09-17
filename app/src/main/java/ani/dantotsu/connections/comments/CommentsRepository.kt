package ani.dantotsu.connections.comments

/**
 * App-facing boundary for the comments subsystem.
 *
 * The Android UI should depend on this abstraction rather than directly on
 * the legacy remote API. The current implementation remains a compatibility
 * bridge until the AniLab backend is ready.
 */
interface CommentsRepository {
    suspend fun getCommentsForId(
        mediaId: Int,
        page: Int = 0,
        sort: String = "newest"
    ): CommentResponse?
}

/** Compatibility implementation backed by the existing legacy API. */
object LegacyCommentsRepository : CommentsRepository {
    override suspend fun getCommentsForId(
        mediaId: Int,
        page: Int,
        sort: String
    ): CommentResponse? = CommentsAPI.getCommentsForId(mediaId, page, sort)
}
