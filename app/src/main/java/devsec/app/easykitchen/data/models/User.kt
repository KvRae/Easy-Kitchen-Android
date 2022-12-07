package devsec.app.easykitchen.data.models

data  class User (
    val  id :  String ,

    val  username :  String ,
    val  email :  String ,
    val  password :  String ,
    val  phone :  String ) {
    constructor(username: String, password: String) : this("", username, "", password, "")
    constructor(username: String, email: String, password: String, phone: String) : this("", username, email, password, phone)
}
//    val  photo :  String