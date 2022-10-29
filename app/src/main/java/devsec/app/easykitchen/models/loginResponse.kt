package devsec.app.easykitchen.models

data class loginResponse(val error: Boolean,
                         val message: String,
                         val user: User)