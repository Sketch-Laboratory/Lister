package com.sasarinomari.twitter

import twitter4j.auth.AccessToken


class Twitter {
    companion object {
        private lateinit var consumerKey: String
        private lateinit var consumerSecret: String

        fun setOAuthConsumer(consumerKey: String, consumerSecret: String) {
            Companion.consumerKey = consumerKey
            Companion.consumerSecret = consumerSecret
        }
    }
    val id: Long
        get() { return client.id }

    val client: twitter4j.Twitter

    constructor(accessToken: AccessToken) : this() {
        client.oAuthAccessToken = accessToken
    }
    constructor() {
        val t = twitter4j.TwitterFactory().instance
        t.setOAuthConsumer(consumerKey, consumerSecret)
        this.client = t
    }
}

