package ani.dantotsu.media.comments

/**
 * Current interaction mode for the comments composer.
 *
 * Kept as a package-level type so CommentsFragment can retain the same
 * edit/reply state behavior while the comments transport is migrated behind
 * CommentsRepository.
 */
enum class InteractionState {
    NONE, EDIT, REPLY
}
