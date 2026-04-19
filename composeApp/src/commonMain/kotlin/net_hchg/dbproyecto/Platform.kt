package net_hchg.dbproyecto

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform