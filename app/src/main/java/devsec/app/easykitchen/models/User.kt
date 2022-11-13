package devsec.app.easykitchen.models

data  class User (
    val  id :  String ,
    val  username :  String ,
    val  email :  String ,
    val  password :  String ,
    val  phone :  String ) {
    constructor(name: String, password: String) : this("", name, "", password, "")
}
//    val  photo :  String