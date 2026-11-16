
const lessonTitle = document.getElementById("lessonTitle");
const lessonContent = document.getElementById("lessonContent");

async function loadLesson() {
    const params = new URLSearchParams(window.location.search);
    const lessonId = params.get("id");

    if (!lessonId) {
        lessonTitle.textContent = "Lesson not found";
        lessonContent.textContent = "No lesson ID provided.";
        return;
    }

    try {
        const response = await fetch("/lessons");

        if (!response.ok) {
            throw new Error("Could not load lessons");
        }

        const lessons = await response.json();

        const lesson = lessons.find(function (item) {
            return String(item.id) === lessonId;
        });

        if (!lesson) {
            lessonTitle.textContent = "Lesson not found";
            lessonContent.textContent = "This lesson does not exist.";
            return;
        }

        lessonTitle.textContent = lesson.title;
        lessonContent.textContent = lesson.content;

        
const progressResponse = await fetch("/progress/1");

if (progressResponse.ok) {
    const progressList = await progressResponse.json();

    const savedProgress = progressList.find(function (progress) {
        return progress.lessonId === lesson.id && progress.completed;
    });

    if (savedProgress) {
        completeButton.textContent = "Completed ✓";
        completeButton.disabled = true;
        completionMessage.textContent = "Lesson already completed!";
    }
}


    } catch (error) {
        lessonTitle.textContent = "Error";
        lessonContent.textContent = "Could not load lesson. Check the server.";
    }
}

loadLesson();


const completeButton = document.getElementById("completeButton");
const completionMessage = document.getElementById("completionMessage");

completeButton.addEventListener("click", async function () {
    const params = new URLSearchParams(window.location.search);
    const lessonId = Number(params.get("id"));

    // Temporary student ID for testing
    const studentId = Number(localStorage.getItem("studentId"));

    try {
        const response = await fetch("/progress", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                studentId: studentId,
                lessonId: lessonId,
                completed: true
            })
        });

        if (!response.ok) {
            throw new Error("Could not save progress");
        }

        completionMessage.textContent = "Lesson Completed! Progress saved.";
        completeButton.textContent = "Completed ✓";
        completeButton.disabled = true;

    } catch (error) {
        completionMessage.textContent =
            "Could not save progress. Please try again.";
    }
});


