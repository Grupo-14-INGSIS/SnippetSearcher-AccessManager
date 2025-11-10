package com.grupo14IngSis.snippetSearcherAccessManager.domain

import java.io.Serializable

data class ShareId(
  var snippetId: String = "",
  var userId: String = ""
) : Serializable