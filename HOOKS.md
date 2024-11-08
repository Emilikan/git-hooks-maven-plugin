# Git Hooks Documentation

## 1. applypatch−msg
**When it is triggered**: Invoked by `git-am(1)` with a file parameter holding the commit log message.  
**Important Information**: Can be used to standardize commit messages or prevent a commit if certain conditions are met. The default hook runs `commit−msg` if it is enabled.

---

## 2. pre−applypatch
**When it is triggered**: Invoked by `git-am(1)` after applying a patch but before committing.  
**Important Information**: Can block the commit based on conditions in the working tree. The default hook, if enabled, calls `pre−commit`.

---

## 3. post−applypatch
**When it is triggered**: Invoked by `git-am(1)` after a patch is applied and committed.  
**Important Information**: Primarily for notifications and does not influence `git am`.

---

## 4. pre−commit
**When it is triggered**: Invoked by `git-commit(1)` before a commit is made. Can be bypassed with `--no-verify`.  
**Important Information**: Prevents commits with trailing whitespaces and non-ASCII filenames by default.

---

## 5. pre−merge−commit
**When it is triggered**: Called by `git-merge(1)` after a successful merge but before committing.  
**Important Information**: Runs `pre−commit` if enabled. It can abort the commit if exited with a non-zero status.

---

## 6. prepare−commit−msg
**When it is triggered**: Invoked by `git-commit(1)` after preparing the default log message and before the editor starts.  
**Important Information**: Edits the commit message file and cannot be bypassed with `--no-verify`.

---

## 7. commit−msg
**When it is triggered**: Invoked by `git-commit(1)` and `git-merge(1)`, affecting the commit if a non-zero exit status is returned.  
**Important Information**: Used for validating or editing commit messages. The default hook checks for duplicate `Signed-off-by` trailers.

---

## 8. post−commit
**When it is triggered**: Invoked by `git-commit(1)` after a commit is made.  
**Important Information**: Serves mainly for notifications and does not impact commit results.

---

## 9. pre−rebase
**When it is triggered**: Called by `git-rebase(1)` to prevent rebases based on specific rules.  
**Important Information**: It receives upstream and branch parameters and can abort a rebase if necessary.

---

## 10. post−checkout
**When it is triggered**: Invoked after a `git-checkout(1)` or `git-switch(1)`, with three parameters indicating the refs and type of checkout.  
**Important Information**: It also runs after `git-clone(1)` and can check repo validity or modify metadata.

---

## 11. post−merge
**When it is triggered**: Invoked by `git-merge(1)` after `git pull`. Takes a flag parameter indicating a squash merge.  
**Important Information**: Can be used for restoring metadata but doesn't run if a merge fails due to conflicts.

---

## 12. pre−push
**When it is triggered**: Invoked by `git-push(1)` before the push proceeds. Receives destination details and information about refs.  
**Important Information**: Can prevent pushes and provide custom error messages to the user.

---

## 13. pre−receive
**When it is triggered**: Invoked by `git-receive-pack(1)` just before updating refs on the remote during a `git push`.  
**Important Information**: The hook reads details from standard input and can prevent ref updates.

---

## 14. update
**When it is triggered**: Called by `git-receive-pack(1)` before updating each ref during a push.  
**Important Information**: Prevents forced updates and can be used for access control.

---

## 15. proc−receive
**When it is triggered**: Invoked by `git-receive-pack(1)` if `receive.procReceiveRefs` is set.  
**Important Information**: Handles commands via a custom protocol for updating refs and communicating results.

---

## 16. post-update
**When it is triggered**: Invoked by `git-receive-pack(1)` when it reacts to `git push` and updates reference(s) in its repository. It executes on the remote repository once after all the refs have been updated.  
**Important Information**: This hook is meant primarily for notifications and cannot affect the outcome of `git-receive-pack`. It can be used to keep information used by dumb transports (e.g., HTTP) up to date and is helpful when publishing a Git repository accessible via HTTP.

---

## 17. reference-transaction
**When it is triggered**: Invoked by any Git command that performs reference updates, including prepared, committed, or aborted reference transactions.  
**Important Information**: This hook takes the state of the reference transaction (`prepared`, `committed`, or `aborted`) and provides the old and new object names for each reference update in the transaction. The hook can return a non-zero exit status in the "prepared" state to abort the transaction.

---

## 18. push-to-checkout
**When it is triggered**: Invoked by `git-receive-pack(1)` when a push attempts to update the branch that is currently checked out, and the `receive.denyCurrentBranch` configuration is set to `updateInstead`.  
**Important Information**: This hook can override the default behavior by allowing changes to the working tree and index when the current branch is updated. It can refuse the push or make changes to bring the working tree and index to the desired state.

---

## 19. pre-auto-gc
**When it is triggered**: Invoked by `git gc --auto`.  
**Important Information**: This hook takes no parameters and aborts the `git gc --auto` process if it exits with a non-zero status.

---

## 20. post-rewrite
**When it is triggered**: Invoked by commands that rewrite commits, such as `git commit --amend` and `git rebase`.  
**Important Information**: This hook provides a list of the rewritten commits, including their old and new object names. It runs after automatic note copying and is useful for further processing of rewritten commits.

---

## 21. sendemail-validate
**When it is triggered**: Invoked by `git-send-email(1)`.  
**Important Information**: This hook is used to validate the email content before sending. It receives the email file and SMTP headers, allowing checks for conflicts or ensuring the patch series can be applied on top of the upstream repository.

---

## 22. fsmonitor-watchman
**When it is triggered**: Invoked when the `core.fsmonitor` configuration is set to `.git/hooks/fsmonitor-watchman` or `.git/hooks/fsmonitor-watchmanv2`.  
**Important Information**: This hook outputs a list of files that may have changed since the provided timestamp or token. It is used to optimize file change detection, especially in large repositories.

---

## 23. p4-changelist
**When it is triggered**: Invoked by `git-p4 submit`.  
**Important Information**: This hook runs after the changelist message has been edited. It can be used to modify the message file or to refuse the submit based on content.

---

## 24. p4-prepare-changelist
**When it is triggered**: Invoked by `git-p4 submit` before the editor starts.  
**Important Information**: This hook is used to edit the changelist message before submission. It is not suppressed by the `--no-verify` option and is always invoked.

---

## 25. p4-post-changelist
**When it is triggered**: Invoked by `git-p4 submit` after the submit has been successfully completed in P4.  
**Important Information**: This hook is primarily for notification purposes and cannot affect the outcome of the `git p4 submit` action.

---

## 26. p4-pre-submit
**When it is triggered**: Invoked by `git-p4 submit` before the submit starts.  
**Important Information**: This hook can prevent the `git-p4 submit` from launching if it exits with a non-zero status. It can be bypassed using the `--no-verify` option.

---

## 27. post-index-change
**When it is triggered**: Invoked when the index is written during the `do_write_locked_index` process.  
**Important Information**: This hook provides information on whether the working directory or the index was updated, with the ability to detect changes to the `skip-worktree` bit. It is useful for tracking changes to the index and working directory.

---

Each of these hooks serves a unique purpose in different stages of Git operations and can be customized to suit project needs. For detailed information on configuring these hooks, refer to the [official Git documentation](https://git-scm.com/docs/githooks).
