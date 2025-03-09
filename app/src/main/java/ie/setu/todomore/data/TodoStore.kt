package ie.setu.todomore.data

interface TodoStore {

    // Retrieve all To-do tasks
    fun findAll(): List<TodoModel>

    // Retrieve a specific To-do task by id
    fun findById(id:Long) : TodoModel?

    // Add a new To-do task
    fun create(todo: TodoModel)

    // Update an existing To-do task
    fun update(todo: TodoModel)

    // Delete an existing To-do task
    fun delete(todo: TodoModel)
}