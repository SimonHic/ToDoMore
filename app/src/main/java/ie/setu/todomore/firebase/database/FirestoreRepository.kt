package ie.setu.todomore.firebase.database

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.SetOptions
import ie.setu.todomore.data.rules.Constants.TASK_COLLECTION
import ie.setu.todomore.data.rules.Constants.USER_EMAIL
import ie.setu.todomore.firebase.services.AuthService
import ie.setu.todomore.firebase.services.Task
import ie.setu.todomore.firebase.services.Tasks
import ie.setu.todomore.ui.screens.streaks.StreakData
import ie.setu.todomore.firebase.services.FirestoreService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import java.time.LocalDate

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

    override suspend fun getStreakData(email: String): StreakData {
        val docRef = firestore.collection("streaks").document(email)
        val streakDoc = docRef.get().await()

        // Extract the streak values and default to zero if missing
        return if(streakDoc.exists()){
            val current = streakDoc.getLong("current_streak")?.toInt()?: 0
            val longest = streakDoc.getLong("longest_streak")?.toInt()?: 0
            val lastDate = streakDoc.getString("last_completed_date")
            val total = streakDoc.getLong("total_cleared_tasks")?.toInt() ?: 0
            StreakData(current, longest, lastDate, total)
        } else{
            // Nothing done yet default zero
            StreakData()
        }
    }

    override suspend fun updateStreakOnClear(email: String, completedTaskCount: Int){
        if(completedTaskCount == 0)
            return

        val streakRef = firestore.collection("streaks").document(email)
        val today = LocalDate.now().toString()
        val yesterday = LocalDate.now().minusDays(1).toString()

        // Firestore transaction ensures streak fields update together (accurate streak scores)
        firestore.runTransaction{transaction ->
            val snapshot = transaction.get(streakRef)
            val current = snapshot.getLong("current_streak")?.toInt() ?: 0
            val longest = snapshot.getLong("longest_streak")?.toInt() ?: 0
            val lastCompleted = snapshot.getString("last_completed_date")?: 0

            // Add onto the streaks, if tasks were completed 'today' then keep current streak score else it resets back to 1 day
            val newStreak = when(lastCompleted){
                yesterday -> current +1
                today -> current
                else -> 1
            }
            // Keep track of the total amount of tasks completed
            val newLongest = maxOf(longest,newStreak)
            val oldTotal = snapshot.getLong("total_cleared_tasks")?.toInt()?: 0
            val newTotal = oldTotal + completedTaskCount
            // Save new streak info to firestore
            val updateMap = mapOf("current_streak" to newStreak, "longest_streak" to newLongest, "last_completed_date" to today, "total_cleared_tasks" to newTotal)

            transaction.set(streakRef, updateMap, SetOptions.merge()) //Dont overwrite the document just merge the new streak info
        }.await()
    }

    // For later image support
    // override suspend fun updatePhotoUris(...) { ... }
}
