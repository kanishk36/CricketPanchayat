package com.cricketpanchayat.models

class Menu(title: String, menuType: MENUTYPE) {

    var title: String
    var menuType: MENUTYPE

    enum class MENUTYPE {
        ADD, LANGUAGE, SETTINGS
    }

    init {
        this.title = title
        this.menuType = menuType
    }

}