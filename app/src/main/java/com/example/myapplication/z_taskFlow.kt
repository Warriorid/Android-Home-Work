package com.example.myapplication

fun main() {

    val taskFlow = TaskFlow()
    while (true) {
        println("Меню:")
        println("1. Создать задачу")
        println("2. Показать все задачи")
        println("3. Показать завершенные задачи")
        println("4. Отметить задачу как завершенную/незавершенную")
        println("5. Показать задачи по описанию")
        println("6. Выход")

        when (readlnOrNull()?.toIntOrNull()) {
            1 -> {
                taskFlow.createdTask()
            }

            2 -> {
                if (taskFlow.taskList.isEmpty()) {
                    println("Список задач пуст")
                } else {
                    taskFlow.taskList.forEach { taskToPrint ->
                        println(taskToPrint.formatToString())
                    }
                }
            }

            3 -> taskFlow.showCompleteTask()
            4 -> taskFlow.markTask()
            5 -> taskFlow.showTasksByDescriptions()
            6 -> return
            else -> println("Не верный выбор")
        }
    }
}


class Task(
    val taskId: Int,
    val title: String,
    val description: String?,
    val priority: Int,
    var isCompleted: Boolean
)

private fun Task.formatToString() = """
        taskId = $taskId,
        title = $title,
        description = $description, 
        priority = $priority,
        isCompleted = $isCompleted
    """.trimIndent()

class TaskFlow {
    private var taskIdCounter = 0
    val taskList by lazy { mutableListOf<Task>() }

    fun createdTask() {
        println("Введите название задачи:")
        val title = readlnOrNull()?.ifEmpty { null }
        require(title != null) { "Название задачи не может быть пустым" }

        println("Введите описание:")
        val description = readlnOrNull()

        println("Введите приоритет:")
        val priority = readlnOrNull()?.toIntOrNull() ?: 0

        val createdTask = Task(
            taskId = taskIdCounter++,
            title = title,
            description = description,
            priority = priority,
            isCompleted = false
        )
        taskList.add(createdTask)
        println(createdTask.formatToString())
    }

    fun markTask() {
        println("Введите номер задачи:")
        val taskNumber = readlnOrNull()?.toIntOrNull() ?: -1
        val taskIndex = taskList.indexOfFirst { it.taskId == taskNumber }

        if (taskIndex == -1) {
            println("Задача не найдена")
            return
        }
        taskList[taskIndex].isCompleted = !taskList[taskIndex].isCompleted
        println("Состояние обновлено")
    }

    fun showCompleteTask() {
        taskList.filter { it.isCompleted }
            .forEach { println(it.formatToString()) }
    }

    fun showTasksByDescriptions() {
        println("Введите описание задачи:")
        val descriptionToFind = readlnOrNull().orEmpty()

        taskList.filter { it.description?.contains(descriptionToFind, ignoreCase = true) ?: false } //Проверка на null + регистронезависимость
            .forEach { println(it.formatToString()) }
    }

}