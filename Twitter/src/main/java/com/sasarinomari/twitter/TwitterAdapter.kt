package com.sasarinomari.twitter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.sasarinomari.twitter.simplizatedClass.User

class TwitterAdapter {
    companion object {
        private const val LOG_TAG: String = "TwitterAdapter"

        fun openProfile(context: Context, screenName: String) {
            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("twitter://user?screen_name=${screenName}")
                    )
                )
            } catch (e: Exception) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/#!/${screenName}")
                    )
                )
            }
        }
        fun openStatus(context: Context, statusId: Long) {
            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("twitter://status?id=$statusId")
                    )
                )
            } catch (e: Exception) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/i/web/status/$statusId")
                    )
                )
            }
        }
    }
    
    var twitter = Twitter()
    fun initialize(accessToken: twitter4j.auth.AccessToken) : TwitterAdapter {
        twitter = Twitter(accessToken)
        return this
    }

    fun blockUsers(targetUsersIds: ArrayList<Long>, apiInterface: IterableInterface, startIndex: Int = 0) {
        apiInterface.onStart()
        var cursor = 0
        try {
            for (it in startIndex until targetUsersIds.size) {
                cursor = it
                apiInterface.onIterate(it)
                twitter.client.createBlock(targetUsersIds[it])
            }
            apiInterface.onFinished()
        } catch (te: twitter4j.TwitterException) {
            object : TwitterExceptionHandler(te, "createBlock") {
                override fun onRateLimitExceeded() {
                    apiInterface.onRateLimit(cursor)
                }

                override fun onRateLimitReset() {
                    blockUsers(targetUsersIds, apiInterface, cursor)
                }

                override fun onUncaughtError() {
                    apiInterface.onUncaughtError()
                }

                override fun onNetworkError() {
                    apiInterface.onNetworkError { blockUsers(targetUsersIds, apiInterface, cursor) }
                }
            }.catch()
        }
    }

    fun getFriendsIds(targetUserId: Long, apiInterface: FetchListInterface, startIndex: Long = -1, list: ArrayList<Long> = ArrayList()) {
        apiInterface.onStart()
        var cursor: Long = startIndex
        try {
            while (true) {
                apiInterface.onFetch(list.count())
                val users = twitter.client.getFriendsIDs(targetUserId, cursor, 5000)
                list.addAll(users.iDs.toList())
                Log.i(LOG_TAG, "Count of Collected Users: ${list.count()}")
                if (users.hasNext()) cursor = users.nextCursor
                else break
            }
            apiInterface.onFinished(list)
        } catch (te: twitter4j.TwitterException) {
            object : TwitterExceptionHandler(te, "getFriendsIDs") {
                override fun onUncaughtError() {
                    apiInterface.onUncaughtError()
                }

                override fun onRateLimitExceeded() {
                    apiInterface.onRateLimit(list.count())
                }

                override fun onRateLimitReset() {
                    getFriendsIds(targetUserId, apiInterface, cursor, list)
                }

                override fun onNetworkError() {
                    apiInterface.onNetworkError { getFriendsIds(targetUserId, apiInterface, cursor, list) }
                }
            }.catch()
        }
    }

    fun getFollowersIds(targetUserId: Long, apiInterface: FetchListInterface, startIndex: Long = -1, list: ArrayList<Long> = ArrayList()) {
        apiInterface.onStart()
        var cursor: Long = startIndex
        try {
            while (true) {
                apiInterface.onFetch(list.count())
                val users = twitter.client.getFollowersIDs(targetUserId, cursor, 5000)
                list.addAll(users.iDs.toList())
                Log.i(LOG_TAG, "Count of Collected Users: ${list.count()}")
                if (users.hasNext()) cursor = users.nextCursor
                else break
            }
            apiInterface.onFinished(list)
        } catch (te: twitter4j.TwitterException) {
            object : TwitterExceptionHandler(te, "getFollowersIDs") {
                override fun onUncaughtError() {
                    apiInterface.onUncaughtError()
                }

                override fun onRateLimitExceeded() {
                    apiInterface.onRateLimit(list.count())
                }

                override fun onRateLimitReset() {
                    getFollowersIds(targetUserId, apiInterface, cursor, list)
                }

                override fun onNetworkError() {
                    apiInterface.onNetworkError { getFollowersIds(targetUserId, apiInterface, cursor, list) }
                }
            }.catch()
        }
    }

    fun getMe(apiInterface: FetchObjectInterface) {
        apiInterface.onStart()
        try {
            val me = twitter.client.showUser(twitter.client.id)
            apiInterface.onFinished(User(me))
        } catch (te: twitter4j.TwitterException) {
            object : TwitterExceptionHandler(te, "showUser") {
                override fun onUncaughtError() {
                    apiInterface.onUncaughtError()
                }

                override fun onRateLimitExceeded() {
                    apiInterface.onRateLimit()
                }

                override fun onRateLimitReset() {
                    getMe(apiInterface)
                }

                override fun onNetworkError() {
                    apiInterface.onNetworkError { getMe(apiInterface) }
                }
            }.catch()
        }
    }

    fun getFriends(targetUserId: Long, apiInterface: FetchListInterface, startIndex: Long = -1, list: ArrayList<twitter4j.User> = ArrayList()) {
        apiInterface.onStart()
        var cursor: Long = startIndex
        try {
            while (true) {
                apiInterface.onFetch(list.count())
                val users = twitter.client.getFriendsList(targetUserId, cursor, 200, true, true)
                list.addAll(users)
                if (users.hasNext()) cursor = users.nextCursor
                else break
            }
            apiInterface.onFinished(list)
        } catch (te: twitter4j.TwitterException) {
            object : TwitterExceptionHandler(te, "getFriendsList") {
                override fun onUncaughtError() {
                    apiInterface.onUncaughtError()
                }

                override fun onRateLimitExceeded() {
                    apiInterface.onRateLimit(list.count())
                }

                override fun onRateLimitReset() {
                    getFriends(targetUserId, apiInterface, cursor, list)
                }

                override fun onNetworkError() {
                    apiInterface.onNetworkError { getFriends(targetUserId, apiInterface, cursor, list) }
                }
            }.catch()
        }
    }

    fun getFollowers(targetUserId: Long, apiInterface: FetchListInterface, startIndex: Long = -1, list: ArrayList<twitter4j.User> = ArrayList()) {
        apiInterface.onStart()
        var cursor: Long = startIndex
        try {
            while (true) {
                apiInterface.onFetch(list.count())
                val users = twitter.client.getFollowersList(targetUserId, cursor, 200, true, true)
                list.addAll(users)
                if (users.hasNext()) cursor = users.nextCursor
                else break
            }
            apiInterface.onFinished(list)
        } catch (te: twitter4j.TwitterException) {
            object : TwitterExceptionHandler(te, "getFollowersList") {
                override fun onUncaughtError() {
                    apiInterface.onUncaughtError()
                }

                override fun onRateLimitExceeded() {
                    apiInterface.onRateLimit(list.count())
                }

                override fun onRateLimitReset() {
                    getFollowers(targetUserId, apiInterface, cursor, list)
                }

                override fun onNetworkError() {
                    apiInterface.onNetworkError { getFollowers(targetUserId, apiInterface, cursor, list) }
                }
            }.catch()
        }
    }

    fun getTweets(apiInterface: FetchListInterface, startIndex: Int = 1, list: ArrayList<twitter4j.Status> = ArrayList()) {
        apiInterface.onStart()
        var lastIndex = startIndex
        try {
            for (i in startIndex..Int.MAX_VALUE) {
                apiInterface.onFetch(list.count())
                val paging = twitter4j.Paging(i, 20)
                val statuses = twitter.client.getUserTimeline(paging)
                list.addAll(statuses)
                lastIndex = i
                if (statuses.size == 0) break
            }
            apiInterface.onFinished(list)
        } catch (te: twitter4j.TwitterException) {
            object : TwitterExceptionHandler(te, "getUserTimeline") {
                override fun onUncaughtError() {
                    apiInterface.onUncaughtError()
                }

                override fun onRateLimitExceeded() {
                    apiInterface.onRateLimit(list.count())
                }

                override fun onRateLimitReset() {
                    getTweets(apiInterface, lastIndex, list)
                }

                override fun onNetworkError() {
                    apiInterface.onNetworkError { getTweets(apiInterface, lastIndex, list) }
                }
            }.catch()
        }
    }

    fun destroyStatus(statuses: ArrayList<twitter4j.Status>, apiInterface: IterableInterface, startIndex: Int = 0) {
        apiInterface.onStart()
        // TODO : 이미 트윗이 지워진 경우 등 예외상황에 잘 동작하는지 확인할 필요 있음
        var cursor = 0
        try {
            val statusCount = statuses.count()
            for (i in startIndex until statusCount) {
                cursor = i
                val status = statuses[i]
                apiInterface.onIterate(cursor)
                if(!BuildConfig.DEBUG) twitter.client.destroyStatus(status.id)
            }
            apiInterface.onFinished()
        } catch (te: twitter4j.TwitterException) {
            object : TwitterExceptionHandler(te, "destroyStatus") {
                override fun onUncaughtError() {
                    apiInterface.onUncaughtError()
                }

                override fun onRateLimitExceeded() {
                    apiInterface.onRateLimit(cursor)
                }

                override fun onRateLimitReset() {
                    destroyStatus(statuses, apiInterface, cursor)
                }

                override fun onNetworkError() {
                    apiInterface.onNetworkError { destroyStatus(statuses, apiInterface, cursor) }
                }
            }.catch()
        }
    }

    fun getBlockedUsers(apiInterface: FetchListInterface, startIndex: Long = -1, list: ArrayList<Long> = ArrayList()) {
        apiInterface.onStart()
        var cursor: Long = startIndex
        try {
            while (true) {
                apiInterface.onFetch(list.count())
                val users = twitter.client.getBlocksIDs(cursor)
                list.addAll(users.iDs.toList())
                if (users.hasNext()) cursor = users.nextCursor
                else break
            }
            apiInterface.onFinished(list)
        } catch (te: twitter4j.TwitterException) {
            object : TwitterExceptionHandler(te, "getBlocksIDs") {
                override fun onUncaughtError() {
                    apiInterface.onUncaughtError()
                }

                override fun onRateLimitExceeded() {
                    apiInterface.onRateLimit(list.count())
                }

                override fun onRateLimitReset() {
                    getBlockedUsers(apiInterface, cursor, list)
                }

                override fun onNetworkError() {
                    apiInterface.onNetworkError { getBlockedUsers(apiInterface, cursor, list) }
                }
            }.catch()
        }
    }

    fun unblockUsers(list: ArrayList<Long>, apiInterface: IterableInterface, startIndex: Int = 0) {
        apiInterface.onStart()
        var cursor = 0
        try {
            for (i in startIndex until list.size) {
                cursor = i
                apiInterface.onIterate(i+1)
                twitter.client.destroyBlock(list[i])
            }
            apiInterface.onFinished()
        } catch (te: twitter4j.TwitterException) {
            object : TwitterExceptionHandler(te, "createBlock") {
                override fun onUncaughtError() {
                    apiInterface.onUncaughtError()
                }

                override fun onRateLimitExceeded() {
                    apiInterface.onRateLimit(cursor + 1)
                }

                override fun onRateLimitReset() {
                    unblockUsers(list, apiInterface, cursor)
                }

                override fun onNetworkError() {
                    apiInterface.onNetworkError { apiInterface.onRateLimit(cursor + 1) }
                }
            }.catch()
        }
    }

    fun lookup(screenName: String, apiInterface: FoundObjectInterface) {
        apiInterface.onStart()
        try {
            val user = twitter.client.showUser(screenName)
            apiInterface.onFinished(user)
        } catch (te: twitter4j.TwitterException) {
            object : TwitterExceptionHandler(te, "lookup") {
                override fun onUncaughtError() {
                    apiInterface.onUncaughtError()
                }

                override fun onRateLimitExceeded() {
                    apiInterface.onRateLimit()
                }

                override fun onRateLimitReset() {
                    lookup(screenName, apiInterface)
                }

                override fun onNotFound() {
                    apiInterface.onNotFound()
                }

                override fun onNetworkError() {
                    apiInterface.onNetworkError { lookup(screenName, apiInterface) }
                }
            }.catch()
        }
    }

    fun lookStatus(id: Long, apiInterface: FoundObjectInterface) {
        apiInterface.onStart()
        try {
            val user = twitter.client.showStatus(id)
            apiInterface.onFinished(user)
        } catch (te: twitter4j.TwitterException) {
            object : TwitterExceptionHandler(te, "lookStatus") {
                override fun onUncaughtError() {
                    apiInterface.onUncaughtError()
                }

                override fun onRateLimitExceeded() {
                    apiInterface.onRateLimit()
                }

                override fun onRateLimitReset() {
                    lookStatus(id, apiInterface)
                }

                override fun onNotFound() {
                    apiInterface.onNotFound()
                }

                override fun onNetworkError() {
                    apiInterface.onNetworkError { lookStatus(id, apiInterface) }
                }
            }.catch()
        }
    }

    fun publish(text: String, apiInterface: PostInterface) {
        apiInterface.onStart()
        try {
            val user = twitter.client.updateStatus(text)
            apiInterface.onFinished(user)
        } catch (te: twitter4j.TwitterException) {
            object : TwitterExceptionHandler(te, "publish") {
                override fun onUncaughtError() {
                    apiInterface.onUncaughtError()
                }

                override fun onRateLimitExceeded() {
                    apiInterface.onRateLimit()
                }

                override fun onRateLimitReset() {
                    publish(text, apiInterface)
                }

                override fun onNetworkError() {
                    apiInterface.onNetworkError { publish(text, apiInterface) }
                }
            }.catch()
        }
    }

}