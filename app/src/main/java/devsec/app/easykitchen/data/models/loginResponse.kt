package devsec.app.easykitchen.data.models

data class loginResponse(val error: Boolean,
                         val message: String,
                         val user: User
)