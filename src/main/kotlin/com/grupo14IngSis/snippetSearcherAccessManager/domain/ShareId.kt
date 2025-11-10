package com.grupo14IngSis.snippetSearcherAccessManager.domain

import java.io.Serializable

data class ShareId(
  val snippetId: String = "",
  val userId: String = ""
) : Serializable