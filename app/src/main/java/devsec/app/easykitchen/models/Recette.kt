package devsec.app.easykitchen.models

data  class Recette (
    val  id :  String ,
    val  name :  String ,
    val  description :  String ,
    val  image :  String ,
    val  isBio :  Boolean,
    val  duration :  Number,
    val  person :  Number,
    val  difficulty :  String)
{
    constructor(name: String, description: String) : this("", name, description,"", false,0,0,"")


}