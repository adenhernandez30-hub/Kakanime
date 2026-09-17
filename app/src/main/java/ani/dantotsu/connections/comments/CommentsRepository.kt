package ani.dantotsu.connections.comments

/**
 * App-facing boundary for the comments subsystem.
 *
 * Android UI code can depend on this contract while the transport is migrated
 * from the legacy service to the AniLab backend.
 */
interface CommentsRepository {
    suspend fun getCommentsForId(
        mediaId: Int,
        page: Int = 1,
        tag: Int? = null,
        sort: String = "newest"
    ): CommentResponse?

    suspend fun getSingleComment(commentId: Int): Comment?

    suspend fun getRepliesFromId(commentId: Int, page: Int = 1): CommentResponse?
}

/** Compatibility implementation backed by the existing legacy API. */
object LegacyCommentsRepository : CommentsRepository {
    override suspend fun getCommentsForId(
        mediaId: Int,
        page: Int,
        tag: Int?,
        sort: String
    ): CommentResponse? = CommentsAPI.getCommentsForId(mediaId, page, tag, sort)

    override suspend fun getSingleComment(commentId: Int): Comment? =
        CommentsAPI.getSingleComment(commentId)

    override suspend fun getRepliesFromId(commentId: Int, page: Int): CommentResponse? =
        CommentsAPI.getRepliesFromId(commentId, page)
}
