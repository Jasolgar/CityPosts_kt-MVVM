package es.jasolgar.cityposts_kt.data.remote

class ApiEndPoint {

    companion object {
        const val ENDPOINT_POST = "https://jsonplaceholder.typicode.com/posts"

        const val ENDPOINT_USERS = "https://jsonplaceholder.typicode.com/users"

        const val ENDPOINT_COMMENTS = "https://jsonplaceholder.typicode.com/comments"

        const val ENDPOINT_RANDOM_IMAGE = "https://i.picsum.photos/id/%s/300/200.jpg"

        const val ENDPOINT_RANDOM_AVATAR = "https://api.adorable.io/avatars/face/eyes%s/nose%s/mouth%s/%s"
    }

}