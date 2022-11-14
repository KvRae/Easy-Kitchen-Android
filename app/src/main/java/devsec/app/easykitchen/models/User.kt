package devsec.app.easykitchen.models

data  class User (
    val  id :  String ,
    val  username :  String ,
    val  email :  String ,
    val  password :  String ,
    val  phone :  String ) {
    constructor(username: String, password: String) : this("", username, "", password, "")
}
//    val  photo :  String