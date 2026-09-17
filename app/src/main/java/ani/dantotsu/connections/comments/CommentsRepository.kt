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

    suspend fun createComment(
        mediaId: Int,
        parentCommentId: Int?,
        content: String,
        tag: Int?
    ): Comment?

    suspend fun editComment(commentId: Int, content: String): Boolean

    suspend fun deleteComment(commentId: Int): Boolean

    suspend fun reportComment(
        commentId: Int,
        username: String,
        mediaName: String,
        userId: String
    ): Boolean

    suspend fun banUser(userId: String): Boolean

    suspend fun vote(commentId: Int, voteType: Int): Boolean

    fun isAuthenticated(): Boolean

    fun isBanned(): Boolean

    fun isAdmin(): Boolean

    fun isMod(): Boolean

    fun currentUserId(): String

    fun logout()
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

    override suspend fun createComment(
        mediaId: Int,
        parentCommentId: Int?,
        content: String,
        tag: Int?
    ): Comment? = CommentsAPI.comment(mediaId, parentCommentId, content, tag)

    override suspend fun editComment(commentId: Int, content: String): Boolean =
        CommentsAPI.editComment(commentId, content)

    override suspend fun deleteComment(commentId: Int): Boolean =
        CommentsAPI.deleteComment(commentId)

    override suspend fun reportComment(
        commentId: Int,
        username: String,
        mediaName: String,
        userId: String
    ): Boolean = CommentsAPI.reportComment(commentId, username, mediaName, userId)

    override suspend fun banUser(userId: String): Boolean = CommentsAPI.banUser(userId)

    override suspend fun vote(commentId: Int, voteType: Int): Boolean =
        CommentsAPI.vote(commentId, voteType)

    override fun isAuthenticated(): Boolean = CommentsAPI.authToken != null

    override fun isBanned(): Boolean = CommentsAPI.isBanned

    override fun isAdmin(): Boolean = CommentsAPI.isAdmin

    override fun isMod(): Boolean = CommentsAPI.isMod

    override fun currentUserId(): String = CommentsAPI.userId ?: ""

    override fun logout() = CommentsAPI.logout()
}