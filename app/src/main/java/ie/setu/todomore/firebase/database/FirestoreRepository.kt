package ie.setu.todomore.firebase.database

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import ie.setu.todomore.data.rules.Constants.TASK_COLLECTION
import ie.setu.todomore.data.rules.Constants.USER_EMAIL
import ie.setu.todomore.firebase.services.AuthService
import ie.setu.todomore.firebase.services.Task
import ie.setu.todomore.firebase.services.Tasks
import ie.setu.todomore.firebase.services.FirestoreService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepository
@Inject constructor(
    private val auth: AuthService,
    private val firestore: FirebaseFirestore
) : FirestoreService {

    override suspend fun getAll(email: String): Tasks {
        return firestore.collection(TASK_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .dataObjects()
    }

    override suspend fun get(email: String, taskId: String): Task? {
        return firestore.collection(TASK_COLLECTION)
            .document(taskId).get().await().toObject()
    }

    override suspend fun insert(email: String, task: Task) {
        // No email or imageUri fields to copy just insert as is
        val docRef = firestore.collection(TASK_COLLECTION).document()
        val taskWithId = task.copy(_id = docRef.id, userEmail = email)
        docRef.set(taskWithId).await()
        /*firestore.collection(TASK_COLLECTION)
            .add(task).await()*/
    }

    override suspend fun update(email: String, task: Task) {
        firestore.collection(TASK_COLLECTION)
            .document(task._id)
            .set(task).await()
    }

    override suspend fun delete(email: String, taskId: String) {
        firestore.collection(TASK_COLLECTION)
            .document(taskId).delete().await()
    }

    // For later image support
    // override suspend fun updatePhotoUris(...) { ... }
}
