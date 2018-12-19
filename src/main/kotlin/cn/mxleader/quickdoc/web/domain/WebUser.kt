package cn.mxleader.quickdoc.web.domain

data class WebUser(val id: String,
                   var username: String,
                   var displayName: String,
                   var title: String,
                   var avatarId: String,
                   var groups: Set<String>,
                   var email: String? = null)