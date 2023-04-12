package com.joaoparedes.instagram.profile.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.Post
import com.joaoparedes.instagram.common.model.User

class FireProfileDataSource : ProfileDataSource {

    override fun fetchUserProfile(userUUID: String,callback: RequestCallback<Pair<User, Boolean?>>) {

        FirebaseFirestore.getInstance()
            .collection("/users")
            .document(userUUID)
            .get()
            .addOnSuccessListener { res ->
                val user = res.toObject(User::class.java)

                when(user) {
                    null -> {
                        callback.onFailure("Falha ao converter usuario")
                    }
                    else -> {
                        if(user.uuid == FirebaseAuth.getInstance().uid) {
                            callback.onSuccess(Pair(user, null))
                        } else {
                            FirebaseFirestore.getInstance()
                                .collection("/followers")
                                .document(userUUID)
                                .get()
                                .addOnSuccessListener { response ->
                                    if(!response.exists()) {

                                        callback.onSuccess(Pair(user, false))
                                    } else {
                                        val list = response.get("followers") as List<String>
                                        callback.onSuccess(Pair(user, list.contains(FirebaseAuth.getInstance().uid)))
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    callback.onFailure(exception.message ?: "Falha ao buscar seguidores")
                                }
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Falha ao buscar usuario")
            }

    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {

        FirebaseFirestore.getInstance()
            .collection("posts")
            .document(userUUID)
            .collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { res ->
                val documents = res.documents
                val posts = mutableListOf<Post>()
                for (document in documents) {
                    val post = document.toObject(Post::class.java)
                    post?.let {
                        posts.add(it)
                    }
                }
                callback.onSuccess(posts)
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Falha ao buscar posts")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun followUser(userUUID: String,isFollow: Boolean,callback: RequestCallback<Boolean>) {

        val uid = FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuario não logado!!!")

        FirebaseFirestore.getInstance()
            .collection("/followers")
            .document(userUUID)
            .update(
                "followers", if (isFollow) FieldValue.arrayUnion(uid)
                else FieldValue.arrayRemove(uid)
            )
            .addOnSuccessListener {
                followingCounter(uid, isFollow)
                followerCounter(userUUID, callback)
                updateFeed(userUUID,isFollow)
            }
            .addOnFailureListener { exception ->
                val err = exception as? FirebaseFirestoreException

                if (err?.code == FirebaseFirestoreException.Code.NOT_FOUND) {
                    FirebaseFirestore.getInstance()
                        .collection("/followers")
                        .document(userUUID)
                        .set(
                            hashMapOf(
                                "followers" to listOf(uid)
                            )
                        )
                        .addOnSuccessListener {
                            followingCounter(uid, isFollow)
                            followerCounter(userUUID, callback)
                            updateFeed(userUUID,isFollow)
                        }
                        .addOnFailureListener {
                            callback.onFailure(exception.message ?: "Falha ao criar seguidor")
                        }
                }

                callback.onFailure(exception.message ?: "Falha ao atualizzar seguidor")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }

    }


    private fun followingCounter(uid: String, isFollow: Boolean) {

        val meRef = FirebaseFirestore.getInstance()
            .collection("/users")
            .document(uid)

        if(isFollow) meRef.update("following", FieldValue.increment(1))
        else meRef.update("following", FieldValue.increment(-1))
    }

    private fun followerCounter(uid: String, callback: RequestCallback<Boolean>) {

        val meRef = FirebaseFirestore.getInstance()
            .collection("/users")
            .document(uid)

        FirebaseFirestore.getInstance()
            .collection("/followers")
            .document(uid)
            .get()
            .addOnSuccessListener { response ->
                if (response.exists()) {
                    val list = response.get("followers") as List<String>
                    meRef.update("followers", list.size)
                }
                callback.onSuccess(true)
            }
    }


    private fun updateFeed(uid: String, isFollow: Boolean) {
        if (!isFollow) {
            FirebaseFirestore.getInstance()
                .collection("/feeds")
                .document(FirebaseAuth.getInstance().uid!!)
                .collection("posts")
                .whereEqualTo("publisher.uuid", uid)
                .get()
                .addOnSuccessListener { response ->
                    val documents = response.documents
                    for (document in documents) {
                        document.reference.delete()
                    }
                }

        } else {
            FirebaseFirestore.getInstance()
                .collection("/posts")
                .document(uid)
                .collection("posts")
                .get()
                .addOnSuccessListener { response ->
                    val posts = response.toObjects(Post::class.java)

                    posts.lastOrNull()?.let {
                        FirebaseFirestore.getInstance()
                            .collection("/feeds")
                            .document(FirebaseAuth.getInstance().uid!!)
                            .collection("posts")
                            .document(it.uuid!!)
                            .set(it)
                    }
                }
        }
    }


}