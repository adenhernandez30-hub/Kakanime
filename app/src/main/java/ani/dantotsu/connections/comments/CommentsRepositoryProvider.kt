package ani.dantotsu.connections.comments

/**
 * Shared app-level access point for the comments boundary.
 *
 * The implementation is intentionally swappable so the UI does not need to
 * know which transport currently backs the comments subsystem.
 */
object CommentsRepositoryProvider {
    val repository: CommentsRepository = LegacyCommentsRepository
}